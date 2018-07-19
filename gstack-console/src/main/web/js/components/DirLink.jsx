import React from 'react'
import {Link} from 'react-router-dom'

class DirLink extends React.Component {
    render() {
        let {dir = '', to} = this.props
        if (dir.endsWith('/'))
            dir.length--
        else if (dir.startsWith('/'))
            dir = dir.substr(1)
        let s = dir.lastIndexOf('/')

        if (s >= 0) {
            let path = dir.substr(0, s + 1)
            let name = dir.substr(s + 1)
            return <Link to={to}><span className="text-muted">{path}</span>{name}</Link>
        }
        else return <Link to={to}>{dir}</Link>
    }
}

export default DirLink