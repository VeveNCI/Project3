package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Showdata extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Button buttonUpdate, buttonDelete;
    Module module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Books");
        listView = (ListView)findViewById(R.id.txtListView);
        buttonUpdate = (Button)findViewById(R.id.buttonUpdate);
        buttonDelete = (Button)findViewById(R.id.buttonDelete);
        module = ((Module)getApplicationContext());
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Books book = dataSnapshot.getValue(Books.class);
                //Toast.makeText(Showdata.this, dataSnapshot.getKey(), Toast.LENGTH_LONG).show();
                Toast.makeText(Showdata.this, book.getBookID(), Toast.LENGTH_LONG).show();
                //System.out.println("key: " + dataSnapshot.getKey());
                //System.out.println("ID: " + book.getBookID());
                //System.out.println("Title: " + book.getBookTitle());
                String txt = book.getBookID() + " . " + book.getBookTitle() + " - " + book.getBookAuthor() + " - " + book.getBookPages();
                arrayList.add(txt);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                module.setGvalue_id(arrayList.get(i));
                module.setGvalue_title(arrayList.get(i));
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(module.getGvalue_id().equals("")) {
                    Toast.makeText(Showdata.this, "Please select book before!", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), Update.class);
                    startActivity(intent);
                }
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String str = module.getGvalue_id().substring(0,7);
                if (str == "") {
                    Toast.makeText(Showdata.this, "Please select book before!", Toast.LENGTH_LONG).show();
                }else {
                    databaseReference.child("Books").child(str).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            databaseReference.child(str).removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(Showdata.this, "Book removed!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Showdata.class);
                    startActivity(intent);
                }
            }
        });
    }
}
