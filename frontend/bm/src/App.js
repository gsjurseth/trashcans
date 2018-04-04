import React from "react";
import { Container, Header, Image } from 'semantic-ui-react'
import LoginInfo from "./components/LoginInfo";
import Trashcans from "./components/Trashcans";
import trashcanLogo from "./trashcanLogo.jpg";


const App = () => (

  <div>
    <div class="ui pointing menu">
      <a href="/#" class="header item">
        <img
          class="ui mini image"
          src={trashcanLogo} />
      </a>
      <a href="#" class="item">Trashcan Stock</a>
      <a href="#" class="item">App User</a>
      <div>
      <LoginInfo />
      </div>
    </div>
    <div>
      <Trashcans />
    </div>
  </div>

);

export default App;
