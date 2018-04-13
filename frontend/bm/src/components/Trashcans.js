import React from 'react'
import chunk from 'chunk'
import { Grid, Card, Icon, Modal, Button, Image, Header } from 'semantic-ui-react'
import { connect } from "react-redux";
import { fetchTrashcanStock } from "../actions"

const mapStateToProps = state => {
  return { i: chunk(state.rootReducer.trashcanStock,3) };
};

const ConnectedTrashcans = ({ i, fetchTrashcanStock }) => {
  return (
      <div class="container">
        <div class="ui segment">
          <div class="title">Fetch Trashcan Inventory</div>
          <div calss="content">
            <a onClick={fetchTrashcanStock} id="fetchTrashcanStock">Fetch Trashcans</a>
          </div>
        </div>
        <Grid columns='four'>
          {
            i.map( row => {
              return (
                <Grid.Row>
                {
                  row.map( t => ( <Trashcan
                    id={t.idx}
                    thumbnailURL={t.thumbnailURL}
                    imageURL={t.imageURL}
                    name={t.name}
                    stock={t.stock}
                    description={t.description} /> ))
                }
                </Grid.Row>
              )
            })
          }
        </Grid>
      </div>
  );
}

class Trashcan extends React.Component {

    constructor(props) {
        console.log('our props: %j', props);
        super(props);
    }

    render() {
        const { thumbnailURL, imageURL, name, stock, description} = this.props;
        return (<Grid.Column>
                <Card size="small">
                    <Image src={thumbnailURL} size='small' />
                    <Card.Header>{name}</Card.Header>
                    <Card.Meta>{"Stock: " + stock}</Card.Meta>
                    <Card.Description>{description}</Card.Description>
                </Card>
                  <Modal trigger={<Button>Show Detail</Button>}>
                    <Modal.Header>Select a Photo</Modal.Header>
                    <Modal.Content image>
                      <Image wrapped size='medium' src={imageURL} />
                      <Modal.Description>
                        <Header>Stock: {stock}</Header>
                        {description}
                      </Modal.Description>
                    </Modal.Content>
                  </Modal>
            </Grid.Column>);
    }
}

/*
*/
const Trashcans = connect(mapStateToProps, {fetchTrashcanStock})(ConnectedTrashcans);

export default Trashcans;