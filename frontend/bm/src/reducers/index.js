import { LOGIN, LOGIN_FULFILLED, FETCH_TRASHCAN_STOCK, TRASHCAN_STOCK_FETCHED } from "../constants/action-types";
import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';

const initialState = {
    loginInfo: {
        "developer.email": "not logged in",
        "access_token": "not logged in",
        "organization_name": "not logged in",
        "token_type": "not logged in",
        "application_name": "not logged in",
    },
    trashcanStock: [
       {
           "name": "First",
           "description": "Stylish trashcan",
           "imageURL": "http://storage.googleapis.com/apigee-trashcan-backends.appspot.com/copperCan/copperCan.jpg",
           "thumbnailURL": "http://storage.googleapis.com/apigee-trashcan-backends.appspot.com/copperCan/copperCan_thumbnail.jpg",
           "stock": 100,
           "price": "50"
       }
    ]
};

const rootReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOGIN:
      return { ...state, loginInfo : {
        "developer.email": "pending",
        "access_token": "pending",
        "organization_name": "pending",
        "token_type": "pending",
        "application_name": "pending"
      }};
    case LOGIN_FULFILLED:
      return { ...state, loginInfo: action.payload };
    case FETCH_TRASHCAN_STOCK:
      console.log('fetching trashcan stock');
      return { ...state, trashcanStock: [] };
    case TRASHCAN_STOCK_FETCHED:
      console.log('trashcan stock fetched: %j', action.payload);
      return { ...state, trashcanStock: action.payload };
    default:
      console.log('default reducer');
      return state;
  }
};
export default combineReducers( {
  rootReducer,
  routing: routerReducer }
);