import React from 'react'
import {Link} from "react-router-dom";
import {Card} from 'semantic-ui-react'
import ExecuteButton from "./ExecuteButton";
import Icon from "./Icon";


class SuiteItem extends React.Component {
    render() {
        let {title, description} = this.props
        return <Card>
            <Card.Content>
                <Card.Header className="card-title">{title}</Card.Header>
                <Card.Description>{description}</Card.Description>

            </Card.Content>
            <Card.Content extra>
                <Link to={title} className="ui basic large button">
                    <Icon name="box" size={16}/> 进入</Link>
                <ExecuteButton suite={title} path="." className="ui basic large button blue" title={`Suite:${title}`}/>
            </Card.Content>
        </Card>
    }
}

export default SuiteItem