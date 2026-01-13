package com.example.studentmanagementapptemplate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    Context context;
    ArrayList<Student> studentList;
    androidx.activity.result.ActivityResultLauncher<Intent> launcher;

    public StudentAdapter(Context context, ArrayList<Student> studentList,
                          androidx.activity.result.ActivityResultLauncher<Intent> launcher) {
        this.context = context;
        this.studentList = studentList;
        this.launcher = launcher;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.activity_item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student s = studentList.get(position);

        holder.tvName.setText(    "Name    : " + s.name);
        holder.tvIndex.setText(   "Index      : " + s.index);
        holder.tvGrade.setText(   "Grade     : " + s.grade);
        holder.tvContact.setText( "Contact  : " + s.contact);
        holder.tvGuardian.setText("Guardian : " + s.guardian);
        holder.tvAddress.setText( "Address  : " + s.address);
        holder.tvGender.setText(  "Gender   : " + s.gender);

        // Edit button
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, Add_Student.class);
            intent.putExtra("id", s.id);
            intent.putExtra("index", s.index);
            intent.putExtra("name", s.name);
            intent.putExtra("address", s.address);
            intent.putExtra("grade", s.grade);
            intent.putExtra("contact", s.contact);
            intent.putExtra("guardian", s.guardian);
            intent.putExtra("gender", s.gender);
            launcher.launch(intent); // launch Add_Student
        });

        // Delete button
        holder.btnDelete.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(context)
                    .setTitle("Delete Student")
                    .setMessage("Are you sure you want to delete this student?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        StudentDBHelper dbHelper = new StudentDBHelper(context);
                        boolean deleted = dbHelper.deleteStudent(s.id);
                        if (deleted) {
                            studentList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, studentList.size());
                            Toast.makeText(context, "Student deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvIndex, tvGrade, tvContact, tvGuardian, tvAddress, tvGender;
        Button btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvStudentName);
            tvIndex = itemView.findViewById(R.id.tvStudentIndex);
            tvGrade = itemView.findViewById(R.id.tvStudentGrade);
            tvContact = itemView.findViewById(R.id.tvStudentContact);
            tvGuardian = itemView.findViewById(R.id.tvStudentGuardian);
            tvAddress = itemView.findViewById(R.id.tvStudentAddress);
            tvGender = itemView.findViewById(R.id.tvStudentGender);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
