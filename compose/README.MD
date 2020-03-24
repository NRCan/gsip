# Uploading data 

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

(milleage might vary)