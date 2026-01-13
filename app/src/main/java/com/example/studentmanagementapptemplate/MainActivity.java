package com.example.studentmanagementapptemplate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnAddStudent;
    Button btnViewStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Button reference
        btnAddStudent = findViewById(R.id.buttonAddStudent);
        btnViewStudent = findViewById(R.id.buttonViewStudents);

        // Button click → open Add Student page
        btnAddStudent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Add_Student.class);
            startActivity(intent);
        });

        // Button click → open Add Student page
        btnViewStudent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, view_student.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
