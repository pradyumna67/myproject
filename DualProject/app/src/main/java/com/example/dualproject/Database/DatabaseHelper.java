package com.example.dualproject.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="usernote.db";

    public static final String TABLE_USER="user";
    public static final String TABLE_STUDENT="student";
    public static final String COL_USER_ID="user_id";
    public static final String COL_USER_NAME="user_name";
    public static final String COL_USER_EMAIL="user_email";
    public static final String COL_USER_PHONE="user_phone";
    public static final String COL_USER_PASSWORD="user_password";

    public static final String COL_EMPLOYEE_ID="student_id";
    public static final String COL_EMPLOYEE_FIRST_NAME="first_name";
    public static final String COL_EMPLOYEE_LAST_NAME="last_email";
    public static final String COL_EMPLOYEE_EMAIL="student_email";
    public static final String COL_EMPLOYEE_PHONE="student_phone";
    public static final String COL_EMPLOYEE_DEPARTMENT="student_department";
    public static final String COL_EMPLOYEE_EXPERIENCE="student_experience";
    public static final String COL_EMPLOYEE_PLATFORM="student_platform";
    public static final String COL_EMPLOYEE_IMAGE="student_image";

    public static final String TABLE_NOTE="notes_table";
    public static final String COL_ID="id";
    public static final String COL_TITLE="note_title";
    public static final String COL_DESCRIPTION="note_description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private String CREATE_USER_TABLE="CREATE TABLE " +TABLE_USER +"("+COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +COL_USER_NAME+" TEXT,"+COL_USER_EMAIL+" TEXT  ,"+COL_USER_PHONE+" TEXT,"+COL_USER_PASSWORD+" TEXT "+")";
    private String ONUPGRADE="DROP TABLE IF EXISTS "+TABLE_USER;

    private String CREATE_STUDENT_TABLE="CREATE TABLE " +TABLE_STUDENT +"("+COL_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +COL_EMPLOYEE_FIRST_NAME+" TEXT,"+COL_EMPLOYEE_LAST_NAME+" TEXT  ,"+COL_EMPLOYEE_EMAIL+" TEXT,"+COL_EMPLOYEE_PHONE+" TEXT ,"+COL_EMPLOYEE_DEPARTMENT+" TEXT ,"+COL_EMPLOYEE_EXPERIENCE+" TEXT ," +
            ""+COL_EMPLOYEE_PLATFORM+" TEXT ,"+COL_EMPLOYEE_IMAGE+" BLOB "+")";
    private String ONUPGRADE1="DROP TABLE IF EXISTS "+TABLE_STUDENT;

    private String CREATE_NOTE="CREATE TABLE " +TABLE_NOTE +"("+COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COL_TITLE +" TEXT ,"+COL_DESCRIPTION+" TEXT "+")";
    private String ONUPGRADE2="DROP TABLE IF EXISTS "+TABLE_NOTE;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ONUPGRADE);
        db.execSQL(ONUPGRADE1);
        db.execSQL(ONUPGRADE2);
    }
    public boolean checkUser(String email,String message)
    {
        String[] columns={COL_USER_ID};
        SQLiteDatabase db=this.getReadableDatabase();
        String selection= COL_USER_EMAIL + " = ?";
        String[] selectionArgs={email};
        Cursor cursor=db.query(TABLE_USER,columns,selection,selectionArgs,null,null,null);

        int cursorCount=cursor.getCount();
        cursor.close();
        db.close();
        if(cursorCount>0){
            return true;
        }
        return false;
    }
    public boolean checkUserEmailPwd(String email,String password){
        String[] columns={COL_USER_ID};
        String selection=COL_USER_EMAIL +" = ?"+" AND "+COL_USER_PASSWORD+" = ?";
        String[] selectionArgs={email,password};
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_USER,columns,selection,selectionArgs,null,null,null);
        int cursorCount=cursor.getCount();
        cursor.close();
        db.close();
        if(cursorCount>0){
            return true;
        }
        return false;
    }



   /* public Student getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_STUDENT + " WHERE "
                + COL_STUDENT_ID_ID + " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Student student = new Student();
        student.setId(c.getInt(c.getColumnIndex(COL_STUDENT_ID_ID)));
        student.setFirst((c.getString(c.getColumnIndex(COL_STUDENT_FIRST_NAME))));
        student.setSecond(c.getString(c.getColumnIndex(COL_STUDENT_LAST_NAME)));
        student.setEmail(c.getString(c.getColumnIndex(COL_STUDENT_EMAIL)));
        student.setPhone(c.getString(c.getColumnIndex(COL_STUDENT_PHONE)));
        student.setBranch(c.getString(c.getColumnIndex(COL_STUDENT_BRANCH)));
        byte[] studentByte=c.getBlob(c.getColumnIndex(DatabaseHelper.COL_STUDENT_IMAGE));
        Bitmap bitmap= BitmapFactory.decodeByteArray(studentByte,0,studentByte.length);
        student.setImage(studentByte);
        return student;

    }
    public int updateData(int upId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Student student=new Student();
        ContentValues values = new ContentValues();
        values.put(COL_STUDENT_FIRST_NAME,student.getFirst() );
        values.put(COL_STUDENT_LAST_NAME,student.getSecond() );
        values.put(COL_STUDENT_PHONE,student.getPhone() );
        values.put(COL_STUDENT_BRANCH,student.getBranch() );

        return db.update(TABLE_STUDENT, values, COL_STUDENT_ID_ID + " = ?",
                new String[] { String.valueOf(student.getId()) });
    }*/


}
