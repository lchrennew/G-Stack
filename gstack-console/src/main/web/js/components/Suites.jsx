import React from 'react'
import Main from "./Main";
import SuiteItem from "./SuiteItem";
import { Card } from 'semantic-ui-react'

class Suites extends React.Component {
    render() {
        let {suites = [{dir: "123", title: 'demo', description: '...'}]} = this.props
        return <Main>
            <h1 className="mt-5">Test Suites</h1>
            <div className="row">
                <div className="col">
                    <Card.Group itemsPerRow={4}>
                        {
                            suites.map((suite, i) => <SuiteItem key={i} {...suite}/>)
                        }
                    </Card.Group>
                </div>
            </div>
        </Main>
    }
}

export default Suites