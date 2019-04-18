package com.example.dualproject.Activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dualproject.Database.DatabaseHelper;
import com.example.dualproject.Model.Student;
import com.example.dualproject.R;
import com.example.dualproject.Utils.Constants;
import com.example.dualproject.Utils.StudentAdapter;
import com.example.dualproject.Validation.InputValidation;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class UpdateActivity extends AppCompatActivity {
    private EditText edit_firstName,edit_lastName,editEmail,editPhone,editTextDepartment,editTextExperience,editTextPlatform;
    private Button btnUpdate,btnUploadImage,btnAddNote;
    private CircleImageView circleImageView;
    private int studentId = -1;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    private List<Student> studentList;
    private StudentAdapter adapter;
    private InputValidation inputValidator;
    Spinner spinnerBranch;
    private int REQUEST_CODE_GALLERY=999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        edit_firstName=findViewById(R.id.edit_first_name);
        edit_lastName=findViewById(R.id.edit_last_name);
        editEmail=findViewById(R.id.edit_email);
        editPhone=findViewById(R.id.edit_phone);

        circleImageView=findViewById(R.id.editstudent_photo);
        editTextDepartment=findViewById(R.id.et_update_department);
        editTextExperience=findViewById(R.id.et_update_experience);
        editTextPlatform=findViewById(R.id.edit_update_platform);
        btnAddNote=findViewById(R.id.btn_add_note);

        btnUploadImage=findViewById(R.id.editbtn_upload_image);
        btnUpdate=findViewById(R.id.btn_update);
        inputValidator=new InputValidation(this);
        showData();
        update();

        studentId = getIntent().getIntExtra(Constants.STUDENT_ID,0);


        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(UpdateActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
            }
        });
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateActivity.this,NoteHomeActivity.class);
                startActivity(intent);
            }
        });
    }
    public void update(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputValidator.isInputEdiTextFilled(edit_firstName,getString(R.string.fieled_empty_name))) {
                    return;
                }
                String first = edit_firstName.getText().toString();
                String last =  edit_lastName.getText().toString();
                String email = editEmail.getText().toString();
                String phone = editPhone.getText().toString();
                String department = editTextDepartment.getText().toString();
                String experience=editTextExperience.getText().toString();
                String platform=editTextPlatform.getText().toString();
                byte[] photo=imageviewtobyte(circleImageView);
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COL_EMPLOYEE_FIRST_NAME, first);
                values.put(DatabaseHelper.COL_EMPLOYEE_LAST_NAME, last);
                values.put(DatabaseHelper.COL_EMPLOYEE_EMAIL, email);
                values.put(DatabaseHelper.COL_EMPLOYEE_PHONE, phone);
                values.put(DatabaseHelper.COL_EMPLOYEE_IMAGE,photo);
                values.put(DatabaseHelper.COL_EMPLOYEE_DEPARTMENT,department);
                values.put(DatabaseHelper.COL_EMPLOYEE_EXPERIENCE,experience);
                values.put(DatabaseHelper.COL_EMPLOYEE_PLATFORM,platform);

                int updated = db.update(DatabaseHelper.TABLE_STUDENT, values, DatabaseHelper.COL_EMPLOYEE_ID + " = " + studentId, null);
                if (updated != -1) {
                    Toast.makeText(UpdateActivity.this, getString(R.string.updated), Toast.LENGTH_SHORT).show();
                    Student student1 = new Student();
                    student1.setImage(photo);
                    student1.setFirst(first);
                    student1.setSecond(last);
                    student1.setPhone(phone);
                    student1.setBranch(department);
                    student1.setId(studentId);
                    Intent intent = new Intent();
                    intent.putExtra(Constants.studentObject,student1);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
    public void showData(){
        studentId = getIntent().getIntExtra(Constants.STUDENT_ID,0);
        databaseHelper=new DatabaseHelper(this);
        db=databaseHelper.getReadableDatabase();
        Cursor c1=db.query(DatabaseHelper.TABLE_STUDENT,null,DatabaseHelper.COL_EMPLOYEE_ID+" = "+ studentId,null,null,null,null);
        studentList=new ArrayList<>();
        final Student student=new Student();
        if(c1 !=null && c1.getCount() !=0)
        {
            while(c1.moveToNext())
            {
                edit_firstName.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_FIRST_NAME)));
                edit_lastName.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_LAST_NAME)));
                editEmail.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_EMAIL)));
                editPhone.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_PHONE)));
                editTextDepartment.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_DEPARTMENT)));
                editTextExperience.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_EXPERIENCE)));
                editTextPlatform.setText(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_PLATFORM)));
                byte[] studentByte=c1.getBlob(c1.getColumnIndex(DatabaseHelper.COL_EMPLOYEE_IMAGE));
                Bitmap bitmap= BitmapFactory.decodeByteArray(studentByte,0,studentByte.length);
                circleImageView.setImageBitmap(bitmap);

            }
        }

    }
    private byte[] imageviewtobyte(CircleImageView image)
    {
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream straem=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,straem);
        byte[] byteArray=straem.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode== REQUEST_CODE_GALLERY){
            if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else
                Toast.makeText(UpdateActivity.this,getString(R.string.access_denied),Toast.LENGTH_SHORT).show();
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                circleImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
