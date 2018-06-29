# 构造请求

## 构造Forms Request
* HTTP:SETUP <file:specs/DSL/Http/Arrange/config.json>
* POST "request"
* EXTRACT:CONTENT "c4"

## 构造Json Request
* HTTP:SETUP <file:specs/DSL/Http/Arrange/config1.json>
* POST "request"
* EXTRACT:CONTENT "c5"
* PRINT "@{c5}"

## 构造Text Request
* HTTP:SETUP <file:specs/DSL/Http/Arrange/config2.json>
* POST "request"
* EXTRACT:CONTENT "c5"
* PRINT "@{c5}"

## 新需求
* BODY:FORM "a" "1"
* REQUEST:NEW
* POST "request"

