import fetch from 'whatwg-fetch'

const REQ = 'DIR_FETCH_REQ'
const REC = 'DIR_FETCH_REC'

const should = (dir, state) => state.dir.fetching != dir
const request = dir => ({type: REQ, dir})
const receive = (dir, list) => ({type: REC, dir, list})

const sync = dir => async dispatch => {
    dispatch(request(dir))
    let response = await fetch(`http://127.0.0.1:8082/dir`, {dir})(dispatch)
    if (response.ok) {
        // TODO:
        let list = response.json()
        return dispatch(receive(dir, list))
    }
    return []
}

const load = dir => (dispatch, getState) => {
    let state = getState()
    if (should(dir, state))
        return dispatch(sync(dir))
    else
        return Promise.resolve()
}


export {
    load as fetch,
    REQ,
    REC,
}