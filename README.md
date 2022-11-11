# 2way SSL 에서 x509 값 추출하여 판별하는 예제

x509 인증서 추출 및 검증은 spring security 에서 제공하는 기능을 사용한다.  
x509 양식의 특정 값을 추출하려고 할 때, SubjectDnX509PrincipalExtractor 클래스를 이용한다.  

## 사전준비

gateway server 의 ssl 구성이 2way SSL 방식 으로 되어 있어야 한다.  
이 설정이 client-auth: need 이다.

```
server:
    port: 8443
    ssl:
        key-store: 'classpath:ssl/server.p12'
        key-store-password: 123456
        
        client-auth: need
        trust-store: 'classpath:ssl/truststore.jks'
        trust-store-password: 123456
```

## keystore 생성

해당 기능은 script.sh 파일을 참고할 것.

## code 분석

BeanConfig 클래스의 securityWebFilterChain() 함수에 있는 내용이 핵심이다.  

```
http.x509(x509 -> {})
```

## gateway filter 에서 x509 추출값 사용

ReactiveSecurityContextHolder.getContext() 함수를 사용하면 된다. 

