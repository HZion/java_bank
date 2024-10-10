#!/bin/bash
# Install any dependencies or clean previous installations

# 예: 시스템 패키지 업데이트
sudo yum update -y

# 예: 기존 애플리케이션 파일 제거
sudo rm -rf /opt/myapp/*

sudo chmod 777 /opt/myapp/scripts/*.sh
