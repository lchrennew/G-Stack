export default function (initState, ...reducers) {
    if (reducers) {
        return function (state, action) {
            let state1 = state || initState || {}
            for (var i = 0; i < reducers.length; i++) {
                state1 = reducers[i](state1, action)
            }
            return state1
        }
    }
    else
        return function (state, action) {
            return state || initState || {}
        }
}