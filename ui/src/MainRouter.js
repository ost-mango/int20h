import React, {Component} from 'react';
import {HashRouter, Redirect, Route, Switch} from 'react-router-dom';
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
                                <Redirect from="/" to="/album"/>
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