# 📝나만의 블로그 만들기 - Version 3
##  **💡Spring Boot를 활용하여 나만의 블로그 만들기**

- **프로젝트 기간 : 2022.07.06 ~ 2022.08.21**
    
<br/>

## 💡 프로젝트 목표
- 블로그 version2 의 코드를 리팩토링하여 중복되는 코드를 줄이고 가독성을 높임
- 블로그 version2 에서 하지 못했던 추가 기능 구현 (파일업로드 기능, 좋아요기능 등)
- spring security 기능 사용
- junit 이용한 테스트 코드 작성 
- [블로그 ver1 깃허브](https://github.com/emperor-juwon/blog_project_version_1)
- [블로그 ver2 깃허브](https://github.com/emperor-juwon/blog_project_version_2)

<br/>

## 💡 블로그 ver2 에서 업그레이드 된 부분
> ### Spring Security
- 보안기능을 높이기 위해 spring security를 사용함. 비밀번호는 해쉬화하여 저장했음 
- [관련공부기록](https://blog.naver.com/fwangjuwon/222693657746)

<br/>

> ### Handler
- 예외발생을 handler로 처리하여 중복코드를 줄임
- [관련공부기록](https://blog.naver.com/fwangjuwon/222698014061)
<br>

> ### 코드재사용패턴
- 자주 사용되는 코드를 util로 만들어놓고 필요한 곳에 href로 넣어 중복코드를 줄임
- [관련공부기록](https://blog.naver.com/fwangjuwon/222704702524)
<br/>

>### builder 패턴
- 한 번에 객체를 생성하고, 각 인자가 어떤 의미인지 알기 쉬운 빌더패턴 사용
- [관련공부기록](https://blog.naver.com/fwangjuwon/222697981850)
<br/>

>### JUnit
- JUnit으로 테스트코드 작성해서 자동화 테스트 진행
- [관련공부기록](https://blog.naver.com/fwangjuwon/222763880131)
<br/>

>### Validation
- @Valid를 이용한 데이터 유효성 검증 
- [관련공부기록](https://blog.naver.com/fwangjuwon/222698014061)
<br>

>### fileupload(외부 파일 경로 지정)
- 외부폴더에 사진을 저장했고, WebMvcConfigurer으로 외부경로를 탐색해서 업로드 함
- [관련공부기록](https://blog.naver.com/fwangjuwon/222707746250)
<br>

>### 페이징
- Pageable 객체를 사용해서 자동으로 paging처리
- [관련공부기록](https://blog.naver.com/fwangjuwon/222707750046)
<br/>

>### AOP 처리
- AOP처리를 해서 핵심로직에만 집중하게 함
- [관련공부기록](https://blog.naver.com/fwangjuwon/222791307014)


<br/>

 ## 💡 사용 기술

<img src="https://img.shields.io/badge/-Java-007396"/>  <img src="https://img.shields.io/badge/-Spring-6DB33F"/>  <img src="https://img.shields.io/badge/-Apach%20Tomcat-F8DC75"/> <img src="https://img.shields.io/badge/-MariaDB-071D49"/> 
<img src="https://img.shields.io/badge/-HTML5-E34F26"/> <img src="https://img.shields.io/badge/-CSS-1572B6"/> <img src="https://img.shields.io/badge/-JavaScript-F7DF1E"/> <img src="https://img.shields.io/badge/-JQuery-0769AD"/> 
<img src="https://img.shields.io/badge/-Github-181717"/> <img src="https://img.shields.io/badge/-Git-F05032"/> <img src="https://img.shields.io/badge/-BootStrap-7952B3"/> 

- **개발 언어**: Java 11, HTML 5, CSS, JavaScript
- **DataBase**: MariaDB 10.6

- **Library**
- ***Front***
    - Bootstrap 5.1.3, jQuery 3.5.1, Summernote, Mustache
- ***Back***
    - Spring Web, Spring Boot Devtools, Lombok, MariaDB Driver, Spring Data JPA, Spring Security
- **개발 환경** : VS Code, SpringBoot 2.5.12, Gradle, Lombok, JPA
- **Test** : JUnit
<br/>
<br/>
  
## 💡블로그 ver.3 에서 추가된 기능 

### 🛠 파일업로드 기능
| 프로필사진 업로드 | 게시판 사진 업로드 |
|------|------|
|![profileupload](https://user-images.githubusercontent.com/104547351/185288784-8879f49c-1f6f-4726-a855-03789480374d.gif)|![upload](https://user-images.githubusercontent.com/104547351/185288798-e82e7f28-66d8-4a18-9013-e7dde3bdb164.gif)|
<br/>

### 🛠 좋아요 기능
| 좋아요 | 좋아요 취소 |
|------|------|
|![like](https://user-images.githubusercontent.com/104547351/185288802-3179e8b5-4a04-40a9-a69b-7b4bcd756af9.gif)|![unlike](https://user-images.githubusercontent.com/104547351/185288796-d52acf8a-c6bf-4bf8-a392-c118a9383302.gif)|
<br/>

### 🛠 방문자 수 count 기능
| 방문자수 count | 방문자수 count |
|------|------|
|![visitcount](https://user-images.githubusercontent.com/104547351/185288800-0918fe00-e358-4177-8c48-5eff85959e42.gif)|![visitcount](https://user-images.githubusercontent.com/104547351/185288800-0918fe00-e358-4177-8c48-5eff85959e42.gif)|
<br/>

## 💡구현결과(영상 링크)
 [<img width="1268" alt="Screenshot_15" src="https://user-images.githubusercontent.com/104547351/184179515-2269f1f9-61af-4c1e-a9eb-796294c01b3f.png">](https://youtu.be/7IsoCG80q2o)

 <br>

## 💡ERD
<img width="564" alt="Screenshot_34" src="https://user-images.githubusercontent.com/104547351/185295300-1b505a6c-715f-4929-9fa8-e2b4e775aeb9.png">


<br/>

## 💡 프로젝트 리뷰 및 개선방향
- Spring security으로 보안기능을 업그레이드 했다. 회원가입 시, 암호를 해쉬화 시켜서 저장했다. 하지만 회원 수정 시, 암호를 재설정 해서 저장했을 때 해쉬화되지 않고 저장이 되었다. 이 부분을 어떻게 해결해야할 지 계속 고민해봐야겠다.
- 연관된 테이블이 많아질수록 n+1 문제가 많이 발생했다. (레이지로딩) n+1문제를 해결하기 위해 응답dto를 따로 생성해 영속화를 끊어주었다. 이 부분은 개발할 때마다 헷갈린다. 고민을 잘 하고 개발해야겠다. 
- junit으로 테스트코드를 처음 작성해봤다. 지금까지는 postman 툴을 사용해서 원하는 응답이 나타나는지 일일이 확인했었다. 이 경우 매번 코드 변화가 있을 때마다 검증작업을 해야하는 것이 귀찮았다. 
 테스트 코드를 작성하니 자동적으로 테스트되어서 훨씬 편했다. 어떤 코드를 추가했을 때, 그 결과가 다른 코드에 영향을 끼치는지도 자동화되어 해결할 수 있었다는 점이 좋았다. 
잘 짠 테스트는 그 자체로 코드의 문서 역할을 할 수 있다는 것을 알았다. 또, 동료 개발자에게 해당 기능을 설명하는 역할을 할 수 있다는 점을 알게되었다. 
- 아직 배포를 한 번도 해보지 않았다. 다음 프로젝트에서는 배포까지 마쳐서 나만의 웹사이트를 만들어보고 싶다. 