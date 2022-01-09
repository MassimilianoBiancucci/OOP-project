# **OOP-project**
University project for the object oriented programming course.

## **Overview**
This project aims to create a simple java application using the Springboot framework. This application runs a Tomcat server that provides a local RESTful API over HTTP protocol, for the analysis of the people engagement on Twitter, based on the responses on some tweets. 

This application use the Twitter API to retrieve the tweets informations, applying to them modular filters, enabling the client to request metrics only on specific tweets.

# **Index**

- [**Project struscture**](#--project-struscture--)
- [**Project components**](#--project-components--)
  * [**SpringBoot framework**](#--springboot-framework--)
  * [**Twitter API V2**](#--twitter-api-v2--)
    + [**Authentication**](#--authentication--)
- [**RESTful API docs**](#--restful-api-docs--)
  * [**Routes**](#--routes--)
    + [**Requests for raw tweets** </br>](#--requests-for-raw-tweets-----br-)
    + [**Reqest by tweets id**](#--reqest-by-tweets-id--)
    + [**Reqest by user id**](#--reqest-by-user-id--)
    + [**Requests for tweets metrics**](#--requests-for-tweets-metrics--)
    + [**Requests body documentation**](#--requests-body-documentation--)
- [**Junit tests**](#--junit-tests--)
- [**UML diagrams**](#--uml-diagrams--)
- [**USEFUL LINKS**](#--useful-links--)
  * [SpringBoot](#springboot)
  * [Twitter API docs](#twitter-api-docs)


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

- #### **Authentication**


---
## **RESTful API docs**

### **API overview**

### **Filters**
Filters are an important part of the API, they can be passed trough the body of the request, in a json format, to determine which tweets will be used for the engagement evaluation, they could be selected based on the text of the tweet, or maybe based on the date because only a certain temporal interval matter.

In this implementation of these filters there are 3 important chuncks that make the filter, those are: `field`, `operator` and `operator's value`. The operators and the values inside, specifies the requirements that the field must satisfy, and the field specifie to which field the requirement should be applied.

Below there is an example:
```json
// examples of syntax
{ <field>: <operator> } // template #1
{ <field>: {"$gt": <operator's value>}} //templeate #2 

// real example: 
// filter that filter only tweets created after the specified date
{ "created_at": {"$gt": "2022-01-06T18:29:41.000Z"}}
```
- #### **Filter operators** 
    There are tree types of operators, 2 normal and one special. The special ones are the `logical operators`, those opratars can be used either on other operators either on well formed filters, but not together.
    The other ones are the `match operators` that could be used for match or unmatch list of values while the `conditional operators` are used for specify interval of values accepted, below there are more accurate explenation of each type.

    - **Logical operators** </br>
        The logical operators can't be applied alone, they are used for:
        - filter combination or inversion. 
        - operator combinations or invertion.

        They are 3 operators, that permit to create unions, intersections or inversion on ensembles, if you consider all the tweets as the starting enseble and each well formed filter or operator as a subset of the general ensamble, these operators could be used for create ensables operations between these subsets.
        If the `logical operators` are used with other operators inside, they are considered operators and should be included inside a filter, if they are used with filters inside, they are considered filters itself.
        This type of operator should be used only on `operators` or only on `filters`, otherwise the mix of the thwo will bring to an exeception, and to the refuse of the request.

        <table style="width:100%" border="2" bordercolor = "#fffff">
        <tbody>
        <tr>
            <td>symbol</td>
            <td>description</td>
            <td>example of condition</td>
        </tr>
        <tr>
            <td>$not</td>
            <td>Negation logical operator</td>
            <td>{"$not" : {filter or operator}}</td>
        </tr>
        <tr>
            <td>$or</td>
            <td>Logical operator</td>
            <td>{"$or": [{filter1},{filter2},...]}</td>
        </tr>
        <tr>
            <td>$and</td>
            <td>Logical operator</td>
            <td>{"$and": [{filter1},{filter2},...]}</td>
        </tr>
        </tbody>
        </table>

    - **Match operators**

        <table style="width:100%" border="2" bordercolor = "#fffff">
        <tbody>
        <tr>
            <td>symbol</td>
            <td>description</td>
            <td>example of condition</td>
        </tr>
        <tr>
            <td>$in</td>
            <td>Match any value in array</td>
            <td>{"$in" : [value1, value2, ...]}</td>
        </tr>
        <tr>
            <td>$nin</td>
            <td>Not match any value in array</td>
            <td>{"$nin" : [value1, value2, ...]}</td>
        </tr>
        </tbody>
        </table>


    - **Conditional operators**

        <table style="width:100%" border="2" bordercolor = "#fffff">
        <tbody>
        <tr>
            <td>symbol</td>
            <td>description</td>
            <td>example of condition</td>
        </tr>
        <tr>
            <td>$gt</td>
            <td>&gt;</td>
            <td>{"$gt": 10000}</td>
        </tr>
        <tr>
            <td>$gte</td>
            <td>&gt;=</td>
            <td>{"$gte": "2022-01-06T18:29:41.000Z"}</td>
        </tr>
        <tr>
            <td>$lt</td>
            <td>&lt;</td>
            <td>{"$lt": 10000}</td>
        </tr>
        <tr>
            <td>$lte</td>
            <td>&lt;=</td>
            <td>{"$lte": "2022-01-06T18:29:41.000Z"}</td>
        </tr>
        <tr>
            <td>$bt</td>
            <td>&lt;= value &lt;=</td>
            <td>{"$bt": [1000, 2000]}</td>
        </tr>
        </tbody>
        </table>

- #### **Specific fields and properties**

    - **Date filters**

    - **Metric filters**
        
    - **Message filters**

- #### **Filter combinations**


### **Routes**

- #### **Requests for raw tweets**

    - **Input**

        - **Reqest by tweets id**

            - **Metadata**

                <details>

                <summary>Click to expand json</summary>

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

            - **Example**

                <details>

                <summary>Click to expand json</summary>

                ```json
                {
                    "tweetIds" : [
                        23454,
                        345676,
                        456778563
                    ],
                    "filters" : [
                        {
                            "$or": [
                                {
                                    filter1
                                },
                                {
                                    filter1
                                }
                            ]
                        },
                        {

                        }
                    ] 
                }
                ```
                </details>

        - **Reqest by user id**
            
            - **Metadata**

                <details>

                <summary>Click to expand json</summary>

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

            - **Example**

                <details>

                <summary>Click to expand json</summary>

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

    - **Output**

        - **Metadata**

            <details>

            <summary>Click to expand json</summary>

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

        - **Example**

            <details>

            <summary>Click to expand json</summary>

            ```json
            {
                "tweetIds" : [
                    23454,
                    345676,
                    456778563
                ],
                "filters" : [
                    {
                        "$or": [
                            {
                                filter1
                            },
                            {
                                filter1
                            }
                        ]
                    },
                    {

                    }
                ] 
            }
            ```
            </details>

- #### **Requests for tweets metrics**
    
    - #### **Reqest by tweets id**

        - **Metadata**

            <details>

            <summary>Click to expand json</summary>

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

        - **Example**

            <details>

            <summary>Click to expand json</summary>

            ```json
            {
                "tweetIds" : [
                    23454,
                    345676,
                    456778563
                ],
                "filters" : [
                    {
                        "$or": [
                            {
                                filter1
                            },
                            {
                                filter1
                            }
                        ]
                    },
                    {

                    }
                ] 
            }
            ```
            </details>

    - #### **Reqest by user id**
        
        - **Metadata**

            <details>

            <summary>Click to expand json</summary>

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

        - **Example**

            <details>

            <summary>Click to expand json</summary>

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


- #### **Requests body documentation**

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

### Docs tools
- [markdown-toc](http://ecotrust-canada.github.io/markdown-toc/)
- [tables generator](https://www.tablesgenerator.com/html_tables)