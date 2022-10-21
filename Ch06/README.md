-   이벤트

### 1. 터치 이벤트 수신 함수

```java
public boolean dispatchTouchEvent(MotionEvent ev){
    return super.dispatchTouchEvent(ev);
}

public boolean onTouchEvent(MotionEvent event){
    return super.onTouchEvent(event);
}
```

-   dispatchTouchEvent, onTouchEvent 두 가지가 있다
-   아래 메소드를 통해 터치 이벤트에 대한 로그를 확인 할 수 있음

| Method       | Behavior                                                                           |
| ------------ | ---------------------------------------------------------------------------------- |
| getAction    | {ACTION_DOWN, ACTION_UP, ACTION_MOVE, ACTION_CANCEL}을 각각 {0, 1, 2, 3} 으로 반환 |
| getX         | 이벤트가 발생한 X축 좌표                                                           |
| getY         | 이벤트가 발생한 Y축 좌표                                                           |
| getEventTime | 이벤트가 발생한 시간을 millisecond로 표시                                          |
| getDownTime  | ACTION_DOWN이 발생한 시간을 millisecond로 표시                                     |

### 2. 터치 이벤트 전달 과정

-   <strong>Display -> Activity -> View -> Button</strong>
-   위의 순서로 dispatchTouchEvent를 수행하고 역순으로 onTouchEvent를 수행한다
-   dispatchTouchEvent와 onTouchEvent의 반환값은 기본값이 false이며, true이면 이벤트를 처리했다고 봄

```java
// onInterceptTouchEvent()는 parent가 이벤트를 가로챈다. 이 경우 child의 getAction 반환값은 ACTION_CANCEL가 된다
public class CustomViewGroup extends FrameLayout {
    public CustomViewGroup(@NonNull Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}

// 위와 같이 parent에 requestDisallowInterceptTouchEvent() 메소드를 적용하면 이벤트를 가로채지 않도록 요구 할 수 있다
public class CustomView extends View {

    private boolean isFirstDown = true;

    public CustomView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isFirstDown == true && event.getAction() == motionEvent.ACTION_DOWN){
            getParent().requestDisallowInterceptTouchEvent(true);
            isFirstDown = false; // 한 번만 실행되도록 함
        }
        return super.onTouchEvent(event);
    }
}
```

### 3. 터치 이벤트 리스너

-   뷰 영역을 클릭 했을 때 호출됨.

```java
// 1. Nested Class
public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button1);
        button.setOnTouchListener(new MyTouchListener());
    }
    public class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }
}

// 2. View.OnTouchListener 직접 구현
public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button1);

        button.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}

// 3. Annoymous Class
public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button1);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }
}
```

### 4. 클릭 리스터

-   ACTION_DOWN, ACTION_UP이 조합된 터치 동작이 일어났을 때

```java
// 1. View.OnClickListener의 onClick을 재정의해 행위를 기술
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // handling
    }
}
```

```xml
// 2. xml에서 onClick 메소드의 이름을 등록
    <Button
        ...
        android:onClick="onMyButtonClick"
    />
```

```java
// onMyButtonClick() 메소드를 구현함
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public void onMyButtonClick(View v){
        // // handling
    }
}
```

### 5. 롱클릭 리스너

-   View를 500ms 이상 누르고 있을 때 호출됨

```java
// 1. View.OnClickListener의 onClick을 재정의해 행위를 기술
public class MainActivity extends AppCompatActivity implements View.OnLongClickListaner{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnLongClickListaner(this);
    }

    @Override
    public boolean onLongClick(View view) {
        // handling
    }
}
```
