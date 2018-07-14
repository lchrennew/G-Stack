// 目录列表

import React from 'react'
import DirItem from "./DirItem";
import {ParentDirItem} from "./ParentDirItem";


class Dir extends React.Component{
    render(){
        let {items, dir} = this.props
        let hasParent = dir !== '' && dir !== '/'
        return <table className="table table-sm table-hover files">
            <tbody>
            <ParentDirItem visible={hasParent}/>
            {
                items.map(({name, itemtype}, i) => (<DirItem {...{name, itemtype}} key={i}/>))
            }
            </tbody>
        </table>
    }

}

export default Dir