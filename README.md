# CRO(A Virtual Company Registrations Office)
CRO is a virtual Company Registrations Office(CRO)
A company is a legal form of business organisation. It is a separate legal entity and, therefore, is separate and distinct from those who run it.
This application about write a RESTFUL webservice that can be accessed via http/https.


## CRO Web services
I have created a tiny​ ​REST​ /​ ​JSON​ web service in Java using ​Spring(Boot, MVC, Security,Jpa and some other spring modules)​ with an API that supports the following:
 Create new company
* Get a list of all companies
* Get details about a company
* Able to update a company
* Able to add beneficial owner(s) of the company

### A company has the following attributes:
* Company ID
* Name
* Address
* City
* Country
* E-mail (not required)
* Phone Number (not required)
* One or more beneficial owner(s)-->stakeholders

● I have wrote some JUnit Tests abd tested APIs with integration tests.●

## Following is explaining in details how the web services can be queried using​ ​cURL​/Postman - README have written in​ ​Markdown​ as requested


### JavaScript client
No javascript client

### Code repository and deployed app
● Code have been pushed to public code repository:[CRO Project at Github.com](https://github.com/gnaderi/CRO.git)
● CRO project have been published on a [Heroku](https://www.heroku.com/): [Live CRO](TBD)

### Authentication​ to your web service, but propose a protocol / method and justify your choice
- I have implemented JSON Web Token. 
  - Just in nutshell I may justify my choice.
   - What is JSON Web Token?
   JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact and self-contained way for securely transmitting information between parties as a JSON object. This information can be verified and trusted because it is digitally signed. 
- I have implemented HTTPS(just a dummy [Self-Signed Certificate](https://www.sslshopper.com/article-how-to-create-a-self-signed-certificate.html) :D)  

### How can you make the service redundant? What considerations should you do?*
**Note:** I'll give my reasons on my interview ;).


## Project Structure and tools and frameworks
 * I have used Java 8 as requested/expected 
 * I have used spring for IOC, Spring JPA and Spring Boot 
 * Implemented JSON Web Token
 * I used H2 Database Engine - it is good for rapid prototyping and development, but not suitable for production at least in most cases. 
 * Implemented fully functional security module.
 * Implemented https assess port 8443
 * Docker file and  maven config implemented but had a issue with heath check accessing it, so I'll fix it later. 
 * Implemented swagger to design, build, document, and consume RESTful Web services. Get CRO swagger file on runtime:`https://localhost:8443/v2/api-docs`


### DB configuration
I have used an H2 database to keep the records.
- There is a script file that run at start-up and create some pre-defined record for created entities.
  - Script path:~/cro/src/main/resources/sql/CRO_DB_Schema.sql
  - The script file set it in application.properties to be run at starting of spring boot application
- There are three entities(Tables):
  - BENEFICIAL_OWNER
  - COMPANY
  - STAKEHOLDER
  
- There are also three table for storing authentication info
  - USER
  - ROLE
  - USER_ACCESS


## DB Schema/ ERD Diagram
- https://github.com/gnaderi/cro/wiki
- CRO ERD
![alt text](./src/main/resources/CRO_ERD.png)


## Api Documentation
I have used [Swagger](https://swagger.io/) for build documents for REST API Services.
There are two files:
- REST-API-DOC-swagger.json: It's the Json version of the REST API Doc and you could used it in[http://editor.swagger.io/](http://editor.swagger.io/) to visualize the Phone shop API.
- REST-API-DOC-swagger.yaml: It's YAML file that converted from Json to be used if you prefer YAML.

[Swagger](https://en.wikipedia.org/wiki/Swagger_(software)) is an open source software framework backed by a large ecosystem of tools 
that helps developers design, build, document, and consume RESTful Web services.
Swagger is the world’s largest framework of API developer tools for the OpenAPI Specification(OAS), enabling development across the entire API lifecycle, from design and documentation, to test and deployment.


 
Company Registrations Office REST API Interface

* GET /cro/companies
  - Get a list of all companies stored in application.

* POST /cro/companies
  - Receive a company details as a request to be created on Company Registration Office(CRO).

* PUT /cro/companies/{companyId}
  - Receive a company details as an update request to be updated on Company Registration Office.

* PUT /cro/companies/{companyName}
  - Search a company based on the received company Id.

* GET /cro/stakeholders
  - Get a list of all stakeholders stored in application.

* POST /cro/stakeholders
  - Receive a Stakeholder details as a request to register it at CRO.

* PUT /cro/stakeholders/{id}
  - Receive a Stakeholder details to update a Stakeholder on CRO.

* GET /cro/stakeholders/{name}
  - Search a stakeholder based on the received stakeholder name.

* GET /cro/stakeholders/{stakeholderId}
  - Search a stakeholder based on the received stakeholder name.

**Note:** To get a full documentation and details for request and response please:
* got to http://editor.swagger.io/
* Copy/Paste content of `resource/REST-API-DOC-swagger.json` file into the swagger editor
 to see the _**magic**_**!**

![Swagger API Doc](./src/main/resources/Swagger-Visualized.png)



## **Important!** all links are based on local deployment
I have deployed the application to:
* https://boiling-refuge-19519.herokuapp.com/cro 
* You will need to replace `https://localhost:8443/cro` or `http://localhost:8080/cro` with above URL to test the online one!

## Tools
Only tools and API was used to build this project is:
- Java 8 SDK
- Spring boot
- Maven
- H2 in memory database
- Swagger for API Documentations

## How To Run The CRO Application?
Use one of the several ways of running a Spring Boot application. Below are just three options:

1. Build using maven goal: `mvn clean package` and execute the resulting artifact as follows `java -jar cro-0.0.1-SNAPSHOT.jar` or
2. On Unix/Linux based systems: run `mvn clean package` then run the resulting jar as any other executable `./cro-0.0.1-SNAPSHOT.jar`
3. Build and start as a Docker container. Instructions at: [README](src/main/docker/README.md)





## How to Test/Play with my virtual Company Registration Office(CRO)?
- The best way to test or play around the application is by installing [Postman](https://www.getpostman.com/).
- I'll explain both cURL and Postman ways to call RESTful Apis  one is using Curl call from Linux terminal and second is using [Postman](https://www.getpostman.com/) chrome extension.

## Application Security

### First you will need the following basic pieces of information:

 * Client Id: croClientId
 * Secret: XY2371kmzoNzl
 * Non-admin username and password: 
   - User: ganderi  
   - Password: jwtpass
 * Admin User and pass
   - User: admin  
   - Password: jwtpass
 * Example of resource accessible to all authenticated users:
   - GET https://localhost:8443/cro/companies  
   - GET https://localhost:8443/cro/companies?name=naderihub  
   - GET https://localhost:8443/cro/companies/{companyId}/
 * Example of resource accessible to only an admin user:
   - POST https://localhost:8443/cro/companies 
   - PUT https://localhost:8443/cro/companies 
   

 #### Generate an access token

   Use the following generic commands to generate an access token based on user role:
   
    $ curl -i -u client:secret https://${SERVER_IP}:8443/oauth/token -d grant_type=password -d username=user -d password=pwd

   - For this specific application, to generate an access token for the `admin` user, run:
   
    $ curl -k -i -u  croClientId:XY2371kmzoNzl https://localhost:8443/oauth/token -d grant_type=password -d username=admin -d password=jwtpass
   
   - To generate an access token for the non-admin user  i.e gnaderi, run:
      
    $ curl -k -i -u  croClientId:XY2371kmzoNzl https://localhost:8443/oauth/token -d grant_type=password -d username=gnaderi -d password=jwtpass
   
   You'll receive a response similar to below:
   
    {
        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2FtcGxlSnd0UmVzb3VyY2VJZCJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1MjkzMTcyMjEsImF1dGhvcml0aWVzIjpbIlNUQU5EQVJEX1VTRVIiLCJBRE1JTl9VU0VSIl0sImp0aSI6IjU5MjI3YTQzLTM0OWEtNDIwOC1iNzQ2LWVjYWZkNjIwNmI0YiIsImNsaWVudF9pZCI6ImNyb0NsaWVudElkIn0.xXJRYfejuQoyJKIkU07uNKp1zpHpauHJZEqQngutPms",
        "token_type": "bearer",
        "expires_in": 43199,
        "scope": "read write",
        "jti": "59227a43-349a-4208-b746-ecafd6206b4b"
     }

#### Use the jwt token to access resources through your RESTful API
##### Access content available to all authenticated users(i.e gnaderi)
Use the generated token  as the value of the Bearer in the Authorization header as follows or use [Postman](https://www.getpostman.com/) with that token:
   
        curl -k -X GET \
        https://localhost:8443/cro/companies/ \
        -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2FtcGxlSnd0UmVzb3VyY2VJZCJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1MjkzMTcyMjEsImF1dGhvcml0aWVzIjpbIlNUQU5EQVJEX1VTRVIiLCJBRE1JTl9VU0VSIl0sImp0aSI6IjU5MjI3YTQzLTM0OWEtNDIwOC1iNzQ2LWVjYWZkNjIwNmI0YiIsImNsaWVudF9pZCI6ImNyb0NsaWVudElkIn0.xXJRYfejuQoyJKIkU07uNKp1zpHpauHJZEqQngutPms' \
        -H 'Cache-Control: no-cache' \
        -H 'Postman-Token: 09d88b4c-83fa-4746-9b2e-61289bc1ecd2'
 
A screenshot of [Postman](https://www.getpostman.com/):

 ![Postman](./src/main/resources/postman.png)


###### The response for [Postman](https://www.getpostman.com/) or above cURL comand will all the companies stored at CRO(` https://localhost:8443/cro/companies`):
        
        [
             {
                 "Id": 1,
                 "Name": "naderihub",
                 "Address": "behan square",
                 "City": "dublin",
                 "Country": "ireland",
                 "Email": "nhl@gmail.com",
                 "PhoneNumber": "0174320",
                 "Stakeholders": [
                     {
                         "Id": 1,
                         "FirstName": "ghodrat",
                         "LastName": "naderi"
                     },
                     {
                         "Id": 2,
                         "FirstName": "naz",
                         "LastName": "mni"
                     }
                 ]
             },
             {
                 "Id": 2,
                 "Name": "google",
                 "Address": "silicon vally",
                 "City": "mountain view",
                 "Country": "usa",
                 "Email": "gooogle@goolge.com",
                 "PhoneNumber": "+1855 855 9772",
                 "Stakeholders": [
                     {
                         "Id": 3,
                         "FirstName": "richard",
                         "LastName": "haythornthwaite"
                     },
                     {
                         "Id": 4,
                         "FirstName": "silvio",
                         "LastName": "barzi"
                     },
                     {
                         "Id": 5,
                         "FirstName": "marc",
                         "LastName": "octavio"
                     }
                 ]
             },
             {
                 "Id": 3,
                 "Name": "mastercard.ie",
                 "Address": "mountain view, central park",
                 "City": "dublin",
                 "Country": "ireland",
                 "Email": "info@mastercard.ie",
                 "PhoneNumber": "012178600",
                 "Stakeholders": [
                     {
                         "Id": 6,
                         "FirstName": "larry",
                         "LastName": "page"
                     },
                     {
                         "Id": 7,
                         "FirstName": "eric",
                         "LastName": "schmidt"
                     },
                     {
                         "Id": 8,
                         "FirstName": "sergey",
                         "LastName": "brin"
                     }
                 ]
             }
         ]

##### Access content available only to an `admin` user:
As with the previous example first generate an access token for the admin user with the credentials provided above then run       
1. Generate access token for Admin:
       
       {
           "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2FtcGxlSnd0UmVzb3VyY2VJZCJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1MjkzMjAxMzgsImF1dGhvcml0aWVzIjpbIlNUQU5EQVJEX1VTRVIiLCJBRE1JTl9VU0VSIl0sImp0aSI6Ijc3ZmU2NzQ1LWQzNDQtNDk5My1iMTdiLTExYTg3M2FhODU5MSIsImNsaWVudF9pZCI6ImNyb0NsaWVudElkIn0.JqQ_FSrQWpJE9mg90DrNO_FBir8UVk2HNdeYPH1i3z8",
           "token_type": "bearer",
           "expires_in": 43199,
           "scope": "read write",
           "jti": "77fe6745-d344-4993-b17b-11a873aa8591"
        }
 
2.  Then register/create a company:

        curl -k -X POST \
           https://localhost:8443/cro/companies/ \
               -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2FtcGxlSnd0UmVzb3VyY2VJZCJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1MjkzMTcyMjEsImF1dGhvcml0aWVzIjpbIlNUQU5EQVJEX1VTRVIiLCJBRE1JTl9VU0VSIl0sImp0aSI6IjU5MjI3YTQzLTM0OWEtNDIwOC1iNzQ2LWVjYWZkNjIwNmI0YiIsImNsaWVudF9pZCI6ImNyb0NsaWVudElkIn0.xXJRYfejuQoyJKIkU07uNKp1zpHpauHJZEqQngutPms' \
           -H 'Cache-Control: no-cache' \
           -H 'Content-Type: application/json' \
           -H 'Postman-Token: e9c3a266-6198-4990-85fa-7fbbb2c74c57' \
           -d '{
            "Name": "Facebook.ie",
            "Address": "Hanover Reach 5/7 Hanover Quay",
            "City": "dublin",
            "Country": "ireland",
            "Email": "info@facebook.ie",
            "PhoneNumber": "0174320",
            "Stakeholders": [1,2]
         }
            
3. The result will be like below:
    
        {
          "Id": 4,
          "Name": "Facebook.ie",
          "Address": "Hanover Reach 5/7 Hanover Quay",
          "City": "dublin",
          "Country": "ireland",
          "Email": "info@facebook.ie",
          "PhoneNumber": "0174320"
        }          
        
##### Update a company
    curl -X PUT \
      https://localhost:8443/cro/companies/2 \
      -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2FtcGxlSnd0UmVzb3VyY2VJZCJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1Mjk0MDg4MTQsImF1dGhvcml0aWVzIjpbIlNUQU5EQVJEX1VTRVIiLCJBRE1JTl9VU0VSIl0sImp0aSI6ImU3ZGVlNWI4LTk1MjgtNGZiNy04MTllLTQ0ZWRhY2JlZjlkMCIsImNsaWVudF9pZCI6ImNyb0NsaWVudElkIn0.u2i4gpBN_ARWr64mM7dEmN5VEa1D7lfKthmvYqI9NtY' \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: 40ad8785-a393-4565-b6c2-74e87f4ac42a' \
      -d '{
       "Name": "Facebook.ie",
       "Address": "Hanover Reach 5/7 Hanover Quay",
       "City": "dublin",
       "Country": "ireland",
       "Email": "info@facebook.ie",
       "PhoneNumber": "0174320"
    }'
    
    
##### Search for a company with a `name=mastercard` as query param

    curl -X GET \
      'https://localhost:8443/cro/companies?name=mastercard' \
      -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2FtcGxlSnd0UmVzb3VyY2VJZCJdLCJ1c2VyX25hbWUiOiJnbmFkZXJpIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTUyOTM4NjE3NiwiYXV0aG9yaXRpZXMiOlsiU1RBTkRBUkRfVVNFUiJdLCJqdGkiOiJhMTc0MGQ0Yi1jZTg1LTRkYzctYTIzZi1iNTM0NGM0ZTIwYjMiLCJjbGllbnRfaWQiOiJjcm9DbGllbnRJZCJ9.xyMJuM_AXQGnhFg-JSXsNDUlr5WlPmYheUAJOFO4wU0' \
      -H 'Cache-Control: no-cache' \
      -H 'Postman-Token: b6bd44c5-427e-458c-b1f1-198ddeb2264b'
      


### Same story to register/update/search/get a stakeholder
####List of stakeholders
    curl -X GET \
      https://localhost:8443/cro/stakeholders \
      -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2FtcGxlSnd0UmVzb3VyY2VJZCJdLCJ1c2VyX25hbWUiOiJnbmFkZXJpIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTUyOTM4NjE3NiwiYXV0aG9yaXRpZXMiOlsiU1RBTkRBUkRfVVNFUiJdLCJqdGkiOiJhMTc0MGQ0Yi1jZTg1LTRkYzctYTIzZi1iNTM0NGM0ZTIwYjMiLCJjbGllbnRfaWQiOiJjcm9DbGllbnRJZCJ9.xyMJuM_AXQGnhFg-JSXsNDUlr5WlPmYheUAJOFO4wU0' \
      -H 'Cache-Control: no-cache' \
      -H 'Postman-Token: 648ef95a-56da-44a9-b172-173f5c2b8d4e'
      
#### List of stakeholders based on firstName and lastName   
    curl -X GET \
      'https://localhost:8443/cro/stakeholders?firstname=larry&lastname=page' \
      -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2FtcGxlSnd0UmVzb3VyY2VJZCJdLCJ1c2VyX25hbWUiOiJnbmFkZXJpIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTUyOTM4NjE3NiwiYXV0aG9yaXRpZXMiOlsiU1RBTkRBUkRfVVNFUiJdLCJqdGkiOiJhMTc0MGQ0Yi1jZTg1LTRkYzctYTIzZi1iNTM0NGM0ZTIwYjMiLCJjbGllbnRfaWQiOiJjcm9DbGllbnRJZCJ9.xyMJuM_AXQGnhFg-JSXsNDUlr5WlPmYheUAJOFO4wU0' \
      -H 'Cache-Control: no-cache' \
      -H 'Postman-Token: 5f115b8b-e86f-45cc-a8f5-9701d07c8b0d'
      
#### Create a Stakeholder
    curl -X POST \
      https://localhost:8443/cro/stakeholders \
      -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsic2FtcGxlSnd0UmVzb3VyY2VJZCJdLCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1Mjk0MDg4MTQsImF1dGhvcml0aWVzIjpbIlNUQU5EQVJEX1VTRVIiLCJBRE1JTl9VU0VSIl0sImp0aSI6ImU3ZGVlNWI4LTk1MjgtNGZiNy04MTllLTQ0ZWRhY2JlZjlkMCIsImNsaWVudF9pZCI6ImNyb0NsaWVudElkIn0.u2i4gpBN_ARWr64mM7dEmN5VEa1D7lfKthmvYqI9NtY' \
      -H 'Cache-Control: no-cache' \
      -H 'Content-Type: application/json' \
      -H 'Postman-Token: 456a3a35-e75d-4a38-9553-b6a27b804308' \
      -d '{
        "FirstName":"root",
        "LastName":"safe"
    }'
    
    
    
    
    
    
    
    





   