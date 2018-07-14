import React from 'react'
import Icon from "./Icon";
import {connect} from 'react-redux'
import {withRouter} from "react-router-dom";
import {executeScenario} from "../actions";

const mapDispatchToProps = dispatch => {
    return {
        executeScenario: (suite, path) => dispatch(executeScenario(suite, path)),
    }
}

const mapStateToProps = (state, props) => ({})

class ScenarioItem extends React.Component {

    execute(e) {
        e.preventDefault();
        let path = this.buildLink(['.']),
            {match: {params: {suite}}, executeScenario} = this.props
        executeScenario(suite, path)
    }

    buildLink(prefix) {
        let {match: {isExact, params: {dir}, url}, location: {pathname}, lineNumber} = this.props

        let body = dir ?
            isExact ?
                [dir, name] :
                [dir, pathname.substr(url.length).replace('/', '')] :
            []
        prefix.push(...body)
        return [prefix.join('/'), lineNumber].join(':')
    }

    render() {
        let {title, tags, lineNumber} = this.props
        return <tr>
            <th scope="row" className="icon"><Icon name="activity"/></th>
            <td className="content">{title}</td>
            <td className="message">{tags.length > 0 ? <span><Icon name="tag"/>{tags.join(', ')}</span> : null}</td>
            <td className="actions">
                <a href="#" className="link" onClick={this.execute.bind(this)}><Icon name="play"/></a>
                <a href="#" className="link"><Icon name="clock"/></a>
            </td>
        </tr>
    }
}

export default connect(
    mapStateToProps,
    mapDispatchToProps)(
    withRouter(ScenarioItem))