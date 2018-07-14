import React from "react";
import Main from "./Main";
import IndexProvider from "./IndexProvider";
import ScenarioList from "./ScenarioList";
import {SpecTitle} from "./SpecProvider";
import {Link} from "react-router-dom";
import {ParentLink} from "./ParentDirItem";
import Icon from "./Icon";


class File extends React.Component {

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
                            <div className="mr-auto">
                                <SpecTitle/>
                            </div>
                        </div>
                        <div className="file-wrap">
                            <ScenarioList/>
                        </div>
                    </IndexProvider>
                </div>
            </div>
        </Main>
    }
}

export default File