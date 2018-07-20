import React from 'react'
import {fetchIndex} from "../actions";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import {Dimmer, Loader} from "semantic-ui-react";

const mapDispatchToProps = dispatch => {
    return {
        loadIndex: (suite) => dispatch(fetchIndex(suite)),
    }
}

const mapStateToProps = (state, props) => {
    return {
        needShowLoading: state.index.idx == null
    }
}


class IndexProvider extends React.Component {
    async componentDidMount() {
        const {loadIndex, match: {params: {suite}}} = this.props
        await loadIndex(suite)
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

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(withRouter(IndexProvider))