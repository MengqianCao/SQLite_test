package com.comp7506.sql_test;

import android.os.Bundle;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comp7506.sql_test.database.UserDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText usr_name,usr_pwd,usr_pwd2;
    Button submit;
    UserDatabase userDatabase;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usr_name = this.findViewById(R.id.usrname);
        usr_pwd =  this.findViewById(R.id.usrpwd);
        usr_pwd2 = this.findViewById(R.id.usrpwd2);
        submit = this.findViewById(R.id.submit);

        userDatabase = new UserDatabase(this,"Userinfo",null,1);
        db = userDatabase.getReadableDatabase();

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        boolean flag = true;
        String name = usr_name.getText().toString();
        String pwd1 = usr_pwd.getText().toString();
        String pwd2 = usr_pwd2.getText().toString();
        if(name.isEmpty() || pwd1.isEmpty() || pwd2.isEmpty()){
            Toast.makeText(Register.this, "Can not be null!", Toast.LENGTH_LONG).show();
        }
        else{
            Cursor cursor = db.query("users",new String[]{"user_name"},null,null,null,null,null);
            while (cursor.moveToNext()){
                if(cursor.getString(0).equals(name)){
                    flag = false;
                    break;
                }
            }
            cursor.close();
            if(flag){
                if (pwd1.equals(pwd2)) {
                    ContentValues values = new ContentValues();
                    values.put("user_name",name);
                    values.put("user_pwd",pwd1);
                    db.insert("users",null,values);
                    values.clear();

                    Intent intent = new Intent();
                    intent.setClass(Register.this,MainActivity.class);
                    startActivity(intent);
                    db.close();
                    Toast.makeText(Register.this, "Sign up successfully!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Register.this, "Inconsistent password!", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(Register.this, "The user already exists!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
