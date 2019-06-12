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
import android.widget.Toast;
import android.widget.TextView;
import android.content.DialogInterface;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.content.Context;



public class ContatoActivity extends AppCompatActivity {
	private TextView resultado;
	private int itemFromList;
	private final String NOMES= "nomes.txt";
	private final String TELEFONES= "telefones.txt";
	private final String EMAILS= "emails.txt";

	@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
				
				Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
				assert getSupportActionBar() != null;   //null check
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
				
				
			
        resultado= (TextView) findViewById(R.id.resultado);
        List<Contato> contatos= new ArrayList<>();
				
				try{
					ArrayList<String> nome=  getArrayList(NOMES);
					ArrayList<String> telefone= getArrayList(TELEFONES);
					ArrayList<String> email= getArrayList(EMAILS);
					
					resultado.setText("Nome: " + nome.get(0) + "\nTelefone: " + telefone.get(0) + "\nEmail: " + email.get(0));
			 
					
					
				}
				catch(Exception e){
				}
    }
		
		
	
		
		public void onBackPressed(){
			finish();
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
			}catch(Exception e){
				
			}
			return new ArrayList<String>();
    }
		
		public void saveArrayList(String fileName, ArrayList<String> list){
			Gson gson = new Gson();
			String json = gson.toJson(list);
			try{
				//write converted json data to a file named "CountryGSON.json"
				FileOutputStream fos = openFileOutput(fileName,0);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {
						Intent intent = new Intent(ContatoActivity.this, MainActivity.class);
						startActivity(intent);
        }
			

        return super.onOptionsItemSelected(item);
    }
		
		@Override
		public boolean onSupportNavigateUp(){
				finish();
				return true;  
		}
}
