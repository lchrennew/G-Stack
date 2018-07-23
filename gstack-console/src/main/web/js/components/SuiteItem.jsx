import React from 'react'
import {Link} from "react-router-dom";
import {Button, Card} from 'semantic-ui-react'
import ExecuteButton from "./ExecuteButton";
import Icon from "./Icon";


class SuiteItem extends React.Component {
    render() {
        let {title, description} = this.props
        return <Card>
            <Card.Content>
                <Card.Header className="card-title">{title}</Card.Header>
                <div className="ui three buttons">
                    <Link to={title} className="ui basic large button">
                        <Icon name="box" size={24}/></Link>
                    <Link to={`${title}/logs`} className="ui basic large button">
                        <Icon name="clock" size={24}/>
                    </Link>
                    <ExecuteButton suite={title} size={24} className="ui basic large button"
                                   title={`Suite:${title}`}/>
                </div>
            </Card.Content>
        </Card>
    }
}

export default SuiteItem