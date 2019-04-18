package com.example.dualproject.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dualproject.Database.DatabaseHelper;
import com.example.dualproject.Model.User;
import com.example.dualproject.R;
import com.example.dualproject.Validation.InputValidation;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextName,editTextEmail,editTextPhone,editTextPassword,editTextConfirmPassword;
    Button btnSignIn;
    TextView textViewAlreadyMember;
    User user;
    InputValidation inputValidation;
    DatabaseHelper databaseHelpers;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        Listener();
        object();
    }
    private void initView()
    {
        editTextName=findViewById(R.id.editText_signup_name);
        editTextEmail=findViewById(R.id.editText_signup_email);
        editTextPhone=findViewById(R.id.editText_signup_phone);
        editTextPassword=findViewById(R.id.editText_signup_password);
        editTextConfirmPassword=findViewById(R.id.editText_signup_confirmpassword);

        btnSignIn=findViewById(R.id.btn_signup);
        textViewAlreadyMember=findViewById(R.id.text_already_signn);

    }
    private void object() {
        user = new User();
        inputValidation = new InputValidation(this);
        databaseHelpers = new DatabaseHelper(this);
    }
    private void Listener()
    {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadToSQLite();
            }
        });
        textViewAlreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivities(new Intent[]{new Intent(getApplicationContext(), LogInActivity.class)});
                finish();
            }
        });
    }
    public void LoadToSQLite()
    {
        final Pattern PASSWORD_PATTERN =Pattern.compile("^"+"(?=.*[0-9])"+"(?=.*[a-z])"+"(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$)"+".{6,}$");
        String password=editTextPassword.getText().toString();
        if(!inputValidation.isInputEdiTextFilled(editTextName,getString(R.string.fieled_empty_name))) {
            return;
        }
        if(!inputValidation.isInputEdiTextFilled(editTextEmail,getString(R.string.fieled_empty_email))) {
            return;
        }
        if(!inputValidation.isInputEditTextValid(editTextEmail,getString(R.string.error_email))){
            return;
        }
        if(!inputValidation.isInputEdiTextFilled(editTextPhone,getString(R.string.fieled_empty_phone))) {
            return;
        }
        if(!inputValidation.isInputEditTextPhone(editTextPhone,getString(R.string.invalid_phone)))
        {return;}
        if(!inputValidation.isInputEdiTextFilled(editTextPassword,getString(R.string.fieled_empty_password))) {
            return;
        }

        if(!inputValidation.isInputEditTextMatches(editTextPassword,editTextConfirmPassword,getString(R.string.password_unmatched))) {
            return;
        }
        if(!PASSWORD_PATTERN.matcher(password).matches()){
            editTextPassword.setError(getString(R.string.password_validation));
            return;
        }

        if(!databaseHelpers.checkUser(editTextEmail.getText().toString().trim(),getString(R.string.email_exists))) {
            db=databaseHelpers.getWritableDatabase();
            String name=editTextName.getText().toString();
            String phone=editTextPhone.getText().toString();
            String email=editTextEmail.getText().toString();
            String pwd=editTextPassword.getText().toString();
            ContentValues value=new ContentValues();
            value.put(DatabaseHelper.COL_USER_NAME,name);
            value.put(DatabaseHelper.COL_USER_PHONE,phone);
            value.put(DatabaseHelper.COL_USER_EMAIL,email);
            value.put(DatabaseHelper.COL_USER_PASSWORD,pwd);
            long inserted=db.insert(DatabaseHelper.TABLE_USER,null,value);
            if(inserted != -1){
                Toast.makeText(SignUpActivity.this,"Signup",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SignUpActivity.this, EmployeeListActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(SignUpActivity.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
