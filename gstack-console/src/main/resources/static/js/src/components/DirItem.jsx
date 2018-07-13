// 文件夹项
import React from 'react'
import {Link, withRouter} from "react-router-dom";

class DirItem extends React.Component {
    buildLink(prefix) {
        let {match: {isExact, params: {dir}, url}, location: {pathname}, name, itemtype} = this.props

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
        let branch = itemtype === 'document' ? 'clob' : 'tree'
        return this.buildLink(['', suite, branch])
    }

    execute(e) {
        e.preventDefault();
        let path = this.buildLink(['.'])
        alert(path)

        let {match: {params: {suite}}} = this.props
        alert(suite)
    }

    render() {
        // itemtype: folder => /tree/**
        // itemtype: document => /clob/**
        let {name, itemtype} = this.props
        let link = this.getLink()
        return <tr>
            <th scope="row" className="icon"><i className={`icon ion-ios-${itemtype}`}/></th>
            <td className="content"><Link to={link}>{name}</Link></td>
            <td className="message">&nbsp;</td>
            <td className="actions">
                <a href="#" className="link" onClick={this.execute.bind(this)}><i className="icon ion-md-play"></i></a>
                <a href="#" className="link"><i className="icon ion-md-list"></i></a>
            </td>
        </tr>
    }
}

export default withRouter(DirItem)