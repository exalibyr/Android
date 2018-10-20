package com.example.excalibur.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Main2Activity extends AppCompatActivity {

    RadioGroup formatType;
    TextView converted;
    EditText number;
    int mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        formatType = findViewById(R.id.converter);
        converted = findViewById(R.id.converted_number);
        number = findViewById(R.id.input_view);
        mode = formatType.getCheckedRadioButtonId();

        formatType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mode = checkedId;
                if(number.getText().toString().isEmpty()){
                    return;
                }
                switch (mode){
                    case R.id.hex:{
                        converted.setText(Integer.toHexString(
                                Integer.parseInt(number.getText().toString()))
                        );
                        break;
                    }
                    case R.id.oct:{
                        converted.setText(Integer.toOctalString(
                                Integer.parseInt(number.getText().toString()))
                        );
                        break;
                    }
                    case R.id.bin:{
                        converted.setText(Integer.toBinaryString(
                                Integer.parseInt(number.getText().toString()))
                        );
                        break;
                    }
                    default:break;
                }
            }
        });

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(number.getText().toString().isEmpty()){
                    converted.setText("");
                    return;
                }
                switch (mode){
                    case R.id.hex:{
                        converted.setText(Integer.toHexString(Integer.parseInt(s.toString())));
                        break;
                    }
                    case R.id.oct:{
                        converted.setText(Integer.toOctalString(Integer.parseInt(s.toString())));
                        break;
                    }
                    case R.id.bin:{
                        converted.setText(Integer.toBinaryString(Integer.parseInt(s.toString())));
                        break;
                    }
                    default:break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("NUMBER", number.getText().toString());
        outState.putString("CONVERTED", converted.getText().toString());
        outState.putInt("MODE", mode);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mode = savedInstanceState.getInt("MODE");
        formatType.check(mode);
        number.setText(savedInstanceState.getString("NUMBER"));
        converted.setText(savedInstanceState.getString("CONVERTED"));
    }

    public void returnToMainActivity(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
