import {combineReducers} from 'redux'

/*
1. get suites
2. get index by suite
*/

const index = (state = {idx: null}, action) => {
    switch (action.type) {
        case 'FETCH_INDEX':
            return {fetch: true, idx: state.idx, suite: action.suite}
        case 'RECEIVE_INDEX':
            return {fetch: false, idx: action.idx, suite: action.suite}
        default:
            return state
    }
}

const suites = (state = {list: null}, action) => {
    switch (action.type) {
        case 'FETCH_SUITES':
            return {fetch: true}
        case 'RECEIVE_SUITES':
            return {fetch: false, list: action.list}
        case 'CREATING_SUITE':
            return {fetch:false, list:[...state.list, action.suite]}
        case 'CREATED_SUITE':
            return state
        default:
            return state
    }
}

const results = (state = {list: null}, action) => {
    switch (action.type) {
        case 'FETCH_RESULTS':
            return {fetch: true, list: state.list, suite: action.suite}
        case 'RECEIVE_RESULTS':
            return {fetch: false, list: action.list, suite: action.suite}
        default:
            return state
    }
}

export default combineReducers({index, suites, results,})