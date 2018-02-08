const Promise = require('bluebird'),
  express     = require('express'),
  fetch       = require('node-fetch');


fetch.Promise = Promise;

/* some express and runtime constants */
const app = new express();
const searchSvc   = 'http://gsjurseth-eval-test.apigee.net/inventory/v1/trashcans/';
const pricingSvc  = 'http://gsjurseth-eval-test.apigee.net/shopping/v1/pricing/trashcans';


app.get('/catalog', (req,res) => {
  fetch(pricingSvc)
    .then( d => d.json() )
    .map( d => {
      return fetch( `${searchSvc}${d.name}` )
        .then( r => r.json()  )
        .then( r => {
          console.log('here I am with the r: %j', r);
          return Object.assign({}, r[0], {price: d.price});
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

app.listen(3000, () => console.log('Example app listening on port 3000!'))
