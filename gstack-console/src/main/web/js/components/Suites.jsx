import React from 'react'
import Main from "./Main";
import SuiteItem from "./SuiteItem";
import { Card } from 'semantic-ui-react'
import {connect} from 'react-redux'
import CreateSuiteCard from "./CreateSuiteCard";

const mapDispatchToProps = dispatch => {
    return {}
}

const mapStateToProps = (state, props) => {
    return {
        suites: state.suites.list
    }
}

class Suites extends React.Component {
    render() {
        let {suites} = this.props
        return <Main>
            <h1 className="mt-5">Test Suites</h1>
            <div className="row">
                <div className="col">
                    <Card.Group itemsPerRow={4}>
                        {
                            suites.map((suite, i) => <SuiteItem key={i} {...suite}/>)
                        }
                        <CreateSuiteCard/>
                    </Card.Group>
                </div>
            </div>
        </Main>
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Suites)