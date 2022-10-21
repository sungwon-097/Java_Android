-   자바로 레이아웃 구성하기

### 1. xml뷰를 Java 객체로 정의

```xml
// activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_margin="20dp">

    <LinearLayout
        android:orientation="horizontal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:text="@string/input"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1" />
        <EditText
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            />
    </LinearLayout>
    <Button
        android:text="@string/save"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
</LinearLayout>
```

-   : { LinearLayout { LinearLayout { TextView, EditText }, Button } } 형태로 Activity를 구현

```java
// MainActivity.java

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout rootLinear = new LinearLayout(this);
        rootLinear.setOrientation(LinearLayout.VERTICAL);

        ContentFrameLayout.LayoutParams rootLP = new ContentFrameLayout.LayoutParams(
                ContentFrameLayout.LayoutParams.MATCH_PARENT,
                ContentFrameLayout.LayoutParams.MATCH_PARENT
        );
        rootLP.setMargins(20, 20, 20, 20);

        LinearLayout nameInputLinear = new LinearLayout(this);
        nameInputLinear.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams nameInputLP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        TextView nameTv = new TextView(this);
        nameTv.setText(R.string.input);
        LinearLayout.LayoutParams nameTextLP = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        nameTextLP.weight = 1;

        EditText nameEt = new EditText(this);
        LinearLayout.LayoutParams nameEditLP = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        nameEditLP.weight = 1;

        Button saveBt = new Button(this);
        saveBt.setText(R.string.save);
        LinearLayout.LayoutParams saveButtonLP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        nameInputLinear.addView(nameTv, nameTextLP);
        nameInputLinear.addView(nameEt, nameEditLP);
        rootLinear.addView(nameInputLinear, nameInputLP);
        rootLinear.addView(saveBt, saveButtonLP);
    }
}
```

-   위의 xml뷰와 같은 화면이 됨

### 2. 같이 사용하는 방법

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/input"
        android:id="@+id/button1"
        />
</LinearLayout>
```

1. 위와 같이 Button을 정의해 button1이라는 이름을 부여하고

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button)findViewById(R.id.button1);
        button1.setText(R.string.input)
    }
```

2. button1 객체를 생성
    - android:text="@string/input" {.xml}
    - button1.setText(R.string.input) (.class)
    - 위의 두 코드는 동일한 동작
