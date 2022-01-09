# **OOP-project**
University project for the object oriented programming course.

## **Overview**
This project aims to create a simple java application using the Springboot framework. This application runs a server that provides a local RESTful API over HTTP protocol, for the analysis of the people engagement on Twitter, based on the responses on some tweets. This application use the Twitter API to retrieve the tweets informations.

# **Index**

- [**Project struscture**](#project-struscture)
- [**API**](#API)
    - [**Routes**](#Routes)
        - [**Requests for metadata**](#Requests-for-metadata)
        - [**Requests for raw tweets**](#Requests-for-raw-tweets)
        - [**Requests for tweets metrics**](#Requests-for-tweets-metrics)
    - [**Requests body documentation**](#Requests-body-documentation)
    - [**Filters documentation**](#Filters-documentation)
- [**Junit tests**](#Junit-tests)
- [**UML diagrams**](#UML-diagrams)

---
## **Project struscture**

Below is shown the structure of the project and what is the content of each folder, with a little description of each one.

```text


```

---
## **Project components**

In this section are explained the principal frameworks and components used in the project, with a little overview of each one, with an insight about how are used. 

### **SpringBoot framework**

### **Twitter API V2**

- **Authentication**


---
## **RESTful API docs**
This api expose

### **Routes**

- **Requests for raw tweets** </br>
    
    - **Reqest by tweets id**

        - **Metadata**

            ```json
            {
                "metadata":[
                    {
                        "field" : "tweetIds",
                        "type" : "JsonArray",
                        "content":[
                            "int"
                        ],
                        "description": "Array of ids of each tweet that should be returned in the response."
                    },
                    {
                        "field" : "filters",
                        "type" : "JsonArray",
                        "content":[
                            "JsonObject"
                        ],
                        "description": "Array of filters, follow the documentation for further details on filters."
                    }
                ]
            }
            ```

        - **example** </br>
            ```json
            {
                "tweetIds" : [
                    23454,
                    345676,
                    456778563
                ],
                "filters" : [
                    {
                        
                    },
                    {

                    }
                ] 
            }
            ```

    - **Reqest by user id**
        
        - **Metadata**

            <details>

            <summary>Click to expand the response json</summary>

            ```json
            {
                "metadata":[
                    {
                        "field" : "tweetIds",
                        "type" : "JsonArray",
                        "content":[
                            "int"
                        ],
                        "description": "Array of ids of each tweet that should be returned in the response."
                    },
                    {
                        "field" : "filters",
                        "type" : "JsonArray",
                        "content":[
                            "JsonObject"
                        ],
                        "description": "Array of filters, follow the documentation for further details on filters."
                    }
                ]
            }
            ```
            </details>

        - **example**

            <details>

            <summary>Click to expand the response json</summary>
            
            ```json
            {
                "tweetIds" : [
                    23454,
                    345676,
                    456778563
                ],
                "filters" : [
                    {
                        
                    },
                    {

                    }
                ] 
            }
            ```
            </details>

- **Requests for tweets metrics**
    
    - **Metadata**


    - **something**


- **Requests body documentation**

    - 

    - **Filters**

        - **Date filters**

        - **Metric filters**

        - **Message filters**
        

---
## **Junit tests**

---
## **UML diagrams**

---
## **USEFUL LINKS**

### SpringBoot 
- [SpringBoot initializer](https://start.spring.io/)

### Twitter API docs
- [Twitter API v2 homepage](https://developer.twitter.com/en/docs/twitter-api)
- [Twitter API v2: Tweets lookup](https://developer.twitter.com/en/docs/twitter-api/tweets/lookup/introduction)
- [Twitter API v2: Timelines](https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/introduction)

### API RESTful
- [SpringBoot initializer](https://start.spring.io/)
