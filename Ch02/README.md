-   뷰와 뷰그룹

### 1. View

-   ImageView, TextView, EditView, Button은 모두 View를 상속받음
-   View는 눈에 보이는 모든 요소이며 클래스의 인스턴스가 만들어져 View에 나타난다
-   View는 ViewGroup에 의해 배치되며, ViewGroup도 결국엔 View를 상속받는다

### 2. ViewGroup

-   layout은 자체 속성과 view를 배치하기 위한 정보인 layoytParams라는 속성을 사용함
-   ViewGroup의 종류
    1. LinearLayout : 전체적인 레이아웃을 분리시켜 차례로 배치
    2. RelativeLayout : 분리된 영역 내의 각 뷰를 기준 뷰인 anchor를 기준으로 상세히 배치
    3. FrameLayout : 뷰를 겹쳐서 배치 할 수 있으며 레이아웃의 전환이 필요할 경우 사용
    4. TableLayout : 태이블 형태로 뷰를 순서대로 배치함
    5. GridLayout : LinearLayout의 순서로 뷰가 추가되며 TableLayout형태 또한 가지고 있음. TableLayout의 병합 이슈를 해결 가능
