package main

import (
	"encoding/json"
	"github.com/gorilla/mux"
	"google.golang.org/appengine"
	"google.golang.org/appengine/datastore"
	"google.golang.org/appengine/log"
	"net/http"
	"strconv"
)

const message = `{"statusCode": 200, "msg": "Hello World"}`
const dudeMessage = `{"statusCode": 200, "msg": "Hello World, Dude!"}`

type TrashcanEntity struct {
	Name         string `datastore:"name"`
	Description  string `datastore:"description,noindex"`
	ImageURL     string `datastore:"imageURL,noindex"`
	ThumbnailURL string `datastore:"thumbnailURL,noindex"`
	Stock        int    `datastore:"stock,noindex"`
}

func homeHandler(w http.ResponseWriter, r *http.Request) {
	json.NewEncoder(w).Encode(message)
}

func dudeHandler(w http.ResponseWriter, r *http.Request) {
	ctx := appengine.NewContext(r)
	log.Infof(ctx, "In the dude method")
	json.NewEncoder(w).Encode(dudeMessage)
}

func listAllTrashcans(w http.ResponseWriter, r *http.Request) {
	ctx := appengine.NewContext(r)
	log.Infof(ctx, "Going to list all trashcans")

	q := datastore.NewQuery("TrashcanEntity")

	var cans []TrashcanEntity
	if k, err := q.GetAll(ctx, &cans); err != nil && err != err.(*datastore.ErrFieldMismatch) {
		log.Errorf(ctx, "Received error: `%s` and for record: `%v`", err, k)
	} else if err == err.(*datastore.ErrFieldMismatch) {
		log.Errorf(ctx, "Received damned ErrFieldMismatch error: %v .. But fetched this record: %v", err, k)
	}
	log.Infof(ctx, "Trashcan records: %v", cans)
	json.NewEncoder(w).Encode(cans)
}

func listTrashcans(w http.ResponseWriter, r *http.Request) {
	ctx := appengine.NewContext(r)
	offset, err := strconv.Atoi(r.URL.Query().Get("offset"))
	limit, err := strconv.Atoi(r.URL.Query().Get("limit"))

	log.Infof(ctx, "Going to list trashcans with limit and offset")

	if err != nil {
		log.Errorf(ctx, "Received error: %v", err)
	}

	q := datastore.NewQuery("TrashcanEntity").Offset(offset).Limit(limit)

	var cans []TrashcanEntity
	if k, err := q.GetAll(ctx, &cans); err != nil && err != err.(*datastore.ErrFieldMismatch) {
		log.Errorf(ctx, "Received error: `%s` and for record: `%v`", err, k)
	} else if err == err.(*datastore.ErrFieldMismatch) {
		log.Errorf(ctx, "Received damned ErrFieldMismatch error: %v .. But fetched this record: %v", err, k)
	}
	log.Infof(ctx, "Trashcan records: %v", cans)
	json.NewEncoder(w).Encode(cans)
}

func displayTrashcan(w http.ResponseWriter, r *http.Request) {
	name := mux.Vars(r)["name"]
	ctx := appengine.NewContext(r)
	log.Infof(ctx, "Looking for trashcan: %s...", name)

	q := datastore.NewQuery("TrashcanEntity").Filter("name =", name).Limit(1)

	var cans []TrashcanEntity
	if k, err := q.GetAll(ctx, &cans); err != nil && err != err.(*datastore.ErrFieldMismatch) {
		log.Errorf(ctx, "Received error: `%s` and for record: `%v`", err, k)
	} else if err == err.(*datastore.ErrFieldMismatch) {
		log.Errorf(ctx, "Received damned ErrFieldMismatch error: %v .. But fetched this record: %v", err, k)
	}
	log.Infof(ctx, "Trashcan records: %v", cans)
	json.NewEncoder(w).Encode(cans[0])
}

func init() {
	r := mux.NewRouter()
	r.HandleFunc("/", homeHandler)
	r.HandleFunc("/dude", dudeHandler)
	r.HandleFunc("/trashcans", listTrashcans).Queries("offset", "{[0-9]+}")
	r.HandleFunc("/trashcans", listAllTrashcans)
	r.HandleFunc("/trashcans/{name}", displayTrashcan)

	// The path "/" matches everything not matched by some other path.
	http.Handle("/", r)
}
