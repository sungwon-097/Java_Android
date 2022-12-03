-   프로세스와 스레드

### 프로세스와 스레드

: 안드로이드는 기본적으로 멀티 프로세스와 멀티 스레드를 지원한다

-   코드가 프로세스에 올라와 실행
-   실행중에 생성된 변수, 객체도 프로세스에 존재
-   프로세스는 실행의 흐름인 스레드로 구성된다
-   프로세스라는 자원을 스레드라는 작업자가 처리한다

```java
    Thread thread = new Thread(){
        public void run(){
            // 처리해야 할 일을 기술
        }
    };
    thread.start();
```

-   각 스레드가 정해진 time slice를 나눠 CPU를 점유하는데 공유 자원을 사용하는 만큼 동시성 제어를 해야 한다. (synchronized 등의 구문을 사용)
-   Main thread와 Worker thread가 존재하는데 각종 생명주기 함수를 처리하고 화면에 뷰를 띄우는 역할을 하는 것은 Main thread이다
-   4대 컴포넌트와 생명주기 함수는 모두 메인 스레드를 가지고 있다

### ANR

-   Application Not Responding

    -   액티비티가 5초 이상 입력에 반응하지 않을 때
    -   리시버가 10초 혹은 60초 이내로 리턴하지 않을 때
    -   서비스가 20초 이상 메인 스레드를 잡고 있을 때

-   메인 스레드는 UI스레드라고도 부르는데 앱이 일정 시간동안 요청에 반응하지 않을 때 이 대화상자를 띄운다
-   Strict Mode
    -   안드로이드에서는 아래의 세 가지 사항을 메인 스레드 사용 위반으로 본다
    1. 디스크에서 파일 쓰기
    2. 디스크에서 파일 읽기
    3. 네트워크 사용
    -   앱이 끊김 없이 원활하게 동작하도록 정의 내린 것이며 메인 스레드를 오래 점유 할 수 있는 일들이다
    -   개발 모드에서 on/off 제어가 가능하고 exception 또는 warning을 반환 할 지 선택 가능하다
    -   긴 작업은 모두 Worker thread에서 처리하도록 하면 Main thread는 기다릴 필요 없이 작업을 할 수 있다

### GUI 모델

-   안드로이드에서는 메인 스레드가 아닌 스레드에서 화면에 그리는 작업을 혀용하지 않는다
-   여러 스레드에서 뷰를 변경 할 때 동시성 문제가 생기기 때문, 안드로이드에서는 뷰를 그리는 순서를 중요하게 여긴다
-   메인 스레드에서만 그리는 작업을 허용하여 그리는 순서를 보장하며 이를 GUI 모델이라고 한다(GUI 단일 스레드 처리)
-   그렇지 않다면 CalledFromWrongThreadException을 반환한다

```java
    Thread thread = new Thread(){
        public void run(){
            // Worker Thread 에서 처리해야 할 일을 기술
            view.setText("이 구문은 예외를 반환한다. 그리는 작업을 Main thread로 작업을 이관해야 한다");
            mHandler.post(new Runnable(
                @Override
                public void run(){
                    view.setText("Handler 객체를 통해 그리는 작업을 Main thread로 작업을 이관 할 수 있다");
                    // Main Thread 에서 처리해야 할 일을 기술
                }
            ))
        }
    };
    thread.start();
```

### Looper객체

-   메인 스레드의 콜 스택을 보면 메시지 큐와 루퍼 객체를 계속 사용하고 있다
-   Process(Main Thread(Looper.loop(){},,,),,,) GUI App은 이러한 구조로 되어 있다
-   looper에는 message queue라는 처리할 일을 넣어두는 큐가 존재하고 루퍼가 하나씩 꺼내서 처리한다
-   스레드에서 핸들러를 생성하면 내부적으로 메인 스레드의 static 루퍼 객체를 참조한다

```java
// Looper.java
    public class Looper{
        ...
        private static Looper sMainLooper;
        final MessageQuese mQueue;
        ...
        public static Looper getMainLooper(){
            synchronized(Looper.class){
                return sMainLooper;
            }
        }
    }
```

-   편지를 보내는 과정에 비유해보면,
    -   Handler는 우체부, 또는 편지(Main Thread가 할 일)
    -   message는 편지 봉투
    -   messageQueue는 목적지하고 할 수 있다

```java
// Message에 할 일을 담는 두 가지 방법

    // 1. Runnable callback
    Runnable(){
        run(){
            ...
        }
    }

    //2. Handler target
    handler(){
        handleMessage(Message msg){
            ...
        }
    }
    // Message의 menber는 외우자❗️
    Message{
        int what;
        int arg1, arg2;
        Object obj;
        Bundle data;
    }
```

-   Looper는 messageQueue에서 메시지를 하나씩 꺼낸다
    -   Runnable객체가 존재하면 run()을 실행
    -   존재하지 않으면 Handler의 handleMessage를 호출한다

### Handler

-   Runnable
    -   간단한 실행 코드를 추가하는 목적으로 사용
