import fetch from 'cross-fetch'
import Stomp from 'stompjs'
import SockJS from 'sockjs-client'

const webApi = 'localhost:8082'
const api = (endpoint, ...args) => async (dispatch) => await fetch(`//${webApi}/${endpoint}`, ...args)

const json = (body, opt) => Object.assign({}, opt, {
    method: 'POST',
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(body),
})

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
    let response = await api(`suites/${suite}/index`, {credentials: 'include'})(dispatch)
    if (response.ok) {
        let idx = await response.json()
        return dispatch(receiveIndex(idx, suite))
    }
    return null
}

export const fetchIndex = (suite) => (dispatch, getState) => {
    let state = getState()
    if (!state.index.fetch) {
        return dispatch(_fetchIndex(suite))
    }
    else return Promise.resolve()
}

const printOutput = (uuid, onPrint, onEnd) => {
    let socket = new SockJS(`http://${webApi}/gstack-console-websocket`),
        stompClient = Stomp.over(socket)
    stompClient.connect({}, function (frame) {
        stompClient.subscribe(`/specs/output/` + uuid, function (message) {
            if (message.headers['Connection'] !== 'Close') {
                onPrint && onPrint(message.body)
            }
            else {
                onEnd && onEnd(message.body === '0')
                stompClient.disconnect()
            }
        })
    })
}

const _executeScenario = (suite, path) => (onStart, onPrint, onEnd) => async dispatch => {
    onStart && onStart()
    let response = await api(`suites/${suite}/execute`,
        json({suite, files: [path]},
            {credentials: 'include'})
    )(dispatch)
    if (response.ok) {
        let uuid = await response.json()
        onPrint && onPrint(`Session ID: ${uuid}`)
        console.log(uuid)
        printOutput(uuid,
            onPrint ? onPrint : console.log,
            onEnd ? onEnd : console.log)
    }
}


export const executeScenario =
    (suite, path) =>
        (onStart, onPrint, onEnd) =>
            (dispatch, getState) =>
                dispatch(
                    _executeScenario
                    (suite, path)
                    (onStart, onPrint, onEnd))

