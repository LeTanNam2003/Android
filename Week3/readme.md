# Hướng Dẫn Chi Tiết File activity_main.xml

`ConstraintLayout` là một loại layout trong Android dùng để thiết kế giao diện linh hoạt và tối ưu hơn so với `RelativeLayout` hoặc `LinearLayout`. Nó cho phép định vị và sắp xếp các View dựa trên các ràng buộc (constraints) giữa các View với nhau hoặc với cha (parent).

## 1. Giới Thiệu
File `activity_main.xml` chứa giao diện chính của ứng dụng, được xây dựng bằng `ConstraintLayout`. Giao diện này có các thành phần sau:
- **Tiêu đề "Học sinh viết"**
- **EditText** để nhập bài viết của học sinh
- **Tiêu đề "Cô giáo sửa"**
- **TextView** để hiển thị nội dung đã chỉnh sửa
- **Button "NỘP BÀI"**

---

## 2. Cấu Trúc Layout
```xml
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/light_blue"
    tools:context=".MainActivity">
```
- **`ConstraintLayout`**: Được sử dụng để tạo bố cục linh hoạt.
- **`android:background="@color/light_blue"`**: Đặt màu nền của màn hình chính.
- **`tools:context=".MainActivity"`**: Xác định Activity sẽ sử dụng layout này.

---

## 3. Tiêu Đề "Học Sinh Viết"
```xml
<TextView
    android:id="@+id/studentWrite_TextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Học sinh viết"
    android:textSize="20sp"
    android:textColor="@color/black"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    android:layout_marginTop="20dp" />
```
- **Hiển thị tiêu đề "Học sinh viết"**.
- **Căn giữa màn hình** bằng các thuộc tính `layout_constraintStart_toStartOf` và `layout_constraintEnd_toEndOf`.
- **`textSize="20sp"`**: Kích thước chữ 20sp.
- **`android:textColor="@color/black"`**: Màu chữ đen.

---

## 4. Ô Nhập Bài Viết (EditText)
```xml
<EditText
    android:id="@+id/studentWrite_Text"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:hint="Nhập bài viết..."
    android:inputType="text"
    android:padding="12dp"
    android:background="@drawable/edittext_background"
    app:layout_constraintTop_toBottomOf="@id/studentWrite_TextView"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    android:layout_margin="16dp" />
```
- **EditText** để nhập nội dung bài viết.
- **`android:hint="Nhập bài viết..."`**: Hiển thị gợi ý.
- **`android:inputType="text"`**: Cho phép nhập văn bản.
- **`android:background="@drawable/edittext_background"`**: Đặt nền tùy chỉnh.
- **Chiều rộng `0dp` kết hợp với ràng buộc** giúp nó mở rộng hết màn hình.

---

## 5. Tiêu Đề "Cô Giáo Sửa"
```xml
<TextView
    android:id="@+id/teacherFix_TextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Cô giáo sửa"
    android:textSize="20sp"
    android:textColor="@color/black"
    app:layout_constraintTop_toBottomOf="@id/studentWrite_Text"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    android:layout_marginTop="20dp" />
```
- **Hiển thị tiêu đề "Cô giáo sửa"**.
- **Căn giữa màn hình** tương tự như tiêu đề trước.
- **Khoảng cách trên `layout_marginTop="20dp"`** để tạo khoảng cách với phần trên.

---

## 6. Hiển Thị Nội Dung Sửa Bài (TextView)
```xml
<TextView
    android:id="@+id/teacherFix_received"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:textSize="18sp"
    android:background="@drawable/teacher_fix_background"
    android:padding="12dp"
    app:layout_constraintTop_toBottomOf="@id/teacherFix_TextView"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    android:layout_margin="16dp" />
```
- **Hiển thị nội dung sửa bài** của giáo viên.
- **Có nền tùy chỉnh `@drawable/teacher_fix_background`**.
- **Chiều rộng `0dp` giúp nó mở rộng theo chiều ngang của màn hình**.

---

