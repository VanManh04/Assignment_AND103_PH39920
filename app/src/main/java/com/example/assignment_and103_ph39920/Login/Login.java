package com.example.assignment_and103_ph39920.Login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.assignment_and103_ph39920.Home;
import com.example.assignment_and103_ph39920.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextView btn_Register_Login;

    EditText edtUsername_Login,edtPassword_Login;
    Button btn_Login;
    String email,password;
    private FirebaseAuth mAuth;
    TextView btn_Signup_Login,txtforgotpass;

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // neu user da dang nhap vao tu phien truoc thi su dung user luon
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        VideoView videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_1);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });
        // Đặt màu nền cho RelativeLayout
//        RelativeLayout relativeLayout = findViewById(R.id.login);
//        relativeLayout.setBackgroundColor(getResources().getColor(R.color.black));


        btn_Register_Login = findViewById(R.id.btn_Register_Login);
        btn_Register_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        edtUsername_Login = findViewById(R.id.edtUsername_Login);
        edtPassword_Login = findViewById(R.id.edtPassword_Login);
        btn_Signup_Login = findViewById(R.id.btn_Register_Login);
        btn_Login = findViewById(R.id.btn_Login);
        txtforgotpass= findViewById(R.id.txtforgotpass);

        Button btnLoginPhone = findViewById(R.id.btnLoginPhone);
        btnLoginPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Login_Phone.class);
                startActivity(i);
            }
        });


        btn_Signup_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtUsername_Login.getText().toString();
                password = edtPassword_Login.getText().toString();
                SignIn();
            }
        });

        txtforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailForgot = edtUsername_Login.getText().toString();
                if (emailForgot.trim().isEmpty()){
                    Toast.makeText(Login.this, "Vui lòng nhập email !", Toast.LENGTH_SHORT).show();
                }else {
                    resetPassEmail(emailForgot);
                }
            }
        });
    }

    private  void resetPassEmail(String emailResetPass){
        mAuth.sendPasswordResetEmail(emailResetPass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Vui lòng kiểm tra hộp thư để cận nhật Pass!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Login.this, "Lỗi gửi email !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  void SignIn(){
        if(email.trim().isEmpty()||password.trim().isEmpty()){
            Toast.makeText(this, "Không bỏ trống thông tin !", Toast.LENGTH_SHORT).show();
//        }else  if (check.checkPassword(password)){
//            Toast.makeText(this, "Mật khẩu quá yếu !", Toast.LENGTH_SHORT).show();
//        }else  if (check.checkGmailFormat(email)){
//            Toast.makeText(this, "Email không đúng định dạng !", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //lấy thông tin tài khoản vừa mới đăng kí
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(Login.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(Login.this, "Đăng nhập thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}