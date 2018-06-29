# MySql

## 执行SQL&检查SQL
* SQL:STATEMENT "gapi" "CREATE TABLE IF NOT EXISTS `demo` (`id` int(11) NOT NULL AUTO_INCREMENT, `value` varchar(45) DEFAULT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;"
* SQL:STATEMENT "gapi" "TRUNCATE TABLE gapi.demo;"
* SQL:ASSERT "gapi" "SELECT case COUNT(id) when 0 then 1 else 0 end reuslt FROM gapi.demo"
* SQL:SCRIPT "gapi" "SELECT 1;SELECT 2;INSERT INTO `demo` (`value`) VALUES ('test');INSERT INTO demo (value) VALUES ('test1');"
* SQL:ASSERT "gapi" "SELECT case COUNT(id) when 0 then 0 else 1 end result FROM `gapi`.`demo` WHERE `value`='test';"
* SQL:STATEMENT "gapi" "DROP TABLE `demo`"
