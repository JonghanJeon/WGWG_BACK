# WGWG : MZ 세대 소비 습관 개선 커뮤니티

### Collaborators
![image](https://github.com/JonghanJeon/WGWG_BACK/assets/117141827/bcb14df0-c73e-4d6f-8d16-20a6eb4dafc5)

### Introduction

기존의 거지방은 `채팅 이외의 기능이 존재하지 않고, 강력한 동기부여 요소의 부재`.<br/>
`챌린지 기능을 통해 실질적 참여`를 높이고, `가계부 기록을 통해 본인의 지출내역을 시각화` 하였으며, `보증금 제도를 통한 강력한 동기부여`를 통한 소비 습관 개선을 목적으로 개발.

### Tech Stack

![image](https://github.com/JonghanJeon/WGWG_BACK/assets/117141827/004d71e0-0c6c-4934-bfc8-47317a2108c3)

### FEATURES
주요기능을 서술합니다.

#### 챌린지

![image](https://github.com/JonghanJeon/WGWG_BACK/assets/117141827/48c12918-38d1-4a78-9658-5db39647decc)

- N 챌린지와 커피 챌린지 두가지로 나누어 집니다.
- N 챌린지는 일정 기간동안 일정 금액으로 생활하는 챌린지 입니다.
- 커피 챌린지는 일정 기간동안 매일 일정 금액을 저축하는 챌린지 입니다.
- 두 챌린지 모두 `PortOne 결제 API`를 사용하여 챌린지에 알맞은 금액을 결제하거나 보증금을 결제하게 됩니다.
- `스케줄러(@Scheduled)`를 사용하여 매일 자정에 유저의 결제 내역 및 개인 지출 내역을 확인인하고, 이를 통해 성공/실패 여부를 나누어 현재 유저들의 상황을 빠르게 확인할 수 있습니다.

#### 마이페이지

![image](https://github.com/JonghanJeon/WGWG_BACK/assets/117141827/b88ca8d0-9702-4f0e-830e-9203bf49dbb6)

- *가계부* : 가계부를 작성하여 본인의 지출 내역을 월별로 조회할 수 있고, 누적 수입/지출 내역을 확인할 수 있습니다.
- *챌린지* : 진행중/완료한 챌린지를 확인할 수 있고, 챌린지로 발생한 차익을 확인할 수 있습니다.
- `nivo`차트 라이브러리를 사용하여 기존 데이터를 차트화 하여 한눈에 볼 수 있습니다.

#### 커뮤니티

![image](https://github.com/JonghanJeon/WGWG_BACK/assets/117141827/894b8afd-8ae7-4b43-ac50-891de79e3d34)

- `Spring Pagination API` 와 `React Pagination 라이브러리`를 사용하여 페이징 처리.
- 여러 게시물을 페이지 단위로 분리하여 보내어 I/O 부하를 줄였습니다.
