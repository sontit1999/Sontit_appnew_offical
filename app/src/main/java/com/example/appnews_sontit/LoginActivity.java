package com.example.appnews_sontit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String urllogin = "https://sontithaui.000webhostapp.com/login.php" ;
    EditText edtUsername,edtPassword;
    TextView txtregister;
    Button btnDangnhap;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        permisseion();
        anhxa();
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim() ;
                String password = edtPassword.getText().toString().trim() ;
                if(username.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Ko dc bỏ trống nhá!", Toast.LENGTH_SHORT).show();
                }else if(edtUsername.length() <6 || edtPassword.length() < 6)
                {
                    Toast.makeText(LoginActivity.this, "Tài khoản và mật khẩu ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                }
                else{
                    Dangnhap(username,password);
                }
            }
        });
        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhxa() {
        txtregister = (TextView) findViewById(R.id.textviewregisger);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
        edtUsername = (EditText) findViewById(R.id.edittextusername);
        edtPassword = (EditText) findViewById(R.id.edittextpassword);
        btnDangnhap = (Button) findViewById(R.id.buttonlogin);
    }
    private void Dangnhap(final String username, final String password)
    {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urllogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                if(response.toString().equals("success"))
                {
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Lỗi gì đấy ?", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("username",username);
                param.put("password",password);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void permisseion(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {

        }
     // Else ask for permission
        else {
            ActivityCompat.requestPermissions(this, new String[]
                    { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
    }
}
