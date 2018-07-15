import React from 'react'
import NotificationSystem from "react-notification-system";
import {Modal, ModalBody, ModalHeader} from "reactstrap";

const notifyRef = React.createRef()

// 向全局组件中添加此组件
export class NotificationManager extends React.Component {
    render() {
        return <NotificationSystem ref={notifyRef}/>
    }
}

// 其他js可以调用notify(notification)
export const notify = opt => notifyRef.current && notifyRef.current.addNotification(opt)

const shellRef = React.createRef()

export const openShell = () => shellRef.current && shellRef.current.start()
export const closeShell = () => shellRef.current && shellRef.current.close()
export const printShell = (line) => shellRef.current && shellRef.current.print(line)

class ShellOutput extends React.Component {
    constructor(props) {
        super(props)
        this.state = {open: false, lines: []}
    }

    start() {
        this.setState({open: true, lines: []})
    }

    close() {
        this.setState({open: false, lines: []})
    }

    print(line) {
        this.setState({open: true, lines: [...shellRef.current.state.lines, line]})
    }

    render() {
        return <Modal isOpen={this.state.open} size="lg">
            <ModalHeader toggle={this.close.bind(this)}>执行输出</ModalHeader>
            <ModalBody className="shell">
                {this.state.lines.map((line, i) => <p key={i}>{line}</p>)}
            </ModalBody>
        </Modal>
    }

}
//    "Content-Type": "text/plain; charset\u003dutf-8"
export class ShellManager extends React.Component {

    render() {
        return <ShellOutput ref={shellRef}/>
    }
}
