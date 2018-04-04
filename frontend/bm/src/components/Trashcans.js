import React from 'react'
import chunk from 'chunk'
import { Grid, Card, Icon } from 'semantic-ui-react'
import { connect } from "react-redux";
import { fetchTrashcanStock } from "../actions"

const mapStateToProps = state => {
  return { i: chunk(state.trashcanStock,3) };
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
        super(props);
    }

    render() {
        const { thumbnailURL, name, stock, description} = this.props;
        return (<Grid.Column>
                <Card
                    image={thumbnailURL}
                    header={name}
                    meta={"Stock: " + stock}
                    description={description}
                />
            </Grid.Column>);
    }
}

/*
*/
const Trashcans = connect(mapStateToProps, {fetchTrashcanStock})(ConnectedTrashcans);

export default Trashcans;