import React from "react";
import Main from "./Main";
import IndexProvider from "./IndexProvider";
import ScenarioList from "./ScenarioList";
import {SpecTitle} from "./SpecProvider";
import {Link} from "react-router-dom";
import {ParentLink} from "./ParentDirItem";
import {Breadcrumb, Divider} from "semantic-ui-react";
import Placeholder from "./Placeholder";


class File extends React.Component {

    getDir() {
        let {match: {isExact, params: {dir}, url}, location: {pathname}} = this.props
        return isExact ?
            dir :
            [dir, pathname.substr(url.length).replace('/', '')].join('/')
    }

    render() {
        let {match: {params: {suite}}} = this.props
        let segs = this.getDir().split('/')
        return <Main>
            <Breadcrumb size="huge">
                <Link to={`/${suite}`} className="section"><b>{suite}</b></Link>
                <Breadcrumb.Divider>/</Breadcrumb.Divider>
                {
                    segs.map((path, k) => k + 1 < segs.length ? <Placeholder key={k}>
                        <Link
                            to={`/${suite}/tree/${segs.slice(0, k + 1).join('/')}`}
                            className="section">{path}</Link>
                        <Breadcrumb.Divider>/</Breadcrumb.Divider>
                    </Placeholder> : <Breadcrumb.Section key={k}>{path}</Breadcrumb.Section>)
                }
            </Breadcrumb>
            <Divider hidden/>
            <IndexProvider>
                <div className="commit-tease">
                    <div className="mr-auto">
                        <SpecTitle/>
                    </div>
                </div>
                <div className="file-wrap">
                    <ScenarioList/>
                </div>
                <Divider hidden/>
            </IndexProvider>
        </Main>
    }
}

export default File