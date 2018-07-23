import React from 'react'
import {Card} from "semantic-ui-react";
import ResultItem from "./ResultItem";

class Results extends React.Component {
    render() {
        const {results} = this.props
        return <Card.Group>
            {
                results.map((result, i) => <ResultItem {...result} key={i}/>)
            }
        </Card.Group>
    }
}

export default Results