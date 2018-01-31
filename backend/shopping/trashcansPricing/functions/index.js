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

app.get("/pricing/trashcans", jsonParser, (req, res) => {
  let tcans = db.collection('trashcans');
  tcans.get()
    .then( d => {
      let resp = [];
      d.forEach( b => {
        resp.push(b.data());
      })
      return resp;
    })
    .then( d => {
        return res.json(d);
    }) 
    .catch( e => {
      console.error('We failed: %s', e.stack);
      res.status(500).json({msg: "failed running query", stack: e.stack})
    });
});

app.get("/pricing/trashcans/:tname", jsonParser, (req, res) => {
  let tcans = db.collection('trashcans').doc(req.params.tname);
  tcans.get()
    .then( d => {
        return res.json(d.data());
    }) 
    .catch( e => {
      console.error('We failed: %s', e.stack);
      res.status(500).json({msg: "failed running fetch", stack: e.stack})
    });
});

app.delete("/pricing/trashcans/:tname", jsonParser, (req, res) => {
  return db.doc(`/trashcans/${req.params.tname}`).delete()
    .then( r => res.json(r) )
    .catch( e => {
      console.error('We failed: %s', e.stack);
      res.status(500).json({msg: "failed running delete()", stack: e.stack})
    });
});

app.post("/pricing/trashcans", jsonParser, (req, res) => {
  let tcans = db.doc(`/trashcans/${req.body.name}`);
  tcans.set( req.body )
    .then( r => {
      return res.json( r );
    })
    .catch( e => {
      console.error('We failed: %s', e.stack);
      res.status(500).json({msg: "failed running set()", stack: e.stack})
    });
});

const api = functions.https.onRequest(app);

module.exports = {
  api
};
