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
            <td>{"$or": [{filter1 or operator1},{filter2 or operator2},...]}</td>
        </tr>
        <tr>
            <td>$and</td>
            <td>Logical operator</td>
            <td>{"$and": [{filter1 or operator1},{filter2 or operator2},...]}</td>
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
        The last one are the `conditional operators`, and can be used only on numeric values like metrics and on dates, so they can't be used only with strings. The usage if these operators with string values will throw an exception. Another exception will be raised even if these operators are applied to string fields.
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
This api has two main functionalities, the first is the request of the raw tweets directly taken from the twitter api, with the application of the passed filters. The second functionality is the request of some statistics on the tweets requested after the application of the passed filters.
Each functionality can be requested with two different routes (four routes in total), each one enable to request the same functionality in two different ways. The fisrt by passing a list of tweets ids in the request, the second by specifying the user id in the route.

Note that for each route are present on more route for the metadata request, that explain with a schematic format the structure of the response, with field names, fields types, and some special structure for explain the inner structure of nested objects and arrays. The documentation for the metadata json is present below: [Metadata formt documentation](#metadata-formt-documentation).

Here there is the comprensive table with all the routes of this API:

<table 
style="width:100%" 
border="2" 
bordercolor = "#fffff">
<tbody>
<tr>
<th style="width:5%"> Type</th>
<th style="width:25%">Route</th>
<th>description</th>
</tr>
<tr>
<td> GET</td>
<td>/tweets</td>
<td>Route for raw tweets request</td>
</tr>
<tr>
<td> GET</td>
<td>/tweets/metadata</td>
<td>Route for json request/response body metadata of: /tweets</td>
</tr>
<tr>
<td> GET</td>
<td>/user/:userId</td>
<td>Route raw tweets request by :userId parameter</td>
</tr>
<tr>
<td> GET</td>
<td>/user/metadata</td>
<td>Route for json request/response body metadata of: /user/:userId </td>
</tr>
<tr>
<td> GET</td>
<td>/tweets/metrics</td>
<td>Route for tweets metrics request</td>
</tr>
<tr>
<td> GET</td>
<td>/tweets/metrics/metadata</td>
<td>Route for json request/response body metadata of: /tweets/metrics</td>
</tr>
<tr>
<td> GET</td>
<td>/user/:userId/metrics</td>
<td>Route for tweets metrics request by :userId parameter</td>
</tr>
<tr>
<td> GET</td>
<td>/user/metrics/metadata</td>
<td>Route for json request/response body metadata of: /user/:userId/metrics</td>
</tr>
</tbody>
</table>

Below all the routes are explained in detail, with example of requests and examples of responses for each case. 

- #### **Requests for raw tweets**
    This funcionality enable to request the raw tweets as they are returned by the twitter API, with the possibility of applying to them some filters if needed, so it's possible to query the tweets with the twitter api format, but for example applying to them a filter for retrive only tweets with certain words in the text field, or all other type of filters above explained. As metioned there are two methods for retrive these tweet's raw data, by a list of tweets ids or by a twitter user id and specifing the number of tweets needed, note that in that way only the last tweets are taken.

    - #### **Input**
        In this section there are the two methods routes for the <b>requests of raw tweets</b> explained in detail with a formal documentation that explain each parameter in the route and in the request body with an example request for the two routes.

        - #### **Reqest by tweets id**
            In the table there are the two routes relative to the service for the request of the raw tweets by a list of tweets ids. The first is the route for the effective service, the second one always returns the same result, a special json that encode the structure of the response with types and descriptions of each field.
            <table 
                style="width:100%" 
                border="2" 
                bordercolor = "#fffff">
            <tbody>
            <tr>
            <th style="width:5%"> Type</th>
            <th style="width:25%">Route</th>
            <th>description</th>
            </tr>
            <tr>
            <td> GET</td>
            <td>/tweets</td>
            <td>Route for raw tweets request</td>
            </tr>
            <tr>
            <td> GET</td>
            <td>/tweets/metadata</td>
            <td>Route for json request/response body metadata of: /tweets</td>
            </tr>
            </tbody>
            </table>

            - #### **Request parameters**
                In this section there is the list of parameters that should be in the request, in the body and in the route:
                - #### **Route parameters**
                    The request by tweets id has a static route, all the parameters are in the request body.
                - #### **Request body parameters**
                    The request body must be in json format, below the parameters:
                    - `tweetIds`: json array of int, that constain the id's of each tweet that the service will query from the Twitter API, and where the passed filters will be applied.
                    - `filters`: json object that can contain one filter or one logical operator, note that match operators or cnditional operators aren't allowed in the first level. more than one filter can be passed inside a logical operator `$and` or `$or`, more than one operator can be passed inside one filter. For further detail on filters and operators nesting, check the [Filters](#filters) section.

            - #### **Metadata**
                For the metadata route there aren't any parameters to pass. Note that this method differ from the last method only for the absence of the list of tweets ids in the request body. Below there is the result of the API call to the `/tweets/metadata` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "metadata":{
                        "input": [
                            {
                                "field" : "tweetIds",
                                "type" : "JsonArray",
                                "description": "Array of ids of each tweet that should be returned in the response.",
                                "content":[
                                    "int"
                                ]
                            },
                            {
                                "field" : "filters",
                                "type" : "JsonObject",
                                "description": "Object that contain combinations of filters, follow the documentation for further details on filters.",
                                "content":[
                                    "JsonObject"
                                ]
                            }
                        ],
                        "output": [
                            {
                                "field" : "tweets",
                                "type" : "JsonArray",
                                "description": "An array of tweets, each JsonObject inside this array, is a tweet.",
                                "contentTypes":[
                                    "JsonObject"
                                ],
                                "content":{
                                    "obj1": [
                                        {
                                            "field" : "id",
                                            "type" : "String",
                                            "description": "tweet id."
                                        },
                                        {
                                            "field" : "text",
                                            "type" : "String",
                                            "description": "tweet text content."
                                        },
                                        {
                                            "field" : "created_at",
                                            "type" : "String",
                                            "description": "date of creation of the corresponding tweet."
                                        },
                                        {
                                            "field" : "public_metrics",
                                            "type" : "JsonObject",
                                            "description": "json object that contain all the metrics needed for the engagement anlysis: retweet_count, reply_count, like_count, quote_count.",
                                            "contentTypes":[
                                                "int"
                                            ],
                                            "content":[
                                                {
                                                    "field" : "retweet_count",
                                                    "type" : "int",
                                                    "description": "number of retweet associated to the corresponding tweet."
                                                },
                                                {
                                                    "field" : "reply_count",
                                                    "type" : "int",
                                                    "description": "Number of comments to the corresponding tweet."
                                                },
                                                {
                                                    "field" : "like_count",
                                                    "type" : "int",
                                                    "description": "number of likes associated to the corresponding tweet."
                                                },
                                                {
                                                    "field" : "quote_count",
                                                    "type" : "int",
                                                    "description": "Number of retweet with a quote by the user that retweeted that tweet."
                                                }
                                            ]
                                        }
                                    ]
                                }
                            }
                        ]
                    }
                }
                ```
                </details> </br>

            - #### **Example**
                Below there is an example of API call to the `/tweets` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "tweetIds" : [
                        23454,
                        345676,
                        456778563
                    ],
                    "filters" : {
                        "$or": [
                            {"created_at": {"$bt": ["2022-01-04T14:07:05Z", "2022-01-05T14:07:02Z"]}},
                            {"created_at": {"$bt": ["2022-01-07T14:07:05Z", "2022-01-08T14:07:02Z"]}},
                            {"like_count": {"$gt": 1000}}
                        ]
                    {
                }
                ```
                </details> </br>

        - #### **Reqest by user id**
            
            <table 
                style="width:100%" 
                border="2" 
                bordercolor = "#fffff">
            <tbody>
            <tr>
            <th style="width:5%"> Type</th>
            <th style="width:25%">Route</th>
            <th>description</th>
            </tr>
            <tr>
            <td> GET</td>
            <td>/user/:userId</td>
            <td>Route raw tweets request by :userId parameter</td>
            </tr>
            <tr>
            <td> GET</td>
            <td>/user/metadata</td>
            <td>Route for json request/response body metadata of: /user/:userId </td>
            </tr>
            </tbody>
            </table>

            - #### **Request parameters**
                In this section there is the list of parameters that should be in the body and in the route:
                
                - #### **Route parameters**
                    The request by user id has only one parameter in the route, showed in the above table with the following placeholder:

                    - `:userId`: should be replaced with the Twitter user id of the user of which we want retrive the tweets. this parameter its simply a number. 

                - #### **Request body parameters**
                    The request body must be in json format, below the parameters:
                    - `filters`: json object that can contain one filter or one logical operator, note that match operators or cnditional operators aren't allowed in the first level. more than one filter can be passed inside a logical operator `$and` or `$or`, more than one operator can be passed inside one filter. For further detail on filters and operators nesting, check the [Filters](#filters) section.

            - #### **Metadata**
                For the metadata route there aren't any parameters to pass. Note that this method differ from the last method only for the absence of the list of tweets ids in the request body. Below there is the result of the API call to the `/user/metadata` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "metadata":{
                        "input": [
                            {
                                "field" : "filters",
                                "type" : "JsonArray",
                                "description": "Array of filters, follow the documentation for further details on filters.",
                                "content":[
                                    "JsonObject"
                                ]
                            }
                        ],
                        "output": [
                            {
                                "field" : "tweets",
                                "type" : "JsonArray",
                                "description": "An array of tweets, each JsonObject inside this array, is a tweet.",
                                "contentTypes":[
                                    "JsonObject"
                                ],
                                "content":{
                                    "obj1": [
                                        {
                                            "field" : "id",
                                            "type" : "String",
                                            "description": "tweet id."
                                        },
                                        {
                                            "field" : "text",
                                            "type" : "String",
                                            "description": "tweet text content."
                                        },
                                        {
                                            "field" : "created_at",
                                            "type" : "String",
                                            "description": "date of creation of the corresponding tweet."
                                        },
                                        {
                                            "field" : "public_metrics",
                                            "type" : "JsonObject",
                                            "description": "json object that contain all the metrics needed for the engagement anlysis: retweet_count, reply_count, like_count, quote_count.",
                                            "contentTypes":[
                                                "int"
                                            ],
                                            "content":[
                                                {
                                                    "field" : "retweet_count",
                                                    "type" : "int",
                                                    "description": "number of retweet associated to the corresponding tweet."
                                                },
                                                {
                                                    "field" : "reply_count",
                                                    "type" : "int",
                                                    "description": "Number of comments to the corresponding tweet."
                                                },
                                                {
                                                    "field" : "like_count",
                                                    "type" : "int",
                                                    "description": "number of likes associated to the corresponding tweet."
                                                },
                                                {
                                                    "field" : "quote_count",
                                                    "type" : "int",
                                                    "description": "Number of retweet with a quote by the user that retweeted that tweet."
                                                }
                                            ]
                                        }
                                    ]
                                }
                            }
                        ]
                    }
                }
                ```
                </details> </br>

            - #### **Example**
                Below there is an example of API call to the `/user/:userId` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "tweetIds" : [
                        23454,
                        345676,
                        456778563
                    ],
                    "filters" : {
                        "$or": [
                            {"created_at": {"$bt": ["2022-01-04T14:07:05Z", "2022-01-05T14:07:02Z"]}},
                            {"created_at": {"$bt": ["2022-01-07T14:07:05Z", "2022-01-08T14:07:02Z"]}},
                            {"like_count": {"$gt": 1000}}
                        ]
                    {
                }
                ```
                </details> </br>

    - #### **Output**
        In this section there is the output explenation of the response from the api that return calling the `/tweets` and `/user/:userId` routes, note that the result from this two routes are the same. All the variables in the respose of this routes are a subset of the parameters that could be retrived directly from the Twitter API.

        - #### **Response body parameters**
            In this section are explained all the parameters that return from the call of the above mentioned routes:
            - `tweets`: the tweets field it's an array of json obects, each java object contain all the information of one tweetthat are: id, text, created_at and public metrics.
            - `id`: This field is contained inside each object inside the <b>tweets</b> array, it's an <b>int</b> field and indicates the id of the corresponding tweet.
            - `text`: This field is contained inside each object inside the <b>tweets</b> array, it's a <b>String</b> field and indicates the message of the corresponding tweet.
            - `created_at`:This field is contained inside each object inside the <b>tweets</b> array, it's a <b>String</b> field that encode a timestamp and indicates the date and time of creation of the corresponding tweet.
            - `public_metrics`:This field is contained inside each object inside the <b>tweets</b> array, it's a <b>json object</b> field and containes the metrics relativ to the engagement of the corresponding tweet, the field contained inside are: retweet_count, reply_count, like_count, quote_count.
            - `retweet_count`:This field is contained inside the <b>public_metrics</b> object, it's a <b>float</b> field and indicates the number of retweets of the corresponding tweet.
            - `reply_count`:This field is contained inside the <b>public_metrics</b> object, it's a <b>float</b> field and indicates the number of replies of the corresponding tweet.
            - `like_count`:This field is contained inside the <b>public_metrics</b> object, it's a <b>float</b> field and indicates the number of likes of the corresponding tweet.
            - `quote_count`:This field is contained inside the <b>public_metrics</b> object, it's a <b>float</b> field and indicates the number of quotes of the corresponding tweet.

        - #### **Example**
            In this section there is an example of response from the api that could be returned from a call to the `/tweets` or `/user/:userId` routes, passing the correct json request body:
            <details>

            <summary style="color: red"><b> Click to expand json </b></summary>

            ```json
            {
                "tweets": [
                    {
                        "id": "23454",
                        "text": "just setting up my twttr",
                        "created_at": "2006-03-21T20:50:14.000Z",
                        "public_metrics": {
                            "retweet_count": 121701,
                            "reply_count": 10062,
                            "like_count": 173362,
                            "quote_count": 17802
                        }
                    },
                    {
                        "id": "345676",
                        "text": "just setting up my twttr",
                        "created_at": "2006-03-21T20:51:43.000Z",
                        "public_metrics": {
                            "retweet_count": 6105,
                            "reply_count": 147,
                            "like_count": 4449,
                            "quote_count": 262
                        }
                    },
                    {
                        "id": "456778563",
                        "text": "just setting up my twttr",
                        "created_at": "2006-03-21T21:00:54.000Z",
                        "public_metrics": {
                            "retweet_count": 4767,
                            "reply_count": 77,
                            "like_count": 3491,
                            "quote_count": 152
                        }
                    }
                ]
            }
            ```
            </details> </br>

