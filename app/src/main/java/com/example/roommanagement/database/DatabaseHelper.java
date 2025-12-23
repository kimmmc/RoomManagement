package com.example.roommanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RoomManagement.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_ROOM = "rooms";
    private static final String TABLE_COURSE = "courses";
    private static final String TABLE_STUDENT = "students";

    // Common Column Names (ID)
    private static final String COL_ID = "id";

    // Room Columns
    private static final String COL_ROOM_NAME = "name";
    private static final String COL_ROOM_CATEGORY = "category";
    private static final String COL_ROOM_CAPACITY = "capacity";
    private static final String COL_ROOM_PRICE = "price";
    private static final String COL_ROOM_IMAGE = "image_uri";

    // Course Columns
    private static final String COL_COURSE_TITLE = "title";
    private static final String COL_COURSE_DESC = "description";
    private static final String COL_COURSE_DATE = "start_date";
    private static final String COL_COURSE_ROOM_ID = "room_id";

    // Student Columns
    private static final String COL_STUDENT_NAME = "name";
    private static final String COL_STUDENT_EMAIL = "email";
    private static final String COL_STUDENT_GENDER = "gender";
    private static final String COL_STUDENT_COURSE_ID = "course_id";
    private static final String COL_STUDENT_PIC = "profile_pic";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRoomTable = "CREATE TABLE " + TABLE_ROOM + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ROOM_NAME + " TEXT, " +
                COL_ROOM_CATEGORY + " TEXT, " +
                COL_ROOM_CAPACITY + " INTEGER, " +
                COL_ROOM_PRICE + " TEXT, " +
                COL_ROOM_IMAGE + " TEXT)";

        String createCourseTable = "CREATE TABLE " + TABLE_COURSE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_COURSE_TITLE + " TEXT, " +
                COL_COURSE_DESC + " TEXT, " +
                COL_COURSE_DATE + " TEXT, " +
                COL_COURSE_ROOM_ID + " INTEGER, " +
                "FOREIGN KEY(" + COL_COURSE_ROOM_ID + ") REFERENCES " + TABLE_ROOM + "(" + COL_ID + "))";

        String createStudentTable = "CREATE TABLE " + TABLE_STUDENT + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_STUDENT_NAME + " TEXT, " +
                COL_STUDENT_EMAIL + " TEXT, " +
                COL_STUDENT_GENDER + " TEXT, " +
                COL_STUDENT_COURSE_ID + " INTEGER, " +
                COL_STUDENT_PIC + " TEXT, " +
                "FOREIGN KEY(" + COL_STUDENT_COURSE_ID + ") REFERENCES " + TABLE_COURSE + "(" + COL_ID + "))";

        db.execSQL(createRoomTable);
        db.execSQL(createCourseTable);
        db.execSQL(createStudentTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM);
        onCreate(db);
    }

    // --- CRUD Operations for Room ---
    public long addRoom(DBRoom room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ROOM_NAME, room.getName());
        values.put(COL_ROOM_CATEGORY, room.getCategory());
        values.put(COL_ROOM_CAPACITY, room.getCapacity());
        values.put(COL_ROOM_PRICE, room.getPrice());
        values.put(COL_ROOM_IMAGE, room.getImageUri());
        long id = db.insert(TABLE_ROOM, null, values);
        db.close();
        return id;
    }

    public List<DBRoom> getAllRooms() {
        List<DBRoom> roomList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ROOM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DBRoom room = new DBRoom();
                room.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                room.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_ROOM_NAME)));
                room.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COL_ROOM_CATEGORY)));
                room.setCapacity(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ROOM_CAPACITY)));
                room.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(COL_ROOM_PRICE)));
                room.setImageUri(cursor.getString(cursor.getColumnIndexOrThrow(COL_ROOM_IMAGE)));
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roomList;
    }

    public int updateRoom(DBRoom room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ROOM_NAME, room.getName());
        values.put(COL_ROOM_CATEGORY, room.getCategory());
        values.put(COL_ROOM_CAPACITY, room.getCapacity());
        values.put(COL_ROOM_PRICE, room.getPrice());
        values.put(COL_ROOM_IMAGE, room.getImageUri());
        return db.update(TABLE_ROOM, values, COL_ID + " = ?", new String[] { String.valueOf(room.getId()) });
    }

    public void deleteRoom(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ROOM, COL_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    // --- CRUD Operations for Course ---
    public long addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_COURSE_TITLE, course.getTitle());
        values.put(COL_COURSE_DESC, course.getDescription());
        values.put(COL_COURSE_DATE, course.getStartDate());
        values.put(COL_COURSE_ROOM_ID, course.getRoomId());
        long id = db.insert(TABLE_COURSE, null, values);
        db.close();
        return id;
    }

    public int updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_COURSE_TITLE, course.getTitle());
        values.put(COL_COURSE_DESC, course.getDescription());
        values.put(COL_COURSE_DATE, course.getStartDate());
        values.put(COL_COURSE_ROOM_ID, course.getRoomId());
        return db.update(TABLE_COURSE, values, COL_ID + " = ?", new String[] { String.valueOf(course.getId()) });
    }

    public void deleteCourse(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE, COL_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_COURSE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                course.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_COURSE_TITLE)));
                course.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_COURSE_DESC)));
                course.setStartDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_COURSE_DATE)));
                course.setRoomId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_COURSE_ROOM_ID)));
                courseList.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return courseList;
    }

    // --- CRUD Operations for Student ---
    public long addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STUDENT_NAME, student.getName());
        values.put(COL_STUDENT_EMAIL, student.getEmail());
        values.put(COL_STUDENT_GENDER, student.getGender());
        values.put(COL_STUDENT_COURSE_ID, student.getCourseId());
        values.put(COL_STUDENT_PIC, student.getProfilePicUri());
        long id = db.insert(TABLE_STUDENT, null, values);
        db.close();
        return id;
    }

    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STUDENT_NAME, student.getName());
        values.put(COL_STUDENT_EMAIL, student.getEmail());
        values.put(COL_STUDENT_GENDER, student.getGender());
        values.put(COL_STUDENT_COURSE_ID, student.getCourseId());
        values.put(COL_STUDENT_PIC, student.getProfilePicUri());
        return db.update(TABLE_STUDENT, values, COL_ID + " = ?", new String[] { String.valueOf(student.getId()) });
    }

    public void deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, COL_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                student.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_NAME)));
                student.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_EMAIL)));
                student.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_GENDER)));
                student.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_STUDENT_COURSE_ID)));
                student.setProfilePicUri(cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_PIC)));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    public DBRoom getRoom(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ROOM, null, COL_ID + "=?", new String[] { String.valueOf(id) }, null, null,
                null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        DBRoom room = new DBRoom();
        room.setId(Integer.parseInt(cursor.getString(0)));
        room.setName(cursor.getString(1));
        room.setCategory(cursor.getString(2));
        room.setCapacity(cursor.getInt(3));
        room.setPrice(cursor.getString(4));
        room.setImageUri(cursor.getString(5));

        cursor.close();
        return room;
    }

    public Course getCourse(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COURSE, null, COL_ID + "=?", new String[] { String.valueOf(id) }, null, null,
                null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        Course course = new Course();
        course.setId(Integer.parseInt(cursor.getString(0)));
        course.setTitle(cursor.getString(1));
        course.setDescription(cursor.getString(2));
        course.setStartDate(cursor.getString(3));
        course.setRoomId(cursor.getInt(4));

        cursor.close();
        return course;
    }

    public Student getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENT, null, COL_ID + "=?", new String[] { String.valueOf(id) }, null, null,
                null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return null;

        Student student = new Student();
        student.setId(Integer.parseInt(cursor.getString(0)));
        student.setName(cursor.getString(1));
        student.setEmail(cursor.getString(2));
        student.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_GENDER)));
        student.setCourseId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_STUDENT_COURSE_ID)));
        student.setProfilePicUri(cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_PIC)));

        cursor.close();
        return student;
    }
}
