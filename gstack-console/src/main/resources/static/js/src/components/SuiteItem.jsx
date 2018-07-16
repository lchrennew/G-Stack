import React from 'react'
import {Link} from "react-router-dom";


class SuiteItem extends React.Component {
    render() {
        let {title, description, dir} = this.props
        return <div className="card">
            <div className="card-body">
                <h5 className="card-title">{title}</h5>
                <p className="card-text">{description}</p>
                <Link to={dir}>进入</Link>
            </div>
        </div>
    }
}

export default SuiteItem