## 7. Nút "NỘP BÀI"
```xml
<Button
    android:id="@+id/buttonAssign"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="NỘP BÀI"
    android:backgroundTint="@color/primary"
    android:textColor="@color/white"
    android:padding="12dp"
    android:elevation="4dp"
    app:layout_constraintTop_toBottomOf="@id/teacherFix_received"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    android:layout_marginTop="20dp" />
```
- **Nút bấm để nộp bài viết của học sinh**.
- **Có màu nền `@color/primary` và chữ trắng `@color/white`**.
- **Tạo hiệu ứng nâng (`elevation="4dp"`) giúp nút nổi bật**.
- **Căn giữa màn hình và đặt dưới phần nội dung chỉnh sửa của giáo viên**.

---

## 8. Tổng Kết
- **Giao diện ứng dụng đơn giản nhưng đầy đủ**:
  - Học sinh nhập bài.
  - Giáo viên sửa bài.
  - Học sinh có thể nộp bài sau khi hoàn thành.
- **Bố cục được thiết kế bằng ConstraintLayout** giúp giao diện linh hoạt trên nhiều kích thước màn hình.
- **Sử dụng màu sắc, padding và nền để làm nổi bật từng thành phần**.

# Hướng Dẫn Second Activity XML

## 1. Khai báo ConstraintLayout
```xml
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp"
    android:background="@color/light_gray"
    tools:context=".Second_activity">
```
- `ConstraintLayout`: Bố cục chính giúp căn chỉnh linh hoạt.
- `android:padding="16dp"`: Thêm khoảng cách lề để bố cục thoáng hơn.
- `android:background="@color/light_gray"`: Đặt màu nền xám nhạt cho giao diện.

## 2. TextView: Hiển thị tiêu đề "Đoạn văn"
```xml
<TextView
    android:id="@+id/paragraph_textView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Đoạn văn"
    android:textSize="22sp"
    android:textStyle="bold"
    android:textColor="@color/black"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    android:layout_marginTop="24dp"/>
```
- Hiển thị tiêu đề "Đoạn văn" với kích thước chữ lớn (`22sp`) và in đậm.
- Canh giữa theo chiều ngang (`layout_constraintStart_toStartOf="parent"` và `layout_constraintEnd_toEndOf="parent"`).
- Khoảng cách 24dp từ mép trên (`layout_marginTop="24dp"`).

## 3. EditText: Nhập nội dung đoạn văn
```xml
<EditText
    android:id="@+id/paragraph_editText"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:hint="Nhập đoạn văn..."
    android:inputType="textMultiLine"
    android:minHeight="100dp"
    android:background="@drawable/edittext_background"
    android:padding="12dp"
    android:textSize="18sp"
    app:layout_constraintTop_toBottomOf="@id/paragraph_textView"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    android:layout_marginTop="12dp"
    android:layout_marginHorizontal="20dp"/>
```
- `EditText` cho phép nhập văn bản nhiều dòng (`inputType="textMultiLine"`).
- Đặt khoảng cách 12dp phía trên (`layout_marginTop="12dp"`).
- Lề ngang 20dp (`layout_marginHorizontal="20dp"`) để tránh sát mép.
- `android:minHeight="100dp"`: Đảm bảo chiều cao tối thiểu đủ rộng để nhập nội dung.

## 4. Button: Nút "ĐÃ SỬA BÀI"
```xml
<Button
    android:id="@+id/corrected_Button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="ĐÃ SỬA BÀI"
    android:textSize="18sp"
    android:backgroundTint="@color/primary"
    android:textColor="@color/white"
    android:padding="12dp"
    android:elevation="4dp"
    app:layout_constraintTop_toBottomOf="@id/paragraph_editText"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    android:layout_marginTop="20dp"/>
```
- `padding` là khoảng cách giữa nội dung bên trong một View và biên của View đó. Nó giúp tạo không gian trống bên trong View để nội dung không bị dính sát vào cạnh.
- `Button` cho phép người dùng xác nhận đã sửa bài.
- Kích thước chữ `18sp`.
- `backgroundTint="@color/primary"`: Đặt màu nền chính của ứng dụng.
- `elevation="4dp"`: Tạo hiệu ứng nổi để nút trông nổi bật hơn.
- `layout_constraintTop_toBottomOf="@id/paragraph_editText"`: Đặt bên dưới `EditText` với khoảng cách `20dp` (`layout_marginTop="20dp"`).

