import React from "react";
import Main from "./Main";
import {Link} from "react-router-dom";
import VisibleDir from "./VisibleDir";

class Suite extends React.Component {
    componentDidMount() {

    }
    render() {
        let {match} = this.props
        return <Main>
            <h1 className="mt-5">{match.params.suite}</h1>
            <div className="row">
                <div className="col">
                    <div className="commit-tease">
                        <div className="mr-auto">test</div>
                    </div>
                    <div className="file-wrap">
                        <VisibleDir dir="/" />
                    </div>
                </div>
            </div>
        </Main>

    }
}

export default Suite