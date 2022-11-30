-   대표적인 뷰그룹

### 1. LinearLayout

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"

    android:orientation="vertical" // 뷰를 쌓을 기준 축
    android:gravity="center" // 어디부터 뷰를 쌓을 것인지. "vertical | horizontal" 로 혼합해 사용 가능
    android:baselineAligned="true" // 높이가 긴 뷰를 기준, 텍스트 하단으로 정렬
    android:baselineAlignedChildIndex="1" // LinearLayout이 뷰 그룹에 속해 있을 경우 가장 아래의 child에 나머지 뷰를 맞춤
    android:measureWithLargestChild="true" // 뷰를 가장 큰 높이에 맞춤
    android:weightSum="100"

    tools:context=".MainActivity">

    <TextView
        android:layout_height="0dp"
        android:layout_width="wrap_content"

        android:layout_gravity="center" // 텍스트를 배치 할 곳
        android:layout_weight="10" // 각각의 크기를 지정
        android:text="LinearLayout"/>
</LinearLayout>
```

### 2. RelativeLayout

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"

    android:gravity="center" // 어디부터 뷰를 쌓을 것인지
    android:ignoreGravity="" // 지정한 뷰의 gravity를 무시

    tools:context=".MainActivity">

    <TextView
        android:layout_height="0dp"
        android:layout_width="wrap_content"

        android:layout_alignParentLeft="true" // 부모를 기준으로 배치
        android:layout_alignParentRight="true"
        android:layout_alignParentBottom="true"
        android:layout_alignParentTop="true"
        android:layout_centerInParent="true"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true"

        android:layout_alignLeft="" // 기준 뷰의 기준선과 같이
        android:layout_alignRight=""
        android:layout_alignBottom=""
        android:layout_alignTop=""
        android:layout_above=""
        android:layout_below=""
        android:layout_toLeftOf="" // 기준 뷰 영역의 밖
        android:layout_toRightOf=""
        android:layout_alignBaseline="" // 기준뷰의 텍스트와 이 뷰의 텍스트를 맞춤

        android:visibility="invisible"
        android:layout_alignWithParentIfMissing="true" // 참조했던 뷰가 사라졌을 때, 그 속성을 따라감

        android:text="RelativeLayout"/>
</RelativeLayout>
```

### 3. FrameLayout

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"

    android:foreground="@mipmap/ic_launcher" // 아이콘을 모든 자식 뷰보다 앞에 위치시킴
    android:foregroundGravity="center" // 중앙
    android:measureAllChildren="true"  // Visibility에 관계없이 영역을 유지

    tools:context=".MainActivity">

    <TextView
        android:layout_height="0dp"
        android:layout_width="wrap_content"

        android:visibility="gone"
        android:layout_gravity="center"

        android:text="FrameLayout"/>
</FrameLayout>
```

### 4. TableLayout

```xml
<?xml version="1.0" encoding="utf-8"?>
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:collapseColumns="0" // 해당 열을 invisible
    android:shrinkColumns="1" // 전체 너비에 맞게 해당 열을 줄임
    android:stretchColumns="1" // 전체 너비에 맞게 해당 열을 늘림
    tools:context=".MainActivity">
    <TableRow>
        <Button
            android:layout_height="wrap_content"
            android:layout_width="wrap_content"
            android:text="Button1"
            android:layout_gravity="center"/>
        <Button
            android:layout_height="wrap_content"
            android:layout_width="wrap_content"
            android:text="Button2"
            android:layout_gravity="center"/>
    </TableRow>
</TableLayout>
```

### 5. GridLayout

```xml
<?xml version="1.0" encoding="utf-8"?>
<GridLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"

    android:orientation="horizontal" // Grid 시작 방향, horizontal이면 가로방향으로 진행되고 1행, 2행 순서로 내려감
    android:columnCount="4" // 열의 갯수
    android:rowCount="10"   // 행의 갯수
    android:alignmentMode="alignBounds" // alignBounds이면 margin을 제외하고 정렬. alignMargin이면 여백 포함
    android:columnOrderPreserved="true" // 보이지 않는 열을 표현하기 위해 빈 열 앞으로 당겨옴
    android:useDefaultMargins="true"    // 뷰 사이의 margin을 8dp로 유지
    tools:context=".MainActivity">

    <Button
        android:layout_marginLeft="50dp"
        android:layout_gravity="center"
        android:layout_row="1"  // 행 위치
        android:layout_column="1"   // 열 위치
        android:layout_rowSpan="2"  //  행 병합
        android:layout_columnSpan="2"   // 열 병합
        />
</GridLayout>
```
