showSection('home'); // 기본적으로 홈 섹션을 보이게 함

function showSection(sectionId) {
    const sections = document.querySelectorAll('main > section');
    sections.forEach(section => {
        section.style.display = 'none'; // 모든 섹션 숨기기
    });

    document.getElementById(sectionId).style.display = 'block'; // 선택한 섹션 보이기
}
