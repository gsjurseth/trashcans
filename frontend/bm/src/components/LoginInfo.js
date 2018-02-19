import React from 'react'
//import PropTypes from 'prop-types';
import { Item, Card, Icon } from 'semantic-ui-react'
import { connect } from "react-redux";
import { login } from "../actions"

const mapStateToProps = state => {
  return { i: state.loginInfo };
};

const ConnectedInfo = ({ i, login }) => (
    <div>
      <button onClick={login}>Perform Login</button>
      <Item.Group>
      <Item>
        <Item.Content>
            <Item.Header>Login Details</Item.Header>
            <Item.Meta>Email: {i["developer.email"]}</Item.Meta>
            <Item.Description>{"Organization: " + i.organization_name}</Item.Description>
            <Item.Extra>
              <Icon name='user' />
              token_type: {i.token_type}
              <br />
              access_token: {i.access_token}
              <br />
              application_name: {i.application_name}
            </Item.Extra>
        </Item.Content>
      </Item>
      </Item.Group>
  </div>
);
const LoginInfo = connect(mapStateToProps, {login})(ConnectedInfo);

export default LoginInfo;