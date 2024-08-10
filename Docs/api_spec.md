## API Specs
### 1. 유저 대기열 토큰 발급 API POST /api/token
##### Request
- userId : Long
##### Response
```json
{
  "code": 200,
  "desc": "성공",
  "data": {
    "userId": "Long", // 토큰
    "position": "Long", // 대기 번호
    "waitingTime": "String" // 예상 대기 시간
  }
}
```
### 2. 유저 대기열 확인 API GET /api/token/{userId}
##### Request (Path Parameter)
- userId : Long
##### Response
```json
{
  "code": 200,
  "desc": "성공",
  "data": {
    "userId": "Long", // 토큰
    "position": "Long", // 대기 번호
    "waitingTime": "String" // 예상 대기 시간
  }
}
```
### 3. 콘서트 조회 API GET /api/concerts
##### Header
- userId : Long
##### Request
- page : int
- size : int
##### Response
```json
{
  "code": 200,
  "desc": "성공",
  "data": [
    {
      "concertId": "Long", // 콘서트 id
      "title": "String" // 콘서트 제목
    }
  ]
}
```
### 4. 예약 가능 날짜 조회 API GET /api/concerts/schedules/{contentId}
##### Header
- userID : Long
##### Request (Path Parameter)
- contentId : Long
##### Response
```json
{
  "code": 200,
  "desc": "성공",
  "data": [
    {
      "scheduleId": "Long", // 날짜 id
      "concertId": "Long", // 콘서트 id
      "scheduleDate": "LocalDateTime", // 날짜
      "totalSeat": "Long" // 총 좌석
    }
  ]
}
```
### 5. 해당 날짜 예약 가능 좌석 조회 API GET /api/concerts/seats/{scheduleId}
##### Header
- userId : Long
##### Request (Path Parameter)
- scheduleId : Long
##### Response
```json
{
  "code": 200,
  "desc": "성공",
  "data": [
    {
      "seatId": "Long", // 좌석 id
      "seatStatus": "SeatStatus", // 좌석 상태 (예약 가능, 예약 불가)
      "seatNumber": "Long", // 좌석 번호
      "scheduleId": "Long", // 날짜 id
      "price": "Long" // 가격
    }
  ]
}
```
### 6. 좌석 예약 요청 API POST /api/reservations/seats
##### Header
- userId : Long
##### Request
```json
{
  "userId": "Long", // 사용자 id
  "contentId": "Long", // 콘서트 id
  "scheduleId": "Long", // 날짜 id
  "seatIdList": "List<Long>" // 예약 좌석 리스트
}
```
##### Response
```json
{
  "code": 200,
  "desc": "성공",
  "data": {
    "reservationId": "Long", // 예약 id
    "concertTitle": "string", // 콘서트 제목
    "userId": "Long", // 사용자 id
    "seatIdList": "List<Long>", // 예약 좌석 리스트
    "totalPrice": "Long", // 총 가격
    "reservationExpiry": "LocalDateTime" // 만료 시간
  }
}
```
### 7. 잔액 충전 API PUT /api/balance/charge
##### Request
```json
{
  "userId": "Long", // 사용자 id
  "amount": "Long" // 잔액
}
```
##### Response
```json
{
  "code": 200,
  "desc": "성공",
  "data": {
    "userId": "Long", // 사용자 id
    "amount": "Long" // 잔액
  }
}
```
### 8. 잔액 조회 API GET /api/balance/{userId}
##### Request (Path Parameter)
- userId : Long
##### Response
```json
{
  "code": 200,
  "desc": "성공",
  "data": {
    "userId": "Long", // 사용자 id
    "amount": "Long" // 잔액
  }
}
```
### 9. 결제 API POST /api/reservations/payments
##### Header
- userId : Long
##### Request
```json
{
  "userId": "Long", // 사용자 id
  "reservationId": "Long", // 예약 id
  "amount": "Long" // 결제 금액
}
```
##### Response
```json
{
  "code": 200,
  "desc": "성공",
  "data": {
    "paymentId": "Long", // 결제 id
    "paymentTime": "LocalDateTime" // 결제 시간
  }
}
```
