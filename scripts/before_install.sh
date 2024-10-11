#!/bin/bash
yum update -y

rm -rf /opt/myapp/*

chmod 777 /opt/myapp/scripts/*.sh