## Tóm tắt
- **Giao diện bao gồm:**
  1. **TextView**: Hiển thị tiêu đề "Đoạn văn".
  2. **EditText**: Cho phép nhập nội dung đoạn văn.
  3. **Button**: Nút xác nhận "ĐÃ SỬA BÀI".
- **Cách bố trí:**
  - Sử dụng `ConstraintLayout` để căn chỉnh linh hoạt.
  - Đặt khoảng cách hợp lý để giao diện dễ nhìn.
  - Áp dụng màu nền và hiệu ứng cho các thành phần UI.

# Hướng Dẫn MainActivity.java

## 1. Giới Thiệu
`MainActivity.java` là Activity chính của ứng dụng, cho phép học sinh nhập bài viết và gửi sang `Second_activity` để giáo viên sửa chữa. Kết quả sửa sẽ được hiển thị lại trên giao diện.

---

## 2. Giải Thích Code

### 2.1. Khai báo package và import thư viện cần thiết
```java
package com.example.week3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
```
- Nhập các thư viện cần thiết cho Activity, Intent, giao diện người dùng và xử lý kết quả Activity.

---

### 2.2. Khai báo biến
```java
EditText studentType;
TextView teacherFix;
Button btnSend;
```
- `studentType`: Ô nhập liệu của học sinh.
- `teacherFix`: Hiển thị nội dung đã được giáo viên sửa.
- `btnSend`: Nút gửi bài viết đến `Second_activity`.

---

### 2.3. Định nghĩa ActivityResultLauncher
```java
private final ActivityResultLauncher<Intent> activityLauncher =
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == 66 && result.getData() != null) {
                            String fixedText = result.getData().getStringExtra("fixed");
                            teacherFix.setText(fixedText);
                        }
                    }
                });
```
- `ActivityResultLauncher<Intent>` thay thế cho phương thức `startActivityForResult()` cũ.
- Khi `Second_activity` trả về kết quả với `resultCode == 66`, chương trình lấy nội dung đã sửa và hiển thị trên `teacherFix`.

---

