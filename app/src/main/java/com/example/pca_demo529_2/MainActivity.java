package com.example.pca_demo529_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private int K;//根据输入K的值提取特征
    private String resultImage_filename_all;//结果的文件路径和文件名
    private Button button1;
    private EditText text_K_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取输入的K值
        text_K_value = findViewById(R.id.text_K_value);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp_K = text_K_value.getText().toString();
                K = Integer.parseInt(temp_K);
                Toast.makeText(MainActivity.this,String.valueOf(K),Toast.LENGTH_SHORT).show();
                initPython();
                callPythonCode();

                Intent intent = new Intent();
                intent.setClass(MainActivity.this,TestActivity.class);
                intent.putExtra("resultImage_filename_all",resultImage_filename_all);
                intent.putExtra("K_value",K);
                startActivity(intent);
            }
        });
    }

    //初始化Python环境
    void initPython(){
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
    }

    //调用Python代码
    void callPythonCode(){
        //创建python实例
        Python python = Python.getInstance();
//        PyObject obj1 = python.getModule("pcaDemo").callAttr("test", 2,3);
//        // 将Python返回值换为Java中的Integer类型
//        Integer sum = obj1.toJava(Integer.class);
//        Log.d(TAG,"add = "+sum.toString());

        PyObject obj2 = python.getModule("pcaDemo").callAttr("main",K);
        resultImage_filename_all = obj2.toJava(String.class);
        Log.d(TAG,"resultImage_filename = "+resultImage_filename_all);

    }

    //分割文件路径、文件名



}