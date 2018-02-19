import { ajax } from 'rxjs/observable/dom/ajax';
import * as at from '../constants/action-types';
import * as actions from '../actions';
import 'rxjs';
const config = require('../config');

export const fetchTrashcanStockEpic = (action$,st) =>
  action$.ofType(at.FETCH_TRASHCAN_STOCK)
    .filter( () => (st.getState().loginInfo.access_token !== 'not logged in'))
    .mergeMap( action =>
      ajax({ url: config.stockUrl,
        headers: { "Accept": "application/json", "Authorization": "Bearer " + st.getState().loginInfo.access_token } })
        .map( d => d.response)
        .map(actions.trashcanStockFetched)
    );

export const loginEpic = action$ =>
  action$.ofType(at.LOGIN)
    .mergeMap( action =>
      ajax.post(config.tokenUrl,
        { grant_type: "client_credentials"},
        { "Accept": "application/json",
          "Authorization": Buffer.from( config.client_id + ":" + config.secret, "utf8" ).toString("base64")
        })
        .map( d => d.response)
        .map(actions.loginFullfilled)
    );
