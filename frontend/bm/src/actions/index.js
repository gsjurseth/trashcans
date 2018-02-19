import * as types from "../constants/action-types";

// login
export const login                  = payload => ({ type: types.LOGIN, payload: payload });
export const loginFullfilled        = payload => ({ type: types.LOGIN_FULFILLED, payload: payload });

// traschan inventory
export const fetchTrashcanStock     = payload => ({ type: types.FETCH_TRASHCAN_STOCK, payload: payload });
export const trashcanStockFetched   = payload => ({ type: types.TRASHCAN_STOCK_FETCHED, payload: payload });


