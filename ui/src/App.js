import React, {Component} from 'react';
import {HashRouter, Route, Switch} from 'react-router-dom';
import './App.css';
import MainRouter from "./MainRouter";

class App extends Component {

    render() {

        return (<div>

            <HashRouter>
                <Switch>
                    <Route path="/**" name="MainRouter" component={MainRouter}/>
                </Switch>
            </HashRouter>
        </div>);
    }
}

export default App;