package com.example.project3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update extends AppCompatActivity {
    EditText tID, tTitle, tAuthor, tPages;
    Button buttonUpdate, buttonCancel;
    DatabaseReference databaseReference;
    Module module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        tID = (EditText)findViewById(R.id.tID);
        tTitle = (EditText)findViewById(R.id.tTitle);
        tAuthor = (EditText)findViewById(R.id.tAuthor);
        tPages = (EditText)findViewById(R.id.tPages);
        buttonUpdate = (Button)findViewById(R.id.buttonUpdate2);
        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        databaseReference = FirebaseDatabase.getInstance().getReference("Books");
        module = ((Module)getApplicationContext());
        final String str = module.getGvalue_id().substring(0,7);
        tID.setText(str);
        tID.setEnabled(false);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Books books = dataSnapshot.child(str).getValue(Books.class);
                tTitle.setText(books.getBookTitle());
                tAuthor.setText(books.getBookAuthor());
                tPages.setText(books.getBookPages());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateArrayList();
                Cleartxt();
                Intent intent = new Intent(getApplicationContext(),Showdata.class);
                startActivity(intent);
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cleartxt();
                Intent intent = new Intent(getApplicationContext(),Showdata.class);
                startActivity(intent);
            }
        });
    }

    private void Cleartxt() {
        tID.setText("");
        tTitle.setText("");
        tAuthor.setText("");
        tPages.setText("");
        tID.requestFocus();
    }

    private void updateArrayList() {
        final String ID = tID.getText().toString().trim();
        final String Title = tTitle.getText().toString().trim();
        final String Author = tAuthor.getText().toString().trim();
        final String Pages = tPages.getText().toString().trim();

        if (TextUtils.isEmpty(ID)) {
            tID.setError("Please enter ID!");
        }else if (TextUtils.isEmpty(Title)) {
            tTitle.setError("Please enter title!");
        }else if (TextUtils.isEmpty(Author)) {
            tAuthor.setError("Please enter author!");
        }else if (TextUtils.isEmpty(Pages)) {
            tPages.setError("Please enter pages!");
        }else {
            Books books = new Books(ID, Title, Author, Pages);
            databaseReference.child("Books").child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Books").child(ID).child("title").setValue(Title);
                    databaseReference.child("Books").child(ID).child("author").setValue(Author);
                    databaseReference.child("Books").child(ID).child("pages").setValue(Author);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Toast.makeText(this, "Book updated!", Toast.LENGTH_LONG).show();
            Cleartxt();
        }
    }
}
