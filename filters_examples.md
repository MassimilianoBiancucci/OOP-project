# Filter example 1
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

# Filter example 2
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

# Filter example 3 (WRONG CASE!)
All the elements inside the same array must be of the same type, the $not operator must be placed inside the filter!
<b>WRONG FILTER</b>
```json
{
    "filters":{
        "$or":[
            {"$not":{"like_count": {"$gt": 1000}}}, <--- WRONG!
            {"created_at": {"$gt": "2022-01-01T14:07:02Z"}},
            {"text": {"$in": ["deeplearning", "transformers", "cnn"]}}
        ]
    }
}
```
<b>CORRECT FILTER</b>
```json
{
    "filters":{
        "$or":[
            {"like_count": {"$not":{"$gt": 1000}}, <--- CORRECT!
            {"created_at": {"$gt": "2022-01-01T14:07:02Z"}},
            {"text": {"$in": ["deeplearning", "transformers", "cnn"]}}
        ]
    }
}
```
<b>EQUIVALENT FILTER</b>
```json
{
    "filters":{
        "$or":[
            {"like_count": {"$lte": 1000}},
            {"created_at": {"$gt": "2022-01-01T14:07:02Z"}},
            {"text": {"$in": ["deeplearning", "transformers", "cnn"]}}
        ]
    }
}
```


