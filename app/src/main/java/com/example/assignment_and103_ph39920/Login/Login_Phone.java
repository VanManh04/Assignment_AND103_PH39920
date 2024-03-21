package com.example.assignment_and103_ph39920.Login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment_and103_ph39920.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login_Phone extends AppCompatActivity {
    Button btn_LoginPhone,btn_GetOTP;
    EditText edtPhone,edtOTP;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallblacks;
    private  String mVerificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_phone);

        edtPhone = findViewById(R.id.edtPhone);
        edtOTP = findViewById(R.id.edtOTP);
        btn_LoginPhone = findViewById(R.id.btn_LoginPhone);
        btn_GetOTP = findViewById(R.id.btn_GetOTP);

        mAuth = FirebaseAuth.getInstance();

        mCallblacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //có thể tự nhậm OTP
                edtOTP.setText(phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {


            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                //gọi khi otp gueit thành công
                mVerificationId = s;
            }
        };

        btn_GetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOTP(edtPhone.getText().toString());
                Toast.makeText(Login_Phone.this, edtPhone.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_LoginPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTP(edtOTP.getText().toString());

            }
        });
    }

    private void getOTP(String phoneNumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84"+phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallblacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private  void  verifyOTP(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);

        signInWithPhoneAuthCredential(credential);
    }

    private  void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login_Phone.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                        }else {
                            Log.w(TAG,"signInWithCredential: failire",task.getException());
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){

                            }
                        }
                    }
        });
    }
}