# 数据库插入

## 插入多行
* SQL:STATEMENT "gapi" "CREATE TABLE IF NOT EXISTS `demo` (`id` int(11) NOT NULL AUTO_INCREMENT, `value` varchar(45) DEFAULT NULL, `x` INT(11),  PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;"
* SQL:STATEMENT "gapi" "TRUNCATE TABLE gapi.demo;"
* SQL:ASSERT "gapi" "SELECT case COUNT(id) when 0 then 1 else 0 end reuslt FROM gapi.demo"

* SQL:INSERT "gapi" "demo"
|demo#|value|x|
|A|demo1|3|
|B|demo2|4|

* SQL:ASSERT "gapi" "SELECT case COUNT(id) when 2 then 1 else 0 end result FROM `gapi`.`demo`;"
* SQL:STATEMENT "gapi" "DROP TABLE `demo`"
* ASSERT "@{demo.A.id}" "=" "1"
* ASSERT "@{demo.B.id}" "=" "2"
* ASSERT "@{demo.A.value}" "=" "demo1"
