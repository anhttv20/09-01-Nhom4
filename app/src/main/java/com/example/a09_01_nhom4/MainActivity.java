package com.example.a09_01_nhom4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.a09_01_nhom4.model.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btAdd,btAll,btGet,btUpdate,btDelete;
    private EditText txtId,txtName,txtMark;
    private RadioButton male,female;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private SQLiteStudentHelper sqLite;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btUpdate.setEnabled(false);
        btDelete.setEnabled(false);
        adapter=new RecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        sqLite=new SQLiteStudentHelper(this);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student s=new Student();
                s.setName(txtName.getText().toString());
                boolean g=false;
                if(male.isChecked()){
                    g=true;
                }
                s.setGender(g);
                s.setMark(Double.parseDouble(txtMark.getText().toString()));
                sqLite.addStudent(s);
            }
        });
        btAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Student> list=sqLite.getAll();
                adapter.setStudents(list);
                recyclerView.setAdapter(adapter);
            }
        });
        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int id=Integer.parseInt(txtId.getText().toString());
                    Student s=sqLite.getStudentById(id);
                    if(s==null){
                        Toast.makeText(getApplicationContext(),"Khong co",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        txtName.setText(s.getName());
                        txtMark.setText(s.getMark()+"");
                        if(s.isGender()){
                            male.setChecked(true);
                        }else{
                            female.setChecked(true);
                        }
                        btUpdate.setEnabled(true);
                        btDelete.setEnabled(true);
                        btAdd.setEnabled(false);
                        txtId.setEnabled(false);
                    }
                }catch(NumberFormatException e){
                    System.out.println(e);
                }
            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int id=Integer.parseInt(txtId.getText().toString());
                    sqLite.delete(id);
                    btUpdate.setEnabled(false);
                    btDelete.setEnabled(false);
                    btAdd.setEnabled(true);
                    txtId.setEnabled(true);
                }catch(NumberFormatException e){
                    System.out.println(e);
                }
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int id=Integer.parseInt(txtId.getText().toString());
                    String n=txtName.getText().toString();
                    double m=Double.parseDouble(txtMark.getText().toString());
                    boolean g=false;
                    if(male.isChecked()){
                        g=true;
                    }
                    Student s=new Student(id,n,g,m);
                    sqLite.update(s);
                    btUpdate.setEnabled(false);
                    btDelete.setEnabled(false);
                    btAdd.setEnabled(true);
                    txtId.setEnabled(true);
                }catch(NumberFormatException e){
                    System.out.println(e);
                }
            }
        });

    }

    private void initView() {
        btAdd=findViewById(R.id.btAdd);
        btAll=findViewById(R.id.btAll);
        btGet=findViewById(R.id.btGet);
        btUpdate=findViewById(R.id.btUpdate);
        btDelete=findViewById(R.id.btDelete);

        txtId=findViewById(R.id.stID);
        txtName=findViewById(R.id.stName);
        txtMark=findViewById(R.id.stMark);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        recyclerView=findViewById(R.id.recyclerView);
    }
}
