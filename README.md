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
      
```
