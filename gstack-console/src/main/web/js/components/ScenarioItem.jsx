import React from 'react'
import Icon from "./Icon";
import {withRouter} from "react-router-dom";
// import {executeScenario} from "../actions";
import ExecuteButton from "./ExecuteButton";
import Tags from "./Tags";
import {Table} from 'semantic-ui-react'
//
// const mapDispatchToProps = dispatch => {
//     return {
//         executeScenario: (suite, path) => dispatch(executeScenario(suite, path)),
//     }
// }
//
// const mapStateToProps = (state, props) => ({})

class ScenarioItem extends React.Component {

    async execute(e) {
        e.preventDefault();
        let path = this.buildLink(['.']),
            {match: {params: {suite}}, executeScenario} = this.props
        await executeScenario(suite, path)
    }

    buildLink(prefix) {
        let {match: {isExact, params: {dir}, url}, location: {pathname}, lineNumber} = this.props

        let body = dir ?
            isExact ?
                [dir] :
                [dir, pathname.substr(url.length).replace('/', '')] :
            []
        prefix.push(...body)
        return [prefix.join('/'), lineNumber].join(':')
    }
    render() {
        let {title, tags, match: {params: {suite}}} = this.props
        return <Table.Row>
            <Table.Cell scope="row" className="icon"><Icon name="activity"/></Table.Cell>
            <Table.Cell className="content">{title}</Table.Cell>
            <Table.Cell className="message"><Tags tags={tags}/></Table.Cell>
            <Table.Cell className="actions">
                <ExecuteButton suite={suite} path={this.buildLink(['.'])} title={`场景:${title}`}/>
                <a href="#" className="link"><Icon name="clock"/></a>
            </Table.Cell>
        </Table.Row>
    }
}

export default withRouter(ScenarioItem)