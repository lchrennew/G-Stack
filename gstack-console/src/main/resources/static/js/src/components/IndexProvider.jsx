import React from 'react'
import {fetchIndex} from "../actions";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";

const mapDispatchToProps = dispatch => {
    return {
        loadIndex: (suite) => dispatch(fetchIndex(suite)),
    }
}

const mapStateToProps = (state, props) => {
    return {
        needShowLoading: state.index.idx.length === 0
    }
}


class IndexProvider extends React.Component {
    async componentDidMount() {
        const {loadIndex, match: {params: {suite}}} = this.props
        await loadIndex(suite)
    }

    render() {
        if (this.props.needShowLoading) {
            return <div>Loading...</div>
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