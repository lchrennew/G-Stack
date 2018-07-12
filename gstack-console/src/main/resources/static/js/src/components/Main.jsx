// 主要内容容器

import React from 'react'

class Main extends React.Component {
    render() {
        return <main role="main" className="container">
                {this.props.children}
        </main>
    }
}

export default Main