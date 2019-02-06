import React, {Component} from 'react';
import {HashRouter, Route, Switch} from 'react-router-dom';
import './App.css';
import Album from "./Album";

class MainRouter extends Component {

    render() {

        return (<div>
            {/*<Header/>*/}
            <div>
                <div className="content-wrapper">
                    <div className="container-fluid">
                        <HashRouter>
                            <Switch>
                                <Route path="/album" name="Album" component={Album}/>
                            </Switch>
                        </HashRouter>
                    </div>
                </div>
                {/*<Footer/>*/}
            </div>

        </div>);
    }
}

export default MainRouter;