package com.example.dualproject.Activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dualproject.Database.DatabaseHelper;
import com.example.dualproject.R;
import com.example.dualproject.Validation.InputValidation;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployeeFormActivity extends AppCompatActivity {
    EditText editTextFirst,editTextLast,editTextEmail,editTextPhone,editTextDepartment,editTextExperience,editTextPlatform;
    Button btnSave,btnUpload;
    int REQUEST_CODE_GALLERY=1;
    CircleImageView circleImageView;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    InputValidation inputValidator;
    Spinner spinnerExperience;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_form);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        editTextEmail=findViewById(R.id.editText_email);
        editTextFirst=findViewById(R.id.editText_first_name);
        editTextLast=findViewById(R.id.editText_last_name);
        editTextDepartment=findViewById(R.id.et_dept);
        editTextExperience=findViewById(R.id.et_exp);
        editTextPlatform=findViewById(R.id.editText_platform);
        //editTextDepartment.setEnabled(false);
        spinnerExperience=findViewById(R.id.spinner_experience);
        editTextPhone=findViewById(R.id.editText_phone);
        inputValidator=new InputValidation(this);
        circleImageView=findViewById(R.id.student_photo);
        databaseHelper=new DatabaseHelper(this);

        btnUpload=findViewById(R.id.btn_upload_image);
        btnSave=findViewById(R.id.btn_save);


        String[] countr={"--Select--","Fresher","2+","5+","8+","10+"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,countr);
        spinnerExperience.setAdapter(adapter);

        spinnerExperience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        editTextExperience.setText("");
                        break;
                    case 1:
                        editTextExperience.setText("fresher");
                        break;
                    case 2:
                        editTextExperience.setText("2+");
                        break;
                    case 3:
                        editTextExperience.setText("5+");
                        break;
                    case 4:
                        editTextExperience.setText("8+");
                        break;
                    case 5:
                        editTextExperience.setText("10+");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputValidator.isInputEdiTextFilled(editTextFirst,getString(R.string.first_name_filled))) {
                    return;
                }
                if(!inputValidator.isInputEdiTextFilled(editTextEmail,getString(R.string.fieled_empty_email))) {
                    return;
                }

                if(!inputValidator.isInputEditTextValid(editTextEmail,getString(R.string.error_email))){
                    return;
                }
                db=databaseHelper.getWritableDatabase();
                String first=editTextFirst.getText().toString();
                String second=editTextLast.getText().toString();
                String email=editTextEmail.getText().toString();
                String phone=editTextPhone.getText().toString();
                String department=editTextDepartment.getText().toString();
                String experience=editTextExperience.getText().toString();
                String platform=editTextPlatform.getText().toString();
                byte[] image=imageviewtobyte(circleImageView);
                ContentValues values=new ContentValues();
                values.put(DatabaseHelper.COL_EMPLOYEE_FIRST_NAME,first);
                values.put(DatabaseHelper.COL_EMPLOYEE_LAST_NAME,second);
                values.put(DatabaseHelper.COL_EMPLOYEE_EMAIL,email);
                values.put(DatabaseHelper.COL_EMPLOYEE_PHONE,phone);
                values.put(DatabaseHelper.COL_EMPLOYEE_DEPARTMENT,department);
                values.put(DatabaseHelper.COL_EMPLOYEE_EXPERIENCE,experience);
                values.put(DatabaseHelper.COL_EMPLOYEE_PLATFORM,platform);
                values.put(DatabaseHelper.COL_EMPLOYEE_IMAGE,image);
                long inserted=db.insert(DatabaseHelper.TABLE_STUDENT,null,values);
                if(inserted !=-1){
                    Intent intent=new Intent(EmployeeFormActivity.this, EmployeeListActivity.class);
                    startActivity(intent);
                    Toast.makeText(EmployeeFormActivity.this,getString(R.string.addes_data),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EmployeeFormActivity.this,getString(R.string.failed_to_add),Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(EmployeeFormActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
            }
        });
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
                Toast.makeText(EmployeeFormActivity.this,getString(R.string.access_denied),Toast.LENGTH_SHORT).show();
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
