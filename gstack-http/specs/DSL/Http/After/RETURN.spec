# RETURN
Created by lichun on 2018/6/15

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.
* BODY:CONTENT "{\"foo\": \"bar\", \"hello\":{\"x\":1, \"y\":2, \"z\":{\"m\":3, \"n\":4}}, \"list\":[{\"id\":8},{\"id\":9}]}"
* POST "echo"

## JSON PATH
* EXTRACT:JSONPATH "hello.x" "hello.x"
* ASSERT "@{hello.x}" "=" "1"

## CONTENT
* EXTRACT:CONTENT "c1"
* ASSERT "@{c1}" "=" "{\"foo\": \"bar\", \"hello\":{\"x\":1, \"y\":2, \"z\":{\"m\":3, \"n\":4}}, \"list\":[{\"id\":8},{\"id\":9}]}"

## HEADER
* EXTRACT:HEADER "content-type" "c2"
* ASSERT "@{c2}" "=" "application/json; charset=utf-8"

## COOKIE
* EXTRACT:COOKIE "x" "c3"
* ASSERT "@{c3}" "=" ""

//## JSON PATH WITH JOIN
//* EXTRACT:JSONPATH "list.id" JOIN "id" TO
//|json|
//|A|
//|B|
//* ASSERT "@{json.A.id}" "=" "8"
//* ASSERT "@{json.B.id}" "=" "9"
