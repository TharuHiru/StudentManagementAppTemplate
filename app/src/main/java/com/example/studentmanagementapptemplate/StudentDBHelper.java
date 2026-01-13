package com.example.studentmanagementapptemplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.Cursor;
import java.util.ArrayList;
public class StudentDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "student_db";
    private static final int DATABASE_VERSION = 1;

    public StudentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "student_index TEXT," +
                "name TEXT," +
                "address TEXT," +
                "grade TEXT," +
                "contact TEXT," +
                "guardian TEXT," +
                "gender TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS students");
        onCreate(db);
    }

    // ---------------- ADD STUDENT METHOD ----------------
    public long addStudent(String index, String name, String address, String grade, String contact, String guardian, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("student_index", index);
        cv.put("name", name);
        cv.put("address", address);
        cv.put("grade", grade);
        cv.put("contact", contact);
        cv.put("guardian", guardian);
        cv.put("gender", gender);
        ;

        long result = db.insert("students", null, cv); // returns row id if success, -1 if failed
        db.close();
        return result;
    }

    // ---------------- GET ALL STUDENTS ----------------
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM students", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Student(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("student_index")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("grade")),
                        cursor.getString(cursor.getColumnIndexOrThrow("contact")),
                        cursor.getString(cursor.getColumnIndexOrThrow("address")),
                        cursor.getString(cursor.getColumnIndexOrThrow("guardian")),
                        cursor.getString(cursor.getColumnIndexOrThrow("gender"))
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    // ---------------- DELETE STUDENT ----------------
    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("students", "id=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0; // true if deleted
    }

    // ---------------- UPDATE STUDENT ----------------
    public boolean updateStudent(int id, String name, String address,
                                 String grade, String contact, String guardian, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("address", address);
        cv.put("grade", grade);
        cv.put("contact", contact);
        cv.put("guardian", guardian);
        cv.put("gender", gender);

        int result = db.update("students", cv, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

}

