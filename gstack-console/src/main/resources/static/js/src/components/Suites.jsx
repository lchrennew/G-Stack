import React from 'react'
import Main from "./Main";
import SuiteItem from "./SuiteItem";

class Suites extends React.Component {
    render() {
        let {suites = [{dir: "123", title: 'demo', description: '...'}]} = this.props
        return <Main>
            <h1 className="mt-5">Test Suites</h1>
            <div className="row">
                <div className="col">
                    <div className="card-columns">
                        {
                            suites.map((suite, i) => <SuiteItem key={i} {...suite}/>)
                        }
                    </div>
                </div>
            </div>
        </Main>
    }
}

export default Suites