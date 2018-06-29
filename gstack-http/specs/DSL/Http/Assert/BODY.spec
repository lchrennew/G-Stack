# 响应内容检查
Created by lichun on 2018/6/6

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.
     
## 整体匹配
* BODY:FORM
    |name|value|
    |p1|hello|
    |p2|world|
* POST "echo"
* STATUS "200"
* CHECK:BODY "p1=hello&p2=world"
* FAIL:BODY "ppp"

## 内容匹配
* BODY:FORM
    |name|value|
    |p1|hello|
    |p2|world|
* POST "echo"
* STATUS "200"
* FAIL:CONTENT "regex"
* CHECK:CONTENT "hello"

## JSON PATH
* BODY:CONTENT "{\"foo\": \"bar\", \"hello\":{\"x\":1, \"y\":2, \"z\":{\"m\":3, \"n\":4}}}"
* POST "echo"
* STATUS "200"
* CHECK:JSONPATH "foo" "bar"
* FAIL:JSONPATH "foo" "bar1"
* CHECK:JSONPATH "hello.x" "1"
* CHECK:JSONPATH
|path|value|
|hello1|null|
|hello.y|2|

## JSON SCHEMA
* BODY:CONTENT "\"This is a string\""
* POST "echo"
* CHECK:JSONSCHEMA "{\"type\":\"string\"}"