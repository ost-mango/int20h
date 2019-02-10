import {combineReducers} from 'redux'
import {thumbnailImagesInfo} from "./service/GetThumbnailImagesInfo";

const appReducers = combineReducers({
    thumbnailImagesInfo
});

export default appReducers