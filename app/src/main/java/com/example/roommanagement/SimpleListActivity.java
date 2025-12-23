package com.example.roommanagement;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class SimpleListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        ListView listView = findViewById(R.id.simpleListView);

        String[] dummyData = {
                "Room A", "Room B", "Room C", "Conference Room 1",
                "Conference Room 2", "Training Hall", "Lobby",
                "Cafeteria", "Meeting Room Small", "Meeting Room Large"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dummyData);

        listView.setAdapter(adapter);
    }
}
