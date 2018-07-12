import React from "react";
import Main from "./Main";
import {Link} from "react-router-dom";

class Suite extends React.Component {

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
                        <table className="table table-sm table-hover files">
                            <tbody>
                            <tr>
                                <th></th>
                                <td>
                                    <Link to={`../`}>..</Link>
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <th scope="row" className="icon"><i className="icon ion-ios-folder"/></th>
                                <td className="content"><Link to={`folder`}>folder</Link></td>
                                <td className="message">&nbsp;</td>
                                <td className="actions">&nbsp;</td>
                            </tr>
                            <tr>
                                <th scope="row" className="icon"><i className="icon ion-ios-document"/></th>
                                <td className="content"><Link to={`file`}>file</Link></td>
                                <td className="message">&nbsp;</td>
                                <td className="actions">&nbsp;</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </Main>

    }
}

export default Suite