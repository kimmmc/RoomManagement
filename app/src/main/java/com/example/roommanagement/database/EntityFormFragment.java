package com.example.roommanagement.database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.roommanagement.DbActivity;
import com.example.roommanagement.R;
import java.util.List;
import java.util.ArrayList;
import com.example.roommanagement.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntityFormFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    private static final String ARG_ID = "id";
    private String type;
    private int recordId = -1;
    private DatabaseHelper dbHelper; // Kept for safety if invoked elsewhere, but unused logic main

    private EditText etName, etDescription, etExtra1, etExtra2;
    private Spinner spinnerRelation;
    private RadioGroup rgGender;
    private Button btnSave;

    // Cache lists for spinner selection
    private List<DBRoom> roomList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();

    public static EntityFormFragment newInstance(String type) {
        return newInstance(type, -1);
    }

    public static EntityFormFragment newInstance(String type, int id) {
        EntityFormFragment fragment = new EntityFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_TYPE);
            recordId = getArguments().getInt(ARG_ID, -1);
        }
        dbHelper = new DatabaseHelper(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.etName);
        etDescription = view.findViewById(R.id.etDescription);
        etExtra1 = view.findViewById(R.id.etExtra1);
        etExtra2 = view.findViewById(R.id.etExtra2);
        spinnerRelation = view.findViewById(R.id.spinnerRelation);
        rgGender = view.findViewById(R.id.rgGender);
        btnSave = view.findViewById(R.id.btnSave);

        setupUI();
        if (recordId != -1) {
            loadRecordData();
            btnSave.setText("Update");
        }

        btnSave.setOnClickListener(v -> saveEntity());
    }

    private void setupUI() {
        if (DbActivity.TYPE_ROOM.equals(type)) {
            etName.setHint("Room Name");
            etDescription.setHint("Category");
            etExtra1.setHint("Capacity (Number)");
            etExtra2.setHint("Price");
            spinnerRelation.setVisibility(View.GONE);
            rgGender.setVisibility(View.GONE);
        } else if (DbActivity.TYPE_COURSE.equals(type)) {
            etName.setHint("Course Title");
            etDescription.setHint("Description");
            etExtra1.setHint("Start Date (DD/MM/YYYY)");
            etExtra2.setVisibility(View.GONE);
            spinnerRelation.setVisibility(View.VISIBLE);
            rgGender.setVisibility(View.GONE);

            ApiClient.getService().getRooms().enqueue(new Callback<List<DBRoom>>() {
                @Override
                public void onResponse(Call<List<DBRoom>> call, Response<List<DBRoom>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        roomList = response.body();
                        // DBRoom probably doesn't have a good toString, so let's map to names
                        List<String> roomNames = new ArrayList<>();
                        for (DBRoom r : roomList)
                            roomNames.add(r.getName());

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_spinner_dropdown_item, roomNames);
                        spinnerRelation.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<DBRoom>> call, Throwable t) {
                }
            });

        } else if (DbActivity.TYPE_STUDENT.equals(type)) {
            etName.setHint("Student Name");
            etDescription.setHint("Email");
            etExtra1.setVisibility(View.GONE);
            etExtra2.setVisibility(View.GONE);
            spinnerRelation.setVisibility(View.VISIBLE);
            rgGender.setVisibility(View.VISIBLE);

            ApiClient.getService().getCourses().enqueue(new Callback<List<Course>>() {
                @Override
                public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        courseList = response.body();
                        List<String> courseTitles = new ArrayList<>();
                        for (Course c : courseList)
                            courseTitles.add(c.getTitle());

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_spinner_dropdown_item, courseTitles);
                        spinnerRelation.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Course>> call, Throwable t) {
                }
            });
        }
    }

    private void loadRecordData() {
        if (DbActivity.TYPE_ROOM.equals(type)) {
            ApiClient.getService().getRoom(recordId).enqueue(new Callback<DBRoom>() {
                @Override
                public void onResponse(Call<DBRoom> call, Response<DBRoom> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        DBRoom room = response.body();
                        etName.setText(room.getName());
                        etDescription.setText(room.getCategory());
                        etExtra1.setText(String.valueOf(room.getCapacity()));
                        etExtra2.setText(room.getPrice());
                    }
                }

                @Override
                public void onFailure(Call<DBRoom> call, Throwable t) {
                }
            });
        } else if (DbActivity.TYPE_COURSE.equals(type)) {
            ApiClient.getService().getCourse(recordId).enqueue(new Callback<Course>() {
                @Override
                public void onResponse(Call<Course> call, Response<Course> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Course course = response.body();
                        etName.setText(course.getTitle());
                        etDescription.setText(course.getDescription());
                        etExtra1.setText(course.getStartDate());
                    }
                }

                @Override
                public void onFailure(Call<Course> call, Throwable t) {
                }
            });
        } else if (DbActivity.TYPE_STUDENT.equals(type)) {
            ApiClient.getService().getStudent(recordId).enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Student student = response.body();
                        etName.setText(student.getName());
                        etDescription.setText(student.getEmail());
                        if ("Female".equalsIgnoreCase(student.getGender())) {
                            rgGender.check(R.id.rbFemale);
                        } else {
                            rgGender.check(R.id.rbMale);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                }
            });
        }
    }

    private void saveEntity() {
        String p1 = etName.getText().toString();
        String p2 = etDescription.getText().toString();
        String p3 = etExtra1.getText().toString();
        String p4 = etExtra2.getText().toString();

        if (p1.isEmpty()) {
            Toast.makeText(getContext(), "Name/Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (DbActivity.TYPE_ROOM.equals(type)) {
            int capacity = 0;
            try {
                capacity = Integer.parseInt(p3);
            } catch (NumberFormatException e) {
            }
            DBRoom room = new DBRoom(p1, p2, capacity, p4, "");

            if (recordId != -1) {
                room.setId(recordId);
                ApiClient.getService().updateRoom(recordId, room).enqueue(new Callback<DBRoom>() {
                    @Override
                    public void onResponse(Call<DBRoom> c, Response<DBRoom> r) {
                        getParentFragmentManager().popBackStack();
                    }

                    @Override
                    public void onFailure(Call<DBRoom> c, Throwable t) {
                        getParentFragmentManager().popBackStack();
                    }
                });
            } else {
                ApiClient.getService().addRoom(room).enqueue(new Callback<DBRoom>() {
                    @Override
                    public void onResponse(Call<DBRoom> c, Response<DBRoom> r) {
                        getParentFragmentManager().popBackStack();
                    }

                    @Override
                    public void onFailure(Call<DBRoom> c, Throwable t) {
                        getParentFragmentManager().popBackStack();
                    }
                });
            }
        } else if (DbActivity.TYPE_COURSE.equals(type)) {
            int roomId = -1;
            int pos = spinnerRelation.getSelectedItemPosition();
            if (pos >= 0 && pos < roomList.size()) {
                roomId = roomList.get(pos).getId();
            }

            Course course = new Course(p1, p2, p3, roomId);

            if (recordId != -1) {
                course.setId(recordId);
                ApiClient.getService().updateCourse(recordId, course).enqueue(new Callback<Course>() {
                    @Override
                    public void onResponse(Call<Course> c, Response<Course> r) {
                        getParentFragmentManager().popBackStack();
                    }

                    @Override
                    public void onFailure(Call<Course> c, Throwable t) {
                        getParentFragmentManager().popBackStack();
                    }
                });
            } else {
                ApiClient.getService().addCourse(course).enqueue(new Callback<Course>() {
                    @Override
                    public void onResponse(Call<Course> c, Response<Course> r) {
                        getParentFragmentManager().popBackStack();
                    }

                    @Override
                    public void onFailure(Call<Course> c, Throwable t) {
                        getParentFragmentManager().popBackStack();
                    }
                });
            }
        } else if (DbActivity.TYPE_STUDENT.equals(type)) {
            String gender = "Male";
            if (rgGender.getCheckedRadioButtonId() == R.id.rbFemale)
                gender = "Female";

            int courseId = -1;
            int pos = spinnerRelation.getSelectedItemPosition();
            if (pos >= 0 && pos < courseList.size()) {
                courseId = courseList.get(pos).getId();
            }

            Student student = new Student(p1, p2, gender, courseId, "");
            if (recordId != -1) {
                student.setId(recordId);
                ApiClient.getService().updateStudent(recordId, student).enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> c, Response<Student> r) {
                        getParentFragmentManager().popBackStack();
                    }

                    @Override
                    public void onFailure(Call<Student> c, Throwable t) {
                        getParentFragmentManager().popBackStack();
                    }
                });
            } else {
                ApiClient.getService().addStudent(student).enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> c, Response<Student> r) {
                        getParentFragmentManager().popBackStack();
                    }

                    @Override
                    public void onFailure(Call<Student> c, Throwable t) {
                        getParentFragmentManager().popBackStack();
                    }
                });
            }
        }
    }
}
