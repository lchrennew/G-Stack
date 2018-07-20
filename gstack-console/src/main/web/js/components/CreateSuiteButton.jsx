import React from 'react'
import {Card, Header} from 'semantic-ui-react'
import Icon from './Icon'

class CreateSuiteButton extends React.Component {
    render() {
        return <Card>
            <Card.Content textAlign='center' as="a">
                <Icon name="plus" size={64}/>
            </Card.Content>
        </Card>
    }
}

export default CreateSuiteButton