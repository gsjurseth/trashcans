# Search Service
The idea here is that this really is part of the same Inventory Service but provides search functionality written in go
## Development
To start up the dev server run:
```bash
dev_appserver.py app.yaml
```

## Deployment
To deploy the thing into cloud run:
```bash
gcloud app deploy app.yaml --project apigee-trashcan-backends --version v1
```
