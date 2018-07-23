import React from 'react'
import {connect} from 'react-redux'
import {fetchSuites} from "../actions";
import {Dimmer, Loader} from "semantic-ui-react";


const mapDispatchToProps = dispatch => {
    return {
        loadSuites: () => dispatch(fetchSuites()),
    }
}

const mapStateToProps = (state, props) => {
    return {
        needShowLoading: state.suites.list == null
    }
}

class SuitesProvider extends React.Component {
    async componentDidMount() {
        const {loadSuites} = this.props
        await loadSuites()
    }

    render() {
        if (this.props.needShowLoading) {
            return <Dimmer active>
                <Loader size='massive'>Loading</Loader>
            </Dimmer>
        }
        else {
            return this.props.children
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(SuitesProvider)