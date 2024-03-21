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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.assignment_and103_ph39920.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String email,password,passwordNhaplai;
    EditText edtEmail,edtPassword,edtPassword_Signup_nhaplai;
    Button btn_Signup;
    TextView txt_Login;

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
        setContentView(R.layout.register);

        VideoView videoView = findViewById(R.id.videoViewRegister);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_1);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });

        txt_Login = findViewById(R.id.btn_Login_Register);
        txt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });


        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edtUsername_Signup);
        edtPassword = findViewById(R.id.edtPassword_Signup);
        edtPassword_Signup_nhaplai = findViewById(R.id.edtPassword_Signup_nhaplai);
        btn_Signup = findViewById(R.id.btn_Signup);


        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                passwordNhaplai = edtPassword_Signup_nhaplai.getText().toString();

                if(password.equals(passwordNhaplai)){
                    CreateAccount();
                }else {
                    Toast.makeText(Register.this, "Nhập lại mật khẩu không chính xác !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public  void CreateAccount() {
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            Toast.makeText(this, "Không bỏ trống thông tin !", Toast.LENGTH_SHORT).show();
//        } else if (check.checkPassword(password)) {
//            Toast.makeText(this, "Mật khẩu quá yếu !", Toast.LENGTH_SHORT).show();
//        } else if (check.checkGmailFormat(email)) {
//            Toast.makeText(this, "Email không đúng định dạng !", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //lấy thông tin tài khoản vừa mới đăng kí
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(Register.this, "Đăng kí thành công !", Toast.LENGTH_SHORT).show();
//                        finish();
                        Intent i  = new Intent(Register.this, Login.class);
                        startActivity(i);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(Register.this, "Đăng kí thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}