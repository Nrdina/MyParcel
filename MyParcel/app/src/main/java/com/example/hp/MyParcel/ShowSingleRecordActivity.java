package com.example.hp.MyParcel;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowSingleRecordActivity extends AppCompatActivity {

    String IDholder;
    TextView id, name, resepi, kategori;
    SQLiteDatabase sqLiteDatabase;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    Button Delete, Edit;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_record);

        id = (TextView) findViewById(R.id.textViewID);
        name = (TextView) findViewById(R.id.textViewName);
        resepi = (TextView) findViewById(R.id.textViewResepi);
        kategori = (TextView) findViewById(R.id.textViewKategori);
        Delete = (Button)findViewById(R.id.buttonDelete);
        Edit = (Button)findViewById(R.id.buttonEdit);

        sqLiteHelper = new SQLiteHelper(this);



        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenSQLiteDataBase();

                SQLiteDataBaseQueryHolder = "DELETE FROM "+SQLiteHelper.TABLE_NAME+" WHERE id = "+IDholder+"";

                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);

                sqLiteDatabase.close();

                finish();

            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),EditSingleRecordActivity.class);

                intent.putExtra("EditID", IDholder);

                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {

        ShowSingleRecordInTextView();

        super.onResume();
    }

    public void ShowSingleRecordInTextView() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        IDholder = getIntent().getStringExtra("ListViewClickedItemValue");

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " WHERE id = " + IDholder + "", null);

        if (cursor.moveToFirst()) {

            do {
                id.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID)));
                name.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name)));
                resepi.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_Resepi)));
                kategori.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Kategori)));
            }
            while (cursor.moveToNext());

            cursor.close();

        }
    }

    public void OpenSQLiteDataBase(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }
}
