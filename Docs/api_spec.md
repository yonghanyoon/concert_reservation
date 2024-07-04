## API Specs
### 1. 유저 대기열 토큰 발급 API POST /api/auth/token
##### Request
- userId : Long
##### Response
- code : 200
- desc : 성공
- data {   
  &emsp;user_uuid : String, // 토큰   
  &emsp;position : Integer, // 대기 번호   
  &emsp;expirationTime : LocalDateTime // 토큰 만료 시간   
  }
### 2. 예약 가능 날짜 조회 API GET /api/reservation/{contentId}
##### Header
- user_uuid : String
##### Request
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
### 3. 해당 날짜 예약 가능 좌석 조회 API GET /api/reservation/seat/{scheduleId}
##### Header
- user_uuid : String
##### Request
- scheduleid : Long
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
### 4. 좌석 예약 요청 API POST /api/reservation/seat
##### Header
- user_uuid : String
##### Request
- userId : Long
- contentId : Long
- scheduleId : Long
- seatIdList : List<Long>
##### Response
- code : 200
- desc : 성공
- data : {  
  &emsp;reservationId : Long, // 예약 id   
  &emsp;seatIdList : List<Long>, // 좌석 id 리스트   
  &emsp;totalPrice : Long, // 결제 가격   
  &emsp;reservationExpiry : LocalDateTime // 임시 예약 만료 시간   
  }
### 5. 잔액 충전 API PATCH /api/payment/charge
##### Request
- userId : Long
- amount : Long
- type : PaymentType(charge)
- paymentTime : LocalDateTime
##### Response
- code : 200
- desc : 성공
- data : {   
  &emsp;userId : Long, // 사용자 id   
  &emsp;balance : Long // 잔액   
  }
### 6. 잔액 조회 API GET /api/payment/{userId}
##### Header
- user_uuid : String
##### Request
- userId : Long
##### Response
- code : 200
- desc : 성공
- data : {   
  &emsp;userId : Long, // 사용자 id   
  &emsp;balance : Long // 잔액   
  }
### 7. 결제 API POST /api/payment
##### Header
- user_uuid : String
##### Request
- userId : Long // 사용자 id
- type : PaymentType(payment) // 거래 유형
- reservationId : Long // 예약 id
##### Response
- code : 200
- desc : 성공
- data : {   
  &emsp;reservationStatus : ReservationStatus(success), // 예약 상태   
  &emsp;reservationTime : LocalDateTime // 예약 시간   
  }