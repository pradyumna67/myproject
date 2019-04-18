package com.example.dualproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dualproject.Database.DatabaseHelper;
import com.example.dualproject.R;
import com.example.dualproject.Validation.InputValidation;

import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {
    EditText editTextEmailLogin;
    EditText editTextPasswordLogin;
    Button btnLogin;
    TextView textViewNotRegistered;
    DatabaseHelper databaseHelpers;
    InputValidation inputValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        editTextEmailLogin=findViewById(R.id.editText_login_email);
        editTextPasswordLogin=findViewById(R.id.editText_login_password);
        textViewNotRegistered=findViewById(R.id.text_not_registered);
        btnLogin=findViewById(R.id.btn_login);
        databaseHelpers=new DatabaseHelper(this);
        inputValidation=new InputValidation(this);
        listener();
    }
    public void listener()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyFromSqlite();
            }
        });
        textViewNotRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivities(new Intent[]{new Intent(getApplicationContext(), SignUpActivity.class)});
                finish();
            }
        });
    }
    public void VerifyFromSqlite()
    {
        final Pattern PASSWORD_PATTERN =Pattern.compile("^"+"(?=.*[0-9])"+"(?=.*[a-z])"+"(?=.*[A-Z])"+"(?=.*[@#$%^&+=])"+"(?=\\S+$)"+".{6,}$");
        String password=editTextPasswordLogin.getText().toString();
        if(!inputValidation.isInputEdiTextFilled(editTextEmailLogin,getString(R.string.fieled_empty_email)))
        {
            return;
        }
        if(!inputValidation.isInputEditTextValid(editTextEmailLogin,"Not a valid email"))
        {
            return;
        }
        if(!inputValidation.isInputEdiTextFilled(editTextPasswordLogin,"Please Enter Password"))
        {
            return;
        }
        if(!PASSWORD_PATTERN.matcher(password).matches()){
            editTextPasswordLogin.setError(getString(R.string.password_validation));
            return;
        }
        if(databaseHelpers.checkUserEmailPwd(editTextEmailLogin.getText().toString().trim(),editTextPasswordLogin.getText().toString().trim()))
        {
            Toast.makeText(LogInActivity.this,"Successfully Login",Toast.LENGTH_SHORT).show();
            startActivities(new Intent[]{new Intent(getApplicationContext(), EmployeeListActivity.class)});
            finish();

        }
        else
            Toast.makeText(LogInActivity.this,"Email Or Password Not Correct",Toast.LENGTH_SHORT).show();



    }

}
