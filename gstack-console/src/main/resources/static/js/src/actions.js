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

export const fetchIndex = (suite) => ({
    type: 'FETCH_INDEX',
    suite,
})

export const receiveIndex = (idx, suite) => ({
    type: 'RECEIVE_INDEX',
    idx,
    suite,
})