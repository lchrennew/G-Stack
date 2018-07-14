import React from 'react'
import {connect} from 'react-redux'
import {executeScenario} from "../actions"
import {notify} from "./Contexts";
import Icon from "./Icon";

const mapDispatchToProps = dispatch => ({
    executeScenario:
        (suite, path) =>
            (onStart, onEnd) =>
                dispatch(
                    executeScenario(suite, path)(onStart, onEnd)),
})

const mapStateToProps = (state, props) => ({})

class ExecuteButton extends React.Component {

    onStart() {
        let {title} = this.props
        notify({
            title: '开始执行',
            message: `场景：${title}`,
            level: 'info',
            position: 'tr',
            action: {
                label: '查看输出',
                callback: () => {
                    // todo: show console output
                }
            }
        })
    }

    onEnd(result) {
        let {title} = this.props

        notify({
            title: `执行${result ? '成功' : '失败'}`,
            message: `场景：${title}`,
            level: result ? 'success' : 'err',
            position: 'tr',
        })
    }


    execute(e) {
        e.preventDefault();
        let {suite, executeScenario, path} = this.props
        executeScenario(suite, path)(this.onStart.bind(this), this.onEnd.bind(this))
    }

    render() {
        return <a href="#" className="link" onClick={this.execute.bind(this)}><Icon name="play"/></a>
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(ExecuteButton)
