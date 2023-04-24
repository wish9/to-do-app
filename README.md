[블로그 포스팅 주소](https://velog.io/@wish17/%EC%BD%94%EB%93%9C%EC%8A%A4%ED%85%8C%EC%9D%B4%EC%B8%A0-%EB%B0%B1%EC%97%94%EB%93%9C-%EB%B6%80%ED%8A%B8%EC%BA%A0%ED%94%84-78%EC%9D%BC%EC%B0%A8-Solo-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8)

##  Spring Security를 사용하지 않고 CORS 설정을 하는 방법

### 애너테이션을 이용해 컨트롤러에서 설정하는 방법

[@CrossOrigin](https://spring.io/guides/gs/rest-service-cors/) 애너테이션을 이용해 컨트롤러 혹은 메서드에서 CORS 정책을 설정하면 된다.

애너테이션이 붙은 컨트롤러(혹은 메서드)에서만 적용되며 따라서 원하는 요청에 따른 응답에만 CORS 설정을 할 수 있다.

```java
// 옵션을 이용해 세부적인 설정을 추가
// 메서드단위에도 적용 가능
@CrossOrigin(origins = "https://codestates.com")
@RestController
public class HelloController {
	...
}
```

***

## 실습 Todo 만들기

할일 목록을 처리하는 기능을 간단하게 구현해 봤다.

[Github 풀코드 주소](https://github.com/wish9/to-do-app/commit/9b00c6ee0fc6541ad3fb83554bd6061c0874a02e)

![](https://velog.velcdn.com/images/wish17/post/11313588-5cb8-4ef1-bdf5-049da2df7cdb/image.png)




### 웹사이트에서 백엔드 기능 구현 테스트하기

[테이블 추가 + Todo backend사이트 테스트용 설정 추가 풀코드](https://github.com/wish9/to-do-app/commit/d306154641085508449d8e09e31f5f55a7d7aa92)

> [Todo backend](https://todobackend.com/)
- 클라이언트(View) 보조 사이트

실습으로 만든 todo의 경우 아래 링크로 가면 된다.

 [https://todobackend.com/client/index.html?http://localhost:8080/](https://todobackend.com/client/index.html?http://localhost:8080/)

- ``@CrossOrigin`` http://todobackend.com/에서 테스트하기 위해 추가해야 함.
- 23.04.06 기준 post와 get만 테스트 가능 
 
tmi ``https://todobackend.com/client/index.html?http://localhost:8080/#/``으로 뒤에 샵 잘못입력해서 한참 고생했다.



![](https://velog.velcdn.com/images/wish17/post/339e5c04-49ba-4f71-9bcf-4d69f463f905/image.png)
 
 ***
 
###  JAR → WAR 포맷 변경

1. ``build.gradle`` 수정
 
```java
plugins {
    id 'org.springframework.boot' version '2.4.2'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'war' // 추가합니다.
    id 'java'
}
...
``` 
 
 
2. 어플리케이션 실행파일 수정

```java
@SpringBootApplication
public class ToDoAppApplication extends SpringBootServletInitializer { // SpringBootServletInitializer를 상속

	public static void main(String[] args) {
		SpringApplication.run(ToDoAppApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { // JAR → WAR 포맷 변경
	    return builder.sources(ToDoAppApplication.class);
	}

}
```
 
이후 빌드하면 jar파일 대신 war 파일이 생성 된다.

***

## Tomcat을 이용해 수동 배포하기

1. [JRE 설치](https://www.azul.com/downloads/?version=java-11-lts&os=windows&architecture=x86-64-bit&package=jre#zulu)

![](https://velog.velcdn.com/images/wish17/post/117b47f6-f66a-4eb8-934d-7a450e6c9bb2/image.png)


2. [톰캣 설치](https://tomcat.apache.org/download-90.cgi)

![](https://velog.velcdn.com/images/wish17/post/64c5f91b-36bb-48ef-95a9-8578cfec8807/image.png)

3. 애플리케이션 빌드해서 ``.war``파일 생성



4. 빌드한 애플리케이션 war 파일을 톰캣 파일의 webapps파일(``D:\AAWonJong\Program File\apache-tomcat-9.0.73\webapps``)로 이동
 
 ![](https://velog.velcdn.com/images/wish17/post/c4cdc88a-c21d-4d34-b807-2a4cd2cfaf3b/image.png)

 
 5. ``D:\AAWonJong\Program File\apache-tomcat-9.0.73\conf`` 위치에 있는 ``server.xml`` 파일 수정
 
``<Context path="/" docBase="애플리케이션이름"  reloadable="false" > </Context>`` 이거 한줄 추가해주면 된다.
 
 ```
<Host name="localhost"  appBase="webapps"
            unpackWARs="true" autoDeploy="true">

		<Context path="/" docBase="to-do-app-0.0.1-SNAPSHOT"  reloadable="false" > </Context>

        <!-- SingleSignOn valve, share authentication between web applications
             Documentation at: /docs/config/valve.html -->
        <!--
        <Valve className="org.apache.catalina.authenticator.SingleSignOn" />
        -->

        <!-- Access log processes all example.
             Documentation at: /docs/config/valve.html
             Note: The pattern used is equivalent to using pattern="common" -->
        <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
               prefix="localhost_access_log" suffix=".txt"
               pattern="%h %l %u %t &quot;%r&quot; %s %b" />

      </Host>
```
 
 ![](https://velog.velcdn.com/images/wish17/post/8a94819a-8920-411b-aaf4-74baea7d1514/image.png)

5번과정 대신에 그냥 ``.war`` 파일명을 "ROOT"로 변경해도 된다.
 
 
6. 톰캣 실행 
 
```
D:\Users\...\apache-tomcat-9.0.68\bin> .\startup.bat # 톰캣을 실행

D:\Users\...\apache-tomcat-9.0.68\bin> .\shutdown.bat # 톰캣을 종료
```
 
![](https://velog.velcdn.com/images/wish17/post/79098ebf-f416-4298-bf89-63cb6f6bc69e/image.png)
 
직접 클릭해서도 가능 
 
> cf. 톰캣 실행해두면 인텔리제이 사용하면서 8080포트 충돌나서 못쓰니 아래와 같이 톰캣 포트를 수정할 수 있다.
- server.xml 파일을 아래와 같이 수정
```
<Connector port="8090" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />
```
- port값에 8090을 입력하여 앞으로 외장 톰캣에서는 8090번 포트에서, IDE에서 실행하는 Spring boot(Embeded Tomcat)는 8080번 포트에서 실행하도록 조정 
 
*** 

## Ngrok을 이용해 로컬에서 서버 실행하기

> [Ngrok](https://ngrok.com/docs/secure-tunnels/#what-are-ngrok-secure-tunnels)
- 네트워크 설정을 하지 않아도 방화벽을 넘어 외부에서 로컬 환경에 접근할 수 있게 해주는 터널링 프로그램
- 무료 플랜의 경우 연결 세션이 약 2시간가량 유지

1. [Ngrok 설치](https://ngrok.com/download)

2. 실행

![](https://velog.velcdn.com/images/wish17/post/277e3564-5884-4cd9-8fd8-83922e2dcfa3/image.png)


```
// ngrok http {port} 의 형태로 원하는 포트를 연결
ngrok http 8080
ngrok http 8090
```
![](https://velog.velcdn.com/images/wish17/post/6e6ad452-f062-41d8-8be7-5a64b3db7595/image.png)

위와 같이 ``https://67a8-121-133-205-229.jp.ngrok.io`` 임시주소 확인 가능

아래와 같이 해당 주소로 CRUD 요청을 보내면 정상적으로 수행한다.

![](https://velog.velcdn.com/images/wish17/post/29ff646f-8d81-4e8d-80d3-780c3e4eba7d/image.png)
