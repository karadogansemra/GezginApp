package com.example.gezginapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText login_eposta,login_sifre ;
    Button login_button, register_button;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        // Edittext ve buton id'leri çağrıldı.
        login_eposta = findViewById(R.id.login_eposta);
        login_sifre = findViewById(R.id.login_sifre);
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // trim()-> edittexteki boşluk karakterlerini siler.
                String eposta = login_eposta.getText().toString().trim();
                String password = login_sifre.getText().toString().trim();
                login(eposta,password);
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });


    }

    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Hata Oluştu!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void goToRegister(){
        Intent gotoRegisterPage = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(gotoRegisterPage);
    }
}
