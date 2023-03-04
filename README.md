# NotesService

Small REST API which works with HTTP requests. App made without GUI(front-end), it`s working with endpoints (listed below).

## Features

- CRUD functions for notes
- Authorization with spring security (Required to like posts)
- There is also a request for sorting notes by date (DESC)

The performance of the application has been tested using [Postman](https://www.postman.com)

## All endpoints
Endpoints for notes:
- GET (Show all notes from DB): http://localhost:8090/api/notes
- GET (Show all notes from DB sorted by date of publish DESC): http://localhost:8090/api/notes/sortByDate
- POST (Add new note to DB, Needs text request body): http://localhost:8090/api/notes/add
- DELETE (Delete note from DB by id, Needs text request body(id of note)): http://localhost:8090/api/notes/deleteById
- POST (Update note in DB by id, Needs two request params (id of note and text): http://localhost:8090/api/notes/updateNote
- POST (Like note, Needs request param (id), also needs authorization): http://localhost:8090/api/notes/like
- POST (Unlike note, Needs request param (id), also needs authorization): http://localhost:8090/api/notes/unlike

Endpoints for users:
- GET (Show all users in DB): http://localhost:8090/api/user/showAll
- POST (Add new user to DB, Needs two request params (username and password): http://localhost:8090/api/user/add
- DELETE (Delete current user from DB, Needs one request param (username)): http://localhost:8090/api/user/delete
- GET (Method for logout): http://localhost:8090/logout