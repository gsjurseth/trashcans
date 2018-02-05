const functions = require('firebase-functions'),
  pricingFunc   = require('./pricing'),
  cartsFunc     = require("./carts");

const pricing = functions.https.onRequest(pricingFunc.pricing);
const carts   = functions.https.onRequest(cartsFunc.carts);

module.exports = {
  pricing,
  carts
}

