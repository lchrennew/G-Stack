import React from 'react'
import {Link, withRouter} from "react-router-dom";

class ParentDirItem extends React.Component {
    getParentLink() {
        let {location: {pathname}} = this.props
        let segs = pathname.split('/')
        segs.length--
        if (pathname.endsWith('/'))
            segs.length--
        if (segs.length == 3)
            segs.length--
        return segs.join('/')
    }
    render() {
        if (this.props.visible)
            return <tr>
                <th className="icon"/>
                <td>
                    <Link to={this.getParentLink()}>..</Link>
                </td>
                <td/>
                <td/>
            </tr>
        else
            return null
    }
}

export default withRouter(ParentDirItem)