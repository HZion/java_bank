#!/bin/bash
# Start the application

# 예: 애플리케이션 실행 (백그라운드에서 실행)
cd /opt/myapp
nohup java -jar bank-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &

sudo chmod 777 /opt/myapp/scripts/*.sh
