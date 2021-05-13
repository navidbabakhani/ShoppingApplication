# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.5/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Technologies Used 
* Java 8
* Spring Boot (2.4.5) latest version
* Spring MVC
* Spring Data JPA + Hibernate
* Spring Security
* PostgreSql
* Thymeleaf
* Maven
* Tomcat

### Features
* User login/sign-up
* 2 roles: ADMIN and USER
* ADMIN can have CRUD on products, categories, and users
* ADMIN can block/unblock users
* USER can see just products
* USER cannot delete or add products
* USER can review products (write comment and rate the product)

### Installation
First, create a postgreSql database using this command:
`createdb -h localhost -p 5432 -U postgres shoppingdb`

in `application.properties` file, search for `spring.jpa.hibernate.ddl-auto` and set it to `create-drop` or `update` for initializing application.
For the production environment, this line should be set to `validate`

### Tests
A few unit tests to show you my ability to write spring unit tests

### How to use
After running the application, you can browse `localhost:8080`

The default ADMIN account for initial use and define other products/categories/users is:
`user: admin pass: 123`

There are several urls for searching products which are accessible by USER role:

* products/search/{id}
* products/searchbycode/{code}
* products/searchbyname/{name}
* products/searchbyprice/min/{min}
* products/searchbyprice/max/{max}
* products/searchbyprice/min/{min}/max/(max)

#### Preview:
[My App on heroku](https://shopping-app-egs.herokuapp.com)
