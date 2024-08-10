# 콘서트 예약 서비스

### [MSA 보고서](https://github.com/yonghanyoon/concert_reservation/blob/main/Docs/msa.md)
-----
### [index 보고서](https://github.com/yonghanyoon/concert_reservation/blob/main/Docs/index.md)
-----
### [대기열 보고서](https://github.com/yonghanyoon/concert_reservation/blob/main/Docs/waiting.md)
-----
### [캐싱 보고서](https://github.com/yonghanyoon/concert_reservation/blob/main/Docs/cache.md)
-----
### [락 비교 보고서](https://github.com/yonghanyoon/concert_reservation/blob/main/Docs/lock.md)
-----
### [Milestone](https://github.com/users/yonghanyoon/projects/1)
-----
### [Sequence Diagram](https://github.com/yonghanyoon/concert_reservation/blob/main/Docs/concert_flow.svg)
-----
### [ERD](https://github.com/yonghanyoon/concert_reservation/blob/main/Docs/concert_erd.svg)
-----
### [ApiSpecs](https://github.com/yonghanyoon/concert_reservation/blob/main/Docs/api_spec.md)
-----
### [Swagger](https://github.com/yonghanyoon/concert_reservation/blob/main/Docs/swagger.md)
-----
1️⃣ **`주요` 유저 대기열 토큰 기능**

- 서비스를 이용할 토큰을 발급받는 API를 작성합니다.
- 토큰은 유저의 UUID 와 해당 유저의 대기열을 관리할 수 있는 정보 ( 대기 순서 or 잔여 시간 등 ) 를 포함합니다.
- 이후 모든 API 는 위 토큰을 이용해 대기열 검증을 통과해야 이용 가능합니다.

> 기본적으로 폴링으로 본인의 대기열을 확인한다고 가정하며, 다른 방안 또한 고려해보고 구현해 볼 수 있습니다.
> 
-> TokenQueue 테이블을 이용해 userId로 토큰 대기열 발급 Api 요청 시 해당 userId에 존재하는 토큰이 없거나 만료된 경우 새로 발급
-> 폴링으로 대기열 조회 Api를 계속 요청해 입장 상태이면서 tokenId가 가장 높은 id와 비교해 대기 순번 return

**2️⃣ `기본` 예약 가능 날짜 / 좌석 API**

- 예약가능한 날짜와 해당 날짜의 좌석을 조회하는 API 를 각각 작성합니다.
- 예약 가능한 날짜 목록을 조회할 수 있습니다.
- 날짜 정보를 입력받아 예약가능한 좌석정보를 조회할 수 있습니다.

> 좌석 정보는 1 ~ 50 까지의 좌석번호로 관리됩니다.
> 

3️⃣ **`주요` 좌석 예약 요청 API**

- 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
- 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
- 만약 배정 시간 내에 결제가 완료되지 않는다면 좌석에 대한 임시 배정은 해제되어야 하며 다른 사용자는 예약할 수 없어야 한다.

-> 좌석id와 status를 복합 pk로 가져가면서 lock을 사용하지 않고 동시성 해결
-> 기존에 insert 되어 있던 좌석을 예약 요청이 들어오면 status를 업데이트하는 방식으로 해결
-> 임시 예약 5분 구현 방안
1. 임시 배정 시간을 예약신청과 동시에 5분 후로 업데이트 -> 예약 가능 날짜 조회 시 or 예약 시 현재 시간이 임시 배정 시간보다 이전인 경우 예외처리
2. 콰츠 스케줄러 1분단위 동작 -> 현재 시간보다 임시 배정 시간이 이전이면 상태 변경

4️⃣ **`기본`**  **잔액 충전 / 조회 API**

- 결제에 사용될 금액을 API 를 통해 충전하는 API 를 작성합니다.
- 사용자 식별자 및 충전할 금액을 받아 잔액을 충전합니다.
- 사용자 식별자를 통해 해당 사용자의 잔액을 조회합니다.

5️⃣ **`주요` 결제 API**

- 결제 처리하고 결제 내역을 생성하는 API 를 작성합니다.
- 결제가 완료되면 해당 좌석의 소유권을 유저에게 배정하고 대기열 토큰을 만료시킵니다.
