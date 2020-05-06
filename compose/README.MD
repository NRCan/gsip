# Launch GSIP




to start 

```console
docker-compose up -d
```

 When the system is launched for the first time, the database is empty. Filling the data can be done using a trigger on the gsip_uploader or by manually loading the data 
 
 ## load data using POST

'CAUTION: This curl command is tricky. If you specify just -x DELETE without the --get then it will ignore the ?s parameter and remove EVERYTHING in the default KB instance!'

curl --get -X DELETE -H 'Accept: application/xml' 'http://localhost:8080/bigdata/sparql' --data-urlencode 's=<http://www.w3.org/People/Berners-Lee/card#i>'

 

To load data in blazegraph, https://github.com/blazegraph/database/wiki/REST_API

```console
> curl -D- -H "Content-Type: text/turtle" --upload-file infered_dataset.ttl  -X POST http://localhost:8080/bigdata/sparql

HTTP/1.1 100 Continue

HTTP/1.1 200 OK
Content-Type: application/xml;charset=iso-8859-1
Content-Length: 66
Server: Jetty(9.4.12.v20180830)

<?xml version="1.0"?><data modified="130386" milliseconds="6685"/>

```

## loading with bulk load

(milleage might vary)

## Sparql endpoint

testing with

```console
describe <https://geoconnex.ca/id/hydrogeounits/Richelieu1>
select ?a WHERE {?a a <https://www.opengis.net/def/appschema/hy_features/hyf/HY_Catchment>;}
```

using cURL (needs URL encoding)

```console
curl "http://localhost/sparql?query=describe%20<https://geoconnex.ca/id/hydrogeounits/Richelieu1>"
curl "http://localhost/sparql?query=select%20%3Fa%20WHERE%20%7B%3Fa%20a%20%3Chttps%3A%2F%2Fwww.opengis.net%2Fdef%2Fappschema%2Fhy_features%2Fhyf%2FHY_Catchment%3E%3B%7D"
```



