import React from 'react'
import {Link} from "react-router-dom";

class ParentDirItem extends React.Component {
    render() {
        if (this.props.visible)
            return <tr>
                <th className="icon"/>
                <td>
                    <Link to={`../`}>..</Link>
                </td>
                <td/>
                <td/>
            </tr>
        else
            return null
    }
}

export default ParentDirItem