# WebUI

## 访问百度
* UI:WEB BEGIN
* OPEN "https://www.baidu.com/"
* DEF "SEARCHBAR" "input#kw"
* ENTER "SEARCHBAR" "abc"
* SUBMIT "SEARCHBAR"
* WAIT "1"
* ASSERT "@{ui.title}" "contains" "abc"
* QUIT
* UI:WEB END