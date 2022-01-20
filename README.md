# **OOP-project**
University project for the object oriented programming course.

## **Overview**
This project aims to create a simple java application using the Springboot framework. This application runs a Tomcat server that provides a local RESTful API over HTTP protocol, for the analysis of the people engagement on Twitter, based on the responses on some tweets. 

This application use the Twitter API to retrieve the tweets informations, and applying to them modular filters, enabling the client to request engagement metrics only on specific tweets.

# **Index**

* [**Project struscture**](#project-struscture)
* [**Project components**](#project-components)
    + [**SpringBoot framework**](#springboot-framework)
    + [**Twitter API V2**](#twitter-api-v2)
        - [**Authentication**](#authentication)
* [**RESTful API docs**](#restful-api-docs)
+ [**API overview**](#api-overview)
+ [**Filters**](#filters)
    - [**Filter operators**](#filter-operators)
        - [**Logic operators**](#logic-operators)
        - [**Match operators**](#match-operators)
        - [**Specific fields and properties**](#specific-fields-and-properties)
        - [**Filter and operators combinations**](#filter-and-operators-combinations)
    - [**Filters package**](#filters-package)
        - [**Inheritance and associations**](#inheritance-and-associations)
+ [**Routes**](#routes)
    - [**1- Requests for raw tweets**](#1--requests-for-raw-tweets)
        - [**1- Input**](#1--input)
            - [**1-1- Reqest by tweets id**](#1-1--reqest-by-tweets-id)
                - [**1-1- Request parameters**](#1-1--request-parameters)
                    - [**1-1- Route parameters**](#1-1-route-parameters)
                    - [**1-1- Request body parameters**](#1-1--request-body-parameters)
                - [**1-1- Metadata**](#1-1--metadata)
                - [**1-1- Example**](#1-1--example)
            - [**1-2- Reqest by user id**](#1-2--reqest-by-user-id)
                - [**1-2- Request parameters**](#1-2--request-parameters)
                    - [**1-2- Route parameters**](#1-2--route-parameters)
                    - [**1-2- Request body parameters**](#1-2--request-body-parameters)
                - [**1-2- Metadata**](#1-2--metadata)
                - [**1-2- Example**](#1-2--example)
        - [**1- Output**](#1--output)
            - [**1- Response body parameters**](#1--response-body-parameters)
            - [**1- Example**](#1-example)
    - [**2- Requests for tweets metrics**](#2--requests-for-tweets-metrics)
        - [**2- Input**](#2--input)
            - [**2-1- Reqest by tweets id**](#2-1--reqest-by-tweets-id)
                - [**2-1- Request parameters**](#2-1--request-parameters)
                    - [**2-1- Route parameters**](#2-1--route-parameters)
                    - [**2-1- Request body parameters**](#2-1-request-body-parameters)
                - [**2-1- Metadata**](#2-1--metadata)
                - [**2-1- Example**](#2-1--example)
            - [**2-2- Reqest by tweets id**](#2-2--reqest-by-tweets-id)
                - [**2-2- Request parameters**](#2-2--request-parameters)
                    - [**2-2- Route parameters**](#2-2--route-parameters)
                    - [**2-2- Request body parameters**](#2.2--request-body-parameters)
                - [**2-2- Metadata**](#2-2--metadata)
                - [**2-2- Example**](#2-2--example)
        - [**2- Output**](#2--output)
            - [**2- Response body parameters**](#2--response-body-parameters)
            - [**2- Example**](#2--example)
    - [**Route parameters**](#route-parameters)
    - [**Request body parameters**](#request-body-parameters)
    - [**Response to failed requests**](#response-to-failed-requests)
* [**Junit tests**](#junit-tests)
* [**Postman examples**](#postman-examples)
* [**USEFUL LINKS**](#useful-links)
    + [SpringBoot](#springboot)
    + [Twitter API docs](#twitter-api-docs)
    + [Docs tools](#docs-tools)
    + [Utils](#utils)

---
## **Project struscture**

Below is shown the structure of the project and what is the content of each folder, with a little description of each one. 

```text
src
├───main
│   └───java.com.twitterMetrics
│               └───engagementAnalyzer
│                   │   EngagementAnalyzerApplication.java
│                   │   
│                   ├───Controller
│                   │       EngagementController.java
│                   │       
│                   ├───Exceptions
│                   │       IncorrectFilterFieldException.java
│                   │       IncorrectOperatorSymbolException.java
│                   │       IncorrectOperatorValuesException.java
│                   │       NotImplementedException.java
│                   │       RequestBodyFieldNotPresentException.java
│                   │       UnexpectedRequestBodyFieldException.java
│                   │       UnexpectedRequestBodyFieldValueException.java
│                   │       
│                   ├───Filters
│                   │   │   DateFilter.java
│                   │   │   Filter.java
│                   │   │   MessageFilter.java
│                   │   │   MetricFilter.java
│                   │   │   TimeFilter.java
│                   │   │   
│                   │   └───Operators
│                   │       │   ConditionalOperator.java
│                   │       │   LogicOperator.java
│                   │       │   MatchOperator.java
│                   │       │   Operator.java
│                   │       │   
│                   │       └───Values
│                   │               OperatorDateValues.java
│                   │               OperatorFilterValues.java
│                   │               OperatorIntValues.java
│                   │               OperatorLogicOperatorValues.java
│                   │               OperatorOperatorValues.java
│                   │               OperatorStringValues.java
│                   │               OperatorTimeValues.java
│                   │               OperatorValues.java
│                   │               
│                   ├───Model
│                   │       Statistic.java
│                   │       StatisticsList.java
│                   │       Tweet.java
│                   │       TweetList.java
│                   │       
│                   ├───Parser
│                   │       DateParser.java
│                   │       FiltersParser.java
│                   │       RequestParser.java
│                   │       TwitterResponseParser.java
│                   │       
│                   ├───Service
│                   │   │   EngagementService.java
│                   │   │   EngagementServiceImpl.java
│                   │   │   RoutesMetadata.java
│                   │   │   
│                   │   └───TwitterApiCaller
│                   │           TwitterApiCaller.java
│                   │           
│                   └───supportTypes
│                           DateValue.java
│                           TimeValue.java
└───test
    └───java
        └───com
            └───twitterAnalyzer
                └───engagementAnalyzer
                    │   EngagementAnalyzerApplicationTests.java
                    │   
                    └───TestService
                            EngagementTestServiceFakeData.java
                            EngagementTestServiceImpl.java
```
<b>main/java/</b>
- `com.twitterMetrics.engagementAnalyzer`: Principal package of the project.
- `com.twitterMetrics.engagementAnalyzer.Controller`: Package that contain the calss that manage the requests and run the requested code. 
- `com.twitterMetrics.engagementAnalyzer.Exceptions`: Package that contain all the Exceptions defined for the project.
- `com.twitterMetrics.engagementAnalyzer.Filters`: Package that contain all the filters classes and all the related subpackages.
- `com.twitterMetrics.engagementAnalyzer.Filters.Operators`: Package that contain the operators classes and all the related subpackages.
- `com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values`: Package that contain all the operator's values classes.
- `com.twitterMetrics.engagementAnalyzer.Model`: Package that contain the principal data models, like the class that abstracts the tweet and a list of tweets, in the same way this package contain the statistic of one tweet field and a list of statistics abstracted as classes.
- `com.twitterMetrics.engagementAnalyzer.Parser`: Package that contain all the classes used for the parsing operaton for various type of objects.
- `com.twitterMetrics.engagementAnalyzer.Service`: Package that contain all the classes that coordinate all the other classes of the project for the creation of the requested result. these classes are used inside the controller.
- `com.twitterMetrics.engagementAnalyzer.Service.TwitterApiCaller`: A sub-package of the Service package, used for the interface with the Twitter API.
- `com.twitterMetrics.engagementAnalyzer.supportTypes`: Package with some empty classes used inside the project as placeholder. 

<b>test/java/</b>
- `com.twitterMetrics.engagementAnalyzer`: Principla test package, contain the tests.
- `com.twitterMetrics.engagementAnalyzer.TestService`: Package that contains a modified version of the original service, in that version everything is the same except for the Twitter API caller that isn't used in the test and is replaced by a static result for repeatability.
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
The filters are an important part of the API, they can be passed trough the body of the request, in a json format, to determine which tweets will be used for the engagement evaluation. the tweets could be selected based on the text of the tweet, or maybe based on the date because only a certain temporal interval matter.

In this implementation of these filters there are 3 important chuncks that make the filter, those are: `field`, `operator` and `operator's values`. The operators and the values inside, specifies the requirements that the field must satisfy, and the field specifie to which field the requirement should be applied.

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
    There are tree types of operators, 2 normal and one special. The special ones are the `logic operators`, those opratars can be used either on other operators either on well formed filters, but not together.
    The other ones are the `match operators` that could be used for match or unmatch list of values while the `conditional operators` are used for specify interval of values accepted, below there are more accurate explenation of each type.

    - #### **Logic operators** </br>
        The `logic operators` can't be applied alone, they are used for:
        - filter(s) combination or coniugation. 
        - operator(s) combinations or coniugation.

        <b>NOTE: Logic operator can be used only before a Filter field object. If one logic operator will be passed inside a Filter field object, the API will rise an exception and the request will fail.</b>

        They are 3+1 operators, that permit to create unions, intersections or inversion on ensembles, if you consider all the tweets as the starting enseble and each well formed filter or operator as a subset of the general ensamble, these operators could be used for create ensables operations between these subsets.
        If the `logic operators` must not be used inside a filter, but only with filters inside.
        
        Note that 3+1 operators indicate that there are 3 meaningful operators that should be used, the "+1" is for the $nop operator that as the name suggest does nothing, simply wrap another operator or a filter. The purpose of this operator is internal.
        
        <table style="width:100%" border="2" bordercolor = "#fffff">
        <tbody>
        <tr>
            <th>Symbol</th>
            <th>Description</th>
            <th>Example of condition</th>
        </tr>
        <tr>
            <td>$nop</td>
            <td>No operation logic operator</td>
            <td>{"$nop" : {filter or logicOperator}}</td>
        </tr>
        <tr>
            <td>$not</td>
            <td>Negation logic operator</td>
            <td>{"$not" : {filter or logicOperator}}</td>
        </tr>
        <tr>
            <td>$or</td>
            <td>Logic operator</td>
            <td>{"$or": [{filter or logicOperator},{filter or logicOperator},...]}</td>
        </tr>
        <tr>
            <td>$and</td>
            <td>Logic operator</td>
            <td>{"$and": [{filter or logicOperator},{filter or logicOperator},...]}</td>
        </tr>
        </tbody>
        </table>

    - #### **Match operators** </br>
        The `match operators` are used for filter tweets based on list of values that their fields should match or not match. This type of filters could be used only with strings otherwise the request will throw an exceptionand fail.
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


    - #### **Conditional operators** </br>
        The last one are the `conditional operators`, and can be used only on numeric values like metrics and on dates and timetables, so they can't be used only with strings. The usage if these operators with general string values will throw an exception. Another exception will be raised even if these operators are applied to string fields like `text`.
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
        This field can be applied to <b>match operators</b> and to <b>conditional operators</b>,<br> not on <b>logical operators</b>.
        </td>
    </tr>
    <tr>
        <td>reply_count</td>
        <td>int</td>
        <td>The number of replies or comments to the tweet.<br>
        This field can be applied to <b>match operators</b> and to <b>conditional operators</b>,<br> not on <b>logical operators</b>.
        </td>
    </tr>
    <tr>
        <td>like_count</td>
        <td>int</td>
        <td>The count of likes.<br>
        This field can be applied to <b>match operators</b> and to <b>conditional operators</b>,<br> not on <b>logical operators</b>.
        </td>
    </tr>
    <tr>
        <td>quote_count</td>
        <td>int</td>
        <td>The number of quote, that are retweets with a comments ahead.<br>
        This field can be applied to <b>match operators</b> and to <b>conditional operators</b>,<br> not on <b>logical operators.
        </td>
    </tr>
    <tr>
        <td>engagement</td>
        <td>int</td>
        <td>The value of engagement calculated from the tweet responses.<br>
        This field can be applied to <b>match operators</b> and to <b>conditional operators</b>,<br> not on <b>logical operators.
        </td>
    </tr>
    <tr>
        <td>text</td>
        <td>String</td>
        <td>The text content of the tweet.<br>
        This field can be applied only to <b>match operators</b>.
        </td>
    </tr>
    <tr>
        <td>created_at</td>
        <td>Date</td>
        <td>The publication date and time of the tweet.<br>
        This field can be applied to <b>match operators</b> and to <b>conditional operators</b>,<br> not on <b>logical operators.
        </td>
    </tr>
    <tr>
        <td>created_in_timeslot</td>
        <td>Time</td>
        <td>This parameter is a subparameter of created_at, beacuse it consider<br>
        only the time (hours, minutes and seconds), for each day,<br>
        this mean that can be used for filter tweets in certain time slots.<br>
        This field can be applied to <b>match operators</b> and to <b>conditional operators</b>,<br> not on <b>logical operators</b>.
        </td>
    </tr>
    </tbody>
    </table>

- #### **Filter and operators combinations**

    In this section will be shown some examples of filters combinations, to highlight what can be passed to the API in the request body and what will throw an error.
    By combination of filters it is meant an extensive usage of the `logical operators` to combine many filters, below the examples:

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

- #### **Filters package**
    
    In this section there is an insight on how filters works under the hood. 

    - #### **Inheritance and associations**
        This filters implementation can garant a good flexibility and interchangeability thanks to the extensive usage of classes Inheritance.
        As shown before there are three main componetns of each filter: the `field`, the `operator` and the `operator's values`. there are many field with different characteristics, and many operators that accept only certain type of values, so there is an abstract class for each one of this "abstract" concepts that are extended for each subcase. In this way each class that accept a certain abstract class can accept all the subclasses and apply a different behaviour for each one, for example accept it or throw an exception where the combination of filter, operator and operatorValues can't work together. 

        ![Filters package uml](UMLs/Filters%20package%20diagram.png)

    - Metric filters

    - Message filters

    - Date filters

    - TimeSlot filters


### **Routes**
This api has two main functionalities, the first is the request of the raw tweets directly taken from the twitter api, with the application of the passed filters. The second functionality is the request of some statistics on the tweets requested after the application of the passed filters.
Each functionality can be requested with two different routes (four routes in total), each one enable to request the same functionality in two different ways. The fisrt by passing a list of tweets ids in the request, the second by specifying the user id in the route.

Note that for each route are present one more route for the metadata request, that explain with a schematic format the structure of the expected request body and the response body, with field names, fields types, and some special structure for explain the inner structure of nested objects and arrays. The documentation for the metadata json is present below: [Metadata formt documentation](#metadata-formt-documentation).

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

- #### **1- Requests for raw tweets**
    This funcionality enable to request the raw tweets as they are returned by the twitter API, with the possibility of applying to them some filters if needed, so it's possible to query the tweets with the twitter api format, but for example applying to them a filter for retrive only tweets with certain words in the text field, or all other type of filters above explained. As metioned there are two methods for retrive these tweet's raw data, by a list of tweets ids or by a twitter user id and specifing the number of tweets needed, note that in that way only the last tweets are taken.

    - #### **1- Input**
        In this section there are the two methods routes for the <b>requests of raw tweets</b> explained in detail with a formal documentation that explain each parameter in the route and in the request body with an example request for the two routes.

        - #### **1-1- Reqest by tweets id**
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

            - #### **1-1- Request parameters**
                In this section there is the list of parameters that should be in the request, in the body and in the route:
                - #### **1-1- Route parameters**
                    The request by tweets id has a static route, all the parameters are in the request body.
                - #### **1-1- Request body parameters**
                    The request body must be in json format, below the parameters:
                    - `TwitterBearerToken`: String that contain your OAuth 2.0 Bearer token issued from Twitter in your [Twitter Developer portal](https://developer.twitter.com/en/portal/dashboard), this field must be filled with a valid token or the API will throw an exception and the request will fail.
                    - `tweetIds`: json array of int, that constain the id's of each tweet that the service will query from the Twitter API, and where the passed filters will be applied.
                    - `filters`: json object that can contain one filter or one logical operator, note that match operators or cnditional operators aren't allowed in the first level. more than one filter can be passed inside a logical operator `$and` or `$or`, more than one operator can be passed inside one filter. For further detail on filters and operators nesting, check the [Filters](#filters) section.

            - #### **1-1- Metadata**
                For the metadata route there aren't any parameters to pass. Note that this method differ from the last method only for the absence of the list of tweets ids in the request body. Below there is the result of the API call to the `/tweets/metadata` route:
                
                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "metadata":{
                        "input": [
                            {
                                "field" : "TwitterBearerToken",
                                "type" : "String",
                                "description": "String that contain your OAuth 2.0 Bearer token issued from Twitter."
                            },
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
                                "field" : "status",
                                "type" : "String",
                                "description": "Message that explain the actual status of the request, normaly the value should be 'success'."
                            },
                            {
                                "field" : "tweets_count",
                                "type" : "int",
                                "description": "The number of tweets retrived before the filtering process, the maximum should be 100, but the retrived number depend to the twitter api."
                            },
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
                                            "field" : "engagement",
                                            "type" : "double",
                                            "description": "function that return a general value of the engagement calculated by public_metrics values."
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
                            },
                            {
                                "field" : "errors",
                                "type" : "JsonArray",
                                "description": "An array of errors passed directly from the Twitter API, see the Twitter API V2 documentation for further details."
                            }
                        ]
                    }
                }
                ```
                </details> </br>

            - #### **1-1- Example**
                Below there is an example of API call to the `/tweets` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "TwitterBearerToken": "AAAAAAAAAAAAAAAAAAASC5JzzAAMhUXwEAAAOU%3VcayzHCAGeyYBqAARFm871IloLWyHVYofv%2F0BNEfBoWWCds94xyPgxdVfdGHK8HZDijzgHsK",
                    "tweetIds" : [
                        24,
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
                    }
                }
                ```
                </details> </br>

        - #### **1-2- Reqest by user id**
            
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

            - #### **1-2- Request parameters**
                In this section there is the list of parameters that should be in the body and in the route:
                
                - #### **1-2- Route parameters**
                    The request by user id has only one parameter in the route, showed in the above table with the following placeholder:

                    - `:userId`: should be replaced with the Twitter user id of the user of which we want retrive the tweets. this parameter its simply a number. 

                - #### **1-2- Request body parameters**
                    The request body must be in json format, below the parameters:
                    - `TwitterBearerToken`: String that contain your OAuth 2.0 Bearer token issued from Twitter in your [Twitter Developer portal](https://developer.twitter.com/en/portal/dashboard), this field must be filled with a valid token or the API will throw an exception and the request will fail.
                    - `filters`: json object that can contain one filter or one logical operator, note that match operators or cnditional operators aren't allowed in the first level. more than one filter can be passed inside a logical operator `$and` or `$or`, more than one operator can be passed inside one filter. For further detail on filters and operators nesting, check the [Filters](#filters) section.

            - #### **1-2- Metadata**
                For the metadata route there aren't any parameters to pass. Note that this method differ from the last method only for the absence of the list of tweets ids in the request body. Below there is the result of the API call to the `/user/metadata` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "metadata":{
                        "input": [
                            {
                                "field" : "TwitterBearerToken",
                                "type" : "String",
                                "description": "String that contain your OAuth 2.0 Bearer token issued from Twitter."
                            },
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
                                "field" : "status",
                                "type" : "String",
                                "description": "Message that explain the actual status of the request, normaly the value should be 'success'."
                            },
                            {
                                "field" : "tweets_count",
                                "type" : "int",
                                "description": "The number of tweets retrived before the filtering process, the maximum should be 100, but the retrived number depend to the twitter api."
                            },
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
                                            "field" : "engagement",
                                            "type" : "double",
                                            "description": "function that return a general value of the engagement calculated by public_metrics values."
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
                            },
                            {
                                "field" : "errors",
                                "type" : "JsonArray",
                                "description": "An array of errors passed directly from the Twitter API, see the Twitter API V2 documentation for further details."
                            }
                        ]
                    }
                }
                ```
                </details> </br>

            - #### **1-2- Example**
                Below there is an example of API call to the `/user/:userId` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "TwitterBearerToken": "AAAAAAAAAAAAAAAAAAASC5JzzAAMhUXwEAAAOU%3VcayzHCAGeyYBqAARFm871IloLWyHVYofv%2F0BNEfBoWWCds94xyPgxdVfdGHK8HZDijzgHsK",
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

    - #### **1- Output**
        In this section there is the output explenation of the response from the api that return calling the `/tweets` and `/user/:userId` routes, note that the result from this two routes are the same. All the variables in the respose of this routes are a subset of the parameters that could be retrived directly from the Twitter API.

        - #### **1- Response body parameters**
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
            - `errors`: This field contains a json array with each error passed from the Twitter API, see the Twitter API V2 documentation for further details.

        - #### **1- Example**
            In this section there is an example of response from the api that could be returned from a call to the `/tweets` or `/user/:userId` routes, passing the correct json request body:
            <details>

            <summary style="color: red"><b> Click to expand json </b></summary>

            ```json
            {
                "status": "success",
                "tweets_count": 2,
                "tweets": [
                    {
                        "id": "345676",
                        "text": "just setting up my twttr",
                        "created_at": "2006-03-21T20:51:43.000Z",
                        "engagement": 10963,
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
                        "engagement": 8487,
                        "public_metrics": {
                            "retweet_count": 4767,
                            "reply_count": 77,
                            "like_count": 3491,
                            "quote_count": 152
                        }
                    }
                ],
                "errors": [
                    {
                        "resource_id": "23454",
                        "parameter": "ids",
                        "resource_type": "tweet",
                        "section": "data",
                        "title": "Authorization Error",
                        "value": "23454",
                        "detail": "Sorry, you are not authorized to see the Tweet with ids: [23454].",
                        "type": "https://api.twitter.com/2/problems/not-authorized-for-resource"
                    },
                    {
                        "value": "24",
                        "detail": "Could not find tweet with ids: [24].",
                        "title": "Not Found Error",
                        "resource_type": "tweet",
                        "parameter": "ids",
                        "resource_id": "24",
                        "type": "https://api.twitter.com/2/problems/resource-not-found"
                    }
                ]
            }
            ```
            </details> </br>

- #### **2- Requests for tweets metrics**
    This functionality enable to request statistics on the engagement that are relative to a group of tweets passed through a list of tweets ids or by a twitter user id and specifing the number of tweets needed. 
    
    These statistics are calculated based on the tweets passed in one of the two ways, but if there are filters specified in the json request body, the tweets used for the statistics calculation will be only the filterd tweets.

    - #### **2- Input**
        In this section there are the two routes for the requests of <b>tweets engagement metrics</b>, showed in detail with a formal documentation that explain each parameter in the route and in the request body with an example request for the two routes.

        - #### **2-1- Reqest by tweets id**

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

            - #### **2-1- Request parameters**
                In this section there is the list of parameters that should be in the request, in the body and in the route:
                - #### **2-1- Route parameters**
                    The request by tweets id has a static route, all the parameters are in the request body.
                - #### **2-1- Request body parameters**
                    The request body must be in json format, below the parameters:
                    - `tweetIds`: json array of int, that constain the id's of each tweet that the service will query from the Twitter API, and where the passed filters will be applied.
                    - `filters`: json object that can contain one filter or one logical operator, note that match operators or cnditional operators aren't allowed in the first level. more than one filter can be passed inside a logical operator `$and` or `$or`, more than one operator can be passed inside one filter. For further detail on filters and operators nesting, check the [Filters](#filters) section.


            - #### **2-1- Metadata**
                For the metadata route there aren't any parameters to pass.Note that this method differ from the last method only for the absence of the list of tweets ids in the request body. Below there is the result of the API call to the `/tweets/metrics/metadata` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "metadata":{
                        "input": [
                            {
                                "field" : "TwitterBearerToken",
                                "type" : "String",
                                "description": "String that contain your OAuth 2.0 Bearer token issued from Twitter."
                            },
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
                                "field" : "status",
                                "type" : "String",
                                "description": "Message that explain the actual status of the request, normaly the value should be 'success'."
                            },
                            {
                                "field" : "tweets_count",
                                "type" : "int",
                                "description": "The number of tweets retrived before the filtering process, the maximum should be 100, but the retrived number depend to the twitter api."
                            },
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
                            },
                            {
                                "field" : "errors",
                                "type" : "JsonArray",
                                "description": "An array of errors passed directly from the Twitter API, see the Twitter API V2 documentation for further details."
                            }
                        ]
                    }
                }
                ```
                </details> </br>

            - #### **2-1- Example**
                Below there is an example of API call to the `/tweets/metrics` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "TwitterBearerToken": "AAAAAAAAAAAAAAAAAAASC5JzzAAMhUXwEAAAOU%3VcayzHCAGeyYBqAARFm871IloLWyHVYofv%2F0BNEfBoWWCds94xyPgxdVfdGHK8HZDijzgHsK",
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
                    }
                }
                ```
                </details>

        - #### **2-2- Reqest by user id**
            
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

            - #### **2-2- Metadata**
                For the metadata route there aren't any parameters to pass. Note that this method differ from the last method only for the absence of the list of tweets ids in the request body. Below there is the result of the API call to the `/user/metrics/metadata` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "metadata":{
                        "input": [
                            {
                                "field" : "TwitterBearerToken",
                                "type" : "String",
                                "description": "String that contain your OAuth 2.0 Bearer token issued from Twitter."
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
                                "field" : "status",
                                "type" : "String",
                                "description": "Message that explain the actual status of the request, normaly the value should be 'success'."
                            },
                            {
                                "field" : "tweets_count",
                                "type" : "int",
                                "description": "The number of tweets retrived before the filtering process, the maximum should be 100, but the retrived number depend to the twitter api."
                            },
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
                            },
                            {
                                "field" : "errors",
                                "type" : "JsonArray",
                                "description": "An array of errors passed directly from the Twitter API, see the Twitter API V2 documentation for further details."
                            }
                        ]
                    }
                }
                ```
                </details> </br>

            - #### **2-2- Example**
                Below there is an example of API call to the `/user/:userId/metrics` route:

                <details>

                <summary style="color: red"><b> Click to expand json </b></summary>

                ```json
                {
                    "TwitterBearerToken": "AAAAAAAAAAAAAAAAAAASC5JzzAAMhUXwEAAAOU%3VcayzHCAGeyYBqAARFm871IloLWyHVYofv%2F0BNEfBoWWCds94xyPgxdVfdGHK8HZDijzgHsK",
                    "filters" : {
                        "$or": [
                            {"created_at": {"$bt": ["2022-01-04T14:07:05Z", "2022-01-05T14:07:02Z"]}},
                            {"created_at": {"$bt": ["2022-01-07T14:07:05Z", "2022-01-08T14:07:02Z"]}},
                            {"like_count": {"$gt": 1000}}
                        ]
                    }
                }
                ```
                </details>

    - #### **2- Output**
        In this section there is the output explenation of the response from the api that return calling the `/tweets/metrics` and `/user/:userId/metrics` routes, note that the result from this two routes are the same. All the variables in the respose of this routes are the means and the variances of the main engagement variables of each filterd tweet, with one more variable that is the combination of all those values, the engagement parameter.

        - #### **2- Response body parameters**
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
            - `errors`: This field contains a json array with each error passed from the Twitter API, see the Twitter API V2 documentation for further details.

        - #### **2- Example**
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
                },
                "errors": [
                    {
                        "resource_id": "23454",
                        "parameter": "ids",
                        "resource_type": "tweet",
                        "section": "data",
                        "title": "Authorization Error",
                        "value": "23454",
                        "detail": "Sorry, you are not authorized to see the Tweet with ids: [23454].",
                        "type": "https://api.twitter.com/2/problems/not-authorized-for-resource"
                    },
                    {
                        "value": "24",
                        "detail": "Could not find tweet with ids: [24].",
                        "title": "Not Found Error",
                        "resource_type": "tweet",
                        "parameter": "ids",
                        "resource_id": "24",
                        "type": "https://api.twitter.com/2/problems/resource-not-found"
                    }
                ]
            }
            ```
            </details> </br>

- #### **Response to failed requests**

---
## **Junit tests**

---
## **Postman examples**

---
## **USEFUL LINKS**

### SpringBoot 
- [SpringBoot initializer](https://start.spring.io/)

### Twitter API docs
- [Twitter Developer portal](https://developer.twitter.com/en/portal/dashboard)
- [Twitter API v2 homepage](https://developer.twitter.com/en/docs/twitter-api)
- [Twitter API v2 postman collection](https://developer.twitter.com/en/docs/tutorials/postman-getting-started)
- [Twitter API v2: Tweets lookup](https://developer.twitter.com/en/docs/twitter-api/tweets/lookup/introduction)
- [Twitter API v2: Timelines](https://developer.twitter.com/en/docs/twitter-api/tweets/timelines/introduction)
### Docs tools
- [markdown-toc](http://ecotrust-canada.github.io/markdown-toc/)
- [tables generator](https://www.tablesgenerator.com/html_tables)

### Utils
- [Json formatter](https://jsonformatter.org/json-pretty-print)