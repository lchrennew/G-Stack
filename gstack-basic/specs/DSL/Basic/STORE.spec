# STORE
* PRINT "SETUP"
* SET:SUITE "s1" "s1value"
* SET:SPEC "s2" "s2value"
* SET:SUITE
|name|value|
|s1.1|1.1|
|s1.2|1.2|
* SET:SPEC
|name|value|
|s2.1|2.1|
|s2.2|2.2|
* SET:SCENARIO
|name|value|
|s3.1|3.1|
|s3.2|3.2|



## 保存配置并打印&断言
* PRINT "场景步骤"
* SET:SCENARIO "s3" "s3value"
* PRINT "s1=@{s1}"
* PRINT "s2=@{s2}"
* PRINT "s3=@{s3}"
* PRINT "@{s1};@{s2};@{s3};@{s1};@{s2};@{s3};"
* ASSERT "@{s1};@{s2};@{s3};@{s1};@{s2};@{s3};" "=" "s1value;s2value;s3value;s1value;s2value;s3value;"
* ASSERT
|actual|match|expected|
|@{s1.1}|=|1.1|
|@{s1.2}|=|1.2|
|@{s2.1}|=|2.1|
|@{s2.2}|=|2.2|
|@{s3.1}|=|3.1|
|@{s3.2}|=|3.2|

## 动态表
* SET:SCENARIO
|var|field1|field2|
|x|10|11|
|y|20|22|
* ASSERT
|actual|match|expected|
|@{var.x.field1}|=|10|

## 动态表(显式key)
* SET:SCENARIO
|key#|value|
|m|1|
|n|2|
* ASSERT
|actual|match|expected|
|@{key.m.value}|=|1|
|@{key.n.value}|=|2|

## 间接寻址
*PRINT "@{s@{s2.1}}"
*ASSERT "@{s@{s2.1}}" "=" "2.1"
## 动态参数
* PRINT "Timestamp is @{timestamp}"


___
* PRINT "TEARDOWN"
