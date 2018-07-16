import React from "react";
import Main from "./Main";
import VisibleDir from "./VisibleDir";
import IndexProvider from "./IndexProvider";
import {Breadcrumb, Divider, Grid, GridRow} from "semantic-ui-react";
import {Link} from "react-router-dom";
import Placeholder from "./Placeholder";

class Directory extends React.Component {

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
                    segs.map((path, k) => <Placeholder key={k}>
                        <Link to={`/${suite}/tree/${segs.slice(0, k + 1).join('/')}`}
                              className="section">{path}</Link>
                        <Breadcrumb.Divider>/</Breadcrumb.Divider>
                    </Placeholder>)
                }
            </Breadcrumb>
            <Divider hidden />
            <IndexProvider>
                <div className="commit-tease">
                    <div className="mr-auto">test</div>
                </div>
                <div className="file-wrap">
                    <VisibleDir dir={this.getDir()}/>
                </div>
                <Divider hidden/>
            </IndexProvider>

        </Main>
    }
}

export default Directory