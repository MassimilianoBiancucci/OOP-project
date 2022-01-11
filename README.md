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
{ "<field>": "<operator>" }     // template #1
{ "<field>": {"$gt": "<operator's value>"}}     //templeate #2 

// complete example: 
// filter that filter only tweets created after the specified date
{ "created_at": {"$gt": "2022-01-06T18:29:41.000Z"}}
```
- #### **Filter operators** 
    There are tree types of operators, 2 normal and one special. The special ones are the `logical operators`, those opratars can be used either on other operators either on well formed filters, but not together.
    The other ones are the `match operators` that could be used for match or unmatch list of values while the `conditional operators` are used for specify interval of values accepted, below there are more accurate explenation of each type.

    - **Logical operators** </br>
        The `logical operators` can't be applied alone, they are used for:
        - filter combination or coniugation. 
        - operator combinations or coniugation.

        They are 3 operators, that permit to create unions, intersections or inversion on ensembles, if you consider all the tweets as the starting enseble and each well formed filter or operator as a subset of the general ensamble, these operators could be used for create ensables operations between these subsets.
        If the `logical operators` are used with other operators inside, they are considered operators and should be included inside a filter, if they are used with filters inside, they are considered filters itself.
        This type of operator should be used only on `operators` or only on `filters`, otherwise the mix of the two will bring to an exeception, and to the refuse of the request.

        <table style="width:100%" border="2" bordercolor = "#fffff">
        <tbody>
        <tr>
            <th>Symbol</th>
            <th>Description</th>
            <th>Example of condition</th>
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

    - **Match operators** </br>
        The `match operators` are used for filter tweets based on list of values that their fields should match or not match. This type of filters could be used on numeric values like metrics, on dates or on strings, the only limitation is that if used, the list of values inside should be of only one type and this tye should be coerent with the filter field, otherwise the request will throw an exception.
        <table style="width:100%" border="2" bordercolor = "#fffff">
        <tbody>
        <tr>
            <th>Symbol</th>
            <th>Description</th>
            <th>Example of condition</th>
        </tr>
        <tr>
            <td>$in</td>
            <td>Match any value in array</td>
            <td>{"$in" : [1000, 7520, ...]}</td>
        </tr>
        <tr>
            <td>$nin</td>
            <td>Not match any value in array</td>
            <td>{"$nin" : [1000, 7520, ...]}</td>
        </tr>
        </tbody>
        </table>


    - **Conditional operators** </br>
        The last one are the `conditional operators`, and can be used only on numeric values like metrics and on dates, so the can't be used only with strings. The usage if these operators with string values will throw an exception. Another exception will be raised even if these operators are applied to string fields.
        <table style="width:100%" border="2" bordercolor = "#fffff">
        <tbody>
        <tr>
            <th>Symbol</th>
            <th>Description</th>
            <th>Example of condition</th>
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
    There are 8 fields considered from this API on the tweets, and these are the only fields accepetd in the request body, any other field passed will bring to the rise of an exception and to the fail of the request. Below the list of fields:
    <table style="width:100%" border="2" bordercolor = "#fffff">
    <thead>
    <tr>
        <th>Field</th>
        <th>Type</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>retweet_count</td>
        <td>int</td>
        <td>
        The count of retweet on the original tweet, even if the current tweet is<br>
        retwetted from another author, this value is considered from the original one.<br>
        This field can be applied to match operators and to conditional operators,<br> not on logical operators.
        </td>
    </tr>
    <tr>
        <td>reply_count</td>
        <td>int</td>
        <td>The number of replies or comments to the tweet.<br>
        This field can be applied to match operators and to conditional operators,<br> not on logical operators.
        </td>
    </tr>
    <tr>
        <td>like_count</td>
        <td>int</td>
        <td>The count of likes.<br>
        This field can be applied to match operators and to conditional operators,<br> not on logical operators.
        </td>
    </tr>
    <tr>
        <td>quote_count</td>
        <td>int</td>
        <td>The number of quote, that are retweets with a comments ahead.<br>
        This field can be applied to match operators and to conditional operators,<br> not on logical operators.
        </td>
    </tr>
    <tr>
        <td>engagement</td>
        <td>int</td>
        <td>The value of engagement calculated from the tweet responses.<br>
        This field can be applied to match operators and to conditional operators,<br> not on logical operators.
        </td>
    </tr>
    <tr>
        <td>text</td>
        <td>String</td>
        <td>The text content of the tweet.<br>
        This field can be applied only to match operators.
        </td>
    </tr>
    <tr>
        <td>created_at</td>
        <td>Date</td>
        <td>The publication date of the tweet.<br>
        This field can be applied to match operators and to conditional operators,<br> not on logical operators.
        </td>
    </tr>
    </tbody>
    </table>

- #### **Filter combinations**

    In this section will be shown some examples of filters combinations, to highlight what can be passed to the API in the request body and what will throw an error.
    
    By combination of filters it is meant an extensive usage of the `logical operators` to combine other operators or filters, below the examples:
    - **Combinations of operators:**
        ```json
        {
            "filters":{
                "created_at":{
                    "$and":[
                        {"$not":{"$bt": ["2022-01-04T14:07:05Z", "2022-01-05T14:07:02Z"]}},
                        {"$gt": "2022-01-01T14:07:02Z"}
                    ]
                }
            }
        }
        ```
        This filter select a particular time interval, it select all the tweets of dates greater than the specified date and exclude from this interval one day, note that the operator between select the specified interval but the operator cogniugation specify that this sub interval is excluded.

        In this example is passed one filter for only one field, the date, and the operator applied to this filter is a composition of four operators.
        It's clear that this one is only one filter because there is only one field.

        Note that put another filter inside the and operator of the first filter would make no sense and would generate an error.
        
    - **Combinations of filters:**
        ```json
        {
            "filters":{
                "$or":[
                    {"like_count": {"$gt": 1000}},
                    {"created_at": {"$gt": "2022-01-01T14:07:02Z"}},
                    {"text": {"$in": ["deeplearning", "transformers", "cnn"]}}
                ]
            }
        }
        ```
        This filter select all the tweets in the given set that satisfy at least on of these conditions: has more than 1000 likes, or that are created after the given date or that contain in the text one word in the list.

        This case is clearly a combination of filters, in this case the logical operator is used for combine filters for differents fields together, more filters for the same fields are allowed but the insertion of another operator (without filters inside) in the $or operator would meaning nothing and would throw an error.

- #### **Filters package**
    
    In this section there is an insight on how filters works under the hood. 

    - #### **Inheritance and associations**
        This filters implementation can garant a good flexibility and interchangeability thanks to the extensive usage of classes Inheritance.
        As shown before there are three main componetns of each filter: the `field`, the `operator` and the `operator's values`. there are many field with different characteristics, and many operators that accept only certain type of values, so there is an abstract class for each one of this "abstract" concepts that are extended for each subcase. In this way each class that accept a certain abstract class can accept all the subclasses and apply a different behaviour for each one, for example accept it or throw an exception where the combination of filter, operator and operatorValues can't work together. 

        ![Filters package uml](UMLs/Filters%20package%20diagram.png)

    - Date filters


    - Metric filters


    - Message filters


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