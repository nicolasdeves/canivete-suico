package com.nicolas.canivete_suico;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PicturesActivity extends AppCompatActivity {
    private DatabaseManager databaseManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        databaseManager = new DatabaseManager(this);
        List<Picture> contacts = databaseManager.getAllPictures();

        recyclerView = findViewById(R.id.pictures_recycler);
        PictureAdapter adapter = new PictureAdapter(contacts, databaseManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseManager = new DatabaseManager(this);
    }
}
}
