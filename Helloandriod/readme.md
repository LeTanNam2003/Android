# Hướng Dẫn main and second xml file

`ConstraintLayout` là một loại layout trong Android dùng để thiết kế giao diện linh hoạt và tối ưu hơn so với `RelativeLayout` hoặc `LinearLayout`. Nó cho phép định vị và sắp xếp các View dựa trên các ràng buộc (constraints) giữa các View với nhau hoặc với cha (parent).

## 1. Khai báo XML và ConstraintLayout
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
```
- `<?xml version="1.0" encoding="utf-8"?>`: Khai báo đây là một tài liệu XML.
- `<androidx.constraintlayout.widget.ConstraintLayout>`: Đây là layout chính của giao diện, cho phép đặt các thành phần UI với các ràng buộc (constraints).
- `xmlns:android, xmlns:app, xmlns:tools`: Khai báo namespace để sử dụng các thuộc tính của Android.
- `android:id="@+id/main"`: Gán ID cho layout để có thể tham chiếu trong code Java/Kotlin.
- `android:layout_width="match_parent"`: Chiều rộng bằng kích thước của màn hình.
- `android:layout_height="match_parent"`: Chiều cao bằng kích thước của màn hình.
- `ConstraintLayout`: Bố cục chính, cho phép sử dụng các ràng buộc (“constraints”) để bố trí UI linh hoạt.
- `tools:context=".MainActivity"`: Gợi ý rằng layout này sẽ được sử dụng trong `MainActivity`.

## 2. TextView: Hiển Thị Tiêu Đề
```xml
<TextView
    android:id="@+id/textEquation"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Giải phương trình bậc 2: ax² + bx + c"
    android:textSize="18sp"
    android:textStyle="bold"
    android:layout_marginTop="20dp"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"/>
```
- Hiển thị dòng tiêu đề về phương trình bậc 2.
- `textSize="18sp"`: Cỡ chữ 18sp.
- `textStyle="bold"`: In đậm.
- `android:layout_height="wrap_content"`: Chiều cao tự động theo nội dung.
- `android:layout_marginTop="20dp"`: Cách lề trên 20dp.
- `layout_constraintTop_toTopOf="parent"`: Định vị phần tử này dính vào đỉnh của ConstraintLayout.
- `layout_constraintStart_toStartOf="parent"`: Canh lề trái với lề trái của parent.
- `app:layout_constraintEnd_toEndOf="parent"`: Canh lề phải với lề phải của parent (căn giữa theo chiều ngang).

## 3. EditText: Nhập Số Liệu
### Nhập `a`
```xml
<EditText
    android:id="@+id/inputA"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:minHeight="48dp"
    android:hint="Nhập a"
    android:inputType="numberDecimal"
    app:layout_constraintTop_toBottomOf="@id/textEquation"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    android:layout_marginTop="16dp"/>
```
- `android:id="@+id/inputA"`: ID để tham chiếu trong code.
- `inputType="numberDecimal"`: Chỉ nhập số thực.
- `layout_width="0dp"` + `constraintStart`/`constraintEnd`: Đảm bảo EditText giãn hết chiều ngang.
- `android:layout_marginTop="16dp"`: Cách TextView 16dp.

### Nhập `b` và `c`
```xml
<EditText
    android:id="@+id/inputB"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:minHeight="48dp"
    android:hint="Nhập b"
    android:inputType="numberDecimal"
    app:layout_constraintTop_toBottomOf="@id/inputA"
    android:layout_marginTop="8dp"/>
```
```xml
<EditText
    android:id="@+id/inputC"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:minHeight="48dp"
    android:hint="Nhập c"
    android:inputType="numberDecimal"
    app:layout_constraintTop_toBottomOf="@id/inputB"
    android:layout_marginTop="8dp"/>
```
- Giống `inputA`, nhưng đặt bên dưới `inputA` và `inputB`.

## 4. Button: Giải Phương Trình
```xml
<Button
    android:id="@+id/buttonSolve"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Giải phương trình"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintTop_toBottomOf="@id/inputC"
    android:layout_marginTop="16dp"/>
