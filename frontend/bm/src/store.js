import { createStore, applyMiddleware } from 'redux';
import { createEpicMiddleware, combineEpics } from 'redux-observable';
import { createLogger } from 'redux-logger'
import rootReducer from "./reducers";
import { loginEpic, fetchTrashcanStockEpic } from './epics';

const loggerMiddleware = createLogger()
const epicMiddleware = createEpicMiddleware(
  combineEpics( fetchTrashcanStockEpic, loginEpic )
);


export default createStore(
  rootReducer,
  applyMiddleware(loggerMiddleware, epicMiddleware)
);
