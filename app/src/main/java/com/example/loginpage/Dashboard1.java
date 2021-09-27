package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard1 extends AppCompatActivity {
    Button log_out;
    FloatingActionButton addVideo;
    private FirebaseAuth mAuth;
    RecyclerView recview;
    DatabaseReference likereference;
    Boolean testclick=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        log_out=findViewById(R.id.log_out);
        addVideo=findViewById(R.id.floatingActionButton);
        recview=findViewById(R.id.RecyclerView);
        recview.setLayoutManager(new LinearLayoutManager(this));
        likereference=FirebaseDatabase.getInstance().getReference("likes");

        FirebaseRecyclerOptions<File_Video> options =
                new FirebaseRecyclerOptions.Builder<File_Video>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("MyVideos"), File_Video.class)
                        .build();

        FirebaseRecyclerAdapter<File_Video,myviewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<File_Video, myviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull File_Video model) {
                holder.prepareexoplayer(getApplication(),model.getTitle(),model.getUrl());

                FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                final String userid=firebaseUser.getUid();
                final String postkey=getRef(position).getKey();

                holder.getlikebuttonstatus(postkey,userid);

                holder.like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        testclick=true;

                        likereference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(testclick==true)
                                {
                                    if(snapshot.child(postkey).hasChild(userid))
                                    {
                                        likereference.child(postkey).child(userid).removeValue();
                                        testclick=false;
                                    }
                                    else
                                    {
                                        likereference.child(postkey).child(userid).setValue(true);
                                        testclick=false;
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                });

            }

            @NonNull
            @Override
            public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singelrow,parent,false);
                return new myviewholder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recview.setAdapter(firebaseRecyclerAdapter);

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseAuth.getInstance().signOut();
                Intent sign_out=new Intent(getApplicationContext(),Sign_in_Page1.class);
                startActivity(sign_out);

            }
        });


        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addVideo=new Intent(getApplicationContext(),AddVideo.class);
                startActivity(addVideo);
            }
        });
    }
}