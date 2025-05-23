# 우리FISA :: 개인 맞춤 종합 소비 플랫폼

![image](https://github.com/user-attachments/assets/98eba53a-1446-4b71-84bf-fb78c6ee695c)

- 개인 맞춤형 종합 소비 플랫폼을 통해 개인의 소비를 이해하고 분석
- 고객이 친근하고 재미있게 자신의 소비 데이터를 관리할 수 있는 새로운 경험을 제공

---

## 주요 기능
- 사용자의 소비 성향을 기반으로, 맞춤형 재치있는 문장과 이미지를 생성하여 사용자에게 재미있는 시각적 피드백을 제공
- 사용자가 월간 캘린더와 결제 내역을 관리하고, 소비 평가를 통해 피드백을 제공
- 카드 회사의 혜택 정보를 활용해 사용자가 원하는 혜택을 제공하는 챗봇 서비스
- 금융 / 생활 경제 뉴스 제공
- 사용자의 소비 내역을 분석하여 카테고리, 카드 별 그래프를 보여줌
- 사용자의 현재 위치를 기반으로 근처 매장을 검색하고, 관련 카드 정보 제공

---

## 데이터 소개

- **우리카드 데이터 분석**
  - 코드별로 묶어 대분류로 추출
  - 성별과 연령대별로 소비 카테고리 비율을 분석

- **데이터 생성 (faker api / GPT)**
  - 분석한 비율을 바탕으로 데이터 생성
  - 카테고리 : 13개의 그룹 (기타 제외)
  - 2019년-2024년, 5년치
  - 20-40대 여성과 남성 총 6명
  - 연령대마다 소비 카테고리 비율 차이

## 시스템 아키텍처
![그림3](https://github.com/user-attachments/assets/8f5e02d6-d9fd-4570-ab83-85f77fb0895b)

## 개발환경
- Frontend: HTML, CSS, JavaScrip, Thymeleaf
- Backend: Spring Boot, FastAPI, MySQL, Docker container, 제공된 API 활용
- 버전 및 이슈관리: GitHub, GitHub Issues
- 협업 툴: Notion
- 서비스 배포 환경: AWS

## 개선 사항
- **캘린더 기능 수정**
  - 사용자가 입력하는 식이 아닌 이미 사용중인 캘린더를 연동하여 보여주기

- **소비 예측 모델**
  - 시계열을 반영하여 모델링하기
  - 다양한 모델 사용해보기
