# EcoMate-Server
지구를 위한 챌린지 달성 프로젝트 `EcoMate`의 서버 repository 입니다.
## 소개
> 에코메이트와 함꼐하는 슬기로운 에코생활

에코메이트는 일상 속에서 작은 변화들을 이끌어내어 환경 보호로의 큰 변화를 만들어 나갈 수 있는 플랫폼을 형성하고자 출시한 서비스입니다.
에코메이트에서는 일상에서 가볍게 참여할 수 있는 챌린지를 도전하고, 그 과정을 공유하면서 자연스럽게 지속 가능한 삶의 환경을 조성할 수 있습니다. 더불어, 신선한 에코 정보 및 에코 매장을 제시하여 친환경 경제를 촉진하고 환경 친화적인 선택에 도움을 줄 수 있습니다.

## 기술 스택

<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> 
<img src="https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white"> <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/Firebase-039BE5?style=for-the-badge&logo=Firebase&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<br> 
<img src="https://camo.githubusercontent.com/d6d861dc69e0277b662c1eacfe7fe080392eec62c36905dad5ea61c1eacee200/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f616d617a6f6e205244532d3532374646463f7374796c653d666f722d7468652d6261646765266c6f676f3d416d617a6f6e20524453266c6f676f436f6c6f723d7768697465"> <img src="https://camo.githubusercontent.com/ba50edb9b0efa845ea660bf50a4789f31ad4205ee50cbd05900ec0dff33547d8/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f616d617a6f6e2532306563322d4646393930303f7374796c653d666f722d7468652d6261646765266c6f676f3d616d617a6f6e656332266c6f676f436f6c6f723d7768697465"> <img src="https://camo.githubusercontent.com/377d5e4b9f991e91fe343b09bb2a4c632e8f9650d9698cdeaed0be046a67790c/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f616d617a6f6e25323073332d3536394133313f7374796c653d666f722d7468652d6261646765266c6f676f3d616d617a6f6e7333266c6f676f436f6c6f723d7768697465">

## 시스템 아키텍쳐
![Ecomate_SystemArchi_Client Server](https://github.com/Eco-Mate/.github/assets/75007765/6d28c739-8457-48ca-82f6-7edf9b11c09e)


## 사용 방법

### git clone 실행
```shell
$ git clone https://github.com/Eco-Mate/EcoMate-Server.git
```

### 시스템 설정

#### 1. application-secret.yaml 파일 추가
- src/java/resources 디렉토리에 application-secret.yaml 파일을 추가해주세요.
- `Redis` , `Database`, `aws s3` 연결 설정이 필요합니다.
```yaml
spring:
  datasource:
    username: [database-username]
    password: [database-password]

  jwt:
    secret:
      [jwt-secret-key]

  redis:
    host: [redis-host]

cloud:
  aws:
    credentials:
      access-key: [aws-access-key]
      secret-key: [aws-secret-key]
    s3:
      bucket: [s3-bucket-name]
```

#### 2. firebase 서비스 키 파일 추가
- src/java/resources 디렉토리에 firebase 서비스 키 파일을 추가해주세요.