### 2.4. Phương thức `onCreate()`
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
```
- Kích hoạt `EdgeToEdge` để hỗ trợ thiết kế toàn màn hình.
- Gán layout `activity_main.xml` làm giao diện chính.

#### **Cấu hình Layout để tránh che khuất nội dung**
```java
ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
    return insets;
});
```
- Đảm bảo nội dung không bị che bởi thanh trạng thái và thanh điều hướng.

---

### 2.5. Ánh xạ các thành phần giao diện
```java
studentType = findViewById(R.id.studentWrite_Text);
btnSend = findViewById(R.id.buttonAssign);
teacherFix = findViewById(R.id.teacherFix_received);
```
- Kết nối các thành phần UI với mã Java.

---

### 2.6. Xử lý sự kiện khi nhấn nút gửi bài
```java
btnSend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, Second_activity.class);
        String studentText = studentType.getText().toString();
        intent.putExtra("student_write", studentText);
        activityLauncher.launch(intent); // Gửi dữ liệu đến Second_activity
    }
});
```
- Khi nhấn nút "NỘP BÀI":
  1. Lấy dữ liệu từ `studentType`.
  2. Tạo Intent để chuyển sang `Second_activity`.
  3. Gửi nội dung nhập vào `Second_activity` bằng `activityLauncher.launch(intent)`.

---

## 3. Tổng Kết
- `MainActivity` nhận nội dung nhập từ học sinh và gửi sang `Second_activity` để giáo viên sửa lỗi.
- Khi `Second_activity` trả kết quả, nó sẽ hiển thị lại trong `teacherFix`.
- Dùng `ActivityResultLauncher` để nhận kết quả một cách hiện đại thay vì `startActivityForResult()`.

---

## 4. Mở Rộng
- Hiển thị thông báo nếu học sinh chưa nhập nội dung.
- Lưu lại nội dung đã nhập khi xoay màn hình.
- Thêm chức năng chỉnh sửa nội dung trước khi gửi.

# Hướng Dẫn Second_activity.java

## 1. Giới thiệu

File `Second_activity.java` là một Activity thứ hai trong ứng dụng Android, dùng để nhận đoạn văn từ `MainActivity`, cho phép giáo viên chỉnh sửa, sau đó gửi lại nội dung đã sửa về `MainActivity`.

---

## 2. Giải thích chi tiết code

### 2.1. Import các thư viện cần thiết
```java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
```
- `Intent`: Dùng để truyền dữ liệu giữa `MainActivity` và `Second_activity`.
- `Bundle`: Lưu trạng thái của Activity khi khởi tạo.
- `View`, `Button`, `EditText`: Các thành phần giao diện.
- `AppCompatActivity`: Lớp cha hỗ trợ các tính năng mới của Android.

### 2.2. Khai báo các biến thành viên
```java
public class Second_activity extends AppCompatActivity {
    EditText teacherFix;
    Button btnFixed;
```
- `teacherFix`: Ô nhập văn bản cho giáo viên chỉnh sửa.
- `btnFixed`: Nút bấm để xác nhận đã sửa bài.

### 2.3. Phương thức `onCreate()`
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
```
- `onCreate()` là phương thức khởi tạo Activity.
- `setContentView(R.layout.activity_second);` liên kết Activity với file giao diện `activity_second.xml`.

### 2.4. Ánh xạ các thành phần giao diện
```java
    teacherFix = findViewById(R.id.paragraph_editText);
    btnFixed = findViewById(R.id.corrected_Button);
```
- `findViewById()`: Lấy ID của các thành phần trong `activity_second.xml` để sử dụng trong Java.

### 2.5. Nhận dữ liệu từ `MainActivity`
```java
    Intent receivedIntent = getIntent();
    String paragraphReceived = receivedIntent.getStringExtra("student_write");
    teacherFix.setText(paragraphReceived);
```
- `getIntent()`: Lấy Intent từ `MainActivity` gửi sang.
- `getStringExtra("student_write")`: Nhận nội dung bài viết từ học sinh.
- `setText(paragraphReceived)`: Hiển thị đoạn văn lên `EditText` cho giáo viên chỉnh sửa.

### 2.6. Xử lý khi nhấn nút "ĐÃ SỬA BÀI"
```java
    btnFixed.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String teacherText = teacherFix.getText().toString();
```
- Khi nút `btnFixed` được bấm, chương trình lấy nội dung mà giáo viên đã chỉnh sửa.

### 2.7. Gửi dữ liệu đã chỉnh sửa về `MainActivity`
```java
            Intent resultIntent = new Intent();
            resultIntent.putExtra("fixed", teacherText);
            setResult(66, resultIntent);
            finish();
        }
    });
```
- `Intent resultIntent = new Intent();` tạo một Intent mới để gửi kết quả về `MainActivity`.
- `putExtra("fixed", teacherText)`: Gửi nội dung đã chỉnh sửa.
- `setResult(66, resultIntent)`: Trả về kết quả với mã `66`.
- `finish()`: Đóng `Second_activity` để quay lại `MainActivity`.

---

## 3. Luồng hoạt động
1. Học sinh nhập văn bản vào `MainActivity`.
2. Nhấn "NỘP BÀI" để mở `Second_activity`.
3. `Second_activity` nhận văn bản và hiển thị cho giáo viên chỉnh sửa.
4. Giáo viên chỉnh sửa và nhấn "ĐÃ SỬA BÀI".
5. Nội dung đã sửa được gửi về `MainActivity` và hiển thị.

---

## 4. Kết luận
File `Second_activity.java` đóng vai trò trung gian để nhận bài viết từ `MainActivity`, cho phép giáo viên sửa, và trả kết quả về. Nó sử dụng `Intent` để truyền dữ liệu giữa các Activity một cách hiệu quả.









