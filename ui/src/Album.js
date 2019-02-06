import React, {Component} from 'react';
import {Card, CardBody, CardHeader, Row, Col} from 'reactstrap';

class Album extends Component {



    render() {


        return (
            <div>
                <Card className="mb-3">
                    <CardHeader>
                        <Row>
                            <Col>
                                <i className="fa fa-table"></i>Album
                            </Col>
                        </Row>
                    </CardHeader>
                    <CardBody>
                    </CardBody>
                </Card>
            </div>
        );
    }
}
export default Album;