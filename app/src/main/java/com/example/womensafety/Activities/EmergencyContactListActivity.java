package com.example.womensafety.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.womensafety.Adapters.EmergencyContactsAdapter;
import com.example.womensafety.R;
import com.example.womensafety.SuperAdmin.Models.EmergencyContacts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmergencyContactListActivity extends AppCompatActivity {

    RecyclerView contact_list;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    EmergencyContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact_list);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Default_Emergency_Contacts");

        contact_list=(RecyclerView) findViewById(R.id.emergency_contact_list);
        contact_list.hasFixedSize();
        contact_list.setLayoutManager(new LinearLayoutManager(this));

        final ArrayList<EmergencyContacts>contactsArrayList=new ArrayList<EmergencyContacts>();

        adapter=new EmergencyContactsAdapter(EmergencyContactListActivity.this,contactsArrayList);

        contact_list.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for(DataSnapshot conSnap : snapshot.getChildren())
                 {
                     EmergencyContacts contacts=conSnap.getValue(EmergencyContacts.class);
                     contactsArrayList.add(contacts);
                 }

                 adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}