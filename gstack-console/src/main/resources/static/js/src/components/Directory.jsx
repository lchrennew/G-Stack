import React from "react";
import Main from "./Main";
import VisibleDir from "./VisibleDir";
import IndexProvider from "./IndexProvider";

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
            <div className="row">
                <div className="col">
                    <IndexProvider>
                        <div className="commit-tease">
                            <div className="mr-auto">test</div>
                        </div>
                        <div className="file-wrap">
                            <VisibleDir dir={this.getDir()}/>
                        </div>
                    </IndexProvider>
                </div>
            </div>
        </Main>
    }
}

export default Directory