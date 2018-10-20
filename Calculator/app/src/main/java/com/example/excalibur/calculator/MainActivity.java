package com.example.excalibur.calculator;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    TextView cache;
    TextView operand;
    float result = 0;
    String lastOperation = "=";

    private final static String TAG = "Activity";
    private final static int REQUEST_ACCESS_TYPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cache = findViewById(R.id.cache);
        operand = findViewById(R.id.result);

//        ImageView imageView = findViewById(R.id.image);
//        InputStream inputStream = null;
//        try{
//            inputStream = getApplicationContext().getAssets().open("background_pict.png");
//            Drawable drawable = Drawable.createFromStream(inputStream, null);
//            imageView.setImageDrawable(drawable);
//            imageView.setScaleType(ImageView.ScaleType.MATRIX);
//        }
//        catch (IOException ex){
//            ex.printStackTrace();
//        }
//        finally {
//            try{
//                if(inputStream != null){
//                    inputStream.close();
//                }
//            }
//            catch (IOException e){
//                e.printStackTrace();
//            }
//        }

        Log.d(TAG, "onCreate");


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putFloat("RESULT", result);
        outState.putString("OPERAND", operand.getText().toString());
        outState.putString("OPERATION", lastOperation);
        outState.putString("CACHE", cache.getText().toString());
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cache.setText(savedInstanceState.getString("CACHE"));
        lastOperation = savedInstanceState.getString("OPERATION");
        result = (int)savedInstanceState.getFloat("RESULT");
        operand.setText(savedInstanceState.getString("OPERAND"));
        Log.d(TAG, "onRestoreInstanceState");
    }

    public void onNumberButtonClick(View view) {
        if(!operand.getText().toString().equals("0")){
            operand.append(((Button) view).getText());
        }
        else {
            operand.setText(((Button) view).getText());
        }
    }

    public void onOperationButtonClick(View view) {
        String currentOperation = ((Button)view).getText().toString();
        switch (currentOperation){
            case "/":{
                if(lastOperation.equals("=")){
                    cache.setText(operand.getText() + " / ");
                    result = Float.parseFloat(operand.getText().toString());
                    lastOperation = "/";
                    operand.setText("0");
                }
                break;
            }
            case "*":{
                if(lastOperation.equals("=")){
                    cache.setText(operand.getText() + " * ");
                    result = Float.parseFloat(operand.getText().toString());
                    lastOperation = "*";
                    operand.setText("0");
                }
                break;
            }
            case "-":{
                if(lastOperation.equals("=")){
                    cache.setText(operand.getText() + " - ");
                    result = Float.parseFloat(operand.getText().toString());
                    lastOperation = "-";
                    operand.setText("0");
                }
                break;
            }
            case "+":{
                if(lastOperation.equals("=")){
                    cache.setText(operand.getText() + " + ");
                    result = Float.parseFloat(operand.getText().toString());
                    lastOperation = "+";
                    operand.setText("0");
                }
                break;
            }
            case "=":{
                if(!lastOperation.equals("=")){
                    switch (lastOperation){
                        case "/":{
                            if(operand.getText().toString().equals("0")){
                                cache.setText("Cannot divide by zero!");
                            }
                            else {
                                cache.append(operand.getText());
                                result /= Float.parseFloat(operand.getText().toString());
                                cache.append(" = " + String.valueOf(result));
                            }
                            lastOperation = "=";
                            operand.setText("0");
                            break;
                        }
                        case "*":{
                            cache.append(operand.getText());
                            result *= Float.parseFloat(operand.getText().toString());
                            cache.append(" = " + String.valueOf(result));
                            lastOperation = "=";
                            operand.setText("0");
                            break;
                        }
                        case "-":{
                            cache.append(operand.getText());
                            result -= Float.parseFloat(operand.getText().toString());
                            cache.append(" = " + String.valueOf(result));
                            lastOperation = "=";
                            operand.setText("0");
                            break;
                        }
                        case "+":{
                            cache.append(operand.getText());
                            result += Float.parseFloat(operand.getText().toString());
                            cache.append(" = " + String.valueOf(result));
                            lastOperation = "=";
                            operand.setText("0");
                            break;
                        }
                        default:break;
                    }
                }
                break;
            }
            default:break;
        }
    }

    public void onPointButtonClick(View view) {
        if(!operand.getText().toString().contains(".")){
            operand.append(".");
        }
    }

    public void onClearButtonClick(View view) {
        cache.setText("");
        operand.setText("0");
        lastOperation = "=";
        result = 0;
    }

    public void createActivity(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivityForResult(intent, REQUEST_ACCESS_TYPE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
