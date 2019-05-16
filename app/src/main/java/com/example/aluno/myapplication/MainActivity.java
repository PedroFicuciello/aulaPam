package com.example.aluno.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nomeTxt;
    private Button salvarBtn;
    private ListView list;
    private SQLiteDatabase bd;
    private List<String> itens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeTxt = findViewById(R.id.nomeTxt);
        salvarBtn = findViewById(R.id.salvarBtn);
        list = findViewById(R.id.list);

        bd = openOrCreateDatabase("BD",MODE_PRIVATE,null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS pessoas (nomes VARCHAR(30));");

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,itens);
        list.setAdapter(adapter);


        salvarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtNome = nomeTxt.getText().toString();

                Salvar(txtNome);

                nomeTxt.setText("");
            }
        });

    }

    public void Salvar(String txt){

        try{

            bd.execSQL("INSERT INTO pessoas(nomes) VALUES ('"+ txt +"');");

            Cursor c = bd.rawQuery("SELECT nomes FROM pessoas",null);
            c.moveToFirst();
            do{
                itens.add(c.getString(c.getColumnIndex("nomes")));
            }while (c.moveToNext());
            c.close();

        } catch (Exception e){
            Log.i("BD_ERROR","Error - "+e.getMessage());
        }
    }
}
