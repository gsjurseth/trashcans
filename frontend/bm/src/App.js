import React from "react";
import { Container, Header } from 'semantic-ui-react'
import LoginInfo from "./components/LoginInfo";
import Trashcans from "./components/Trashcans";

const App = () => (
    <Container style={{ padding: '5em 0em' }}>
      <Header as='h2'>Attached Content</Header>
        <LoginInfo />
        <Trashcans />
    </Container>
);

export default App;