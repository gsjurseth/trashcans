const Promise = require('bluebird'),
  express     = require('express'),
  fetch       = require('node-fetch'),
  cache       = require('node-cache'),
  config      = require('./config.json');

/*
 * config.json isn't checked into the repo. It's a simple json
 * {
 *   "client_id" : "daClientIDString",
 *   "secret": "daClientSecret"
 * }
 */

fetch.Promise = Promise;

/* some express and runtime constants */
const app = new express();
const myCache = new cache( { stdTTL: 100, checkperiod: 120 } );
const searchSvc   = 'http://gsjurseth-eval-test.apigee.net/inventory/v1/trashcans/';
const pricingSvc  = 'http://gsjurseth-eval-test.apigee.net/shopping/v1/pricing/trashcans';


function daLoginStuff() {
  return new Promise(  (res,rej) => {
    let OAuthSet = myCache.get( "oauth" );
    if ( OAuthSet && (OAuthSet.expires_in > 10) ) {
      console.log("Returning cached oauth set");
      res(OAuthSet);
    }
    else {
      fetch( config.tokenUrl, { 
        method: 'POST', 
        headers: { "Authorization": Buffer.from( config.client_id + ":" + config.secret, "utf8").toString("base64") }
      })
        .then( d => d.json() )
        .then( d => {
          console.log("Returning fresh oauth set");
          myCache.set( "oauth", d, (d.expires_in - 10) )
          res(d);
        });
    }
  });
}

app.get('/login', (req,res) => {
  daLoginStuff()
    .then( d => {
      res.json(d);
    });
});

app.get('/catalog', (req,res) => {
  daLoginStuff()
    .then( auth => {
      return fetch(pricingSvc, { headers: { "Authorization": `Bearer ${auth.access_token}` } })
        .then( d => d.json() )
        .map( d => {
          return fetch( `${searchSvc}${d.name}`, { headers: { "Authorization": `Bearer ${auth.access_token}` } } )
            .then( r => r.json()  )
            .then( r => {
              console.log('here I am with the r: %j', r);
              return Object.assign({}, r, {price: d.price});
            });
        })
        .then( d => {
          return res.json( d );
        })
        .catch( e => {
          console.error('We failed: %s', e.stack);
          res.status(500).json({msg: "failed running set()", stack: e.stack})
        });
    });
});

app.listen(3000, () => console.log('Example app listening on port 3000!'))
