package com.example.roommanagement.network;

import com.example.roommanagement.database.Course;
import com.example.roommanagement.database.DBRoom;
import com.example.roommanagement.database.Student;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // Rooms
    @GET("api/rooms")
    Call<List<DBRoom>> getRooms();

    @GET("api/rooms/{id}")
    Call<DBRoom> getRoom(@Path("id") int id);

    @POST("api/rooms")
    Call<DBRoom> addRoom(@Body DBRoom room);

    @PUT("api/rooms/{id}")
    Call<DBRoom> updateRoom(@Path("id") int id, @Body DBRoom room);

    @DELETE("api/rooms/{id}")
    Call<Void> deleteRoom(@Path("id") int id);

    // Courses
    @GET("api/courses")
    Call<List<Course>> getCourses();

    @GET("api/courses/{id}")
    Call<Course> getCourse(@Path("id") int id);

    @POST("api/courses")
    Call<Course> addCourse(@Body Course course);

    @PUT("api/courses/{id}")
    Call<Course> updateCourse(@Path("id") int id, @Body Course course);

    @DELETE("api/courses/{id}")
    Call<Void> deleteCourse(@Path("id") int id);

    // Students
    @GET("api/students")
    Call<List<Student>> getStudents();

    @GET("api/students/{id}")
    Call<Student> getStudent(@Path("id") int id);

    @POST("api/students")
    Call<Student> addStudent(@Body Student student);

    @PUT("api/students/{id}")
    Call<Student> updateStudent(@Path("id") int id, @Body Student student);

    @DELETE("api/students/{id}")
    Call<Void> deleteStudent(@Path("id") int id);
}
