import React from 'react'
import {Route, Switch} from "react-router-dom";
import Suite from "./Suite";
import Directory from "./Directory";
import File from './File';
import App from "./App";
import Header from './Header'
import Footer from "./Footer";
import Suites from "./Suites";


class Index extends React.Component {
    componentWillMount() {
        //this.props.router.replace('/t')
    }

    /*
    * 路由：
    * 首页测试包列表    /
    * 测试包首页   /suite/:suite
    * 目录页       /suite/:suite/:dir
    * */
    render() {
        return <App>
            <Header/>
            <Switch>
                <Route path="/:suite/tree/:dir" component={Directory}/>
                <Route path="/:suite/clob/:dir" component={File}/>
                <Route path="/:suite" component={Suite}/>
                <Route path="/" component={Suites}/>
            </Switch>
            <Footer/>
        </App>
    }
}

export default Index