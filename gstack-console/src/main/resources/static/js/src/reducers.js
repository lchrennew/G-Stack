import {combineReducers} from 'redux'

const index = (state = {idx: []}, action) => {
    switch (action.type) {
        case 'FETCH_INDEX':
            return {fetch: true, idx: state.idx, suite: action.suite}
        case 'RECEIVE_INDEX':
            return {fetch: false, idx: action.idx, suite: action.suite}
        default:
            return state
    }
}

export default combineReducers({index})