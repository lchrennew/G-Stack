Specification Heading
=====================
Created by lichun on 2018/6/19

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.
     
Scenario Heading
----------------
* ASSERT "1" "=" "1"
* PRINT "ts:@{timestamp}"
* BODY:FORM "test" "form"
* HTTP:LOG
* POST "http://requestloggerbin.herokuapp.com/request"
* EXTRACT:CONTENT "body"
* PRINT "@{body}"
