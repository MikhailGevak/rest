# Runing program
You can run the program from the maven directly using command:
```
mvn exec:java -Dexec.args="src/main/resources/default.properties"
```

#Configuration
Default configuration:
```
askfm.blacklist.file = blacklist.txt
askfm.server.host_name = http://localhost
askfm.server.port = 9999
askfm.server.context_path = 
askfm.ipinfo.host = http://freegeoip.net/json
```
You can override default configration using your own properties-file and writing its filename as a java-arguments.
```
java rest.jar <my_own.properties>
```

#API
##List all accepted questions
Path:**/questions/all**
Type:GET
Response Type: JSON
Response:List of all questions in the DB.
Example:
```
[{"id":1,"text":"Who killed Kennedy?","country":"US","date":"Feb 10, 2017 12:54:53 AM"},
{"id":2,"text":"Is there any life on Mars?","country":"UA","date":"Feb 10, 2017 12:33:08 PM"},
{"id":3,"text":"For whom the bell tolls?","country":"US","date":"Feb 10, 2017 1:29:02 PM"}
]
```
##Ask question
Path:**/questions/create**
Type:POST
Body: Question as a plain text
Response Type: JSON
Response:Created questions
Example:
```
{"id":1,"text":"Who is on duty today?","country":"CA","date":"Feb 10, 2017 12:54:53 AM"}
```
##List of all accepted questions by country code
Path:**/questions/country/{country_code}**
Type:GET
Response Type: JSON
Response:List of questions filtered by country_code
Example:
```
[{"id":1,"text":"Who killed Kennedy?","country":"US","date":"Feb 10, 2017 12:54:53 AM"},
{"id":3,"text":"For whom the bell tolls?","country":"US","date":"Feb 10, 2017 1:29:02 PM"}
]
```
##Get question by id
Path:**/questions/id/{id}**
Type:GET
Response Type: JSON
Response:Questions with id = {id}
Example:
```
{"id":1,"text":"Who killed Kennedy?","country":"US","date":"Feb 10, 2017 12:54:53 AM"}
```
##Error
If error has occured it is returned a response with suitable code (4xx). Also body contains a JSON with error's code (not http-response code!!!) and error's message.
```
{"code":200,"message":"No Question with ID: 123"}
```
#Blacklist
Blacklist is set using a special file. You can set your own file using [Configuration](#configuration) (default value is *blacklist.txt*). Each word (phrase) have to be in one line. For example:
```
terrorism
ISIS
```
#Limit number of questions coming from a country
It's a limit number of questions coming from a country. To set limit use property *askfm.country.frequency*. The default value is 20. If the limit is reached the response 429 is returned:
```
{ "code": 400,   "message": "Too many requests. Try again soon."}
```
