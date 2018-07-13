// 文件夹项
import React from 'react'
import {Link, withRouter} from "react-router-dom";

class DirItem extends React.Component {

    getLink() {
        let {match: {isExact, params: {dir, suite}, url}, location: {pathname}, name, itemtype} = this.props
        let branch = itemtype === 'document' ? 'clob' : 'tree'
        return dir ?
            isExact ?
                ['', suite, branch, dir, name].join('/') :
                ['', suite, branch, dir, pathname.substr(url.length).replace('/', ''), name].join('/') :
            ['', suite, branch, name].join('/')
    }

    render() {
        // itemtype: folder => /tree/**
        // itemtype: document => /clob/**
        let {name, itemtype} = this.props

        return <tr>
            <th scope="row" className="icon"><i className={`icon ion-ios-${itemtype}`}/></th>
            <td className="content"><Link to={this.getLink()}>{name}</Link></td>
            <td className="message">&nbsp;</td>
            <td className="actions">&nbsp;</td>
        </tr>
    }
}

export default withRouter(DirItem)