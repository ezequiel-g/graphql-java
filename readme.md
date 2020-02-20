# GraphQL Java Example.
graphql.version: 3.0.0, new versions required code changes.

## Requirements.
* a mongoDB with name `hackernews`.
* there are graphql command to send inserts below.

# MAVEN COMMAND
~~~
mvn jetty:run
~~~

*Maven Debugging options*
~~~
SET MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n"
~~~

# LOCAL URL
http://localhost:8080/

# check the console for, it need to be updated on index.html with the new bearer.
~~~
'Authorization': 'Bearer 5dadac6d02c13df6537abe5b',
~~~

## mongoDB create user manually.
~~~
{
    name: "user1",
    email: "user1@mail.com" ,
    password: "secret"
}
~~~

## mutation createUser (this was an intermediate step, that after authentication is being added does not works)
~~~
mutation createUser {
  createUser(
    name: "user1"
    authProvider: {
      email: "user1@mail.com"
      password: "secret"
    }
  )
}
~~~

## mutation signIn
~~~
mutation signIn {
  signinUser(
    auth: {
      email: "user1@mail.com",
      password: "secret"
    }) {
    token
    user {
      id
      name
    }
  }
}

mutation signIn {
  signinUser(
    auth: {
      email: "user2@mail.com",
      password: "secret2"
    }) {
    token
    user {
      id
      name
    }
  }
}
~~~

## mutation createLink
~~~
mutation createLink {
  createLink(url: "www.google.com", description: "search engine") {
    url
    description
  }
}

mutation createLink {
  createLink(url: "spring.io", description: "spring framework home") {
    url
    description
  }
}

mutation createLink {
  createLink(url: "mvnrepository.com", description: "maven repository central") {
    description
    postedBy {
      id
      name
    }
  }
}
~~~

# Queries examples
~~~
--- querying without fields, will return only ids.
{
  allLinks
}
is equivalent to
{
  allLinks {
    id
  }
}

--- querying for some extra fields
{
  allLinks {
    id
    url
    description
    postedBy {
      id
    }
  }
}

--- querying for all available fields
{
  allLinks {
    id
    url
    description
    postedBy {
      id
      name
      email
      password
    }
  }
}
~~~
# Queries with filters.
~~~
--- filter by description_contains
{
  allLinks (filter: { description_contains: "engine" } ) {
    id
    url
    description
    postedBy {
      id
      name
      email
      password
    }
  }
}

--- filter by url_contains
{
  allLinks (filter: { url_contains: "tube" } ) {
    id
    url
    description
    postedBy {
      id
      name
      email
      password
    }
  }
}

--- filter by both
{
  allLinks (filter: { description_contains: "e", url_contains: "o" } ) {
    id
    url
    description
    postedBy {
      id
      name
      email
      password
    }
  }
}

-- filter + pagination
{
  allLinks (filter: { description_contains: "e", url_contains: "o" } first: 2) {
    id
    url
    description
    postedBy {
      id
      name
      email
      password
    }
  }
}

-- filter + pagination
{
  allLinks (filter: { description_contains: "e", url_contains: "o" } skip: 3) {
    id
    url
    description
    postedBy {
      id
      name
      email
      password
    }
  }
}
~~~

## mutation createVote
~~~
mutation createVote {
  createVote(linkId: "5dadbf4c5cc91c5ca4602731", userId: "5dadac6d02c13df6537abe5b") {
    createdAt
    user
  }
}

mutation createVote {
  createVote(linkId: "5dadbf4c5cc91c5ca4602731", userId: "5dadac6d02c13df6537abe5b") {
    createdAt
    user {
      id
      name
    }
  }
}
~~~
