
### 接口说明


POST /oauth/token

    根据请求的grant_type不同，其余的参数也会不同

  #### grant_type: password
  
    需要的参数：
    
    username: 用户名
    password: 用户密码
    scope: 领域
    
    请求头需要传递客户端的的 client id 和 secret
    
    例子：
    
    curl -X POST \
      http://localhost:9000/oauth/token \
      -H 'Authorization: Basic dGVzdGNsaWVudDp0ZXN0cGFzcw==,Basic Y2xpZW50aWQ6Y2xpZW50c2VjcmV0' \
      -H 'cache-control: no-cache' \
      -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
      -F grant_type=password \
      -F username=demoUser1 \
      -F password=123456 \
      -F scope=openid
      
    curl http://localhost:9000/oauth/token -u clientid:clientsecret --data "grant_type=password&username=admin&password=admin"
      
      
GET /oauth/check_token

    curl -X GET \
      'http://localhost:9000/oauth/check_token?token=f7cc1513-eab2-4682-bcac-abbb8c88a02c' \
      -H 'Authorization: Basic Y2xpZW50aWQ6Y2xpZW50c2VjcmV0' \
      -H 'Postman-Token: ddf72559-8960-49bd-ba5f-f8ab5e405577' \
      -H 'cache-control: no-cache'
      
    curl -X GET -u clientid:clientsecret http://localhost:9000/oauth/check_token?token=4a67943c-0ec8-4c70-839f-f5724afef0f5
      
      
      
参考：
https://github.com/Akourtiim/oauth2-spring-boot-2.0.2
