-   액티비티간 데이터 주고받기

### startActivityForResult()

: 앞에서는 인텐트 전달 할 때 다른 액티비티에 데이터를 함께 담아 보내는 상황만 다루었음.  
인텐트 객체를 보낸 액티비티에서 데이터를 다시 결과값을 반환 받을 떄 사용한다

```java
// aActivity
public class aActivity extends AppCompatActivity{
    Intent intent = new Intent();
    ...
    intent.setClass(this, bActivity.class);
    intent.putExtra("PIC_URI", mSendpicUrl);

    startActivityForResult(intent, REQUEST_CODE);
}


// bActivity
public class bActivity extends AppCompatActivity implements View.OnClickListener{
    Intent intent = getIntent();
    Bundle bundle = intent.getExtras();
    String picUrl = bundle.getString("PIC_URI");
    String mResultPicFileUrl = "send : " + picUrl;
    ...

    public void onClick(View v){
        Intent intent = new Intent();
        intent.putExtra("RESULT_PIC_FILE_URL", mResultPicFileUrl);
        setResult(RESULT_OK, intent);
        finish();
    }
}

// aActivity
public class aActivity extends AppCompatActivity{

    ...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            String pirFileUrlStr = data.getStringExtra("RESULT_PIC_FILE_URL");
        }
        // use This
    }
}
```

-   Result Code

    -   public static final int RESULT_CANCELED = 0;
    -   public static final int RESULT_OK = -1;
    -   public static final int RESULT_FIRST_USER = 1;

-   Request Code

    -   여러 액티비티로 인텐트를 전달 할 때 onActivityResult()에서 어떤 액티비티의 결과를 받은 것인지 구분하기 위해 사용
    -   startActivityForResult(intent, REQUEST_CODE) 에서 코드를 생성하고 사용자가 핸들링 한다

-   Process

    1. startActivityForResult(intent, REQUEST_CODE) : 인텐트 전달
    2. Intent intent = getIntent() : 인텐트 받음
    3. setResult(RESULT_OK, intent) : 결과를 전달
    4. onActivityResult(requestCode, resultCode, data) : 전달받은 결과를 사용

-   FLAG_ACTIVITY_FORWARD_RESULT 속성
    -   두 액티비티간 데이터 교환이 아니라 다른 액티비티로부터 전달 받은 결과를 다른 액티비티에서 처리하도록 하고 싶을 때 사용
    -   위의 예제에서 bActivity가 처리해야 하는 결과를 CustomActivity에서 처리해 반환받도록 함
    -   ![img](/Ch07-4/img/flag.png)

```java
    public void onClick(View v){
        Intent intent = getIntent();
        intent.setClass(this, CustomActivity.class)
        intent.addFlags(intent.FLAG_ACTIVITY_FORWARD_RESULT);

        startActivity(intent);
        // finish();
    }
```

### 데이터를 주고 받을 때의 제약사항

1. 서로 다른 태스크에서 실행된 결과는 받을 수 없다
    - intent 전달은 가능하나 전달 후 보낸 쪽의 onActivityResult가 바로 호출되기 때문
2. 주고받는 데이터의 크기는 100KB 이내여야 한다
    - intent 자체는 1MB 이상의 크기를 가지고 있음
    - 해댱 버퍼는 여러 프로세스들이 공유해서 사용하기 때문에 1MB라는 크기를 보장 받을 수 없음
    - 따라서 안드로이드에서는 100KB 이내로 사용 할 것을 권장한다\
    - parcel 사이즈가 초과하면 TransactionToolargeException을 반환
3. onResumed() 이후의 생명주기 함수에서는 setResult()를 사용 할 수 없다
    - onPause(), onStop(), onDestroy()
    - 위의 함수에서 setResult()를 사용한다면 원하는 데이터를 받을 수 없음
    - onResumed() 상태에서 호출되는 finish()의 과정에서 ActivityManager에게 전달하기 때문
    - 따라서 onResumed() 이후의 생명주기에서는 setResult()를 사용하더라도 그 정보는 해당 액티비티만 알고 있는 정보로, 공유가 불가능함