-   handleMessage
    -   메시지의 what 멤버변수로 구분하며, 여러가지 목적의 실행을 한 곳에서 처리 할 때 유용함( "switch(what)" 으로 활용)

```java
// handleMessage
public class MainActivity extends AppCompatAvtivity{

    int mCount = 0;
    TextView mCountTextView = null;

    static final private int MESSAGE_DRAW_CURRENT_COUNT = 1; // what으로 핸들링 할 변수
    ...
    Handler handler = new Handler();

    public void handleMessage(Message msg){
        switch(msg.what){
            case MESSAGE_DRAW_CURRENT_COUNT:{
                // Handler 정의부
            }
        }
    }
};

// Runnable
    Thread countThread = new Thread("Count Thread"){
        public void run(){
            // 메시지 큐에 담을 메시지를 생성한다
            Message message = Message.obtain(mHandler);

            message.what = MESSAGE_DRAW_CURRENT_COUNT;
            message.arg1 = mCount;
            message.obj = mCountTextView;

            mHandler.sendMessage(message);
            // mHandler.sendMessageDelayed(message, 10000); 10후에 큐에 꽂는다
        }
    }
```

-   메시지 생성 함수

    -   Message.obtain() : 빈 메시지 객체를 얻어옴
    -   Message.obtain(Message orig) : 인자로 전달한 orig객체를 복사한 새로운 메시지 객체를 얻어옴
    -   Message.obtain(Handler h) : 인자로 전달한 핸들러 객체가 설정된 메시지 객체를 얻어옴
    -   Message.obtain(Handler h, Runnable callback) : 핸들러, Runnable객체가 설정된 메시지 객체를 가져옴

    -   Message.obtain(Handler h, int what, int arg1, int arg2, Object obj)

-   핸들러를 통한 메시지 생성

    -   mHandler.obtainMessage() : 빈 메시지 객체를 얻어옴
    -   mHandler.obtainMessage(int what) : 인자로 전달한 what 객체가 설정된 메시지 객체를 얻어옴
    -   mHandler.obtainMessage(int what, Object obj) : what, obj객체가 설정된 메시지 객체를 얻어온다
    -   mHandler.obtainMessage(int what, int arg1, int arg2)

    -   mHandler.obtainMessage(int what, int arg1, int arg2, Object obj)

-   메시지 큐에 메시지를 추가 및 삭제하는 핸들러 함수

    -   post로 시작하는 함수는 Runnable 객체를 이용하는 메시지는 별도로 메시지 객체를 생성하지 않아도 된다. 함수 내에서 객체를 자동으로 생성해 주기 때문이다.

        -   mHandler.post(Runnable r) : 큐에 메시지를 추가
        -   mHandler.postAtFrontOfQueue(Runnable r) : 우선처리할 큐를 추가
        -   mHandler.postAtTime(Runnable r, long uptimeMillis) : 부팅 시간을 기준으로 특정 시간에 실행 되도록 함

        -   mHandler.postDelayed(Runnable r, long delayMillis) : 지연 시간 후에 실행되도록 함

    -   send로 시작하는 함수는 handlerMessage 함수가 재정의된 메시지를 큐에 추가한다.

        -   mHandler.sendMessage(Message msg)
        -   mHandler.sendMessageAtFrontOfQueue(Message msg)
        -   mHandler.sendMessageAtTime(Message msg, long uptimeMillis)

        -   mHandler.sendMessageDelayed(Message msg, long delayMillis)

### 액티비티의 핸들러

-   뷰와 액티비티는 자체적으로 핸들러를 가지고 있다
-   별도의 핸들러를 생성하지 않고도 큐에 메시지를 추가 할 수 있다(Main Thread에 전달한다)
-   단, 핸들러의 handleMessage를 사용한 메시지 처리는 핸들러 객체를 생성 할 때 해당 메소드를 재정의 해야 하기 때문에 사용 할 수 없고, Runnable 객체만 가능하다
-   View의 메소드
    -   public boolean post(Runnable action)
    -   public boolean postDelayed(Runnable action, long delayMillis)
-   Activity의 메소드
    -   public final void runOnUiThread(Runnable action)
    -   같이 특정 뷰를 갱신하는 경우에는 대체로 뷰 자체의 핸들러를 사용한다

```java
// handleMessage
    public void run(){
        mCountTextView = getHandler() // 이 메소드를 사용
        ...
        mCountTtextView.post(new Runnable(){
            public void run(){
                ...
                mCountTextView.setText("뷰 자체의 핸들러를 사용한다 : " + mCount)
            }
        })
    }
```

-   뷰의 핸들러는 onCreate(), onStart(), onResume() 메소드가 모두 호출 된 이후에 참조가 가능하다. 이전에는 null이었다가 onResume() 상태에서 set

```java
// handleMessage
    public void run(){
        ...
        MainActivity.this.runOnUiThread(new Runnable(){
            public void run(){
                ...
                mCountTextView.setText("특정 뷰가 아닌 액티비티에 사용되는 여러 뷰를 갱신하고자 할 때 이러한 방식을 사용함" + mCount)
            }
        })

    }
```

![img](/Ch08/img/structure.png)
