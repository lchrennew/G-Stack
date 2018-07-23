import React from 'react'
import {connect} from 'react-redux'
import {executeScenario} from "../actions"
import {notify, openShell, closeShell, printShell} from "./Contexts";
import Icon from "./Icon";

const mapDispatchToProps = dispatch => ({
    executeScenario:
        (suite, path) =>
            (onStart, onPrint, onEnd) =>
                dispatch(
                    executeScenario(suite, path)(onStart, onPrint, onEnd)),
})

const mapStateToProps = (state, props) => ({})

class ExecuteButton extends React.Component {

    onStart() {
        let {title} = this.props
        openShell
    }

    onEnd(result) {
        let {title} = this.props

        notify({
            title: `执行${result ? '成功' : '失败'}`,
            message: `${title}`,
                level: result ? 'success' : 'error',
            position: 'tr',
        })
        // closeShell()
    }

    onPrint(line) {
        printShell(line)
    }
    execute(e) {
        e.preventDefault();
        let {suite, executeScenario, path = ''} = this.props
        executeScenario(suite, path)
        (
            this.onStart.bind(this),
            this.onPrint.bind(this),
            this.onEnd.bind(this)
        )
    }

    render() {
        let {className = "link", size = 16} = this.props
        return <a href="#" className={className} onClick={this.execute.bind(this)}><Icon name="play" size={size}/></a>
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(ExecuteButton)
