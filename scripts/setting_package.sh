#!/bin/bash

# GitHub Packages 설정을 위한 settings.xml 파일 생성
echo "<settings>
  <servers>
    <server>
      <id>github</id>
      <username>${GITHUB_ACTOR}</username>
      <password>${GITHUB_TOKEN}</password>
    </server>
  </servers>
</settings>" > $GITHUB_WORKSPACE/settings.xml
