import React from "react";
import Main from "./Main";

class Directory extends React.Component {

    getDir() {
        let {match: {isExact, params: {dir}, url}, location: {pathname}} = this.props
        return isExact ?
            dir :
            [dir, pathname.substr(url.length).replace('/', '')].join('/')
    }

    render() {
        let {match: {params: {suite}}} = this.props
        return <Main>
            <h1 className="mt-5">{suite}/{this.getDir()}</h1>
        </Main>
    }
}

export default Directory