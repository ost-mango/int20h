import {callRestGet} from '../axionUtils'

export const IMAGES_INFO_REQUEST = 'IMAGES_INFO_REQUEST';
export const IMAGES_INFO_SUCCESS = 'IMAGES_INFO_SUCCESS';
export const IMAGES_INFO_FAILURE = 'IMAGES_INFO_FAILURE';

function requestImagesInfo() {
    return {
        type: IMAGES_INFO_REQUEST,
        isFetching: true,
        data: null
    }
}

function successImagesInfo(data) {
    return {
        type: IMAGES_INFO_SUCCESS,
        isFetching: false,
        data: data,
    }
}

function errorImagesInfo(message) {
    return {
        type: IMAGES_INFO_FAILURE,
        isFetching: false,
        message: message
    }
}

export function getThumbnailImagesInfo(emotion, startIdx, count, dispatch) {
    let params = {
        emotion: emotion,
        startIdx: startIdx,
        count: count
    };
    return callRestGet(
        '/images/thumbnails',
        requestImagesInfo,
        successImagesInfo,
        errorImagesInfo,
        dispatch,
        params)
}

export function thumbnailImagesInfo(state = {
    isFetching: false,
    message: null,
    data: null
}, action) {
    switch (action.type) {
        case IMAGES_INFO_REQUEST:
            return Object.assign({}, state, {
                isFetching: true
            });
        case IMAGES_INFO_SUCCESS:
            return Object.assign({}, state, {
                isFetching: false,
                message: null,
                data: action.data,
            });
        case IMAGES_INFO_FAILURE:
            return Object.assign({}, state, {
                isFetching: false,
                message: action.message,
                data: null,
            });
        default:
            return state
    }
}