-   서비스

### 서비스

-   4대 컴포넌트 중 하나로 백그라운드에서 동작 할 작업을 정의 해준다
-   메인 스레드가 작업을 점유하게 되면 다른 작업을 하지 못할 뿐더러 ANR이 발생한다.(20초) 따라서 작업스레드에서 동작하게 한다
-   액티비티는 종료되더라도 객체가 바로 사라지지는 않는다. 하지만 생명주기가 소멸되면 스레드를 제어 할 방법이 없다
-   따라서 서비스는 다른 컴포넌트에 의존하지 않고 백그라운드에서 독립적으로 수행한다
-   다른 프로세스의 메모리에 접근하는 IPC, RPC를 지원한다

### 서비스의 구성

1. Started

    - 특정 서비스를 동작시켜두는 데에 목적을 둔 서비스
    - 동작시킨 컴포넌트는 서비스를 중단 하는 것 이외에 다른 제어는 할 수 없다

2. Bound
    - 외부 라이브러리를 사용 하는 것과 유사하게 서비스의 메소드를 이용한다

```java
// 아래의 순서로 동작한다
public class CustomService extends Service{

    // 서비스 시작
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스 동작 중
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    // 서비스 끝

// AndroidManifest.xml
    <service android:name=".service.BackgroundService"/>
}
```

-   액티비티에서 startService(), stopService()로 실행시키고 종료 할 수 있다

### 생존 우선순위

-   안드로이드에서 가장 중요한 것은 시스템
-   메모리가 부족 할 때 다른 프로세스를 kill하는 것을 LMS(Less Memory Killer)라고 한다

| 시스템            | 시스템 앱   | 가장 앞에 보이는 앱 | 뒤에 보이는 앱 | 지각되는 서비스 앱  | 서비스 앱 | 홈 앱       | 뒤에 가려진 앱 | 죽은 앱         |
| ----------------- | ----------- | ------------------- | -------------- | ------------------- | --------- | ----------- | -------------- | --------------- |
| 안드로이드 시스템 | 네이티브 앱 | onResume()          | onPause()      | 여기까지 Foreground | Service   | 가려진 home | onStop()       | onDestroy()상태 |

-   여러 상태를 가지고 있으면 생존 순위가 높은 것을 기준으로 함
-   예를 들어 액티비티가 보이면서 서비스도 동작중이면 보이는 상태로 간주
-   Started()가 실행 후(서비스 시작) 30분 이내의 앱은 30분이 지난 서비스보다 안전하다

### 서비스의 생존 방법

1. onStartCommand()

```java
    Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY; // 종료 후 다시 생존하는 방법
    }
```

-   LMK에 의해 강제 종료 된 이후 다시 서비스를 실행 할 지를 결정해준다

| START_STICKY = 1             | START_NOT_STICKY = 2 | START_REDELIVER_INTENT = 3                                            |
| ---------------------------- | -------------------- | --------------------------------------------------------------------- |
| 가용메모리 확보 후 다시 시작 | 다시 시작하지 않음   | Intent정보를 복원해 다시 시작, 재전달 할 경우 flag에 이 값이 넘어온다 |

2. startForeground()

-   현재 서비스를 생존순위가 한 단계 더 높은 지각되는 서비스(perceptible)로 변경한다

```java
    startForegroundService(intent);
    startForegroundService(SERVICE_ID, intent);

// AndroidManifest.xml
    android:name="android.permission.FOREGROUND_SERVICE"
```
