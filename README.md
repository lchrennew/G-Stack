# G-Stack
让你无需写代码就能搞定的自动化验收/接口测试，基于Gauge的全栈测试平台

[![Gauge Badge](https://gauge.org/Gauge_Badge.svg)](https://gauge.org)

## Requirements
* Maven 3+
* Java 1.8+
* Gauge
* IDEA
* Gauge plugins: guage-java, gauge-java, gauge-java-maven, gauge-intellij-idea, gauge-html-report

## DSL reference (WIP)
### Syntax
G-Stack Syntax is fully compatible with Gauge, reference [here](https://docs.gauge.org/latest/writing-specifications.html) for more detail on Gauge Syntax

### Basic Syntax
* If you want to print messages into both console & reports:<br>
`PRINT "content"`

* If you want to assert an actual value to an expected value:
<br>`ASSERT <actual> <match> <expected>`<br>
build-in matches: `=`, `<>`, `!=`, `contains`

* If you want to assert multiple actual values to their expected values one by one in one step:<br>
`ASSERT <table>`<br>
table format: 
```markdown
|actual|match|expected|
|------|-----|--------|
```

* If you want execution to be paused for seconds:<br>
`WAIT <seconds>`

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
table format: 
```markdown
|name|value|
|----|-----|
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
```
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

#### Http Act

#### Assert Response

#### Extract response values


### MySql Syntax


### Web UI Syntax




## License

![GNU Public License version 3.0](http://www.gnu.org/graphics/gplv3-127x51.png)
G-Stack is released under [GNU Public License version 3.0](http://www.gnu.org/licenses/gpl-3.0.txt)

## Copyright

Copyright 2018 CHUN.LI
