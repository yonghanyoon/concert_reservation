## API Specs
### 1. 유저 대기열 토큰 발급 API POST /api/auth/token
##### Request
- userId : Long
##### Response
- code : 200
- desc : 성공
- data : {   
  &emsp;user_uuid : String, // 토큰   
  }
### 2. 유저 대기열 확인 API GET /api/auth/token
##### Request
- user_uuid : String
##### Response
- code : 200
- desc : 200
- data : {   
  &emsp;position : Integer   
  &emsp;tokenStatus : TokenStatus   
  &emsp;expirationTime : LocalDateTime   
  }
### 3. 예약 가능 날짜 조회 API GET /api/reservation/{contentId}
##### Header
- uuid : String
##### Request (Path Parameter)
- contentId : Long
##### Response
- code : 200
- desc : 성공
- data : {   
  &emsp;scheduleList: {[   
  &emsp;&emsp;scheduleId : Long, // 날짜 id   
  &emsp;&emsp;contentId : Long, // 컨텐츠 id   
  &emsp;&emsp;scheduleDate : LocalDateTime // 예약 가능한 날짜   
  &emsp;]}   
  }
### 4. 해당 날짜 예약 가능 좌석 조회 API GET /api/reservation/seat/{scheduleId}
##### Header
- uuid : String
##### Request (Path Parameter)
- scheduleId : Long
##### Response
- code : 200
- desc : 성공
- data : {   
  &emsp;seatList: {[   
  &emsp;&emsp;seatId : Long, // 좌석 id   
  &emsp;&emsp;seatNumber : Long, // 좌석 번호   
  &emsp;&emsp;price : Long // 가격   
  &emsp;]}   
  }
### 5. 좌석 예약 요청 API POST /api/reservation/seat
##### Header
- uuid : String
##### Request
- userId : Long
- contentId : Long
- scheduleId : Long
- seatIdList : List<Long>
##### Response
- code : 200
- desc : 성공
- data : {  
  &emsp;seatIdList : List<Long>, // 좌석 id 리스트   
  &emsp;seatNumberList : List<Integer> // 좌석 넘버 리스트  
  &emsp;totalPrice : Long, // 결제 가격   
  &emsp;reservationExpiry : LocalDateTime // 임시 예약 만료 시간   
  }
### 6. 잔액 충전 API PATCH /api/payment/charge
##### Header
- uuid : String
##### Request
- userId : Long
- amount : Long
- type : PaymentType(charge)
##### Response
- code : 200
- desc : 성공
- data : {   
  &emsp;userId : Long, // 사용자 id   
  &emsp;balance : Long // 잔액   
  }
### 7. 잔액 조회 API GET /api/payment/{userId}
##### Header
- uuid : String
##### Request (Path Parameter)
- userId : Long
##### Response
- code : 200
- desc : 성공
- data : {   
  &emsp;userId : Long, // 사용자 id   
  &emsp;balance : Long // 잔액   
  }
### 8. 결제 API POST /api/payment
##### Header
- uuid : String
##### Request
- userId : Long // 사용자 id
- type : PaymentType(payment) // 거래 유형
- reservationId : Long // 예약 id
##### Response
- code : 200
- desc : 성공
- data : {   
  &emsp;paymentId : Long, // 결제 id   
  &emsp;paymentTime : LocalDateTime // 결제 시간   
  }