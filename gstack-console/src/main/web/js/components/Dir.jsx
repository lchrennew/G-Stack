// 目录列表

import React from 'react'
import DirItem from "./DirItem";
import {ParentDirItem} from "./ParentDirItem";
import {Table} from 'semantic-ui-react'


class Dir extends React.Component{
    render(){
        let {items, dir} = this.props
        let hasParent = dir !== '' && dir !== '/'
        return <Table size="small" basic="very" className="files" selectable>
            <Table.Body>
                <ParentDirItem visible={hasParent}/>
                {
                    items.map(({name, itemtype}, i) => (<DirItem {...{name, itemtype}} key={i}/>))
                }
            </Table.Body>
        </Table>
    }

}

export default Dir