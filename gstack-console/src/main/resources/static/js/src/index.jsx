import React from 'react'
import {render} from 'react-dom'

import {compose, applyMiddleware} from 'redux'
import ReduxThunk from 'redux-thunk'
import {Provider} from 'react-redux'
import {createStore} from 'redux'


import {BrowserRouter as Router, Route} from 'react-router-dom'

import reducer from './repository/reducer'

import Index from './components/Index'

let devTool = typeof window === 'object' && typeof window.devToolsExtension !== 'undefined' ? window.devToolsExtension() : f => f

let store = createStore(
    reducer,
    compose(applyMiddleware(ReduxThunk), devTool))


class Console extends React.Component {
    render() {
        let {store} = this.props
        return (
            <Provider {...{store}}>
                <Router>
                    <Route path="/" component={Index}/>
                </Router>
            </Provider>
        )
    }
}

jQuery(() =>
    render(
        (
            <Console {...{store}} />
        ),
        document.getElementById('gstack-console')))