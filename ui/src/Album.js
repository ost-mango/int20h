import React, {Component} from 'react';
import {Card, CardBody, CardTitle, CardSubtitle} from 'reactstrap';
import Gallery from 'react-grid-gallery';
import FilterComponent from "./FilterComponent";
import {getThumbnailImagesInfo} from "./api/service/GetThumbnailImagesInfo";
import connect from "react-redux/es/connect/connect";
import {BASE_URL} from "./api/axionUtils";

class Album extends Component {

    constructor(props) {
        super(props);
        this.state = {
            images: []
        }
    }

    loadImages(emotion, startIdx, count) {
        console.log(this.state.images)

        this.props.getThumbnailImagesInfo(emotion, startIdx, count)
            .then(() => {
                let newState = this.state;
                newState.images = JSON.parse(JSON.stringify(newState.images));
                this.props.data.filter(imageInfoDto => (newState.images.find(img => img.id === imageInfoDto.id) == null)).forEach(imageInfoDto => {
                    let newImageEntry = {
                        src: BASE_URL + "/images/originals/" + imageInfoDto.id,
                        thumbnail: BASE_URL + "/images/thumbnails/" + imageInfoDto.id,
                        thumbnailWidth: imageInfoDto.thumbnailWidth,
                        thumbnailHeight: imageInfoDto.thumbnailHeight
                    };
                    newState.images.push(newImageEntry);
                } );
                newState.currentIdx = startIdx + count;
                this.setState(newState);
            });
    }

    componentDidMount() {
        this.loadImages(null, 0, 30);
    }

    render() {
        //todo implement filtering logic

        let images = this.state.images;
        return (
            <div>
                <Card border="dark" color="dark" className="text-center main-card">
                    <CardBody>
                        <CardTitle><h1 id="title">The best album</h1></CardTitle>
                        <CardSubtitle className="mb-2 text-muted"><h3>Ever</h3></CardSubtitle>
                        <FilterComponent/>
                        {this.state.images.length > 0 &&
                        <Gallery images={images} enableImageSelection={false}/>}

                    </CardBody>
                    <div className="btn-group-toggle" data-toggle="buttons">
                        <button className="btn btn-secondary" onClick={() => this.loadImages(null, this.state.currentIdx, 30)}>Load more</button>
                    </div>
                </Card>
            </div>
        );
    }
}

function mapDispatchToProps(dispatch) {
    return ({
        getThumbnailImagesInfo: (emotion, startIdx, count) => getThumbnailImagesInfo(emotion, startIdx, count, dispatch)

    })
}

function mapStateToProps(state) {
    const {data, isFetching} = state.thumbnailImagesInfo;

    return {
        data, isFetching
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Album);