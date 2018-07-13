import React from "react";
import Main from "./Main";
import VisibleDir from "./VisibleDir";
import IndexProvider from "./IndexProvider";

class Suite extends React.Component {

    render() {
        let {match} = this.props
        return <Main>
            <h1 className="mt-5">{match.params.suite}</h1>
            <div className="row">
                <div className="col">
                    <IndexProvider>
                        <div className="commit-tease">
                            <div className="mr-auto">test</div>
                        </div>
                        <div className="file-wrap">
                            <VisibleDir dir="/" />
                        </div>
                    </IndexProvider>
                </div>
            </div>
        </Main>

    }
}

export default Suite