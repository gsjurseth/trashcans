import React from "react";
import { Container, Header, Image } from 'semantic-ui-react'
import LoginInfo from "./LoginInfo";
import trashcanLogo from "../trashcanLogo.jpg";


const Navbar = () => (
  <div class="ui pointing menu">
    <a href="/#" class="header item">
      <img
        class="ui mini image"
        src={trashcanLogo} />
    </a>
    <a href="/" class="item">Trashcan Stock</a>
    <a href="/foo" class="item">App User</a>
    <div>
      <div class="right item">
        <LoginInfo />
      </div>
    </div>
  </div>

);

export default Navbar;
