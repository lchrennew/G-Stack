import React from 'react'
import {Card, Header} from 'semantic-ui-react'
import Icon from './Icon'

class CreateSuiteButton extends React.Component {
    render() {
        let {onClick} = this.props
        return <Card.Content
            textAlign='center'
            as="a"
            onClick={onClick}>
            <div>
                <Icon name="plus-circle" size={32}/>
                <Card.Meta>Create new test suite</Card.Meta>
            </div>
        </Card.Content>
    }
}

export default CreateSuiteButton