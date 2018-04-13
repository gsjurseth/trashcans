import React from "react";
import { Route, Switch } from 'react-router-dom';
import { render } from "react-dom";
import { Provider } from "react-redux";
import { ConnectedRouter } from 'react-router-redux';
import store, { history } from "./store";
import App from "./components/App";
import Foo from "./components/Foo";
import 'semantic-ui-css/semantic.min.css';
import 'rxjs';


/*

render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById("root")
);

*/

render(
  <Provider store={store}>
    {}
    <ConnectedRouter history={history}>
      <Switch>
        <Route exact path="/" component={App}/>
        <Route exact path="/foo" component={Foo}/>
      </Switch>
    </ConnectedRouter>
  </Provider>,
  document.getElementById('root')
)
