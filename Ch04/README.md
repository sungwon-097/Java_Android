-   대표적인 뷰

### 1. 최상위 뷰

```xml
    <Linearlayout
        android:layout_width="match_parent"         // 부모 속성에 맞게
        android:layout_height="wrap_content"        // 내용에 fit 하게
        android:id="@+id/viewName"                  // 필수 속성은 아니나 외부에서 참조 할 경우 사용
        android:background="@mipmap/ic_launcher"    // 배경 이미지
        android:layout_marginLeft="50dp"            // 부모 뷰그룹과의 margin
        android:paddingLeft="50dp"                  // 뷰 내부에서의 padding
        android:visibility="visible"/>              // visible: 보이게, invisible: 안보이게, gone: 배치에서 제외

```

### 2. TextView

```xml
    <TextView
        android:layout_width="match_parent"         // 부모 속성에 맞게
        android:layout_height="wrap_content"        // 내용에 fit 하게
        android:id="@+id/viewName"                  // 필수 속성은 아니나 외부에서 참조 할 경우 사용
        android:background="@mipmap/ic_launcher"    // 배경 이미지
        android:layout_marginLeft="50dp"            // 부모 뷰그룹과의 margin
        android:paddingLeft="50dp"                  // 뷰 내부에서의 padding
        android:visibility="visible"                // visible: 보이게, invisible: 안보이게, gone: 배치에서 제외
        android:text="@string/app_name"             // 텍스트 표시
        android:textColor="FFFF"                    // 텍스트 색깔. RGB, RRGGBB, ARGB, AARRGGBB의 포맷 사용
        android:textSize="10sp"                     // 글씨 대각선의 크기
        android:textStyle="italic|bold"             // 기울임, 두껍게
        android:typeface="sans" />                  // 폰트 종류
    <TextView
        android:text="https://developer.android.com/reference/android/Manifest?hl=ko"
        android:singleLine="true"                   // String을 한 줄로
        android:ellipsize="marquee"                 // start, middle, end 가능하며 marquee일 때는 선택 됐을 때 글이 흐름
        android:marqueeRepeatLimit="marquee_forever"// 횟수 지정. 기본값은 3
        android:textSize="50sp"/>

    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="https://developer.android.com/reference/android/Manifest?hl=ko"
        android:maxLines="3"                        // 문자열의 줄 수를 지정. singleLine처럼 말줄임표는 없음
        android:minLines="2"
        android:lines="3"
        android:ellipsize="end"                     // end만 설정 가능
        android:lineSpacingExtra="30dp"             // 줄간격 설정
        android:lineSpacingMultiplier="3"           // 몇줄씩 띄울지 설정
        android:textSize="50sp"/>
```

### 3. EditText

```xml
    <EditText
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:editable="true"                     // 입력과 수정 가능
        android:enabled="true"                      // editable과 기능은 같지만 회색으로 나타남
        android:digits="1234567890*#"               // 입력문자 제한
        android:hint="input String"                 // 나타나는 힌트
        android:textColorHint="FFF"                 // 힌트의 색
        android:selectAllOnFocus="true"             // 누르면 전체선택
        android:textColorHighlight="#F00"           // 글자 배경색

        android:inputType={     // 키보드의 입력 모양
            android:inputType="none"                // 기본
            android:inputType="text"                // Enter자리에 다음 EditText로 이동하는 Next가 추가됨
            android:inputType="phone"               // 전화번호 입력. 줄바꿈 불가능
            android:inputType="textNoSuggestions"   // none + 추천어 표시 x
            android:inputType="number"              // 자연수만 입력 가능, 줄바꿈 불가능
            android:inputType="numberSigned"        // 음수입력 가능
            android:inputType="numberDecimal"       // 소수 입력 가능, numberSigned|numberDecimal으로 모두 표현가능
            android:inputType="time"                // hh:mm:ss,  추천어 표시 x
            android:inputType="date"                // yyyy-mm-dd, 추천어 표시 x
            android:inputType="datetime"            // time + " " +date
            android:inputType="textCapCharacters"   // 모두 대문자
            android:inputType="textCapWords"        // 단어 앞 대문자
            android:inputType="textCapSentences"    // 문장 앞 대문자
            android:inputType="textPassword"        // 글자가 ∙∙∙로 표시
            android:inputType="numberPassword"      // 숫자만 입력 가능하며 ∙∙∙표시. API 11부터 지원
            android:inputType="textEmailAddress"    // 이메일을 입력 하기 위한 {@ , .} 추가됨
            android:inputType="textShortMessage"    // 이모티콘 모음 키 추가
        }/>
```

### 4. Button

```xml
<Button
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:onClick="onButtonClick"/>
```

1. 버튼을 만든 후 동작의 이름을 정해주면

```java
public class MainActivity extends AppCompatActivity {
...
    public void onButtonClick(View v){
        // handling
    }
}
```

2. java코드로 동작을 정의 할수 있다

### 5. Image View

-   이미지는 원본 비율을 깨지 않는 것을 기본으로 한다
-   원본이미지가 뷰 영역보다 크면 종횡비를 유지하며 축소함
    -   뷰 영역과의 종횡비 차이로 인해 이미지 주변 빈 공간이 생길 수 있음

```xml
    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@mipmap/ic_launcher"           // 이미지를 가져옴
        android:adjustViewBounds="true"             // 원본이미지를 축소해 종횡비 차이로 인한 padding공간을 제거
        android:maxWidth="100dp"                    // adjustViewBounds="true" 와 함께 사용
        android:maxHeight="100dp"                   // adjustViewBounds="true" 와 함께 사용
        android:tint="#AA0000FF"                    // 이미지에 색조를 입힘
        android:baselineAlignBottom="true"          // 하단에 정렬 기준선을 만든다. API 11부터 지원
        android:baseline="50dp"                     // baseline의 위치. API 11부터 지원

        android:scaleType={     // 원본이미지의 크기를 조정하는 방법
            android:scaleType="matrix"              // 좌측 상단부터 표시, 이미지의 비율과 크기는 유지
            android:scaleType="center"              // 중앙에 표시, 이미지의 비율과 크기는 유지
            android:scaleType="centerInside"        // 이미지 영역 내에 중앙정렬, 비율만 유지
            android:scaleType="centerCrop"          // 이미지 영역 내에 여백을 남기지 않고 중앙정렬, 비율만 유지
            android:scaleType="fitXY"               // 이미지 영역 내에 여백없이 맞춤, 비율과 크기 모두 변경 가능
            android:scaleType="fitStart"            // 이미지 영역 내에 좌측상단 정렬, 비율만 유지
            android:scaleType="fitCenter"           // 이미지 영역 내에 중앙 정렬, 비율만 유지
            android:scaleType="fitEnd"              // 이미지 영역 내에 우측하단 정렬, 비율만 유지
        }
        android:cropToPadding="true"/>
```
