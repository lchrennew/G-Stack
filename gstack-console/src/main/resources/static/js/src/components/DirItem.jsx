// 文件夹项
import React from 'react'
import {Link, withRouter} from "react-router-dom";
import Icon from "./Icon";
import ExecuteButton from "./ExecuteButton";

class DirItem extends React.Component {
    buildLink(prefix) {
        let {match: {isExact, params: {dir}, url}, location: {pathname}, name} = this.props

        let body = dir ?
            isExact ?
                [dir, name] :
                [dir, pathname.substr(url.length).replace('/', ''), name] :
            [name]
        prefix.push(...body)
        return prefix.join('/')
    }

    getLink() {
        let {match: {params: {suite}}, itemtype} = this.props
        let branch = itemtype === 'file' ? 'clob' : 'tree'
        return this.buildLink(['', suite, branch])
    }

    render() {
        // itemtype: folder => /tree/**
        // itemtype: file => /clob/**
        let {name, itemtype, match: {params: {suite}}} = this.props
        let link = this.getLink()
        return <tr>
            <th scope="row" className="icon"><Icon name={itemtype}/></th>
            <td className="content"><Link to={link}>{name}</Link></td>
            <td className="message">&nbsp;</td>
            <td className="actions">
                <ExecuteButton suite={suite} path={this.buildLink(['.'])} title={`${itemtype}:${name}`}/>
                <a href="#" className="link"><Icon name="clock"/></a>
            </td>
        </tr>
    }
}

export default withRouter(DirItem)