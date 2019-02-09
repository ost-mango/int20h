import React, {Component} from 'react';
import {Emotion} from "./Enums";

class FilterComponent extends Component {

    constructor(props) {
        super(props);
        this.onEmotionCheck = this.onEmotionCheck.bind(this);
        let emotionsSelected = {};
            Object.keys(Emotion).forEach(emotion => emotionsSelected[emotion] = true);
        this.state = {
            emotionsSelected: emotionsSelected
        }
    }

    onEmotionCheck(emotion) {
        let newState = this.state;
        newState.emotionsSelected[emotion] = !newState.emotionsSelected[emotion];
        // this.props.onEmotionChange(newState.emotionsSelected);
        this.setState(newState);
    }

    render() {

        let buttons = Object.keys(Emotion).map(emotion =>
            <li className="list-inline-item">
                <div className="btn-group-toggle" data-toggle="buttons">
                    <button className="btn btn-secondary" type="checkbox" autoComplete="off" onClick={() => this.onEmotionCheck(emotion)}>{emotion}</button>
                </div>
            </li>);

        return (
            <div>
                <ul className="list-inline">
                    {buttons}
                </ul>
            </div>
        );
    }
}

export default FilterComponent;