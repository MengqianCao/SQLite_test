package com.comp7506.sql_test;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comp7506.sql_test.database.UserDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name,pwd;
    Button login;
    Button register;
    UserDatabase userDatabase;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = this.findViewById(R.id.name);
        pwd = this.findViewById(R.id.pwd);
        login = this.findViewById(R.id.login);
        register = this.findViewById(R.id.register);

        userDatabase = new UserDatabase(this, "Userinfo", null, 1);
        db = userDatabase.getReadableDatabase();
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.login) {
            String username = name.getText().toString();
            String password = pwd.getText().toString();
            Cursor cursor = db.query("users", new String[]{"user_name", "user_pwd"},
                    " user_name=? and user_pwd=?", new String[]{username, password}, null, null, null);

            int flag = cursor.getCount();
            cursor.close();

            if (flag != 0) {
                Toast.makeText(MainActivity.this, "Login successfully!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Wrong user or password!", Toast.LENGTH_LONG).show();
            }
        }
        if (v.getId() == R.id.register) {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        }
    }
}
