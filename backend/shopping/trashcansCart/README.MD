# Shopping cart service
This service simply stores a shopping cart .. It stores it forever

Here the idea is to store in a structure like the following:

```javascript
{
  cart: [
    { name: "First", quantity: 2, price: "50" },
    { name: "Second", quantity: 2, price: "35" }
  ],
  created: "2018-01-30-14.48.40",
  updated: "2018-01-31-14.48.40",
}
```
With the created and updated we can theoretically build something to purge entries at
a later date.

Also we store the items with the price they had at the time they were added to the cart... This may be weird, but it's what I'm doing for now.
