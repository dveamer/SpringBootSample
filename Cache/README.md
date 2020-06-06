# Cache Sample

이 샘플 소스코드는 [Spring @Cacheable Cache 처리](http://dveamer.github.io/backend/SpringCacheable.html) 문서를 작성하면서 함께 작성된 샘플입니다.  

## 필요환경

  * JDK 1.8 이상
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
$ java -jar build/libs/sample-0.0.1.jar

or

$ mvn package
$ java -jar target/sample-0.0.1.jar
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




