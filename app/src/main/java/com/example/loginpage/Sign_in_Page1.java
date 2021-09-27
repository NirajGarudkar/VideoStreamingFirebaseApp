package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Sign_in_Page1 extends AppCompatActivity {
    EditText e,p;
    Button sign_in;
    ProgressBar P_bar2;
    TextView new_user;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        e=findViewById(R.id.email_in);
        p=findViewById(R.id.password_in);
        sign_in=findViewById(R.id.log_in);

        P_bar2=findViewById(R.id.progressBar_login);
        new_user=findViewById(R.id.New_user);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(Sign_in_Page1.this, Dashboard1.class);
            startActivity(intent);
            finish();
        }

        mAuth = FirebaseAuth.getInstance();

            new_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sign_up=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(sign_up);
                }
            });
            sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    P_bar2.setVisibility(View.VISIBLE);
                    String email=e.getText().toString();
                    String password=p.getText().toString();
                    if (email.isEmpty() || password.isEmpty()){
                        if (email.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Please Enter Your Email",Toast.LENGTH_LONG).show();
                        }
                        if (password.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Please Enter Your Password",Toast.LENGTH_LONG).show();
                        }
                        P_bar2.setVisibility(View.INVISIBLE);
                    }
                    else {
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(Sign_in_Page1.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            e.getText().clear();
                                            p.getText().clear();
                                            P_bar2.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getApplicationContext(), "Sucessfully Logged In", Toast.LENGTH_LONG).show();
                                            Intent dashboard = new Intent(getApplicationContext(), Dashboard1.class);
                                            startActivity(dashboard);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            e.getText().clear();
                                            p.getText().clear();
                                            P_bar2.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getApplicationContext(), "Invalid Email And Password", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                }
            });
    }
}