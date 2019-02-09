import axios from 'axios';
import {HttpHeaders} from "./HttpHeaders";

export const BASE_URL = process.env.REACT_APP_BASE_URL != null ? process.env.REACT_APP_BASE_URL : '';

function getAxiosConfig() {
    let headers = {};
    // headers[HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN] = HttpHeaders.ALL;
    headers[HttpHeaders.CONTENT_TYPE] = HttpHeaders.APPLICATION_JSON;
    return {
        headers: headers
    };
}

let cancelMap = new Map();

export function callRestGet(endpoint, actionRequest, actionSuccess, actionFailed, dispatch, params) {

    let endpointPath = endpoint.split('?')[0];
    let cancelToken = cancelMap.get(endpointPath);
    if (cancelToken) {
        cancelToken();
    }

    dispatch(actionRequest());
    return axios.get(BASE_URL + endpoint, {
        cancelToken: new axios.CancelToken(function executor(c) {
            // An executor function receives a cancel function as a parameter
            cancelMap.set(endpointPath, c);
        }),
        headers: getAxiosConfig().headers,
        params: params
    })
        .then(res => {
            if (res.data.success === true) {
                let data = res.data.body;
                dispatch(actionSuccess(data))
            } else if (res.data.success === false) {
                dispatch(actionFailed(res.data.body.message));
            } else {
                // rest with another structure of response (ie Actuator)
                dispatch(actionSuccess(res.data))
            }
        })
        .catch(error => {
            if (axios.isCancel(error)) {
                console.log('Request canceled', error.message);
            } else if (error.response != null && error.response.data.body != null) {
                error.notificationMessage = error.response.data.body.message;
                dispatch(actionFailed(error.response.data.body.message));
            } else {
                error.notificationMessage = error.toString();
                dispatch(actionFailed(error.toString()))
            }
            throw error
        })
}