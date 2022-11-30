-   생명주기

### 액티비티 생명주기 함수

| onCreate() | onStart()   | onResume()     | onPause()  | onStop()     | onDestroy() |
| ---------- | ----------- | -------------- | ---------- | ------------ | ----------- |
|            | onRestart() | -------------- | ---------- | ------------ |             |

-   위의 함수들은 Framework에 의해 호출되며, 호출되는 메소드를 재정의해 동작을 정의 할 수 있다.
-   앱은 프로세스이기 때문에 종료 후 재활용 될 수도 있다. 따라서 onStop() 후 onRestart() 상태로 변경 가능하다.
-   함수의 종류
    -   onCreate() : 액티비티의 처음 시작, 화면에 보이는 뷰들의 일반적인 상태를 설정하는 부분
    -   onStart()
        -   맥티비티가 화면에 보이기 바로 전에 호출
    -   onResume()
        -   화면에 레이아웃이 나타나며 동작을 시작 한 상태
    -   onPause()
        -   액티비티 일시 정지 상태, 저장되지 않은 작업을 저장하거나 실행중인 작업을 중지시킴
        -   메소드를 재정의 할 땐, 시간이 오래 걸리는 작업을 추가하면 안됨
    -   onStop()
        -   작업이 중지 된 상태, 작업을 재개하면 onRestart()실행
        -   화면이 보이지 않고 이벤트도 발생하지 않는다
        -   잠금버튼, 홈버튼을 눌렀을 때 이 상태에 들어 갈 수 있으며, notification-bar로 가려지는 경우는 에외이다.
    -   onDestroy()
        -   액티비티가 소멸되어 없어지기 전에 호출, 다시 실행하면 onCreate()부터 실행
        -   isFinishing() 메소드의 반환값이 true이면 정상 종료( finish( ) ), false이면 강제종료

### 액티비티의 강제종료와 재실행 상태

-   backButton을 누른 것 이외의 상황에도, 기기의 환경이 바뀌는 일이 발생하거나 시스템에 의해 강제로 종료되는 경우가 존재함
-   환경이 바뀌는 대표적인 예는 가로/세로 화면 변경. 레이아웃을 다시 그려야 하기 때문에 onDestroy()까지 동작을 마치고 onCreate()로 다시 실행된다.
    -   모든 생명주기 함수를 다시 호출하는 것이 비효율적이라 생각한다면 onConfigurationChanged() 메소드를 사용해보자
    -   android:configChanges="orientation|screenSize"를 AndroidManifest의 액티비티 설정에 추가해주면 화면전환시 동작을 직접 처리하게 됨

```java
@Override
public void onConfigurationChanged(Configuration newConfig){
    super.onConfigurationChanged(newConfig); // 환경 변화가 일어나면

    setContentView(R.layout.activity_main) // 레이아웃을 다시 설정한다
}
// onResumed() : onConfigurationChanged()
```

-   -   기기의 시스템 메모리가 부족해지면 백그라운드 상태의 프로세스를 강제로 종료해 자원을 확보한다.
        -   1. onStop()
        -   2. onPause()

-   액티비티가 종료 됐을 때, 상태를 백업하는 방법이 존재한다
    -   시스템 프로세스에 데이터를 저장하고 다시 실행 될 때 저장된 데이터를 반환한다
    -   onStop()상태 이후, onSavediInstanceState()가 호출되어 기기 메모리에 정보를 저장 할 수 있다
    -   액티비티가 다시 시작 될 때, onResumed() 이전에 onRestoreiInstanceState()가 호출되어 데이터를 불러온다
    -   메모리에 저장되는 것이기 때문에 안전 저장매체는 아니며, 중요한 정보라면 file이나 DB에 저장한다
    -   onRestart() 이후 onStart() 상태로 들어 올 수 있는 상황이 존재하으로 onResumed() 이전에 정보를 받는다
    -   바인더 버퍼 사이즈의 제한 때문에 크기는 100KB가 넘지 않도록 설정해야 한다

```java
@Override
protected void onSavediInstanceState(Bnudle outState){
    String backup = editText.getText.toString();
    outState.putString("BACKUP", backup); // Bundle에 값을 저장

    super.onSavediInstanceState(outState);
}

@Override
protected void onRestoreiInstanceState(Bundle savedInstanceState){
    if(savedInstanceState != null)
        editText.setText(savedInstanceState.getString("BACKUP")); // 원하는 영역에 복원
    super.onRestoreiInstanceState(savedInstanceState);
}


```

-   -   editText.setSavedEnabled(false) 을 사용하면 자동저장을 해제함
    -   TextView와 그것의 하위 뷰와 레이아웃 XML에 뷰의 ID를 추가 한 데이터는 자동복원 기능을 가짐

![img](/Ch07-2/img/lifecycle_of_activity.png)
