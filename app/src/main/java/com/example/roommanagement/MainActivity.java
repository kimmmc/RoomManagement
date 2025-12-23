package com.example.roommanagement;

import com.example.roommanagement.room.Room;
import com.example.roommanagement.room.RoomAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle Insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup Spinner
        Spinner spinner = findViewById(R.id.spinnerCategory);
        String[] categories = { "Category", "Conference Room", "Training Room", "Meeting Room" };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                categories);
        spinner.setAdapter(spinnerAdapter);

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room("A.20 - Training Room Complete", "Conference Room", 20, "240 EUR / hour", true));
        roomList.add(new Room("A.20a - Training Room Part A", "Conference Room", 10, "N/A", false));
        roomList.add(new Room("A.20b - Room Part B", "Training Room", 10, "N/A", false));
        roomList.add(new Room("Waiting lounge", "Conference Room", 10, "N/A", false));

        RoomAdapter adapter = new RoomAdapter(roomList);
        recyclerView.setAdapter(adapter);

        // Setup Navigation to SimpleListActivity
        TextView btnOptions = findViewById(R.id.btnOptions);
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SimpleListActivity.class);
                startActivity(intent);
            }
        });

        // Setup Navigation to DbActivity
        android.widget.Button btnDb = findViewById(R.id.btnDb);
        btnDb.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DbActivity.class);
            startActivity(intent);
        });

        // Back button placeholder
        TextView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}