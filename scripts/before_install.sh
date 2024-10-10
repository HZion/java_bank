#!/bin/bash
# 기존 appspec.yml 파일 삭제
if [ -f /opt/myapp/appspec.yml ]; then
  rm -f /opt/myapp/appspec.yml
fi
