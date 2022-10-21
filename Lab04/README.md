### 1. 리소스 폴더명 조건 명시

-   대시로 구분하여 하나의 폴더에 여러 조건을 명시 할 수 있음

    -   예) drawable-en-rUS-land
    -   여러 조건을 모두 만족할 때 해당 폴더의 리소스가 적용 (즉, And 연산)

-   조건이 나열되는 순서가 있음

    -   예) drawable-hdpi-port (O), drawable-port-hdpi (X)

-   폴더에 서브 폴더로 조건을 세분화하는 건 불가능

    -   예) res/drawable/drawable-en (X)

-   조건에 대한 대소문자는 구분하지 않음

    -   대문자는 가독성을 위한 것일 뿐

-   하나의 폴더에는 각 조건마다 하나의 값만 명시 할 수 있음
    -   예) drawable-rES-rFR (X) → drawable-rES, drawable-rFR
    -   즉, 2개의 폴더를 만들어야 함

![img](/Lab04/img/resource_folder.png)

### 2. 각 컴포넌트의 크기를 나타내는 논리적 단위

-   dp(dip) : 스크린 물리적 밀도에 기초한 단위로 160dpi에 상대적인 수치

-   sp : 폰트 크기에 적용하며 글자 대각선 크기를 지정. dp와 함께, 사용이 권장되는 단위

-   pt : 화면크기의 1/72

-   px, mm, in 등등

-   px = dp \* (dpi / 160)

![img](/Lab04/img/density.png)

```xml
//res/values/dimens.xml

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name="textview_height">25dp</dimen>
    <dimen name="textview_width">150dp</dimen>
    <dimen name="ball_radius">30dp</dimen>
    <dimen name="font_size">16sp</dimen>
</resources>
```
