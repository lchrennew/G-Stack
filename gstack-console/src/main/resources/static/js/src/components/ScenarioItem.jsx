import React from 'react'
import Icon from "./Icon";
import {withRouter} from "react-router-dom";


class ScenarioItem extends React.Component {
    execute(e) {
        e.preventDefault();
        let path = this.buildLink(['.'])
        alert(path)

        let {match: {params: {suite}}} = this.props
        alert(suite)
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
            <th scope="row" className="icon">&nbsp;</th>
            <td className="content">{title}</td>
            <td className="message">&nbsp;</td>
            <td className="actions">
                <a href="#" className="link" onClick={this.execute.bind(this)}><Icon name="play"/></a>
                <a href="#" className="link"><Icon name="clock"/></a>
            </td>
        </tr>
    }
}

export default withRouter(ScenarioItem)