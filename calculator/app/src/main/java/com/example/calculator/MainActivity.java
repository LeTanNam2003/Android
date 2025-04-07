package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvInput;
    private StringBuilder currentInput = new StringBuilder();
    private String operator = "";
    private double firstOperand = 0;
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvInput = findViewById(R.id.tvInput);

        // Gắn sự kiện cho các nút số
        int[] numberButtonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };

        View.OnClickListener numberClickListener = v -> {
            if (isNewOperation) {
                currentInput.setLength(0);
                isNewOperation = false;
            }
            Button b = (Button) v;
            currentInput.append(b.getText());
            tvInput.setText(currentInput.toString());
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(numberClickListener);
        }

        // Gắn sự kiện cho các nút toán tử
        findViewById(R.id.btnPlus).setOnClickListener(v -> onOperatorClicked("+"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> onOperatorClicked("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> onOperatorClicked("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> onOperatorClicked("/"));
        findViewById(R.id.btnPercent).setOnClickListener(v -> onOperatorClicked("%"));

        // Gắn sự kiện cho dấu "="
        findViewById(R.id.btnEqual).setOnClickListener(v -> onEqualClicked());

        // Gắn sự kiện cho "DEL"
        findViewById(R.id.btnDel).setOnClickListener(v -> {
            if (currentInput.length() > 0) {
                currentInput.deleteCharAt(currentInput.length() - 1); // Xóa ký tự cuối cùng
            }
            if (currentInput.length() == 0) {
                tvInput.setText("0");
            } else {
                tvInput.setText(currentInput.toString());
            }
        });
    }

    private void onOperatorClicked(String op) {
        if (currentInput.length() == 0) {
            return; // Nếu không có giá trị đầu vào, không làm gì
        }
        try {
            firstOperand = Double.parseDouble(currentInput.toString());
            operator = op;
            isNewOperation = true;
        } catch (NumberFormatException e) {
            tvInput.setText("Error");
        }
    }

    private void onEqualClicked() {
        try {
            double secondOperand = Double.parseDouble(currentInput.toString());
            double result = 0;

            switch (operator) {
                case "+": result = firstOperand + secondOperand; break;
                case "-": result = firstOperand - secondOperand; break;
                case "*": result = firstOperand * secondOperand; break;
                case "/": result = secondOperand != 0 ? firstOperand / secondOperand : Double.NaN; break;
                case "%": result = firstOperand % secondOperand; break;
                default: result = secondOperand;
            }

            tvInput.setText(String.valueOf(result));
            currentInput = new StringBuilder(String.valueOf(result));
            isNewOperation = true;

        } catch (NumberFormatException e) {
            tvInput.setText("Error");
        }
    }
}
