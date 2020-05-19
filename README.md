# Technical Challenge Documentation

## Database Configuration
In order to configure the database you only need to run some files DBA user in PostgreSQL; the files are located in the following directory `technical-challenge/src/main/resources/scripts`

You must run the files in the following order:
 1. *add_role_database_script.sql*
 2. *add_entities_script.sql*
 3. *add_init_data.sql*

By default, the datasource properties are configured to use PostgreSQL from Heroku server; but you can change those parameters by adding the following properties to the `application.yml` located in the following folder into the project `technical-challenge/src/main/resources`

    url: jdbc:postgresql://localhost:5432/appDB
    username: challenge
    password: admin$1

## Build and Run Project
Since these is a Maven Project to get the build you only have to run a command using a Terminal or Command Line. First go to project directory and run the following command:

    mvn clean install

You will find the build a Jar file called `technical-challenge-0.0.1-SNAPSHOT.jar` into the `technical-challenge/target` directory.

As these is a Spring Boot Project, the Jar file has an embedded Tomcat server; to runs it you just have to execute the following command in a Terminal or Command Line:

    java -jar technical-challenge-0.0.1-SNAPSHOT.jar

## API Documentation

To get authentication you have to use the following URL `https://technical-challenge-app.herokuapp.com/api/login` using POST method and as body put a JSON like this:

    {
    	"username": "admin",
    	"password": "123456"
    }

If the login is success the token is returned in the response as a header named `Authorization` and is valid for 60 minutes

As initial data there are added two users "admin" and "user" both with "123456" as password with ADMIN and USER role respectively.

Logout URL is `https://technical-challenge-app.herokuapp.com/api/logout` using POST method but doesn't work properly.

To perform a security request you have to add the `Authorization` header with the token like this: 

    'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU4OTgzOTQxOX0.Inc0F6WiP4g4qmWLpjZjEKlwgjSWhmEcxIOc6g47ckHImXaU9xd8BlrWlIUOUf_LMxL9AuxrO9cRUpTjLG6RgA'

***NOTE:*** To test the API you can use Postman or use the API explorer which is exposed in `https://technical-challenge-app.herokuapp.com/api/explorer/index.html`  to setup the header on click en **Edit headers** button and the API URL only put `/api` and this will retrieve almost all exposed URLs in the API.

All URLs exposed in the API are the following:

    https://technical-challenge-app.herokuapp.com/api/accounts{?page,size,sort}
    https://technical-challenge-app.herokuapp.com/api/roles{?page,size,sort}
    https://technical-challenge-app.herokuapp.com/api/account-roles{?page,size,sort}
    https://technical-challenge-app.herokuapp.com/api/users{?page,size,sort}
    https://technical-challenge-app.herokuapp.com/api/movies{?page,size,sort}
    https://technical-challenge-app.herokuapp.com/api/movies/search/by-title{?title}
    https://technical-challenge-app.herokuapp.com/api/movies/search/custom-filter{?search,page,size,sort}
    https://technical-challenge-app.herokuapp.com/api/movie-aud-logs{?page,size,sort}
    https://technical-challenge-app.herokuapp.com/api/purchase-orders{?page,size,sort}
    https://technical-challenge-app.herokuapp.com/api/purchase-order-detail{?page,size,sort}
    https://technical-challenge-app.herokuapp.com/api/transaction-logs{?page,size,sort}

Those URL use GET, POST, PUT, PATCH and DELETE methods, for example: if you have `/api/accounts` URL this use those methods as follow:

 1. GET: `/api/accounts` get the *first 20* accounts contained in the *page 0*
 2. GET: `/api/accounts/1` get the account with *id=1*
 3. POST: `/api/accounts` this create a new account with the JSON info in body content
 4. PUT: `/api/accounts/1` update the complete account with *id=1*, so you need to send the complete object
 5. PATCH: `/api/accounts/1` update only the data included in JSON info in body content
 6. DELETE: `/api/accounts/1` delete the account with *id=1* without children

Logged users with role ADMIN can access to all URLs mention above, but logged users with role USER only have access to the following URLs:

 1. Only GET and PATCH methods:

	    https://technical-challenge-app.herokuapp.com/api/movies{?page,size,sort}
	    https://technical-challenge-app.herokuapp.com/api/purchase-orders{?page,size,sort}
	    https://technical-challenge-app.herokuapp.com/api/purchase-order-detail{?page,size,sort}

 2. POST method:

	    https://technical-challenge-app.herokuapp.com/api/purchase-orders{?page,size,sort}
	    https://technical-challenge-app.herokuapp.com/api/purchase-order-detail{?page,size,sort}

The URL that can be access without authentication are:

    https://technical-challenge-app.herokuapp.com/api/movies{?page,size,sort}
    https://technical-challenge-app.herokuapp.com/api/movies/{id}

To search by name you can use the following URL `https://technical-challenge-app.herokuapp.com/api/movies/search/by-title{?title}` where title is the parameter that you have to set to search movie by name.

An also there an endpoint that yo can do custom search an that URL is: `https://technical-challenge-app.herokuapp.com/api/movies/search/custom-filter{?search,page,size,sort}` and the parameters are described as follow:

 1. *search* param: to build the search parameter you have to use the follow operator

	 - Equal  `:` 
	 - Negation `!`
	 - Greater Than `>`
	 - Less Than `<`
	 - Like `~` with prefix and suffix `*`
	 - Left Parenthesis `(`
	 - Right Parenthesis `)`
	 - Or Condition `OR`
	 - And Condition `AND`

	As example you can can create a custom query like this: `/api/movies/search/custom-filter?search=title~*value_to_search* OR ( salePrice:39.99 AND availability:true )`; as you can see each condition is separated by a space.
	But this kind o search have an issue because yo can no search a text with spaces because break the query and throw an exception.

 2. *page* param: Just set the page number that start in zero; like this: `page=0`.
 3. *size* param: Just set the page size like this: `size=50`. By default is 20
 4. *sort* param: Sort the result you have to define the column and the order direction like this: `sort=title,desc`


....
