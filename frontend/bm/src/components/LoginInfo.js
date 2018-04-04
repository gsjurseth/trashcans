import React from 'react'
//import PropTypes from 'prop-types';
import { Item, Card, Icon } from 'semantic-ui-react'
import { connect } from "react-redux";
import { login } from "../actions"

const mapStateToProps = state => {
  return { i: state.loginInfo };
};

const ConnectedInfo = ({ i, login }) => (
  <div class="ui horizontal list">
    <div class="item">
      <div class="image">
        <i class="key icon"></i>
      </div>
      <a class="header" onClick={login} id="doLogin">Login App</a>
    </div>

    <div class="item">
      <div class="content">
        <div class="header">Email</div> {i["developer.email"]}
      </div>
    </div>
    <div class="item">
      <div class="content">
        <div class="header">Application</div> {i.application_name}
      </div>
    </div>
    <div class="item">
      <div class="content">
        <div class="header">Token</div> {i.access_token}
      </div>
    </div>
  </div>
);
const LoginInfo = connect(mapStateToProps, {login})(ConnectedInfo);

export default LoginInfo;
/*
<div class="ui statistics">
  <div class="statistic">
    <div class="value">
      22
    </div>
    <div class="label">
      Faves
    </div>
  </div>
  <div class="statistic">
    <div class="value">
      31,200
    </div>
    <div class="label">
      Views
    </div>
  </div>
  <div class="statistic">
    <div class="value">
      22
    </div>
    <div class="label">
      Members
    </div>
  </div>
</div>

    <div>
      <button onClick={login} id="doLogin">Login App</button>
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
  */
