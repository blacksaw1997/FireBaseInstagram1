package com.example.erdo.firebaseinstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedActivity extends AppCompatActivity {
static ArrayList<String> useremailsFromFB;
    ArrayList<String> userimagesFromFB;
    ArrayList<String> usercommentsFromFB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    PostClass adapter;
    ListView listView;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_post,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.add_post){
            Intent intent =new Intent(getApplicationContext(),UploadActivity.class);
            startActivity(intent);

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        useremailsFromFB=new ArrayList<String>();
        userimagesFromFB=new ArrayList<>();
        usercommentsFromFB=new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();

        adapter=new PostClass(useremailsFromFB,userimagesFromFB,usercommentsFromFB,this);
        listView =(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        getDataFromFirebase();


    }
    protected void getDataFromFirebase(){

        DatabaseReference newReference=firebaseDatabase.getReference("Posts");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.println("children "+dataSnapshot.getChildren());
                //System.out.println("key"+dataSnapshot.getKey());
                //System.out.println("value"+dataSnapshot.getValue());
                for(DataSnapshot ds : dataSnapshot.getChildren() ){
                    HashMap<String , String> hashMap= (HashMap<String, String>) ds.getValue();
                    useremailsFromFB.add(hashMap.get("useremail"));
                    userimagesFromFB.add(hashMap.get("downloadurl"));
                    usercommentsFromFB.add(hashMap.get("comment"));
                    adapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }



}
