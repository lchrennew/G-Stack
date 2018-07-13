import {combineReducers} from 'redux'

const index = (state = {}, action) => {
    switch (action.type) {
        case 'FETCH_INDEX':
            return {fetch: true, idx: [], suite: action.suite}
        case 'RECEIVE_INDEX':
            return {fetch: false, idx: action.idx, suite: action.suite}
        default:
            return state
    }
}

export default combineReducers({index})