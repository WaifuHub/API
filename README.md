# Whub API

Whub API provides a REST API for the various Whub clients and apps. The repo includes the IaC needed to host on AWS.

## Routes

### POST /hubs

Create a new hub. Payload example:

```json
{
    "hubId" : "id",
    "hubName" : "name",
    "hubDescription": "description",
    "hubImageUrl": "imageUrl"
}
```

### GET /hubs

Get hub by id. Paylaod example:

```json
{
    "hubId": "id"
}
```

### DELETE /hubs

Delete a hub by id. Payload example:

```json
{
    "hubId": "id"
}
```

### POST /comments

Create a new comment. Payload example:

```json
{
    "commentId": "id",
    "hubId": "id",
    "userId": "id",
    "datePosted": "date",
    "likeCount": "count",
    "commentText": "text"
}
```

### GET /comments

Get comment by id. Payload example:

```json
{
    "commentId": "id"
}
```

### DELETE /comments

Delete comment by id. Paylaod example:

```json
{
    "commentId": "id"
}
```

### POST /users

Create a new user. Payload exmaple:

```json
{
    "userId": "id",
    "email": "email",
    "displayName": "name",
    "passwordHash": "hash",
    "profileImageUrl": "url"
}
```

### GET /users

Get a user by uid. Payload example:

```json
{
    "userId": "id"
}
```

### DELETE /users

Delete a user by uid. Payload example:

```json
{
    "userId": "id"
}
```

### GET /users/{email}

Get user by email.

## Docker image

You can get the docker image at:

```shell
docker pull apol12/whub_api
```

## Contact

You can get in touch at: nikitin.ivan.dev@gmail.com
