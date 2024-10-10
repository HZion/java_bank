#!/bin/bash
# Post-installation tasks

# 예: 필요한 설정 파일 복사
sudo cp /opt/myapp/config/default.conf /etc/myapp/

# 예: 파일 권한 설정 (옵션)
sudo chmod 777 /opt/myapp/bank-0.0.1-SNAPSHOT.jar

sudo chmod 777 /opt/myapp/scripts/*.sh
