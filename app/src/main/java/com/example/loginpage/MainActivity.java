package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText e,p;
    Button sign_up;
    ProgressBar P_bar;
    TextView already;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e=findViewById(R.id.email);
        p=findViewById(R.id.password);
        P_bar=findViewById(R.id.progressBar);

        already=findViewById(R.id.alreadyR);
        sign_up=findViewById(R.id.Register);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(MainActivity.this, Dashboard1.class);
            startActivity(intent);
            finish();
        }

        mAuth = FirebaseAuth.getInstance();
            sign_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    P_bar.setVisibility(View.VISIBLE);
                    String email=e.getText().toString();
                    String password=p.getText().toString();
                    if (email.isEmpty() || password.isEmpty()){
                        if (email.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Please Enter Your Email",Toast.LENGTH_LONG).show();
                        }
                        if (password.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Please Enter Your Password",Toast.LENGTH_LONG).show();
                        }
                        P_bar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            e.getText().clear();
                                            p.getText().clear();
                                            P_bar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getApplicationContext(), "Sucessfully Registered\nNow Click Already Registered", Toast.LENGTH_LONG).show();


                                        } else {
                                            // If sign in fails, display a message to the user.
                                            e.getText().clear();
                                            p.getText().clear();
                                            P_bar.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_LONG).show();


                                        }
                                    }
                                });
                    }
                }
            });
            already.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent login=new Intent(getApplicationContext(),Sign_in_Page1.class);
                    startActivity(login);
                }
            });


    }


}