import {REC, REQ} from "./actions";

export default (state, action) => {
    switch (action.type) {
        case REQ:
            return {fetching: action.dir}
        case REC:
            return state.fetching == action.dir ? {data: action.list} : state
        default:
            return state
    }
}