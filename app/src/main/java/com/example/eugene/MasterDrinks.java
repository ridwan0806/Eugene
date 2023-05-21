package com.example.eugene;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eugene.Common.MoneyTextWatcher;

import java.math.BigDecimal;

public class MasterDrinks extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_drinks);

        editText = findViewById(R.id.etInputNumber);
        editText.addTextChangedListener(new MoneyTextWatcher(editText));
        editText.setText("0");

        textView = findViewById(R.id.tvInputNumber);
        button = findViewById(R.id.btnOk);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teset();
            }
        });
    }

    private void teset(){
        BigDecimal value = MoneyTextWatcher.parseCurrencyValue(editText.getText().toString());
        textView.setText(String.valueOf(value));
    }
}