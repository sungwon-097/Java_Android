-   액티비티

### 인텐트

: 다른 컴포넌트를 실행하는 매개체. Intent를 이용하여 액티비티, 리시버, 서비스를 실행한다.

-   Process
    1. [TransmitterApp] 인텐트 작성
    2. [TransmitterApp] startActivity()로 인텐트 정보를 ActivityManager로 보냄
    3. [Activitymanager] 인텐트 정보 추출하고 PackageManager로 보냄
    4. [PackageManager] 설치된 패키지인지 확인, 액티비티 정보를 제공
    5. [Activitymanager] 제공받은 정보로 액티비티를 실행하고 인텐트 전달
    6. [RecieverApp] 액티비티 실행하고 인텐트 수신

![img](/Ch07-1/img/intent.png)

-   System Service에서 액티비티의 요청을 처리해줌. IoC

### 직렬화

-   프로세스간의 데이터를 전달하기 위해 직렬화를 수행함.
-   다른 프로세스가 메모리를 침범하여 영향을 주는 것을 막기 위해 프로세스간 데이터 공유는 금지됨
-   인텐트가 전달되는 과정과 같이 커널의 도움을 받아 OS Level까지 데이터를 보내고 전달하려는 컴포넌트로 데이터를 넘겨준다.
-   OS 내의 IPC(Inter-Process Communication)인 커널 공유 메모리를 관리하는 바인더를 통해 데이터를 전달
-   클래스의 메소드가 아닌 멤버변수만 필요하기 때문에 이를 순차적으로 byte코드로 나열하게 해주고 역직렬화 과정에서 byte를 읽어 객체를 복원하는 방식

| 타입 이름    | 내용                 |                                                                                                                                 |
| ------------ | -------------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| Primitive    | (기본 타입)          | 8가지 기본 타입 boolran, char, int, shot, long, byte, float, double. 직렬화 과정이 필요 없음                                    |
| Serializable | (클래스)             | Serializable을 상속받으면 객체 전달 시 멤버변수를 직렬화/역직렬화 가능해짐                                                      |
| Parcel       | (IPC전용 데이터)     | Binder의 Type이 Parcel이고 데이터 전달에 최적화되어 속도가 가장 빠름, 순서대로 읽을 수 있도록 직접 객체를 선언하여 변환해줘야함 |
| Parcelable   | (Parcle 가능한 구현) | Parcel 객체를 매번 정의해주는것이 번거로워 인터페이스를 상속받아 재정의함                                                       |
| Bundle       | (Map)                | Parcelable을 구현한 클래스로 Map과 같은 방식으로 데이터를 읽고 씀. KEY:VAL로 저장하고 KEY를 이용해 조회함                       |
| Intent       | (데이터 전달)        | 위의 데이터 덩어리들을 묶어 전달할 떄 사용                                                                                      |

### 1. Serializable

```java
// Data
public class SampleData implements Serializable{
    private static final long serialVersionUID = {CLASS_VERSION}

    // serialVersionUID는 컴파일러가 자동 추가하지만 직접 핸들링 하는 것을 권장함.
    // 양쪽 class의 버전을 확인해 직렬화를 수행 할 것인지 확인
    // 1. FALSE -> Serialize 하지 않고 throw Exception
    // 2. True -> Serialize하고 field의 갯수가 다르다면
        // 2-1. 보내는 쪽이 field가 더 많으면 드랍됨
        // 2-2. 받는 쪽이 더 많다면 비트 0으로 채워짐
}

// TransmitterApp
    Intent intent = new Intent();
    intent.putExtra("KEY", object);

// RecieverApp
    Intent intent = getIntent();
    SampleData sampleData = (SampleData) intent.getSerializableExtra("KEY")
```

-   Serializable은 프로세스간 통신을 위해서만 활용 되는 것이 아니며 파일, 소켓, 메모리 통신을 위해서도 사용된다.

### 2. Parcel

