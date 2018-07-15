import React from 'react'
import Icon from "./Icon";

class Tags extends React.Component {
    render() {
        let {tags} = this.props
        if (tags.length)
            return <span><Icon name="tag"/>{tags.join(', ')}</span>
        else return null
    }
}

export default Tags