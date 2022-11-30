-   태스크

### Task

-   안드로이드는 앱의 경계가 없다
    -   사용자 관점 : 태스크
    -   개발자 관점 : 패키지
-   앱이 시작되고 끝날 때 까지 액티비티의 실행 순서를 담아두는 스택 공간을 태스크라고 함 : 시스템 서비스인 액티비티 매니저가 관리한다

-   taskAffinity는 액티비티의 속성, affinity는 태스크의 속성을 의미함. 기본값은 모두 패키지명으로 설정됨
-   taskAffinity는 AndroidManifest에서 변경이 가능하다 android:taskAffinity="com.A"

### Task 생성

-   앱을 실행하면 해당 액티비티의 패키지 명으로 태스크가 생성되고, 액티비티는 생성된 태스크에 배치된다
-   실행된 액티비티에서 다른 앱의 액티비티를 실행하더라도 호출된 액티비티에 포함된다
-   ❗️안드로이트 테스크 기본 정책이며, 이것이 사용자 측면에서 직관적임
-   액티비티가 태스크에 추가되는 동작 특성을 Intent flag로 제어 할 수 있다

```java
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    startActivity(intent);
```

| Flag                                | 내용                                                                                                                          |
| ----------------------------------- | ----------------------------------------------------------------------------------------------------------------------------- |
| FLAG_ACTIVITY_NEW_TASK              | 자신의 태스크 친화력 이름을 가진 태스크에서 실행됨. 기존 태스크가 존자한다면 추가, 없으면 새 태스크 생성                      |
| FLAG_ACTIVITY_MULTIPLE_TASK         | FLAG_ACTIVITY_NEW_TASK의 보조 플래그이며 단독으로 사용 불가, 실행되는 액티비티는 무조건 새로운 태스크를 생성한다              |
| FLAG_ACTIVITY_NO_HISTORY            | 액티비티 스택에 남기지 않는다. 한 번만 실행되고 복원되면 안되는 작업에 사용함                                                 |
| FLAG_ACTIVITY_CLEAR_TASK            | 기존 액티비티를 모두 종료해 태스크를 비우고 그 위에 저장된다                                                                  |
| FLAG_ACTIVITY_CLEAR_TOP             | 중복된 액티비티와 그 상위 액티비티를 모두 제거하고 생성. FLAG_ACTIVITY_SINGLE_TOP과 함께 사용하면 새로 생성하지 않고 재활용함 |
| FLAG_ACTIVITY_REORDER_TO_FRONT      | 액티비티가 태스크에 중복 될 경우 맨 앞으로 이동시키고 재활용                                                                  |
| FLAG_ACTIVITY_TASK_ON_HOME          | Home을 현재 액티비티 바로 이전의 태스크로 옮겨 back버튼을 누르면 바로 Home으로 갈 수 있도록 함                                |
| FLAG_ACTIVITY_RESET_TASK_IF_NEEDED  | 이미 만들어진 Task를 재실행 할 때, 아래의 함께 사용하는 속성에 따라 앱을 처음 실행 하는 것 처럼 태스크의 리셋 여부를 결정함   |
|                                     | clearTaskOnLaunch : root에 이 설정을 적용 할 경우, 홈에서 앱을 실행 할 때마다 Task를 비우고 다시 실행함                       |
|                                     | finishOnTaskLaunch : root가 아닌 액티비티에 적용을 하고 다시 실행 될 경우 이 속성을 가진 액티비티를 제거                      |
| FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | FLAG_ACTIVITY_RESET_TASK_IF_NEEDED와 함께 사용되며 다시 실행 될 때, 이 Flag가 적용된 액티비티부터 태스크 탑 까지 제거         |

-   같은 앱의 태스크가 분리 되는 것은 사용자 입장에서 하나의 앱이 여러 개의 앱으로 인식 될 수 있음. 특별한 경우가 아니라면 태스크명 변경은 하지 않음.

### 액티비티 실행 모드

```xml
    <activity android:launchmode=["standard" | "singleTop" | "singleTask" | "singleInstance"]>
    <!-- instance 공통 적용 속성, 인텐트를 전달 할 때 실행 모드를 설정하면 일회성, Mainfenst에 설정하면 항상 적용 -->
```

1. standard
    - 태스크 내에 중복된 액티비티를 허용한다
    - standard 모드는 실행되는 순서대로 스택에 쌓임
2. singleTop
    - 태스크의 톱 액티비티와 중복된 액티비티는 허용하지 않는다
    - 액티비티 스스로 테스크 탑의 중복 거부를 결정
    - 생성을 원치 않고 재사용한다
3. singleTask
    - 태스크 내에 중복된 액티비티를 허용하지 않는다
    - singleTask로 설정된 액티비티는 다른 태스크 친화력을 가진 태스크에서 절대 실행되지 않는다
    - 대표적인 사용 예로 HomeLauncher가 있는데, 홈키를 여러번 누른다고 액티비티가 여러번 중복 실행될 필요는 없음
4. singleInstance
    - 태스크 내에 중복된 액티비티를 허용하지 않을 뿐만 아니라, 다른 액티비티도 허용하지 않는다
    - 태스크 내에 singleInstance로 설정된 액티비티 하나만 유일하게 존재함
    - singleInstance로 설정된 액티비티는 다른 태스크 친화력을 가진 태스크에서는 절대 실행되지 않는다

-   ❗️액티비티를 재사용시 새로 실행된 액티비티를 허용하지 않고 이전 버전을 재사용하기 때문에 인텐트 내용이 반영되지 않음
    -   onCreate()에서 데이터를 받기 때문
    -   onNewIntent()를 사용해 재사용된 액티비티로 인텐트를 전달 받을 수 있다
    -   onPause() -> onNewIntent() -> onResume()

```java
@Override
protected void onNewIntent(Intent intent){}
    super.onNewIntent(intent);

    String caller = intent.getStingExtra("KEY");
    textView.setText(caller);
    // 재사용되면 새로운 액티비티가 받아야 했을 인텐트를 가져옴
```

-   액티비티의 중복을 허용하지 않는 이유
    -   로그인 창을 여러 번 호출 한 후 Top Activity에서 로그인을 진행 -> 뒤로가기 후 다시 로그인 -> ... 이러한 과정을 피하기 위함
    -   중복되는 태스크가 필요하지 않은 작업일 경우(또는 존재하면 안 될 경우) 사용한다