```java
// Parcel클래스에서 아래의 메소드를 제공
public class Parcel{
    ...
    public final void writeInt(int);
    public final void writeLong(long);
    public final void writeString(String);
    public final void writeSerializable(Serializable);
    ...
    public final int readInt();
    public final Long void readLong();
    public final String void readString();
    public final Serializable void readSerializable();
    ...

// in Activity
    Parcel parcel = Parcel.obtain();

    parcel.writeInt(10);
    parcel.writeString("text");

    int iParcel = parcel.readInt();
    String sparcel = parcel.readString();
    // 직렬화 된 객체로 저장하기 때문에 데이터를 추가 한 순서대로 읽어줘야 함
}
```

### 3. Parcelable

```java
// Parcelable을 사용하기 위해 아래 메소드를 재정의해 사용한다
public class SampleData implements Parcelable{ // 제정의한 메소드의 call은 적절한 시점에 프레임워크가 한다

    private int intData = 0;
    private String strData = "text";
    ...
    public SampleData(Parcel in){
        intData = in.readInt();
        strData = in.readString();
    }
    @Override
    public int descriptionContents(){
        return 0;
        // field에 FileDescripter Type이 존재하면 1
    }
    @Override
    public void writeToParcel(Parcel dest, int flags){ // Binder에 들어 갈 때 자동으로 호출
        dest.writeInt(intData);
        dest.writeString(strData);
    }
    public static final Creator <SampleData> CREATOR = new Creator<SampleData>(){
        @Override
        public SampleData createFormParcel(Parcel in){
            return new SampleData(in);
        }

        @Override
        public SampleData[] newArray(int size){
            return new SampleData[size];
        }
    }
}
// 보내는 부분은 Serializable을 사용 하는 방식과 동일, 받을 때는 getParcelableExtra() 메소드 사용
```

### 4. Bundle

```java
public class Bundle implement Parcelable{
    ...
    public void putInt(String key, int val);
    public void putLong(String key, long val);
    public void putString(String key, String val);
    public void putSerializable(String key, Serializable val);
    ...
    public int getInt(String key);
    public Long void getLong(String key);
    public String void getString(String key);
    public Serializable void getSerializable(String key);
    ...

// 보내는 부분은 Serializable을 사용 하는 방식과 동일, 받을 때는 getBundleExtra() 메소드 사용
}
```

### 5. Intent

![img](/Ch07-1/img/intent_internal.png)

-   명시적 인텐트라면(Explicit) 컴포넌트 정보를 사용
-   암시적 인텐트라면(Implicit) 액션, 카테고리, 데이터 위치와 타입을 사용

### 명시적 인텐트

```java
    // 방법 1
    Intent intent = new Intent();

    ComponentName componentName = new ComponentName(
        "com.example.anotherApp",
        "com.example.anotherApp.MainActivity"
    )

    intent.setComponent(componentName);

    intent.putExtra("KEY", "VALUE");

    startActivity(intent);

    // 방법 2
    startActivity(new Intent(this, MainActivity.class));
```

-   외부 패키지의 액티비티를 호출 할 경우, 패키지와 컴포넌트명을 알고 있어야함.
-   실행하려는 앱이 기기에 없을 수도 있고 있더라도 보안상 공개하지 않기 때문에 내부 컴포넌트를 실행 할 때에 주로 사용

### 암시적 인텐트

