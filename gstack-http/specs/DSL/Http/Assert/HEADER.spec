# 响应头
Created by lichun on 2018/6/6

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.

## 检查HEADER头
* HEADER "name" "value"
* COOKIE "name" "value"
* BODY:CONTENT "{\"foo\": \"bar\", \"hello\":{\"x\":1, \"y\":2, \"z\":{\"m\":3, \"n\":4}}}"
* POST "echo"
* STATUS "200"
* CHECK:HEADER "name" null
* CHECK:COOKIE "name" null
* CHECK:CONTENTTYPE "application/json; charset=utf-8"