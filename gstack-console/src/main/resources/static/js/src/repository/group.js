export default function (initState, ...reducers) {
    return function (state, action) {
        let state1 = state || initState || {}
        reducers && reducers.map(r => {
            state1 = r(state1, action)
        })
        return state1
    }
}