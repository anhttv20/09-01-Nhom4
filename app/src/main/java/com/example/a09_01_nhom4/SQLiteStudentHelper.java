package com.example.a09_01_nhom4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a09_01_nhom4.model.Student;

import java.util.ArrayList;
import java.util.List;

public class SQLiteStudentHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="studentDB.db";
    private static final int DATABSE_VERSION=1;

    public SQLiteStudentHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tao table student
        String sql="CREATE TABLE student(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "gender BOOLEAN," +
                " mark REAL)";
        db.execSQL(sql);
    }

    public long addStudent(Student s){
        ContentValues v=new ContentValues();
        v.put("name",s.getName());
        v.put("gender",s.isGender());
        v.put("mark",s.getMark());
        SQLiteDatabase st=getWritableDatabase();
        return st.insert("student",null,v);
    }

    //get all
    public List<Student> getAll(){
        List<Student> list=new ArrayList<>();
        SQLiteDatabase statement=getReadableDatabase();
        Cursor rs=statement.query("student",null,
                null,null,
                null,null,null);
        while((rs!=null)&& rs.moveToNext()){
            int id=rs.getInt(0);
            String name=rs.getString(1);
            boolean g=rs.getInt(2)==1;
            double m=rs.getDouble(3);
            list.add(new Student(id,name,g,m));
        }
        return  list;
    }
    //get student by id
    public Student getStudentById(int id){
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(id)};
        SQLiteDatabase st=getReadableDatabase();
        Cursor rs=st.query("student",null,whereClause,
                whereArgs,null,null,null);
        if(rs.moveToNext()){
            String n=rs.getString(1);
            boolean g=rs.getInt(2)==1;
            double m=rs.getDouble(3);
            return new Student(id,n,g,m);
        }
        return null;
    }
    //update
    public int update(Student s){
        ContentValues v=new ContentValues();
        v.put("name",s.getName());
        v.put("gender",s.isGender());
        v.put("mark",s.getMark());
        SQLiteDatabase st=getWritableDatabase();
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(s.getId())};
        return st.update("student",v,whereClause,whereArgs);
    }
    //delete
    public  int delete(int id){
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(id)};
        SQLiteDatabase st=getWritableDatabase();
        return st.delete("student",whereClause,whereArgs);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
