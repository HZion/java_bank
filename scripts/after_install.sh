# 애플리케이션 설치 후 설정 파일 등을 복사하거나 필요한 후속 작업

#!/bin/bash
# Post-installation tasks

# 예: 필요한 설정 파일 복사
sudo cp /opt/myapp/config/default.conf /etc/myapp/

# 예: 파일 권한 설정 (옵션)
sudo chmod +x /opt/myapp/bank-0.0.1-SNAPSHOT.jar