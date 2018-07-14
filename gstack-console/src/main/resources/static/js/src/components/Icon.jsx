import React from 'react'

class Icon extends React.Component {
    render() {
        let {name, size = 16} = this.props
        return <svg
            width={size}
            height={size}
            fill="none"
            stroke="currentColor"
            strokeWidth="1"
            strokeLinecap="round"
            strokeLinejoin="round"
        >
            <use xlinkHref={`/lib/feather-sprite.svg#${name}`}/>
        </svg>
    }
}

export default Icon