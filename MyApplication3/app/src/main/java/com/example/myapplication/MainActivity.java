package com.example.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.app.AlertDialog;
import java.util.List;
import android.widget.AdapterView;
import android.content.Intent;
import android.app.Activity;
import android.content.SharedPreferences;
import com.google.gson.Gson;  
import java.lang.reflect.Type; 
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken; 

public class MainActivity extends AppCompatActivity {
	private ListView list;
	private final String PREFS_NAME= "nota";
	private final String KEY1= "titulo";
	private final String KEY2= "texto";

	@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
				
				Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
				assert getSupportActionBar() != null;   //null check
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
				
				
			
        list= findViewById(R.id.idList);
        List<Nota> notas= new ArrayList<>();
				
				try{
					List<String> titulo_notas= getArrayList(KEY1);
					List<String> texto_notas= getArrayList(KEY2);
					
					for(int i=0; i<texto_notas.size(); i++){
						notas.add(new Nota(titulo_notas.get(i),texto_notas.get(i)));
					}
			 
					ArrayAdapter adapter= new ArrayAdapter<Nota>(this,R.layout.activity_list,R.id.simple_list_item_1,notas);
					list.setAdapter(adapter);
					list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id){
								Nota nota= (Nota) list.getItemAtPosition(position);
								AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.this);
								alert.setTitle(nota.getTitulo());
								alert.setMessage(nota.getTexto());
								alert.show();
							}
					});
				}
				catch(Exception e){
					
				}
    }
		
		public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME,0);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
						Intent intent = new Intent(MainActivity.this, AddEdit.class);
						startActivity(intent);
        }
			

        return super.onOptionsItemSelected(item);
    }
		
		@Override
		public boolean onSupportNavigateUp(){  
				finish();
				moveTaskToBack(true);
        this.finishAffinity();
				return true;  
		}
}
