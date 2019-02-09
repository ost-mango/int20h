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
            images: [{
                src: "https://c2.staticflickr.com/9/8817/28973449265_07e3aa5d2e_b.jpg",
                thumbnail: "https://c2.staticflickr.com/9/8817/28973449265_07e3aa5d2e_n.jpg",
                thumbnailWidth: 320,
                thumbnailHeight: 174
            },
                {
                    src: "https://c2.staticflickr.com/9/8356/28897120681_3b2c0f43e0_b.jpg",
                    thumbnail: "https://c2.staticflickr.com/9/8356/28897120681_3b2c0f43e0_n.jpg",
                    thumbnailWidth: 320,
                    thumbnailHeight: 212
                },

                {
                    src: "https://c4.staticflickr.com/9/8887/28897124891_98c4fdd82b_b.jpg",
                    thumbnail: "https://c4.staticflickr.com/9/8887/28897124891_98c4fdd82b_n.jpg",
                    thumbnailWidth: 320,
                    thumbnailHeight: 212
                }]
        }
    }

    componentDidMount() {
        this.props.getThumbnailImagesInfo(null, 0, 10);
        window.addEventListener('scroll', this.handleScroll);
    }

    componentWillUnmount() {
        window.removeEventListener('scroll', this.handleScroll);
    }

    handleScroll(event) { //todo load on scroll
        let scrollTop = event.srcElement.body.scrollTop,
            itemTranslate = Math.min(0, scrollTop/3 - 60);

        this.setState({
            transform: itemTranslate
        });
    }

    componentDidUpdate(prevProps, prevState) {
        if (JSON.stringify(prevProps.data) === JSON.stringify(this.props.data)) {
            let newState = this.state;
            this.props.data.forEach(imageInfoDto => {
                let newImageEntry = {
                    src: BASE_URL + "/images/originals/" + imageInfoDto.id,
                    thumbnail: BASE_URL + "/images/thumbnails/" + imageInfoDto.id,
                    thumbnailWidth: imageInfoDto.width,
                    thumbnailHeight: imageInfoDto.height,
                    emotion: imageInfoDto.emotion
                };
                newState.images.push(newImageEntry);
            } );
            this.setState(newState);
        }
    }

    render() {
        //todo implement filtering logic
        return (
            <div>
                <Card border="dark" color="dark" className="text-center main-card">
                    <CardBody>
                        <CardTitle><h1 id="title">The best album</h1></CardTitle>
                        <CardSubtitle className="mb-2 text-muted"><h3>Ever</h3></CardSubtitle>
                        <FilterComponent/>
                        <Gallery images={this.state.images} enableImageSelection={false}/>
                    </CardBody>
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
    const {data} = state.thumbnailImagesInfo;
    return {
        data
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Album);