```java
    Intent intent = new Intent();

    // 메일(MainActivity를 보여줌)
    intent.setAction(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_APP_EMAIL);
    // URI
    intent.setAction(Intent.ACTION_VIEW);
    intent.setData(Uri.parse("https://saint.ssu.ac.kr/irj/portal"));
    // 전화걸기 화면
    intent.setAction(Intent.ACTION_DIAL);
    intent.setData(Uri.parse("tel:01012345678"));
    // 기기에 따라 달라 질 수 있음, URI 값의 유형에 따라 다른 기능을 수행
    intent.setAction(Intent.ACTION_VIEW);
    intent.setData(Uri.parse("tel:01012345678"));
    // ID가 2인 정보의 편집 화면
    intent.setAction(Intent.ACTION_EDIT);
    intent.setData(Uri.parse("content://contacts/people/2"));
    // 전화번호부를 보여줌
    intent.setAction(Intent.ACTION_VIEW);
    intent.setData(Uri.parse("content://contacts/people"));
    // 오디오 파일 재생
    intent.setAction(Intent.ACTION_VIEW);
    intent.setDataAndType(Uri.parse("file:///{PATH}/sample.mp3"), "audio/*");
    // 이미지
    intent.setAction(Intent.ACTION_IMAGE_VIEW);
    intent.setDataAndType(Uri.parse("http://localhost:8080/file/1"), "image/png");

    startActivity(intent);
    // 결과값을 받아 올 수 있는 startActivityForResult() 도 존재
```

-   위와 같이 의도와 정보만으로 다른 컴포넌트를 실행할 수 있는 기능이 암시적 인텐트. 정확하 패키지명이나 액티비티 이름을 몰라도 인텐트를 전달 할 수 있다.

```xml
 <!-- 암시적 인텐트로 등록 하기 위해서는 AndroidMenifest.xml 의 intent-filter를 확인해야 한다 -->
    <action android:name="action.ACTION_IMAGE_VIEW"/>
    <category android:name="action.intent.category.DEFAULT"/>
```

-   intent.setAction()에 위의 내용을 넣어줘야 하고, 액티비티 일 때, category를 DEFAULT로 설정 하지 않으면 어떤 암시적 인텐트도 받을 수 없다.

```xml
    <intent-filter>
        <action android:name="action.ACTION_IMAGE_VIEW"/>
        <category android:name="action.intent.category.DEFAULT"/>
        <data android:scheme="http"
            android:host="www.superdroid.com"
            android:port="80"
            android:path="/fules/images/test.png"
            android:mimeType="image/png"/>
    <intent-filter>
```

-   intent.setDataAndType()의 인자는 데이터 위치 인텐트 필터의 정보와 단 하나라도 일치하지 않으면 실행되지 않는다.
-   android:path를 대체 할 수 있는 유연한 속성들이 있다
    -   pathPrefix : 경로의 앞부분을 고정하고 뒤에 오는 문자열 모두 허용
    -   pathPattern : \* 문자열이 들어간 구간에 가변을 허용
-   mimeType이란 이메일 첨부파일의 데이터 타입을 구분하기 위한 용도로 생겨났다
    -   text/html
    -   video/mpeg
    -   image/jpeg
    -   audio/x-wav
    -   위와 같이 type/subtype 형식으로 구분하며 대부분의 데이터 타입을 표시 할 때 이러한 방식을 사용한다

### 인텐트 발신과 필터 설정

-   MainActivity에서 SubActivity로 인텐트를 보낸다 할 때 아래와 같은 조건이 필요함
-   MainActivity의 인텐트 발신 측
    -   intent.setAction()의 인자가 하나라도 일치해야함
    -   intent.addCategory()의 인자가 모두 존재해야함
    -   URI.parse()의 URI와 타입이 모두 존재해야함
-   SubActivity의 인텐트 필터 설정 측
    -   action android:name="action.ACTION_IMAGE_VIEW"이 일치해야 하며, 발신측의 action은 없이 0:m으로 대응 될 경우 성공하지만 name 속성이 존재하지 않을 경우 실패함
    -   category android:name="action.intent.category.DEFAULT"가 존재해야함
    -   데이터 위치와 타입이 매칭돼야함

### 인텐트 플래그

-   인텐트에는 각종 컴포넌트를 제어하기 위한 플래그가 존재
-   intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) 이러한 방식으로 설정 해줌
-   위의 설정은 실행할 액티비티에서 애니메이션 효과를 사용하지 않겠다는 의미
