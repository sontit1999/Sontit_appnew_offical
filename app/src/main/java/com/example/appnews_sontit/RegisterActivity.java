package com.example.appnews_sontit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

public class RegisterActivity extends AppCompatActivity {
    EditText edtUsername,edtPassword,editTextagainpass;
    TextView txthaveaccount;
    Button btnDangki;
    ProgressBar progressBar;
    String urlregister = "https://sontithaui.000webhostapp.com/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.textviewhaveaccount:
                        finish();
                        break;
                    case R.id.buttonregister:
                        if(edtUsername.getText().toString().trim().length()<8 || edtPassword.getText().toString().trim().length()<8 ){
                            Toast.makeText(RegisterActivity.this, "Tài khoản mật khẩu ít nhất 8 kí tự !", Toast.LENGTH_SHORT).show();
                        }else if(!edtPassword.getText().toString().equals(editTextagainpass.getText().toString())){
                            Toast.makeText(RegisterActivity.this, "2 mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                        }else {
                            String acc = edtUsername.getText().toString().trim();
                            String pass = edtPassword.getText().toString().trim();
                            Dangki(acc,pass);
                        }
                }
            }
        };
        txthaveaccount.setOnClickListener(onClickListener);
        btnDangki.setOnClickListener(onClickListener);
    }

    private void anhxa() {
        txthaveaccount = (TextView) findViewById(R.id.textviewhaveaccount);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        editTextagainpass = (EditText) findViewById(R.id.edittextagainpassword1);
        progressBar.setVisibility(View.INVISIBLE);
        edtUsername = (EditText) findViewById(R.id.edittextusername);
        edtPassword = (EditText) findViewById(R.id.edittextpassword1);
        btnDangki = (Button) findViewById(R.id.buttonregister);
    }
    private void Dangki(final String username, final String password)
    {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlregister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                if(response.toString().equals("succes"))
                {
                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }else{
                    Toast.makeText(RegisterActivity.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("tk",username);
                param.put("mk",password);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
