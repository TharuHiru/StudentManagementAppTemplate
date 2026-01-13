package com.example.studentmanagementapptemplate;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;

public class Add_Student extends AppCompatActivity {

    EditText etIndex, etName, etAddress, etGrade, etContact, etGuardian;
    Button btnSaveStudent;
    Spinner spGender;

    int studentId = -1; // -1 means adding new student

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);

        // Apply edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        etIndex = findViewById(R.id.etIndex);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etGrade = findViewById(R.id.etGrade);
        etContact = findViewById(R.id.etContact);
        etGuardian = findViewById(R.id.etGuardian);
        btnSaveStudent = findViewById(R.id.btnSaveStudent);
        spGender = findViewById(R.id.spGender);

        // Setup gender spinner
        String[] genders = {"Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);

        // Get incoming intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            studentId = intent.getIntExtra("id", -1);

            // Pre-fill form
            etIndex.setText(intent.getStringExtra("index"));
            etName.setText(intent.getStringExtra("name"));
            etAddress.setText(intent.getStringExtra("address"));
            etGrade.setText(intent.getStringExtra("grade"));
            etContact.setText(intent.getStringExtra("contact"));
            etGuardian.setText(intent.getStringExtra("guardian"));

            // Pre-select gender
            String gender = intent.getStringExtra("gender");
            if (gender != null) {
                int spinnerPosition = genderAdapter.getPosition(gender);
                spGender.setSelection(spinnerPosition);
            }

            // Disable editing of index
            etIndex.setEnabled(false);
            etIndex.setTextColor(Color.GRAY);
        }

        // Save button logic
        btnSaveStudent.setOnClickListener(v -> {
            String index = etIndex.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String grade = etGrade.getText().toString().trim();
            String contact = etContact.getText().toString().trim();
            String guardian = etGuardian.getText().toString().trim();
            String gender = spGender.getSelectedItem().toString();

            // Validation
            if (index.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Index and Name are required", Toast.LENGTH_SHORT).show();
                return;
            }

            StudentDBHelper dbHelper = new StudentDBHelper(this);

            if (studentId == -1) {
                // Add new student
                long result = dbHelper.addStudent(index, name, address, grade, contact, guardian, gender);
                if (result != -1) {
                    Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK); // notify previous activity
                    finish();
                } else {
                    Toast.makeText(this, "Failed to add student", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Update existing student
                boolean updated = dbHelper.updateStudent(studentId, name, address, grade, contact, guardian, gender);
                if (updated) {
                    Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK); // notify previous activity
                    finish();
                } else {
                    Toast.makeText(this, "Failed to update student", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
