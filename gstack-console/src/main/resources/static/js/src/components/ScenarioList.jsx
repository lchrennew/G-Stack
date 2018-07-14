// 场景列表

import React from 'react'
import ScenarioItem from "./ScenarioItem";
import {withSpec} from "./SpecProvider";
import {ParentDirItem} from "./ParentDirItem";


class ScenarioList extends React.Component {

    render() {
        let {spec} = this.props
        if (spec)
            return <table className="table table-sm table-hover files">
                <tbody>
                <ParentDirItem visible={true} />
                {
                    spec.scenarios.map(({title, tags, lineNumber}, i) => (
                        <ScenarioItem {...{title, tags, lineNumber}} key={i}/>))
                }
                </tbody>
            </table>
        else return <div>Loading...</div>
    }
}

export default withSpec(ScenarioList)