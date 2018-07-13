import fetch from 'cross-fetch'

const webApi = 'localhost:8082'
const api = (endpoint, ...args) => async (dispatch) => {
    let response = await fetch(`http://${webApi}/${endpoint}`, ...args)
    if (response.status === 401)
        dispatch(actions.ui.status(response.status))
    return response
}

export const addToCart = (suite, ...dirs) => ({
    type: 'ADD_TO_CART',
    suite,
    dirs
})

// TODO: execCart
// TODO: removeFromCart
// TODO: clearCart

// TODO: addSuite
// TODO: fetchSuites
// TODO: receiveSuites
// TODO: removeSuite


export const execDir = (suite, ...dirs) => ({
    type: 'EXEC_DIR',
    suite,
    dirs,
})


export const executing = (execid) => ({
    type: 'EXECUTING',
    execid,
})

const requestIndex = (suite) => ({
    type: 'FETCH_INDEX',
    suite,
})

const receiveIndex = (idx, suite) => ({
    type: 'RECEIVE_INDEX',
    idx,
    suite,
})

const _fetchIndex = suite => async dispatch => {
    dispatch(requestIndex(suite))
    let response = await api(`specs/index`, {credentials: 'include'})(dispatch)
    if (response.ok) {
        let idx = await response.json()
        return dispatch(receiveIndex(idx, suite))
    }
    return []
}

export const fetchIndex = (suite) => (dispatch, getState) => {
    let state = getState()
    if (!state.index.fetch) {
        return dispatch(_fetchIndex(suite))
    }
    else return Promise.resolve()
}
