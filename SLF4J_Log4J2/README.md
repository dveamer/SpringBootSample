# SLF4J-Log4J2 Sample

이 샘플 소스코드는 아래 문서들을 위해 작성된 샘플입니다. 

  * [SLF4J 와 Log4j 2 연동하기](http://dveamer.github.io/java/SLF4J-Log4J2.html)   
  * [SLF4J Logger 사용법 & 잘못된 사용법: Binding Parameters, Logging Exception Stack Trace](http://dveamer.github.io/backend/HowToUseSlf4j.html)   

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




