-   Android 시작하기

## 1. 시작하기

![img](/Ch01/img/platform_architecture.png)

-   안드로이드가 동작 하는 아래의 모든 환경을 플랫폼 아키텍처라고 함
-   HAL과 Linux Kernel은 Android OS의 영역이며 C/C++로 만들어짐
-   Android Runtime은 JVM과 JAVA API로 이루어져 있고 JRE의 구조와 유사함
-   시스템 앱은 Java API Framework에서 제어함. Inversion of Control(IoC)

![img](/Ch01/img/build_process.jpeg)

1. 사용자가 만든 모듈과 Dependency를 참조해 컴파일함
2. 컴파일시에 소스코드는 .DEX로, Dependency는 컴파일된 리소스로 변환(binary)
3. 서명절차와 함께 APK Packager를 통해 .apk파일로 만들어냄

-   이 과정은 Gradle에서 관리해줌

## 2. 컴포넌트 기반 개발

1. 액티비티
    - UI 화면을 가지는 하나의 작업
    - extends AppCompatActivity
    - Activity에 API level 호환성을 추가
    - 액티비티가 모여서 앱이 된다
2. 서비스
    - 백그라운드에서 실행, 오랫동안 실행되는 작업이거나 원격 프로세스를 위한 작업
    - extends Service
    - 서비스의 도움 없이 액티비티끼리 인자를 넘겨줄 수 있지만 안정성이 떨어짐
3. 방송 수신자
    - 방송을 받고 반응하는 컴포넌트, 앱 외부 환경상의 변화를 감지
    - extends BroadcastReciever
    - 시스템 부팅이나 외부 장치 입력등의 Broadcast를 감지함
4. 콘텐트 제공자
    - 데이터를 관리하거나 다른 앱에게 데이터를 제공
    - extends ContentProvider
    - 외부에 데이터를 공유 할 때 사용되며, 앱이 데이터를 외부에 공개하지 않는다면 사용 할 필요 없음

-   안드로이드 앱은 Single Entry Point를 가지지 않고 각각의 컴포넌트별로 독립적으로 실행 가능
-   카메라와 같은 시스템 앱을 사용하고 싶을 경우 BroadcastReciever로 호출해 필요한 기능만 사용
-   응집도는 높고 결합도가 낮은 유지보수에 용이한 코드를 만들 수 있는 방법이다

### 3. Intent

-   앱의 의도를 적어 OS에 전달하면 적절한 컴포넌트를 찾아 활성화하고 실행함
-   외부앱에서 ContentProvider를 사용하기 위해서는 ContentResolver객체를 사용해야 한다
-   {Action : "", Data : ""} 의 형태로 appName이 아닌 의도를 전달하면 그에 맞는 컴포넌트를 사용 가능

### 4. AndroidManifest.xml

-   xml을 사용하며 appName, app의 기본 속성과 같은 메타데이터가 포함됨

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.example.user.app"> // appname

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.App"
        tools:targetApi="31">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
// <activiy> <service> <reciever> <provider>로 구성됨
```

### 5. 리소스 외부화

-   {project} / res / 하위에 app에 사용할 자원을 저장하고 R.{res}.{resName}을 통해 호출, xml에서는 @{res}/{resName}으로 호출
