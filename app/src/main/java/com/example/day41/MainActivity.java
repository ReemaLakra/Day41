package com.example.day41;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText ename, eloc,enamer;
    TextView tv,tv2;
    String sname,sloc,snamer;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ename=findViewById(R.id.Name);
        eloc=findViewById(R.id.Location);
        enamer=findViewById(R.id.RName);
        tv=findViewById(R.id.textView);
        tv2=findViewById(R.id.textView2);
         db = FirebaseFirestore.getInstance();
    }

    public void GBtn(View view)
    {
        sname=ename.getText().toString();
        sloc=eloc.getText().toString();
        tv.setText(sname+" "+sloc);
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", sname);
        user.put("location", sloc);



// Add a new document with a generated ID

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("XYZTAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("XYZTAG", "Error adding document", e);
                    }
                });
    }

    public void rtv(View view)
    {
        snamer=enamer.getText().toString();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String res="";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("RTAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("RTAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}