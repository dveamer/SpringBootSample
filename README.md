# SpringBootSample

## 필요환경

  * JDK 1.7 이상
  * Gradle or Maven

### 내 실행환경

  * Ubuntu 18.04 LTS
  * JDK 1.8
  * Gradle 5.6.4
  * Apache Maven 3.6.0

## 실행방법

### 방법 1 : java -jar

~~~command
$ gradle build
$ java -jar build/libs/io.dveamer.sample-0.0.1.jar
~~~

### 방법 2 : gradle run

~~~command
$ gradle run
~~~

### 방법 3 : maven

~~~command
$ mvn spring-boot:run
~~~

### 방법 4 : IntelliJ

  * build.gradle 파일을 선택 후 Run 실행(Ctrl+Shfit+F10) - 최초 1회
  * Run > Edit Configurations > Groovy > build - 최초 1회
    - Script parameters에 run이라고 입력 후 OK 클릭
  * Run 재실행(Shift + F10) 후 build 선택

## 테스트 방법

브라우저에서 ```http://127.0.0.1:8080/greeting``` 로 접속하시면 됩니다.  

