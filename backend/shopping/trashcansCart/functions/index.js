const functions = require('firebase-functions'),
  Firestore     = require('@google-cloud/firestore'),
  bodyParser    = require('body-parser'),
  Promise       = require('bluebird'),
  express       = require("express");

/* Express and runtime constants stuff*/
const app     = express(),
  jsonParser  = bodyParser.json(),
  db          = new Firestore();

app.get("/", (req, res) => {
  res.send("Slash but no dot");
});

app.get("/howdy", (req, res) => {
  res.send("Howdy from this here script!");
});

app.get("/carts/:id", jsonParser, (req, res) => {
  let tcans = db.collection('carts').doc(req.params.id);
  tcans.get()
    .then( d => {
        return res.json(d.data());
    }) 
    .catch( e => {
      console.error('We failed: %s', e.stack);
      res.status(500).json({msg: "failed running fetch", stack: e.stack})
    });
});

app.delete("/carts/:id", jsonParser, (req, res) => {
  return db.doc(`/carts/${req.params.id}`).delete()
    .then( r => res.json(r) )
    .catch( e => {
      console.error('We failed: %s', e.stack);
      res.status(500).json({msg: "failed running delete()", stack: e.stack})
    });
});

app.post("/carts", jsonParser, (req, res) => {
  let tcans = db.doc(`/carts/${req.body.name}`);
  tcans.set( req.body )
    .then( r => {
      return res.json( r );
    })
    .catch( e => {
      console.error('We failed: %s', e.stack);
      res.status(500).json({msg: "failed running set()", stack: e.stack})
    });
});

const carts = functions.https.onRequest(app);

module.exports = {
  carts
};