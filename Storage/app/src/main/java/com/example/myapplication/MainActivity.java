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
import android.support.v7.app.AlertDialog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.content.Context;

public class MainActivity extends AppCompatActivity {		
		private final String FILENAME= "file.txt";
		private EditText textNome;
		private EditText textTelefone;
		private EditText textEmail;	
		private final String NOMES= "nomes.txt";
		private final String TELEFONES= "telefones.txt";
		private final String EMAILS= "emails.txt";
			
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
				
				Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
				assert getSupportActionBar() != null;   //null check
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
				
				textNome = (EditText) findViewById(R.id.nome);
				textTelefone = (EditText) findViewById(R.id.telefone);
				textEmail = (EditText) findViewById(R.id.email);
				
				Button buttonSalvar = (Button) findViewById(R.id.salvar);
				buttonSalvar.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						try{
							String nome= textNome.getText().toString();
							String telefone= textTelefone.getText().toString();
							String email= textEmail.getText().toString();
							
							ArrayList<String> nomes= new ArrayList<String>();
							ArrayList<String> telefones= new ArrayList<String>();
							ArrayList<String> emails= new ArrayList<String>();
							
							//nomes= getArrayList(NOMES);
							//telefones= getArrayList(TELEFONES);
							//emails= getArrayList(EMAILS);
							
							nomes.add(0,nome);
							telefones.add(0,telefone);
							emails.add(0,email);
							
							saveArrayList(NOMES, nomes);
							saveArrayList(TELEFONES, telefones);
							saveArrayList(EMAILS, emails);
							
							Intent intent = new Intent(MainActivity.this, ListaContatoActivity.class);
							startActivity(intent);
						}
						catch(Exception e){
							AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.this);
							alert.setMessage(e.getMessage());
							alert.show();
						}
					}
				});

    }
		
		public ArrayList<String> getArrayList(String fileName){
			try{
				Context ctx = getApplicationContext();
				FileInputStream fileInputStream = ctx.openFileInput(fileName);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String json = bufferedReader.readLine();
				
				Type type = new TypeToken<ArrayList<String>>() {}.getType();
				Gson gson = new Gson();
				return gson.fromJson(json, type);
			}
			catch(Exception e){
				
			}
			return new ArrayList<String>();
    }
		
		public void saveArrayList(String fileName, ArrayList<String> list){
			Gson gson = new Gson();
			String json = gson.toJson(list);
			try{
				//write converted json data to a file named "CountryGSON.json"
				FileOutputStream fos = openFileOutput(fileName, 0);
				fos.write(json.getBytes());
				fos.close();
				
			}catch(IOException e){
				e.printStackTrace();
			}
    }
		
		@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
		

		
		public void onBackPressed(){
			moveTaskToBack(true);
			finish();
			this.finishAffinity();
		}
		
		@Override
		public boolean onSupportNavigateUp(){  
				moveTaskToBack(true);
        this.finishAffinity();
				return true;  
		}

}