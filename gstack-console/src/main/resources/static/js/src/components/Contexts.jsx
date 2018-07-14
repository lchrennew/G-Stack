import React from 'react'
import NotificationSystem from "react-notification-system";

const notifyRef = React.createRef()

// 向全局组件中添加此组件
export class NotificationManager extends React.Component {
    render() {
        return <NotificationSystem ref={notifyRef}/>
    }
}

// 需要调用this.props.notify方法的组件可以使用withNotify(Component)
export const withNotify = (Component) => <Component notify={notify}/>

// 其他js可以调用notify(notification)
export const notify = opt => notifyRef.current && notifyRef.current.addNotification(opt)
