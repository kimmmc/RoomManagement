package com.example.roommanagement.database;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.roommanagement.DbActivity;
import com.example.roommanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import com.example.roommanagement.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntityListFragment extends Fragment {

    private static final String ARG_TYPE = "type";
    private String type;
    private DatabaseHelper dbHelper;
    private GenericAdapter adapter;

    public static EntityListFragment newInstance(String type) {
        EntityListFragment fragment = new EntityListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_TYPE);
        }
        dbHelper = new DatabaseHelper(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new GenericAdapter(new ArrayList<>(), new GenericAdapter.OnActionClickListener() {
            @Override
            public void onEdit(Object item) {
                int id = -1;
                if (item instanceof DBRoom)
                    id = ((DBRoom) item).getId();
                else if (item instanceof Course)
                    id = ((Course) item).getId();
                else if (item instanceof Student)
                    id = ((Student) item).getId();

                if (getActivity() instanceof DbActivity && id != -1) {
                    ((DbActivity) getActivity()).loadFormFragment(type, id);
                }
            }

            @Override
            public void onDelete(Object item) {
                if (item instanceof DBRoom) {
                    ApiClient.getService().deleteRoom(((DBRoom) item).getId()).enqueue(new retrofit2.Callback<Void>() {
                        @Override
                        public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> r) {
                            loadData();
                        }

                        @Override
                        public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                        }
                    });
                } else if (item instanceof Course) {
                    ApiClient.getService().deleteCourse(((Course) item).getId())
                            .enqueue(new retrofit2.Callback<Void>() {
                                @Override
                                public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> r) {
                                    loadData();
                                }

                                @Override
                                public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                                }
                            });
                } else if (item instanceof Student) {
                    ApiClient.getService().deleteStudent(((Student) item).getId())
                            .enqueue(new retrofit2.Callback<Void>() {
                                @Override
                                public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> r) {
                                    loadData();
                                }

                                @Override
                                public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                                }
                            });
                }
            }
        });
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            if (getActivity() instanceof DbActivity) {
                ((DbActivity) getActivity()).loadFormFragment(type);
            }
        });

        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        // Display loading indicator if possible (omitted for brevity)
        if (DbActivity.TYPE_ROOM.equals(type)) {
            ApiClient.getService().getRooms().enqueue(new retrofit2.Callback<List<DBRoom>>() {
                @Override
                public void onResponse(retrofit2.Call<List<DBRoom>> call, retrofit2.Response<List<DBRoom>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Object> items = new ArrayList<>(response.body());
                        adapter.updateData(items);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<List<DBRoom>> call, Throwable t) {
                    // Handle error (Toast)
                }
            });
        } else if (DbActivity.TYPE_COURSE.equals(type)) {
            ApiClient.getService().getCourses().enqueue(new retrofit2.Callback<List<Course>>() {
                @Override
                public void onResponse(retrofit2.Call<List<Course>> call, retrofit2.Response<List<Course>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Object> items = new ArrayList<>(response.body());
                        adapter.updateData(items);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<List<Course>> call, Throwable t) {
                }
            });
        } else if (DbActivity.TYPE_STUDENT.equals(type)) {
            ApiClient.getService().getStudents().enqueue(new retrofit2.Callback<List<Student>>() {
                @Override
                public void onResponse(retrofit2.Call<List<Student>> call, retrofit2.Response<List<Student>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Object> items = new ArrayList<>(response.body());
                        adapter.updateData(items);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<List<Student>> call, Throwable t) {
                }
            });
        }
    }
}
