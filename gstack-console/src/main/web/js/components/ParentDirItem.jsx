import React from 'react'
import {Link, withRouter} from "react-router-dom";
import Icon from "./Icon";
import {Table} from 'semantic-ui-react'

class parentLink extends React.Component {
    getParentLink() {
        let {location: {pathname}} = this.props
        let segs = pathname.split('/')
        segs.length--
        if (pathname.endsWith('/'))
            segs.length--
        if (segs.length == 3)
            segs.length--
        if (segs.length > 3)
            segs[2] = 'tree'
        return segs.join('/')
    }

    render() {
        return <Link to={this.getParentLink()}>{this.props.children}</Link>
    }
}

export const ParentLink = withRouter(parentLink)

export class ParentDirItem extends React.Component {
    render() {
        if (this.props.visible)
            return <Table.Row>
                <Table.Cell className="icon"/>
                <Table.Cell>
                    <ParentLink>
                        [
                        <Icon name="corner-left-up"/>
                        ..]</ParentLink>
                </Table.Cell>
                <Table.Cell/>
                <Table.Cell/>
            </Table.Row>
        else
            return null
    }
}