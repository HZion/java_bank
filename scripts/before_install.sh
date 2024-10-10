# 애플리케이션 설치 전 환경을 정리하거나 준비하는 작업

#!/bin/bash
# Install any dependencies or clean previous installations

# 예: 시스템 패키지 업데이트
sudo yum update -y

# 예: 기존 애플리케이션 파일 제거
sudo rm -rf /opt/myapp/*