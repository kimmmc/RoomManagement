package com.example.roommanagement;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.roommanagement.database.EntityListFragment;

public class DbActivity extends AppCompatActivity {

    public static final String TYPE_ROOM = "ROOM";
    public static final String TYPE_COURSE = "COURSE";
    public static final String TYPE_STUDENT = "STUDENT";

    private String currentType = TYPE_ROOM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        Button btnRooms = findViewById(R.id.navRooms);
        Button btnCourses = findViewById(R.id.navCourses);
        Button btnStudents = findViewById(R.id.navStudents);

        btnRooms.setOnClickListener(v -> loadListFragment(TYPE_ROOM));
        btnCourses.setOnClickListener(v -> loadListFragment(TYPE_COURSE));
        btnStudents.setOnClickListener(v -> loadListFragment(TYPE_STUDENT));

        // Load default
        loadListFragment(TYPE_ROOM);
    }

    public void loadListFragment(String type) {
        currentType = type;
        EntityListFragment fragment = EntityListFragment.newInstance(type);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    public void loadFormFragment(String type) {
        loadFormFragment(type, -1);
    }

    public void loadFormFragment(String type, int id) {
        com.example.roommanagement.database.EntityFormFragment fragment = com.example.roommanagement.database.EntityFormFragment
                .newInstance(type, id);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}
