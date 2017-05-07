# 2. Pratkikumsaufgabe Software-Architektur Sommer 2017
von Elias Porcio

Heroku-link: https://ancient-hamlet-96691.herokuapp.com/

## Rest-Api documentation
### Media
Media objects must be passed as JSON objects in the request body.

#### Example of a book object
```
{
	"title": "Programmieren mit Java",
	"author": "Reinhard Schiedermeier",
 	"isbn": "978-3-8689-4031-2"
}
```
No field may be null or empty, the ISBN must be a valid ISBN-13.

#### Example of a disc object
```
{
	"title": "Programmieren mit Java, Hörspiel",
	"barcode": "9783868940312",
	"director": "Reinhard Schiedermeier",
 	"fsk":	0
}
```
No field may be null or empty, the barcode must be a valid EAN, fsk must be a positive integer.

#### POST /shareit/media/books
Add a new book.
The ISBN must not have been added yet.

#### POST /shareit/media/discs
Add a new disc.
The barcode must not have been added yet.

#### GET /shareit/media/books/{isbn}
Get a specific book.

#### GET /shareit/media/discs/{barcode}
Get a specific disc.

#### GET /shareit/media/books
Get a list of all books.

#### GET /shareit/media/discs
Get a list of all discs.

#### PUT /shareit/media/books/{isbn}
Update a book.
The ISBN in the URL must match the ISBN in the JSON object, as an ISBN cannot be changed.
Fields that should remain unchanged may be null or contain an escape value.
At least one field must be changed.

#### PUT /shareit/media/discs/{barcode}
Update a disc.
The barcode in the URL must match the barcode in the JSON object, as a barcode cannot be changed.
Fields that should remain unchanged may be null or contain an escape value.
At least one field must be changed.

### Copies (not yet implemented):

#### Example of a book copy object
```
{
	"isbn": "978-3-8689-4031-2",
	"username": "myUsername"
}
```

#### Example of a disc copy object
```
{
	"barcode": "9783868940312",
	"username": "myUsername"
}
```

#### POST /shareit/media/books/copys
Add a new copy of a book.

#### POST /shareit/media/discs/copys
Add a new copy of a disc.
