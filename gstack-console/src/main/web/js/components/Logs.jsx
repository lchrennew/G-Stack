import React from 'react'
import Main from "./Main";
import {Breadcrumb, Divider} from "semantic-ui-react";
import ResultsProvider from "./ResultsProvider";
import {Link} from "react-router-dom";
import {withRouter} from 'react-router-dom'
import VisibleResults from "./VisibleResults";

class Logs extends React.Component {

    render() {
        const {match: {params: {suite}}} = this.props
        return <Main>
            <Breadcrumb size="huge">
                <Link to={`/${suite}`} className="section"><b>{suite}</b></Link>
                <Breadcrumb.Divider>/</Breadcrumb.Divider>
                <Breadcrumb.Section>Logs</Breadcrumb.Section>
            </Breadcrumb>
            <Divider hidden/>
            <ResultsProvider>
                <VisibleResults/>
                <Divider hidden/>
            </ResultsProvider>

        </Main>
    }
}

export default withRouter(Logs)