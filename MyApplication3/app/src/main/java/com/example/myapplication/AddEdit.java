package com.example.myapplication;

import android.view.Menu;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.MotionEvent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.content.SharedPreferences;
import com.google.gson.Gson; 
import java.lang.reflect.Type; 
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;  

public class AddEdit extends AppCompatActivity {		
		private final String PREFS_NAME= "nota";
		private final String KEY1= "titulo";
		private final String KEY2= "texto";
		private EditText textArea;
		private EditText titleText;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
				
				Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
				assert getSupportActionBar() != null;   //null check
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
				
				textArea = (EditText) findViewById(R.id.textArea_information);
				titleText = (EditText) findViewById(R.id.titulo);

				textArea.setOnTouchListener(new View.OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						v.getParent().requestDisallowInterceptTouchEvent(true);
						switch (event.getAction() & MotionEvent.ACTION_MASK){
							case MotionEvent.ACTION_UP:
									v.getParent().requestDisallowInterceptTouchEvent(false);
									break;
						}
						return false;
					}
        });
				
				Button buttonSalvar = (Button) findViewById(R.id.salvar);
				buttonSalvar.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						String title= titleText.getText().toString();
						String text= textArea.getText().toString();
						
						ArrayList<String> titles= new ArrayList<String>();
						ArrayList<String> content= new ArrayList<String>();
						
						titles= getArrayList(KEY1);
						content= getArrayList(KEY2);
						
						titles.add(title);
						content.add(text);
						
						saveArrayList(titles, KEY1);
						saveArrayList(content, KEY2);
						
						Intent intent = new Intent(AddEdit.this, MainActivity.class);
						startActivity(intent);
					}
				});

    }
		
		public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME,0);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
		
		public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }
		
		@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
		

		
		@Override
		public boolean onSupportNavigateUp(){  
				finish();  
				return true;  
		}
	

}