```
- `Button`: Nút bắm.
- `layout_constraintTop_toBottomOf="@id/inputC"`: Đặt bên dưới EditText `inputC`.
- `layout_constraintStart_toStartOf="parent"` + `layout_constraintEnd_toEndOf="parent"`: Căn giữa ngang.

---
## Tóm Tắt
- **Các thành phần chính**:
  1. **TextView**: Hiển thị tiêu đề.
  2. **EditText**: Nhập giá trị `a, b, c`.
  3. **Button**: Bấm để giải phương trình.
- **Cách bố trí**:
  - `ConstraintLayout`: Cấu trúc linh hoạt.
  - Ràng buộc giúp UI gọn gàng, dễ điều chỉnh.



# Hướng Dẫn MainActivity.java

## Giới Thiệu
File `MainActivity.java` trong ứng dụng Android này giúp người dùng nhập vào các hệ số `a, b, c` của phương trình bậc hai, sau đó tính nghiệm và chuyển kết quả sang `SecondActivity.java`.

## Cấu Trúc File
### 1. Import Các Thư Viện Cần Thiết
```java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
```
- **Intent**: Dùng để chuyển dữ liệu giữa các Activity.
- **Bundle**: Lưu trạng thái khi ứng dụng hoạt động.
- **View, Button, EditText, Toast**: Các thành phần UI và thông báo.
- **EdgeToEdge, AppCompatActivity**: Hỗ trợ thiết kế giao diện hiện đại.
- **Insets, ViewCompat, WindowInsetsCompat**: Tạo khoảng cách phù hợp với thanh trạng thái.

### 2. Định Nghĩa Lớp `MainActivity`
```java
public class MainActivity extends AppCompatActivity {
```
Lớp này kế thừa `AppCompatActivity` để có thể sử dụng các thành phần UI của Android.

### 3. Phương Thức `onCreate()`
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
```
- `onCreate()`: Được gọi khi Activity khởi chạy.
- `setContentView(R.layout.activity_main)`: Liên kết giao diện với layout XML.

### 4. Xử Lý Thanh Trạng Thái
```java
ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
    return insets;
});
```
Giúp giao diện tự động điều chỉnh khoảng cách với thanh trạng thái.

### 5. Ánh Xạ Các Thành Phần Giao Diện
```java
EditText inputA = findViewById(R.id.inputA);
EditText inputB = findViewById(R.id.inputB);
EditText inputC = findViewById(R.id.inputC);
Button buttonSolve = findViewById(R.id.buttonSolve);
```
Lấy các thành phần từ file XML `activity_main.xml`.

### 6. Xử Lý Sự Kiện Khi Nhấn Nút
```java
buttonSolve.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
```
Khi người dùng nhấn nút "Giải phương trình", sự kiện này sẽ kích hoạt.

### 7. Lấy Dữ Liệu Người Dùng Nhập
```java
    double a = Double.parseDouble(inputA.getText().toString());
    double b = Double.parseDouble(inputB.getText().toString());
    double c = Double.parseDouble(inputC.getText().toString());
```
Chuyển đổi dữ liệu từ `EditText` thành số thực `double`.

### 8. Tính Delta Và Xác Định Nghiệm
```java
    double delta = b * b - 4 * a * c;
    String result;

    if (delta < 0) {
        result = "Phương trình vô nghiệm";
    } else if (delta == 0) {
        double x = -b / (2 * a);
        result = "Phương trình có nghiệm kép: x = " + x;
    } else {
        double x1 = (-b + Math.sqrt(delta)) / (2 * a);
        double x2 = (-b - Math.sqrt(delta)) / (2 * a);
        result = "Nghiệm x1 = " + x1 + ", x2 = " + x2;
    }
```
- **Nếu `delta < 0`**: Phương trình vô nghiệm.
- **Nếu `delta == 0`**: Có nghiệm kép `x`.
- **Nếu `delta > 0`**: Tính hai nghiệm `x1, x2`.

### 9. Chuyển Kết Quả Sang `SecondActivity`
```java
Intent intent = new Intent(MainActivity.this, SecondActivity.class);
intent.putExtra("result", result);
startActivity(intent);
```
- `Intent` giúp chuyển dữ liệu sang `SecondActivity.java`.
- `putExtra("result", result)`: Đưa kết quả tính toán vào Intent.
- `startActivity(intent)`: Mở `SecondActivity`.

### 10. Xử Lý Ngoại Lệ Khi Nhập Dữ Liệu Sai
```java
} catch (NumberFormatException e) {
    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ số a, b, c!", Toast.LENGTH_SHORT).show();
}
```
Nếu người dùng không nhập đủ `a, b, c`, thông báo lỗi sẽ hiển thị bằng `Toast`.

---
## Tổng Kết
- **Chức năng chính**:
  - Nhận giá trị `a, b, c` từ người dùng.
  - Tính toán nghiệm phương trình bậc hai.
  - Chuyển kết quả sang `SecondActivity`.
- **Các thành phần quan trọng**:
  - `EditText` để nhập dữ liệu.
  - `Button` để kích hoạt tính toán.
  - `Toast` để báo lỗi.
  - `Intent` để chuyển dữ liệu.

**Ghi chú**: Để hiển thị kết quả, cần tạo `SecondActivity.java` để nhận dữ liệu từ Intent!

# Hướng Dẫn SecondActivity.java

## 1. Giới Thiệu
`SecondActivity.java` là activity thứ hai trong ứng dụng, hiển thị kết quả phương trình bậc hai và cho phép quay lại `MainActivity`.

## 2. Cấu Trúc Chính
```java
public class SecondActivity extends AppCompatActivity {
```
- Kế thừa từ `AppCompatActivity`, đảm bảo tương thích với các phiên bản Android khác nhau.

## 3. Phương Thức `onCreate()`
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
```
- `onCreate()` được gọi khi activity khởi tạo.
- `setContentView(R.layout.activity_second);` thiết lập giao diện từ file XML tương ứng (`activity_second.xml`).

## 4. Ánh Xạ Thành Phần Giao Diện
```java
TextView textEquation = findViewById(R.id.textEquation);
TextView textResult = findViewById(R.id.textResult);
Button buttonBack = findViewById(R.id.buttonBack);
```
- `textEquation`: Hiển thị phương trình.
- `textResult`: Hiển thị kết quả nghiệm.
- `buttonBack`: Nút quay lại `MainActivity`.

## 5. Nhận Dữ Liệu Từ Intent
```java
String result = getIntent().getStringExtra("result");
textResult.setText(result);
```
- Lấy chuỗi kết quả từ `Intent` do `MainActivity` gửi sang.
- Hiển thị kết quả lên `textResult`.

## 6. Xử Lý Sự Kiện Nút "Quay Lại"
```java
buttonBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish(); // Đóng SecondActivity để quay lại MainActivity
    }
});
```
- Khi nhấn `buttonBack`, `finish()` sẽ được gọi để đóng `SecondActivity`, đưa người dùng quay lại `MainActivity`.

---
## 7. Tổng Kết
- **`SecondActivity.java`** nhận dữ liệu từ `MainActivity`.
- **Hiển thị kết quả** phương trình.
- **Cung cấp nút "Quay lại"** để trở về `MainActivity`.




