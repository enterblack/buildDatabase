package com.example.builddatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    myDBHelper myHelper;
    EditText edtName,edtNumber,edtNameResult,edtNumResult;
    Button btnInit,btnInsert,btnSelect,btnUpdate,btnDelete;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("가수 그룹 관리 DB");

        edtName = (EditText) findViewById(R.id.edtName);
        edtNumber = (EditText) findViewById(R.id.edtNum);
        edtNameResult = (EditText) findViewById(R.id.edtNameResult);
        edtNumResult = (EditText) findViewById(R.id.edtNumResult);
        btnInit = (Button) findViewById(R.id.btnInit);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        myHelper = new myDBHelper(this);
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB,1,2);
                sqlDB.close();
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                //근데 여기서 먼저 데이터베이스를 확인해서 이름이 중복이면 입력이 안되게 만들어야됨 ㅋㅋㅋㅋㅋㅋ
                //중복입력해버리면 프라이머리키가 중복이 된다.


                //(2021-04-14)
                sqlDB.execSQL("INSERT INTO groupTBL VALUES('" + edtName.getText().toString() +"',"
                        +edtNumber.getText().toString() +");");


                //출력하는 부분 인데 이걸 중복을 줄이는 방법이 있겠지?? (2021.04.11)
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;",null);
                String strNames = "그룹이름" + "\r\n" + "--------" + "\r\n";
                String strNums = "인원" + "\r\n" + "--------" + "\r\n";

                while (cursor.moveToNext()){
                    strNames += cursor.getString(0) + "\r\n";
                    strNums += cursor.getString(1) + "\r\n";
                }

                edtNameResult.setText(strNames);
                edtNumResult.setText(strNums);
                cursor.close();
                sqlDB.close();
                //
                Toast.makeText(getApplicationContext(),"입력됨",Toast.LENGTH_SHORT).show();
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getReadableDatabase();
                //출력하는 부분 인데 이걸 중복을 줄이는 방법이 있겠지?? (2021.04.11)
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;",null);
                String strNames = "그룹이름" + "\r\n" + "--------" + "\r\n";
                String strNums = "인원" + "\r\n" + "--------" + "\r\n";

                while (cursor.moveToNext()){
                    strNames += cursor.getString(0) + "\r\n";
                    strNums += cursor.getString(1) + "\r\n";
                }

                edtNameResult.setText(strNames);
                edtNumResult.setText(strNums);
                cursor.close();
                sqlDB.close();
                //
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("UPDATE groupTBL SET gNumber='" + edtNumber.getText().toString() +
                      "'WHERE gName ='" + edtName.getText().toString() +"';");

                //출력하는 부분 인데 이걸 중복을 줄이는 방법이 있겠지?? (2021.04.11)
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;",null);
                String strNames = "그룹이름" + "\r\n" + "--------" + "\r\n";
                String strNums = "인원" + "\r\n" + "--------" + "\r\n";

                while (cursor.moveToNext()){
                    strNames += cursor.getString(0) + "\r\n";
                    strNums += cursor.getString(1) + "\r\n";
                }

                edtNameResult.setText(strNames);
                edtNumResult.setText(strNums);
                cursor.close();
                sqlDB.close();
                //

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE FROM groupTBL WHERE gName='"+ edtName.getText().toString() +"';");

                //출력하는 부분 인데 이걸 중복을 줄이는 방법이 있겠지?? (2021.04.11)
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;",null);
                String strNames = "그룹이름" + "\r\n" + "--------" + "\r\n";
                String strNums = "인원" + "\r\n" + "--------" + "\r\n";

                while (cursor.moveToNext()){
                    strNames += cursor.getString(0) + "\r\n";
                    strNums += cursor.getString(1) + "\r\n";
                }

                edtNameResult.setText(strNames);
                edtNumResult.setText(strNums);
                cursor.close();
                sqlDB.close();
                //
            }
        });
    }
    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "groupDB", null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase database){
            database.execSQL("CREATE TABLE groupTBL (gName CHAR(20) PRIMARY KEY, gNumber INTEGER);");
        }
        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
            database.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(database);
        }
    }


}
