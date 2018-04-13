import React from 'react'
//import PropTypes from 'prop-types';
import { Menu, Divider } from 'semantic-ui-react'
import { connect } from "react-redux";
import { login } from "../actions"

const mapStateToProps = state => {
  console.log("the state, yo: %j", state);
  return { i: state.rootReducer.loginInfo };
};

const ConnectedInfo = ({ i, login }) => (
  <Menu divided horizontal size='mini'>
    <Menu.Item>
      <div class="image">
        <i class="key icon"></i>
      </div>
      <a class="header" onClick={login} id="doLogin">Login App</a>
    </Menu.Item>
    <Menu.Item>
      <div class="item">
        Email: {i["developer.email"]}
      </div>
      <div class="item">
        Token: {i.access_token}
      </div>
    </Menu.Item>
  </Menu>
);
const LoginInfo = connect(mapStateToProps, {login})(ConnectedInfo);

export default LoginInfo;
/*
  */
