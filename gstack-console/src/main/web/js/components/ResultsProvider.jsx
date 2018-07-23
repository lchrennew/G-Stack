import React from 'react'
import {fetchResults} from "../actions";
import {connect} from 'react-redux'
import {Dimmer, Loader} from "semantic-ui-react";
import {withRouter} from "react-router-dom";


const mapDispatchToProps = dispatch => {
    return {
        loadResults: suite => dispatch(fetchResults(suite)),
    }
}
const noData = state => state.results.list == null,
    suiteChanged = (state, props) => state.results.suite !== props.match.params.suite
const mapStateToProps = (state, props) => {
    return {
        needShowLoading: noData(state) || suiteChanged(state, props)
    }
}

class ResultsProvider extends React.Component {
    async componentDidMount() {
        const {loadResults, match: {params: {suite}}} = this.props
        await loadResults(suite)
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

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(ResultsProvider))