package com.example.gezginapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText register_eposta,register_sifre,register_sifre_onay;
    Button kayitOl_button;
    String email,password,password_onay;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        register_eposta = findViewById(R.id.user_email_register_et);
        register_sifre = findViewById(R.id.user_password_register_et);
        register_sifre_onay =findViewById(R.id.user_confirm_email_register_tv);
        kayitOl_button = findViewById(R.id.button_register);

        kayitOl_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfoAndRegister();
            }
        });
    }

    private void getUserInfoAndRegister(){
        email = register_eposta.getText().toString();
        password = register_sifre.getText().toString();
        password_onay = register_sifre_onay.getText().toString();

        // isEmpty -> boş mu diye kontrol ediyor.
        if(!email.isEmpty() && !password.isEmpty() && !password_onay.isEmpty()){
            // equals -> string metinleri karşılaştırma işlemini yapıyor
            if(password.equals(password_onay)){
                register();
            }else{
                // getApplicationContext -> activity context'i belirtiyor
                Toast.makeText(getApplicationContext(),"Parola uyuşmuyor",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Lütfen tüm alanları doldurunuz.",Toast.LENGTH_LONG).show();
        }

    }

    private void register(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"Kayıt Başarılı"+user.getEmail(),Toast.LENGTH_LONG).show();

                            Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(loginIntent);
                        } else {

                        }


                    }
                })
                .addOnFailureListener(RegisterActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception errorcode) {
                        if (errorcode instanceof FirebaseAuthException) {
                            if(((FirebaseAuthException) errorcode).getErrorCode().equals("ERROR_WEAK_PASSWORD")){
                                Toast.makeText(getApplicationContext(), "Eksik Şifre", Toast.LENGTH_SHORT).show();

                            } else if(((FirebaseAuthException) errorcode).getErrorCode().equals("ERROR_INVALID_EMAIL")){
                                Toast.makeText(getApplicationContext(), "Geçersiz mail", Toast.LENGTH_SHORT).show();

                            } else if(((FirebaseAuthException) errorcode).getErrorCode().equals("ERROR_EMAIL_ALREADY_IN_USE")){
                                Toast.makeText(getApplicationContext(), "Mail zaten kayıtlı", Toast.LENGTH_SHORT).show();

                            }
                            //your other logic goes here
                        }
                    }
                });
    }
}
