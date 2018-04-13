import { createStore, applyMiddleware, combineReducers } from 'redux';
import { createEpicMiddleware, combineEpics } from 'redux-observable';
import { routerReducer, routerMiddleware, push } from 'react-router-redux';
import { createLogger } from 'redux-logger';
import createHistory from 'history/createBrowserHistory';
import rootReducer from "./reducers";
import { loginEpic, fetchTrashcanStockEpic } from './epics';

export const history = createHistory();
const loggerMiddleware = createLogger()
const epicMiddleware = createEpicMiddleware(
  combineEpics( fetchTrashcanStockEpic, loginEpic )
);


export default createStore(
  rootReducer,
  applyMiddleware(loggerMiddleware, routerMiddleware(history), epicMiddleware)
);
