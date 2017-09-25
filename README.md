#JMS Utility

``` gradle bootrun ```

 POST http://localhost:9119/jms/api/v1/{queueName}/send
 The post body will contain the json serialized payload to be pushed to the queue
 Example POST Body : {"targetType":"USER","targetId":"1002","message":"This is a test message","activityId":null}

swagger docs at

http://localhost:9119/jms/swagger-ui.html
