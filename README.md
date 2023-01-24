# USER-MANAGEMENT-REPO
Spring Batch 와 Spring Security를 사용하여 만든 유저관리프로그램
코드 개선점

<hr>
전에 했던 코드 방식과 전혀 다르고 최대한 돌아가는 방식으로 학습하며 만드는 중 
<br>
<br>



1. Member 조회할 때 JPQL 만으로 간단하게 Entity를 분리시키는 방식이 아닌 ElementCollection을 사용했었더라면 ROLE을 Entity로 따로 만들었고 JPQL 로 join 하여 가져오는 것이 아닌 Querydsl로 FetchJoin하여 가져오도록 했다.<br>
      - JPQL로 가져오는 것이 확실히 코드량이 적고 간편하다.<br><br>
      - 하지만 DB에 들어가는 값이 ENUM 이기에 바꾸기 까다로움<br><br>
      - Entity를 분리시키니 DB에 들어가는 값들을 자유롭게 변경이 가능함<br><br>
      - cascade, orpahremoval을 전부 부모에 맞추고 할 경우 @OneToMany 관계에 같이 insert를 실해하게 할 수 있지만 자식 Entity에 id 값을 지정해줘야한다. 일반 id 값이라면 어렵지만 fileName과 같은 UUID 형식이면 부모 Entity에 add 메소드를 생성하는 것이 코드짜기 편하고 다른 사람이 알아보기 편하다<br><br>
      - Entity를 insert 할 때 Entity에 PK(Id)가 GeneratedValue로 설정하지 않으면 insert하기 전에 select가 한 번 일어난다. 그 값이 있는지 조회한다는 소리인데 Member 같은 경우 username을 PK로 설정했다. 이 select 쿼리를 막을 방법은 Id를 따로 만들 거나 Persistable을 상속받아 isNew 메소드를 오버라이딩 하는 경우가 있다. 하지만 여기서는 username에 중복을 허용하지 않을 것이기에 이 상태로 그냥 놔두었다. (useranme을 FK로 쓰는 경우가 있기 때문에 앞에 두 방식으로 설정하면 꼬이는 경우가 발생할 수 있다 생각하여 select를 한 번 일으키는 것이 규모가 커질 경우 문제를 일으키지 않을 것 같아 그렇게 설정했다.
      




2. JWT Security 구조
  
   ProjectConfig에서 SecurityFilterChain을 Beanㅇ로 등록하는데 기존 로그인 처리를 하는 필터인 UsernamePasswordAuthenticationFilter 를 SecurityFilterChain에서 addFilterAt을 통해 변경하고 Filter를 상속받은 클래스인 LoginFilter를 만들어 이 자리를 대체했다. 이 부분에서AuthenticationManager 객체를 생성하는 부분이 좀 헤맸다.
  


```java

      AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);


        authenticationManagerBuilder
                .userDetailsService(memoryUserDetailsService)
                .passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

```
   
   
이렇게 작성하면서 좋은 좀은 SuccessHandler를 다룰 수 있다는 점인 거 같다. 이 부분도 물론 오버라이딩으로 구현 가능했었다.
상속 받은 filter인 UsernamePasswordAuthenticationFilter에 successfulAuthentication 메소드를 오버라이딩하면 인증이 성공처리를 할 수 있는 메소드를 작성할 수 있다. 하지만 여기서는 SuccessHandler를 사용하였다. AuthenticationSuccessHandler를 상속받아 onAuthenticationSuccess를 오버라이딩 하면된다. 이렇게 로그인 처리는 진행되고  SecurityFilterChain에 등록해주어야한다.
(실패 핸들러는 AuthenticationFailureHandler를 상속받으면된다)


```java

  LoginSuccessHandler successHandler = new LoginSuccessHandler(jwtUtil);
  LoginFilter loginFilter = new LoginFilter(authenticationManager);
  loginFilter.setAuthenticationSuccessHandler(successHandler);

```


3. OAuth를 개선해야 할 점

- OAuth를 통해 로그인한 사용자를 구분하려면 Member(유저) 테이블에 social(boolean) 값을 컬럼으로 추가하는 것이 select할 때 편리하다. 

> OAuth 사용자도 서비스를 이용해야 하지만 중요한 정보들이 생길 경우 사용자에 보안이 중요하다. 그렇기에 사용자가 OAuth로 로그인 할 경우 닉네임, 성별 이런 값들은 디폴트로 설정하던가      아니면 OAuth 서버를 통해 받아와야 한다. 그런데 사용자가 해당 서비스를 처음 접속한 것인지 이미 접속했던 적이 있는지 판별이 중요하다 이 값은 첫 방문 할 때만 비밀번호를 설정하면 되기에 social(boolean) 값을 통해 판별할 수 있고 좋은 방법이라 생각한다.

> 이 프로젝트에서는 다른 방법으로 처음 방문했을 때 DB에 email 값을 넣었다. 이유는 다른 서비스들을 봤을 때  OAuth를 통해 접속할 경우 바로 비밀번호를 설정하라는 서비스를 본 적이 없기에 여기서는 소셜로 로그인 할 경우 바로 DB에 넣고 재방문시 기존에 저장한 데이터를 다시 반환하도록 만들었다. 하지만 부가적인 필요한 정보들이 있는데 그 정보들은 소셜 로그인한 사용자 모두 기본 값들로 지정하여 사용할 수 있도록 했다. 


- @Id 를 자동으로 해야하는 것인가

> OAuth를 통해 DB 조회할 때도 마찬가지이지만 @Id 가 @GenratedValue로 자동지정이 되어있지 않으면 insert 쿼리가 일어나기 전에 Id 값이 지정되어 Spring Data JPA는 해당 PK가 이미 있는 값으로 판단하여 db에서 select를 날려 가져온 데이터를 update를 할 준비를 한다. 그렇기에 @Id로만 지정할 경우 insert 전에 select 쿼리가 한 번 일어난다. 하지만 이 부분은 
email이 중복이 되어선 안 되기에 중복처리를 해야하는 부분이다. Member를 제외한 다른 부분들에서 이런 부분이 있다면 웬만하면 @GeneratedValue를 통해 @Id가 insert 쿼리가 일어날때 지정되도록 하는 것이 좋은 방법인 것 같다.