- #### **Requests for tweets metrics**
    This functionality enable to request statistics on the engagement that are relative to a group of tweets passed through a list of tweets ids or by a twitter user id and specifing the number of tweets needed. 
    
    These statistics are calculated based on the tweets passed in one of the two ways, but if there are filters specified in the json request body, the tweets used for the statistics calculation will be only the filterd tweets.

    - #### **Input**
        In this section there are the two routes for the requests of <b>tweets engagement metrics</b>, showed in detail with a formal documentation that explain each parameter in the route and in the request body with an example request for the two routes.

        - #### **Reqest by tweets id**

            <table 
                style="width:100%"
                border="2" 
                bordercolor = "#fffff">
            <tbody>
            <tr>
            <th style="width:5%"> Type</th>
            <th style="width:25%">Route</th>
            <th>description</th>
            </tr>
            <tr>
            <td> GET</td>
            <td>/tweets/metrics</td>
            <td>Route for tweets metrics request</td>
            </tr>
            <tr>
            <td> GET</td>
            <td>/tweets/metrics/metadata</td>
            <td>Route for json request/response body metadata of: /tweets/metrics</td>
            </tr>
            </tbody>
            </table>

            - #### **Request parameters**
                In this section there is the list of parameters that should be in the request, in the body and in the route:
                - #### **Route parameters**
                    The request by tweets id has a static route, all the parameters are in the request body.
                - #### **Request body parameters**
                    The request body must be in json format, below the parameters:
                    - `tweetIds`: json array of int, that constain the id's of each tweet that the service will query from the Twitter API, and where the passed filters will be applied.
                    - `filters`: json object that can contain one filter or one logical operator, note that match operators or cnditional operators aren't allowed in the first level. more than one filter can be passed inside a logical operator `$and` or `$or`, more than one operator can be passed inside one filter. For further detail on filters and operators nesting, check the [Filters](#filters) section.


            - #### **Metadata**
                For the metadata route there aren't any parameters to pass.Note that this method differ from the last method only for the absence of the list of tweets ids in the request body. Below there is the result of the API call to the `/tweets/metrics/metadata` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "metadata":{
                        "input": [
                            {
                                "field" : "tweetIds",
                                "type" : "JsonArray",
                                "description": "Array of ids of each tweet that should be returned in the response.",
                                "content":[
                                    "int"
                                ]
                            },
                            {
                                "field" : "filters",
                                "type" : "JsonObject",
                                "description": "Object that contain combinations of filters, follow the documentation for further details on filters.",
                                "content":[
                                    "JsonObject"
                                ]
                            }
                        ],
                        "output": [
                            {
                                "field" : "tweetsMetrics",
                                "type" : "JsonObject",
                                "description": "An array of tweets metrics, inside this object there are all the statistics on the engagement of the tweets requested.",
                                "contentTypes":[
                                    "float"
                                ],
                                "content":[
                                    {
                                        "field" : "retweet_count_mean",
                                        "type" : "float",
                                        "description": "Mean of the number of retweets associated to the passed tweets."
                                    },
                                    {
                                        "field" : "reply_count_mean",
                                        "type" : "float",
                                        "description": "Mean of the number of comments of the passed tweets."
                                    },
                                    {
                                        "field" : "like_count_mean",
                                        "type" : "float",
                                        "description": "Mean of likes associated to the passed tweets."
                                    },
                                    {
                                        "field" : "quote_count_mean",
                                        "type" : "float",
                                        "description": "Mean of the number of quote of the passed tweets."
                                    },
                                    {
                                        "field" : "engagement_mean",
                                        "type" : "float",
                                        "description": "Mean of the engaement metric associated to the passed tweets."
                                    },
                                    {
                                        "field" : "retweet_count_variance",
                                        "type" : "float",
                                        "description": "Variance of the number of retweets associated to the passed tweets."
                                    },
                                    {
                                        "field" : "reply_count_variance",
                                        "type" : "float",
                                        "description": "Variance of the number of comments of the passed tweets."
                                    },
                                    {
                                        "field" : "like_count_variance",
                                        "type" : "float",
                                        "description": "Variance of likes associated to the passed tweets."
                                    },
                                    {
                                        "field" : "quote_count_variance",
                                        "type" : "float",
                                        "description": "Variance of the number of quote of the passed tweets."
                                    },
                                    {
                                        "field" : "engagement_variance",
                                        "type" : "float",
                                        "description": "Variance of the engaement metric associated to the passed tweets."
                                    }
                                ]
                            }
                        ]
                    }
                }
                ```
                </details> </br>

            - #### **Example**
                Below there is an example of API call to the `/tweets/metrics` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "tweetIds" : [
                        23454,
                        345676,
                        456778563
                    ],
                    "filters" : {
                        "$or": [
                            {"created_at": {"$bt": ["2022-01-04T14:07:05Z", "2022-01-05T14:07:02Z"]}},
                            {"created_at": {"$bt": ["2022-01-07T14:07:05Z", "2022-01-08T14:07:02Z"]}},
                            {"like_count": {"$gt": 1000}}
                        ]
                    {
                }
                ```
                </details>

        - #### **Reqest by user id**
            
            <table 
                style="width:100%" 
                border="2" 
                bordercolor = "#fffff">
            <tbody>
            <tr>
            <th style="width:5%"> Type</th>
            <th style="width:25%">Route</th>
            <th>description</th>
            </tr>
            <tr>
            <td> GET</td>
            <td>/user/:userId/metrics</td>
            <td>Route for tweets metrics request by :userId parameter</td>
            </tr>
            <tr>
            <td> GET</td>
            <td>/user/metrics/metadata</td>
            <td>Route for json request/response body metadata of: /user/:userId/metrics</td>
            </tr>
            </tbody>
            </table>

            - #### **Metadata**
                For the metadata route there aren't any parameters to pass. Note that this method differ from the last method only for the absence of the list of tweets ids in the request body. Below there is the result of the API call to the `/user/metrics/metadata` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "metadata":{
                        "input": [
                            {
                                "field" : "filters",
                                "type" : "JsonObject",
                                "description": "Object that contain combinations of filters, follow the documentation for further details on filters.",
                                "content":[
                                    "JsonObject"
                                ]
                            }
                        ],
                        "output": [
                            {
                                "field" : "tweetsMetrics",
                                "type" : "JsonObject",
                                "description": "An array of tweets metrics, inside this object there are all the statistics on the engagement of the tweets requested.",
                                "contentTypes":[
                                    "float"
                                ],
                                "content":[
                                    {
                                        "field" : "retweet_count_mean",
                                        "type" : "float",
                                        "description": "Mean of the number of retweets associated to the passed tweets."
                                    },
                                    {
                                        "field" : "reply_count_mean",
                                        "type" : "float",
                                        "description": "Mean of the number of comments of the passed tweets."
                                    },
                                    {
                                        "field" : "like_count_mean",
                                        "type" : "float",
                                        "description": "Mean of likes associated to the passed tweets."
                                    },
                                    {
                                        "field" : "quote_count_mean",
                                        "type" : "float",
                                        "description": "Mean of the number of quote of the passed tweets."
                                    },
                                    {
                                        "field" : "engagement_mean",
                                        "type" : "float",
                                        "description": "Mean of the engaement metric associated to the passed tweets."
                                    },
                                    {
                                        "field" : "retweet_count_variance",
                                        "type" : "float",
                                        "description": "Variance of the number of retweets associated to the passed tweets."
                                    },
                                    {
                                        "field" : "reply_count_variance",
                                        "type" : "float",
                                        "description": "Variance of the number of comments of the passed tweets."
                                    },
                                    {
                                        "field" : "like_count_variance",
                                        "type" : "float",
                                        "description": "Variance of likes associated to the passed tweets."
                                    },
                                    {
                                        "field" : "quote_count_variance",
                                        "type" : "float",
                                        "description": "Variance of the number of quote of the passed tweets."
                                    },
                                    {
                                        "field" : "engagement_variance",
                                        "type" : "float",
                                        "description": "Variance of the engaement metric associated to the passed tweets."
                                    }
                                ]
                            }
                        ]
                    }
                }
                ```
                </details> </br>

            - #### **Example**
                Below there is an example of API call to the `/user/:userId/metrics` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "filters" : {
                        "$or": [
                            {"created_at": {"$bt": ["2022-01-04T14:07:05Z", "2022-01-05T14:07:02Z"]}},
                            {"created_at": {"$bt": ["2022-01-07T14:07:05Z", "2022-01-08T14:07:02Z"]}},
                            {"like_count": {"$gt": 1000}}
                        ]
                    {
                }
                ```
                </details>

    - #### **Output**
        In this section there is the output explenation of the response from the api that return calling the `/tweets/metrics` and `/user/:userId/metrics` routes, note that the result from this two routes are the same. All the variables in the respose of this routes are the means and the variances of the main engagement variables of each filterd tweet, with one more variable that is the combination of all those values, the engagement parameter.

        - #### **Response body parameters**
            In this section are explained all the parameters that return from the call of the above mentioned routes:
            - `tweetsMetrics`: the tweetsMetrics field it's an object that contain all the statistics about the engagement metrics (all the parameters below).
            - `retweet_count_mean`:This field is contained inside the <b>tweetsMetrics</b> object, it's a <b>float</b> field and indicates the mean of the number of retweets in the filtered group of passed tweets.
            - `reply_count_mean`:This field is contained inside the <b>tweetsMetrics</b> object, it's a <b>float</b> field and indicates the mean of replies in the filtered group of passed tweets.
            - `like_count_mean`:This field is contained inside the <b>tweetsMetrics</b> object, it's a <b>float</b> field and indicates the mean of likes in the filtered group of passed tweets.
            - `quote_count_mean`:This field is contained inside the <b>tweetsMetrics</b> object, it's a <b>float</b> field and indicates the mean of quote in the filtered group of passed tweets.
            - `engagement_mean`:This field is contained inside the <b>tweetsMetrics</b> object, it's a <b>float</b> field and indicates the mean of the engagement value in the filtered group of passed tweets.
            - `retweet_count_variace`:This field is contained inside the <b>tweetsMetrics</b> object, it's a <b>float</b> field and indicates the variance of retweets in the filtered group of passed tweets.
            - `reply_count_variace`:This field is contained inside the <b>tweetsMetrics</b> object, it's a <b>float</b> field and indicates the variance of reply in the filtered group of passed tweets.
            - `like_count_variace`:This field is contained inside the <b>tweetsMetrics</b> object, it's a <b>float</b> field and indicates the variance of likes in the filtered group of passed tweets.
            - `quote_count_variace`:This field is contained inside the <b>tweetsMetrics</b> object, it's a <b>float</b> field and indicates the variance of quote in the filtered group of passed tweets.
            - `engagement_variace`:This field is contained inside the <b>tweetsMetrics</b> object, it's a <b>float</b> field and indicates the variance of the engagement value in the filtered group of passed tweets.

        - #### **Example**
            In this section there is an example of response from the api that could be returned from a call to the `/tweets/metrics` or `/user/:userId/metrics` routes, passing the correct json request body: 
            
            <details>

            <summary style="color: red"><b> Click to expand json </b></summary>

            ```json
            {
                "tweetsMetrics" : {
                    "retweet_count_mean":       15.94,
                    "reply_count_mean":         56.22,
                    "like_count_mean":          1550.78,
                    "quote_count_mean":         458.9,
                    "engagement_mean":          3658.4,
                    "retweet_count_variance":   1.0,
                    "reply_count_variance":     1.0,
                    "like_count_variance":      1.0,
                    "quote_count_variance":     1.0,
                    "engagement_variance":      1.0
                }
            }
            ```
            </details> </br>

- #### **Metadata format documentation**

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