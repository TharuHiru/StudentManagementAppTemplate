package com.example.studentmanagementapptemplate;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class view_student extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentAdapter adapter;
    StudentDBHelper dbHelper;
    ArrayList<Student> studentList;

    private androidx.activity.result.ActivityResultLauncher<Intent> addEditLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_student);

        recyclerView = findViewById(R.id.recyclerStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new StudentDBHelper(this);

        // Setup ActivityResultLauncher
        addEditLauncher = registerForActivityResult(
                new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Refresh the list after returning from Add_Student
                    loadStudents();
                }
        );

        // load the students
        loadStudents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadStudents() {
        studentList = dbHelper.getAllStudents();
        adapter = new StudentAdapter(this, studentList, addEditLauncher); // pass launcher for edit
        recyclerView.setAdapter(adapter);
    }
}

