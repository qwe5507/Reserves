## 백엔드 포지션 지원자, 이진현

> 현재 근무 중인 회사에서 이번주 주말 까지 급한 일정이 있어, 급하게 개발한 부분 까지 제출합니다.
> 시간을 더 주시면 테스트 코드, 개발 추가요건을 더 개발하고 싶습니다.

### erd
![erd.PNG](src/main/resources/erd.PNG)

- 사용 취소 API를 고려하여, 다대다 테이블로 설계 하였습니다.
  - history에서 사용 취소 시, 여러 Reserves를 Rollback 하기 위함

### API
1. 적립금 적립
2. 적립금 합계 조회
3. 적릭금 사용
4. 적립/사용 내역 조회

### 실행방법 (IntelliJ IDEA)
1. git clone
2. Q파일 생성 (gradle - other - complieQueryDsl)
3. application 구동
