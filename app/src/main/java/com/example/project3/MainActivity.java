package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText txtID, txtTitle, txtAuthor, txtPages;
    Button buttonSave, buttonShow;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtID = (EditText)findViewById(R.id.txtID);
        txtTitle = (EditText)findViewById(R.id.txtTitle);
        txtAuthor = (EditText)findViewById(R.id.txtAuthor);
        txtPages = (EditText)findViewById(R.id.txtPages);
        buttonSave = (Button)findViewById(R.id.buttonSave);
        buttonShow = (Button)findViewById(R.id.buttonShow);

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArrayList();
            }
        });

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtClear();
                Intent intent = new Intent(getApplicationContext(), Showdata.class);
                startActivity(intent);
            }
        });
    }

    private void addArrayList() {
        String ID = txtID.getText().toString().trim();
        String Title = txtTitle.getText().toString().trim();
        String Author = txtAuthor.getText().toString().trim();
        String Pages = txtPages.getText().toString().trim();

        if (TextUtils.isEmpty(ID)) {
            Toast.makeText(this, "Please enter ID!", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(Title)) {
            Toast.makeText(this, "Please enter title!", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(Author)) {
            Toast.makeText(this, "Please enter author!", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(Pages)) {
            Toast.makeText(this, "Please enter pages!", Toast.LENGTH_LONG).show();
        }else{
            //String id = databaseReference.push().getKey();
            Books book = new Books(ID, Title, Author, Pages);
            //databaseReference.child(ID).child("id").setValue(ID.toString());
            //databaseReference.child(ID).child("title").setValue(Title.toString());
            //databaseReference.child(ID).child("author").setValue(Author.toString());
            //databaseReference.child(ID).child("pages").setValue(Pages.toString());
            databaseReference.child(ID).setValue(book);
            Toast.makeText(this,"New book added!",Toast.LENGTH_LONG).show();
            txtClear();
        }
    }

    private void txtClear(){
        txtID.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        txtPages.setText("");
    }
}