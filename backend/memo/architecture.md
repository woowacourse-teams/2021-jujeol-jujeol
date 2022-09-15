*Domain-model*

- UseCase : 도메인의 행동을 담은 인터페이스. 하나의 행동을 표현한다.
- Port : UseCase의 실제 구현에 필요한 저장소(연결)를 의미한다.

*Domain*

- UseCase 가 구현되어있는 모듈. 도메인 비지니스를 표현해준다.

*Data*

- DomainRepository : Data Access 기술에 관계없이 저장소의 CRUD 명령을 표현하는 계층이다.
- Repository : 구체적인 Data Access 기술(JPA, jdbc 등)로 저장소의 CRUD 를 실행한다.

*jujeol-App*

실제로 띄워지는 앱이다. 세가지의 레이어로 나눠져있다.

- Controller : 클라이언트의 요청, 응답에 대한 직렬화, 역직렬화 역할을 맡는다. Request 와 Response 는 api 마다 따로따로 만들어주자.
- Presenter : 표현 계층이라 칭한다. 각 api 의 비지니스를 ‘표현'하는 레이어이다. 무엇을 실행하는지, 어떤 점이 잘못되었을 때 어떻게 표현할 것인지와 같이 어플리케이션 비지니스의 흐름을 표현한다. 아무리 중복코드가 발생한다 해도 표현의 흐름이라면 모두 표현해주자. (각 api 의 비지니스는 독립적이다.)
- Service : 응용 서비스라 칭한다. 도메인을 응용해 다양한 서비스를 제공한다.

*예외 전략*

- JujeolBadRequestException 혹은 JujeolServerException 와 같이 http 상태코드와 연관되어 있는 exception 은 Presenter 레이어에서 정의해준다. (하위 레이어에서 발생된 exception 을 catch 해 화면에 맞는 exception 으로 처리)
- 하위 레이어에서는 각 상황에 맞는 exception 을 던지고 해당 메서드에 throw 로 어떤 예외가 발생할 수 있는지 표현한다.