# USER-MANAGEMENT-REPO
Spring Batch 와 Spring Security를 사용하여 만든 유저관리프로그램
코드 개선점

<hr>
전에 했던 코드 방식과 전혀 다르고 최대한 돌아가는 방식으로 학습하며 만드는 중 
<br>
<br>



1. Member 조회할 때 JPQL 만으로 간단하게 Entity를 분리시키는 방식이 아닌 ElementCollection을 사용했었더라면 ROLE을 Entity로 따로 만들었고 JPQL 로 join 하여 가져오는 것이 아닌 Querydsl로 FetchJoin하여 가져오도록 했다.<br>
      - JPQL로 가져오는 것이 확실히 코드량이 적고 간편하다.<br>
      - 하지만 DB에 들어가는 값이 ENUM 이기에 바꾸기 까다로움<br>
      - Entity를 분리시키니 DB에 들어가는 값들을 자유롭게 변경이 가능함<br>
      - cascade, orpahremoval을 전부 부모에 맞추고 할 경우 @OneToMany 관계에 같이 insert를 실해하게 할 수 있지만 자식 Entity에 id 값을 지정해줘야한다. 일반 id 값이라면 어렵지만 fileName과 같은 UUID 형식이면 부모 Entity에 add 메소드를 생성하는 것이 코드짜기 편하고 다른 사람이 알아보기 편하다<br>
      
```
