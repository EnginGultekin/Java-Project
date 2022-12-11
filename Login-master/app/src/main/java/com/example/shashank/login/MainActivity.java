package com.engleg.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button LogInButton, RegisterButton , ShowButton, DeleteButton;
    EditText Email, Password ;
    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    public static final String UserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogInButton = (Button)findViewById(R.id.buttonLogin);
        RegisterButton = (Button)findViewById(R.id.buttonRegister);
        ShowButton = (Button)findViewById(R.id.buttonShow);
        DeleteButton = (Button)findViewById(R.id.buttonDelete);

        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);

        sqLiteHelper = new SQLiteHelper(this);

        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling EditText is empty or no method.
                CheckEditTextStatus();

                // Calling login method.
                LoginFunction();


            }
        });

        // Adding click listener to register button.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        ShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListUsers.class);
                startActivity(intent);

            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeleteUsers.class);
                startActivity(intent);

            }
        });

    }

    // Başlangıç ----------------------
    public String[] sutunlar={"name","email","password"};
    public Cursor kayitgetir()
    {
        // SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        Cursor okunanlar = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, sutunlar, null, null, null, null, null);
        return okunanlar;
    }
    public void kayitgoster(Cursor goster)
    {

        StringBuilder builder = new StringBuilder();
        while( goster.moveToNext()) {
            String namee =  goster.getString((goster.getColumnIndex("name")));
            String emaill =  goster.getString(( goster.getColumnIndex("email")));
            String passwordd=  goster.getString(( goster.getColumnIndex("password")));

            builder.append("name: ").append(namee+"\n" );
            builder.append("email:").append(emaill+"\n");
            builder.append("password: ").append(passwordd+"\n");
            builder.append("------------- ").append("\n");
        }
        TextView text = (TextView)findViewById(R.id.textViewbilgiler);
        text.setText(builder);
    }

    // Bitiş ----------------------

//    public void butonlogin(View view ){
//        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
//        startActivity(intent);
//    }
//    public void butonregister(View view ){
//        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//        startActivity(intent);
//    }
//    public void butonshow(View view ){
//        Intent intent = new Intent(MainActivity.this, ListUsers.class);
//        startActivity(intent);
//    }
//    public void butondelete(View view ){
//        Intent intent = new Intent(MainActivity.this, DeleteUsers.class);
//        startActivity(intent);
//    }


    // Login function starts from here.
    public void LoginFunction(){

        if(EditTextEmptyHolder) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password));

                    // Closing cursor.
                    cursor.close();
                }
            }

            // Calling method to check final result ..
            CheckFinalResult();

        }
        else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(MainActivity.this,"Please Enter UserName or Password.",Toast.LENGTH_LONG).show();

        }

    }

    // Checking EditText is empty or not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        // Checking EditText is empty or no using TextUtils.
        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult(){

        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {

            Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();

            // Going to Dashboard activity after login success message.
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);

            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(UserEmail, EmailHolder);
            startActivity(intent);


        }
        else {

            Toast.makeText(MainActivity.this,"UserName or Password is Wrong, Please Try Again.",Toast.LENGTH_LONG).show();

        }
        TempPassword = "NOT_FOUND" ;

    }

}