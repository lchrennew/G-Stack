import {connect} from 'react-redux'
import Results from "./Results";

const mapStateToProps = (state, props) => ({
    results: state.results.list
})

const mapDispatchToProps = dispatch => ({})

export default connect(mapStateToProps, mapDispatchToProps)(Results)