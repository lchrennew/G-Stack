import React from 'react'
import CreateSuiteForm from "./CreateSuiteForm";
import Icon from "./Icon";
import {Card} from "semantic-ui-react";
import CreateSuiteButton from "./CreateSuiteButton";

class CreateSuiteCard extends React.Component {
    constructor(props) {
        super(props)
        this.state = {creating: false}
    }

    toggle(e) {
        e && e.preventDefault()
        this.setState({creating: !this.state.creating})
    }

    create({name}) {
        alert(name)
        this.toggle()
    }

    render() {
        return <Card className={this.state.creating ? '' : 'create'}>
            {
                this.state.creating ?
                    <CreateSuiteForm onCancel={this.toggle.bind(this)}/> :
                    <CreateSuiteButton onClick={this.toggle.bind(this)}/>
            }

        </Card>
    }

}

export default CreateSuiteCard