# G-Stack
让你无需写代码就能搞定的自动化验收/接口测试，基于Gauge的全栈测试平台

[![Gauge Badge](https://gauge.org/Gauge_Badge.svg)](https://gauge.org)

## Requirements
* Maven 3+
* Java 1.8+
* Gauge
* IDEA
* Gauge plugins: guage-java, gauge-java, gauge-java-maven, gauge-intellij-idea, gauge-html-report

## DSL reference
### Syntax
G-Stack Syntax is fully compatible with Gauge, reference [here](https://docs.gauge.org/latest/writing-specifications.html) for more detail on Gauge Syntax

### Basic Syntax
* If you want to print messages into both console & reports:<br>
`PRINT "content"`

* If you want to assert an actual value to an expected value:
```markdown
* ASSERT <actual> <match> <expected>
```
build-in matches: `=`, `<>`, `!=`, `contains`

* If you want to assert multiple actual values to their expected values one by one in one step:<br>
```markdown
* ASSERT <table>
```
`<table>` format: 
```markdown
|actual|match|expected|
|------|-----|--------|
```

* If you want execution to be paused for seconds:<br>
`* WAIT <seconds>`

* If you want to share data between steps, scenarios or specs:<br>
``` markdown
* SET:SCENARIO <name> <value>
* SET:SPEC <name> <value>
* SET:SUITE <name> <value>
```
or share multiple values:
```markdown
* SET:SCENARIO <table>
* SET:SPEC <table>
* SET:SUITE <table>
```
`<table>` format: 
```markdown
|name|value|
|----|-----|
|m   |4    |
|n   |5    |
// @{m} => 4
// @{n} => 5
```
or dynamic table
```markdown
|col1|col2|col3|
|----|----|----|
|x   |10  |20  |
// @{col1.x.col2} => 10
// @{col1.x.col3} => 20
```
or dynamic table with explicitly id column
```markdown
|col1#|col2|col3|
|-----|----|----|
|x    |10  |20  |
// @{col1.x.col2} => 10
// @{col1.x.col3} => 20

```




* After set value to their name, you could get the value by our Argument Syntax: 
`@{name}`

#### Arguments
```markdown
* SET:SCENARIO "hello" "world"
* SET:SCENARIO "suffix" "llo"
* PRINT "@{hello}"
* PRINT "I will say: @{hello}"
* PRINT "hello, @{he@{suffix}}!"
```

The output will be:
```text
world
I will say: world
hello, world!
```

#### Dynamic Arguments
There are some arguments whose value can be generated automatically.
Build-in dynamic arguments:

```markdown
@{timestamp}
```

### HTTP Syntax
#### Arrange Request
* If you want to set baseUri:
```markdown
* BASE <baseUri>
```
* If you want to set basePath:
```markdown
* PATH <basePath>
```

* If you want to add query parameters to Url
```markdown
* QUERY <name> <value>
```
or
```markdown
* QUERY <table>
```
`<table>` format:
```markdown
|name|value|
|----|-----|
```
e.g.
```markdown
* QUERY "a" "1"
* QUERY "a" "2"
* QUERY
|name|value|
|----|-----|
|b   |1    |
|c   |2    |
|c   |3    |
```
the final query string will be
```markdown
?a=1&a=2&b=1&c=2&c=3
```

* If you want to add request headers:
```markdown
* HEADER <name> <value>
```
or (`<table>` format is same to `QUERY`)
```markdown
* HEADER <table>
```

* If you want to add cookies:
```markdown
* COOKIE <name> <value>
```
or (`<table>` format is same to `QUERY`)
```markdown
* COOKIE <table>
```

* If you want to set Content-Type
```markdown
* CONTENTTYPE <contentType>
```
or
```markdown
* CONTENTTYPE <contentType> <charSet>
```

* If you want to add form parameters:
```markdown
* BODY:FORM <name> <value>
```
or (`<table>` format is same to `QUERY`)
```markdown
* BODY:FORM <table>
```

* If you want to add text content to body:
```markdown
* BODY:CONTENT <content>
```

* If you want to build a whole new request:
```markdown
* REQUEST:NEW
```

* If you want to build a whole new request from a config file:
```markdown
* HTTP:SETUP <jsonConfig>
```
e.g.
```markdown
* HTTP:SETUP <file:dir/request.config>
```
```json
{
  "method": "GET",
  "baseUri": "https://requestloggerbin.herokuapp.com",
  "basePath": "/",
  "httpVersion": "HTTP/1.1",
  "cookies": {
    "c1": "d4c19a20393919416cc88d29a4dec3cbe1528282256",
    "c2": "null"
  },
  "headers": {
    "accept": "application/json",
    "accept-language": "zh-CN,zh;q=0.9,en;q=0.8"
  },
  "queryString": {
    "a": [
      "1",
      "2"
    ],
    "b": "3"
  },
  "postData": {
    "mimeType": "application/x-www-form-urlencoded",
    "forms": {
      "c": [
        "5",
        "4"
      ],
      "d": "6"
    }
  }
}
```


#### Http Act
* If you want to send GET request:
```markdown
* GET <url>
```

* If you want to send POST request:
```markdown
* POST
```
or 
```markdown
* POST <url>
```


#### Assert Response
* If you want to assert response status code:
```markdown
STATUS <statusCode>
```

* If you want to assert response body content:
```markdown
// entirely match
* CHECK:BODY <content>

// partial match
* CHECK:CONTENT <content>

// not match
* FAIL:BODY <content>

// not contain
* FAIL:CONTENT <content>

```

* If you want to check response as a JSON by GPath (Note that the `<jsonPath>` is a GPath)
```markdown
* CHECK:JSONPATH <jsonPath> <expected>
* FAIL:JSONPATH <jsonPath> <unexpected>

```
or
```markdown
* CHECK:JSONPATH <table>
```
`<table>` format:
```markdown
|path|value|
|----|-----|
```

* If you want to check response as a JSON by JSON Schema Validation:
```markdown
* CHECK:JSONSCHEMA <schema>
```

* If you want to check response headers:
```markdown
// header with expected value
* CHECK:HEADER <name> <value>

// not exist header
* CHECK:HEADER <name> null
```

* If you want to check response cookies:
```markdown
* CHECK:COOKIE <name> <value>
* CHECK:COOKIE <name> null
```

#### Extract response values
If you want to share response data between steps or scenarios in current specification, you could use extraction syntax.

* If you want to extract body content:
```markdown
* EXTRACT:CONTENT <variableName>
```

* If you want to extract value of a GPath
```markdown
* EXTRACT:JSONPATH <path> <variableName>
```

* If you want to extract headers
```markdown
* EXTRACT:HEADER <header> <variableName>
```

* If you want to extract cookies
```markdown
* EXTRACT:HEADER <cookie> <variableName>
```

#### Log requests and response
* If you want to log requests and response into console or report:
```markdown
// before the request is sent
* HTTP:LOG
```


### MySql Syntax
* If you want to execute single sql statement:
```markdown
* SQL:STATEMENT <connection> <sql>
```

* If you want to execute assertion in sql:
```markdown
// <sql> returns 0 for fail and 1 for pass
* SQL:ASSERT <connection> <sql>
```

* If you want to execute batch sql statements:
```markdown
* SQL:SCRIPT <connection> <sql>
```

* If you want to execute insert and share auto-generated id with other steps:
```markdown
* SQL:INSERT <connection> <table>
```
note that the `<table>` is a dynamic table with explicitly id column:
```markdown
|col1#|col2|col3|
|-----|----|----|
|x    |10  |20  |
// @{col1.x.id} => new generated id
// @{col1.x.col2} => 10
// @{col1.x.col3} => 20
```


### Web UI Syntax
* If you want to use Web UI Automation, you must use scope syntax first:
```markdown
* UI:WEB BEGIN
// perform some actions here
* UI:WEB END
```

* If you want to open browser:
```markdown
* OPEN <url>
```

* If you want to define an element with a name:
```markdown
DEF <name> <cssSelector>
```

* If you want to enter text into element:
```markdown
* ENTER <name> <text>
```

* If you want to click on an element:
```markdown
* CLICK <name>
```

* If you want to submit an element:
```markdown
* SUBMIT <name>
```

* If you want to close browser:
```markdown
* QUIT
```


## License

![GNU Public License version 3.0](http://www.gnu.org/graphics/gplv3-127x51.png)
G-Stack is released under [GNU Public License version 3.0](http://www.gnu.org/licenses/gpl-3.0.txt)

## Copyright

Copyright 2018 CHUN.LI
