# POST 请求
Created by lichun on 2018/6/6

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.
     
## POST字符串
* BODY:CONTENT "{\"foo\": \"bar\"}"
* POST "echo"
* STATUS "200"
* CHECK:BODY "{\"foo\": \"bar\"}"

## POST表单
* BODY:FORM
    |name    |value   |
    |p1 |hello|
    |p2|world|
* POST "echo"
* STATUS "200"
* CHECK:BODY "p1=hello&p2=world"

## POST HAR
* REQUEST:NEW
* BODY:FORM "txt" "测试中文"
* PATH "/"
* POST "request"
* EXTRACT:CONTENT "har1"
* PRINT "@{har1}"