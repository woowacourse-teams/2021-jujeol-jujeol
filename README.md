

# 술 알고 마시자! 술 추천 및 리뷰 서비스 주절주절

- [jujeol-jujeol.com](https://jujeol-jujeol.com) 에서 이용하실 수 있어요.
- [주절주절에 대한 더 많고 흥미진진한 이야기들](https://github.com/woowacourse-teams/2021-jujeol-jujeol/wiki)

---

## 🍻 Intro

<p align="center">
  <img width="640" alt="jujeol_og_image" src="https://user-images.githubusercontent.com/67677561/127765454-d9abca84-093f-46b9-b31c-e73e3ef4090c.png">
</p>
<p align="center">
  <img src="https://img.shields.io/badge/react-v17.0.2-9cf?logo=react" alt="react" />
  <img src="https://img.shields.io/badge/spring_boot-v2.5.2-green?logo=springboot"  alt="spring-boot" />
  <img src="https://img.shields.io/badge/typescript-v4.3.5-blue?logo=typescript" alt="typescript"/>
  <img src="https://img.shields.io/badge/mysql-v8.0.26-blue?logo=mysql" alt="mysql"/>
</p>


**술 알고 마시자! 알고 마시면 더 맛있는 술!** </br> 당신을 위한 술을 추천해드립니다.

편의점에서 처음 보는 술을 봤을때, 어떤맛인지 궁금할 땐? </br> *아 오늘 와인이 마시고 싶은데*, 내 취향에 맞는 와인이 궁금할 땐?

바로, **주절주절**
</br>
</br>

## 🚀 Demo

|                   💖 맞춤 술 추천 (로그인O)                   |                   👍 베스트 추천 (로그인X)                    |                         🌟 일괄 평가                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/52682603/138669764-6c39fc46-29a8-498b-bef0-ce716685ff13.gif" alt="유저_맞춤_술_추천" width=80%> | <img src="https://user-images.githubusercontent.com/52682603/138669772-e0481753-3948-45a9-a3d5-b82c12f68cb7.gif" alt="베스트_추천" width=80%> | <img src="https://user-images.githubusercontent.com/52682603/138669812-56e1b460-0950-4915-8cc0-a695057a822c.gif" alt="일괄_평가" width=80%> |

|                         🧐 상세페이지                         |                  👩‍💻 선호도 입력과 리뷰 작성                  |                         🧑‍💻 리뷰 수정                         |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/52682603/138669805-3d7ee13b-cb69-4442-98a3-d51315b729bb.gif" alt="상세페이지" width=80%> | <img src="https://user-images.githubusercontent.com/52682603/138669781-bcabc95b-27e5-4769-8127-4c698f3eed84.gif" alt="선호도_입력과_리뷰_작성" width=80%> | <img src="https://user-images.githubusercontent.com/52682603/138669782-1ccdd80b-53f5-4d7b-b51f-68295086837d.gif" alt="리뷰_수정" width=80%> |

|                👯 로그인 (카카오 소셜 로그인)                 |                     💁 닉네임, 소개 수정                      |                     💆 리뷰와 선호도 관리                     |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/52682603/138669780-9e4cfe84-8a80-409d-8055-a73f9d562b21.gif" alt="로그인(카카오_소셜_로그인)" width=80%> | <img src="https://user-images.githubusercontent.com/52682603/138669758-bb5c2c92-d92e-4a08-bb73-48bc3baac7e8.gif" alt="닉네임_소개_수정" width=80%> | <img src="https://user-images.githubusercontent.com/52682603/138669786-4b29f0db-569a-4fa3-ba15-7c7a656fb3c8.gif" alt="리뷰와_선호도_관리" width=80%> |

|                       🍓 카테고리 검색                        |                       🔎 키워드로 검색                        |                          🥂 전체보기                          |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/52682603/138670451-5b91951d-3045-45a1-a51f-51502f489a67.gif" alt="카테고리_검색" width=80%> | <img src="https://user-images.githubusercontent.com/52682603/138669733-61b71780-15f0-4328-8fbe-57e8f9e088cf.gif" alt="키워드로_검색" width=80%> | <img src="https://user-images.githubusercontent.com/52682603/138669815-6821b6d2-e26c-487d-aae9-72f9aaf8bfff.gif" alt="전체보기" width=80%> |
</br>
</br>

## 👑 Recommendation Algorithm

<p algin="center">
	<img src="https://user-images.githubusercontent.com/52682603/138669346-4e85a80c-9615-40c2-9eff-8173a0d2cf25.png" alt="recommend_algorithm" width=50%>  
</p>

주절주절은 [직접 구현한 **협업 필터링 알고리즘**](https://jujeol-jujeol.github.io/2021/10/24/%EC%95%84%EC%9D%B4%ED%85%9C_%EA%B8%B0%EB%B0%98_%EC%B6%94%EC%B2%9C_Slope_One/)으로 사용자 맞춤형 서비스를 제공해요. 

주절주절은 초기에 **사용자 기반**의 알고리즘을 구성했어요. 이는 한 사용자가 많은 선호도를 남겨야 유의미한 알고리즘이죠.</br>
하지만 **슬로프 원 알고리즘**을 이용해 **아이템 기반** 필터링 방식으로 변경하여 선호도를 적게 남기더라도 즉시 기능을 누릴 수 있게 되었답니다!
</br>
</br>

## ⚡️ Skills

더 많은 기술 이야기는 [조잘조잘](https://jujeol-jujeol.github.io/)에서 확인하세요.

### Front-end

<p>
  <img src="https://user-images.githubusercontent.com/52682603/138834243-fb74d81e-e90d-4c6a-8793-05df588f59ab.png" alt="react" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834262-a7af2293-e398-416d-8dd3-ff5fab8cb80d.png" alt="type_script" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138835731-e0e727ad-0bd1-44ca-a3b3-98c4d1b89c20.png" alt="react_query" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834258-c4b4a706-3a7e-40c8-8a08-c0ac4815d7e0.png" alt="emotion" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834282-b22f1f99-5470-4bc4-9b5b-4b7ed1325643.png" alt="react_testing_library" width=15%>
</p>

- **React** 로 사용자와의 상호작용이 많은 웹 페이지를 효율적으로 구현하고 관리했어요.
- **Typescript** 를 사용해 빌드 시에 오류를 미리 찾아, 코드 안정성을 높였어요. 또한 동료와의 커뮤니케이션이 원활해졌어요.
- **React Query** 를 비동기 관련 로직과 상태를 관리했어요. API 응답 데이터를 캐싱함으로써 서버 통신 비용을 줄였어요.
- **Emotion** 으로 자바스크립트로 스타일을 관리했어요. 반복되는 스타일 컴포넌트를 재활용하고, 상태에 따른 스타일 변경에 용이해요.  
- **React Testing Libray** 로 테스트 코드를 작성했어요. 자신감있는 리팩토링을 통해 완성도 높은 코드를 짤 수 있어요.

---

### Back-end

<p>
  <img src="https://user-images.githubusercontent.com/52682603/138834253-9bcd8b12-241f-41b2-85c4-d723a16bdb58.png" alt="spring_boot" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834267-c86e4b93-d826-4fd4-bcc8-1294f615a82d.png" alt="hybernate" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834280-73acd37b-97ef-4136-b58e-6138eb4fcc46.png" alt="query_dsl" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834265-5e9d309b-6b78-4c5e-adf3-981f705b7042.png" alt="flyway" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834240-a4d7218f-db96-4c51-83f5-9b80f6d38758.png" alt="spring_rest_docs" width=15%>
</p>

- **Springboot** 로 웹 어플리케이션 서버를 구축했어요.
- **Spring Data JPA(Hibernate)** 로 객체 지향 데이터 로직을 작성했어요.
- **QueryDSL** 로 컴파일 시점에 SQL 오류를 감지해요. 더 가독성 높은 코드를 작성할 수 있어요.
- **Flyway** 로 데이터베이스 버전 관리를 하고 있어요.
- **RestDocs** 로 작성한 API 문서를 통해 클라이언트와 원활하게 소통해요.

---

### Infra Structure

<p>
  <img src="https://user-images.githubusercontent.com/52682603/138834271-9004826c-7b4e-418a-95ea-57d1c05f6e8a.png" alt="aws_ec2" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834237-281c9c48-54f6-40b6-a97e-4befc0ab810c.png" alt="docker" width=15%>
</p>

- **AWS EC2** 와 **Docker** 를 사용해 서버를 구축했어요.

#### CI/CD

<p>
  <img src="https://user-images.githubusercontent.com/52682603/138834259-b48d26eb-b6e8-490c-a839-450d8ab9bfd2.png" alt="jenkins" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834250-77b1ee2e-2cd2-492a-a789-0282d4fac0b8.png" alt="sonarqube" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834229-e8a9dcb0-bdb8-4aec-9a3e-be1f9ff44149.png" alt="github_actions" width=15%>
</p>

- **Jenkins** 로 백엔드 코드의 지속적 배포와 무중단 배포를 진행해요.
- **SonarQube**  백엔드 코드의 퀄리티를 분석해 더 양질의 코드를 위해 노력해요.
- **Github Actions** 로 코드 퀄리티와 테스트를 검사해요. 프론트엔드 코드의 지속적 배포를 진행해요. 

#### DB

<p>
  <img src="https://user-images.githubusercontent.com/52682603/138834274-af159b46-dff3-4131-be66-9a6900fb7db9.png" alt="mysql" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834254-91db1f7e-9750-49b8-8c47-ce54d96cc66c.png" alt="elastic_search" width=15%>
</p>

- 데이터 베이스는 **MySQL**을 사용해요.
- **ElasticSearch** 로 검색 데이터베이스를 분리했어요. Full-text 검색으로 더 정확한 결과를 얻을 수 있어요.

#### Network

<p>
  <img src="https://user-images.githubusercontent.com/52682603/138834268-8c9c8420-d854-4b50-ad43-5092243c3bad.png" alt="aws_cloud_front" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138835931-103681d7-a630-45b0-8103-e5f56ef15d9e.png" alt="nginx" width=15%>
</p>

- **CloudFront** 를 활용해 CDN 환경을 구성했어요.  
- **Nginx** 를 리버스 프록시와 로드 밸런서로 활용하고 있어요.

#### Monitoring & Testing

<p>
  <img src="https://user-images.githubusercontent.com/52682603/138834264-b88dfc2d-5649-42da-b2b8-e4532b572f79.png" alt="prometheus" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138843856-e8fe1938-b78a-437d-84fa-75c3cc72b167.png" alt="grafana" width=15%>
  <img src="https://user-images.githubusercontent.com/52682603/138834279-f606ede9-874c-4eee-bf89-ac43daeda6af.png" alt="ngrinder" width=15%>
</p>

- **Prometheus** 로 서버의 상태를 확인해요.
- **Grafana** 가 Prometheus 로 확인한 결과를 시각화 시켜줘요.
- **nGrinder** 로 웹 어플리케이션 서버 요청과 데이터베이스에 대한 부하 테스트를 진행해요.
</br>
</br>

## 🌈 Members

|            [서니](https://github.com/sunhpark42)             |            [티케](https://github.com/devhyun637)             |             [웨지](https://github.com/sihyung92)             |             [소롱](https://github.com/soulgchoi)             |              [피카](https://github.com/pika96)               |             [크로플](https://github.com/perenok)             |             [나봄](https://github.com/qhals321)              |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://avatars.githubusercontent.com/u/67677561?s=400&v=4" width=200px alt="_"/> | <img src="https://avatars.githubusercontent.com/u/59258239?s=400&v=4" width=200px alt="_"/> | <img src="https://avatars.githubusercontent.com/u/51393021?s=400&v=4" width=200px alt="_"/> | <img src="https://avatars.githubusercontent.com/u/52682603?s=400&v=4" width=200px alt="_"> | <img src="https://avatars.githubusercontent.com/u/52442949?s=400&v=4" width=200px alt="_"> | <img src="https://avatars.githubusercontent.com/u/68995534?s=400&v=4" width=200px alt="_"> | <img src="https://avatars.githubusercontent.com/u/63535027?s=400&v=4" width=200px alt="_"> |
|                         🍷 프론트엔드                         |                         🍾 프론트엔드                         |                           🍺 백엔드                           |                           🥃 백엔드                           |                           🍸 백엔드                           |                           🍹 백엔드                           |                           🍶 백엔드                           |
