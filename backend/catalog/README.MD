# Catalog service
Ugly service that aggregates data from the pricing and search service so that we can combing pricing with items that are in stock.

## Some instructions for repeatability and stuff
Here are basically what i've done to get this up and running. Not the extra bit for kubectl which disables istio getting in the way of outbound traffic
```bash
docker build -t catalog .
docker tag catalog gcr.io/apigee-trashcan-backends/catalog:v2
docker images
gcloud docker -- push  gcr.io/apigee-trashcan-backends/catalog:v2
kubectl apply -f <(istioctl kube-inject -f catalog.yaml --includeIPRanges=10.28.0.0/14,10.31.240.0/20)
```

## NOTE
This now requires a config.json file that contains the following elements:
```javascript
{
  "client_id" : "the_client_id",
  "secret": "the_secret",
  "tokenUrl": "the_oauth_token_url"
}
```

This file has not been included and must be created manually for this to work
