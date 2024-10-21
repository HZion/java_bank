package com.sion.bank.controller;

import com.sion.bank.model.*;
import com.sion.bank.service.AccountService;
import com.sion.bank.service.TransactionService;
import com.sion.bank.service.UserService;

import com.sion.bank.service.redisService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;


    @Autowired
    private redisService redis;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @PostMapping("/account")
    public String createAccount(
            @RequestParam("accountName") String accountName,
            @RequestParam("bankName") String bankName,
            @RequestParam("accountType") AccountType accountType,
            @RequestParam(value = "initialBalance", defaultValue = "0.00") BigDecimal initialBalance,
            Model model,
            HttpSession session
            ) {


        try{
            accountService.createAccount(
                    accountName,
                    bankName,
                    accountType,
                    initialBalance,
                    session.getId());

            return "redirect:/home";  // 계좌 생성 후 리다이렉트
        }catch ( Exception e ) {
            model.addAttribute("error", e.getMessage());
            return "/all-functions";  // 회원가입 페이지로 다시 이동
        }

    }

    @PostMapping("/detailAccount")
    public String showAccountDetail(@RequestParam("id") Long id, Model model) {
        Account account = accountService.getAccountByID(id);
        List<Transaction> transactions = transactionService.findAllRelevantTransactionsByAccountId(id);

        model.addAttribute("account", account);
        model.addAttribute("transactions", transactions);

        return "detailAccount";
    }

    @PostMapping("/checkAccount")
    public String checkAccount(@RequestParam("id") Long id, Model model) {


            model.addAttribute("accountId", id);

            return "checkAccount";

    }
    @PostMapping("/checkAccount-result")
    public String checkAccountResult(@RequestParam("myAccount") Long id,
                                     @RequestParam("accountNumber") String accountNumber,
                                     @RequestParam("bank") String bank,
                                     Model model) {
        //
        try {
            //내꺼계좌 id, 계좌번호, 은행

            Account recvAcount = accountService.getAccountByNumber(accountNumber);
            Account myAccount = accountService.getAccountByID(id);
            User recviveUser = userService.getUseById(id);

            model.addAttribute("accountId", id);
            model.addAttribute("myAccount", myAccount);
            model.addAttribute("recvAccount", recvAcount);
            model.addAttribute("recvUser", recviveUser);

            return  "transaction";
        } catch (RuntimeException e) {

            model.addAttribute("accountId", id);
            model.addAttribute("error", "계좌를 찾을수 없어요");
            return "checkAccount";
        }
    }

    @PostMapping("/resultTransaction") //실제 이체 과정
    public String resultTransaction(@RequestParam("myAccount") Long myAccountId,
                                    @RequestParam("recvAccount") Long recvAccountId,
                                    @RequestParam("amount") BigDecimal amount,
                                    Model model) {


        Account recvAccount = accountService.getAccountByID(recvAccountId);

        transactionService.createTransaction(myAccountId,recvAccountId, amount, TransactionType.TRANSFER);


        model.addAttribute("recvAccount",recvAccount);
        model.addAttribute("amount",amount);
        return "resultTransaction";
    }

    @PostMapping("/allAccount")
    public String allAccount(HttpSession session, Model model){
        String sessionId = session.getId();

        // redis를 세션으로 이용한경우
        int userId = (int) redisTemplate.opsForHash().get(sessionId, "userId");

        List<Account> accounts = redis.getRedisAccountsByUser(sessionId, String.valueOf(userId));

        BigDecimal totalBalance = accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("accounts", accounts);
        model.addAttribute("totalBalance", totalBalance);
        return "allAccount";
    }

}