import React from 'react'
import {Card} from "semantic-ui-react";
import Icon from "./Icon";

class ResultItem extends React.Component {
    render() {
        let {shell, succeeded} = this.props
        return <Card fluid color={succeeded ? 'greed' : 'red'}>
            <Card.Content>

                <Card.Meta><Icon name="terminal"/> {shell}</Card.Meta>
                <div content="right floated">
                    <a href="#"><Icon name="pie-chart"/></a>
                    <a href="#"><Icon name="camera"/></a>
                </div>
            </Card.Content>
        </Card>
    }
}

export default ResultItem