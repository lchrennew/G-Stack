import React from 'react'
import {Link} from "react-router-dom";
import { Card, Icon, Image } from 'semantic-ui-react'


class SuiteItem extends React.Component {
    render() {
        let {title, description, dir} = this.props
        return <Card>
            <Card.Content>
                <Card.Header className="card-title">{title}</Card.Header>
                <Card.Description>{description}</Card.Description>

            </Card.Content>
            <Card.Content extra>
                <Link to={dir}>进入</Link>
            </Card.Content>
        </Card>
    }
}

export default SuiteItem