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
import android.content.DialogInterface;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.content.Context;



public class ListaContatoActivity extends AppCompatActivity {
	private ListView list;
	private int itemFromList;
	private final String NOMES= "nomes.txt";
	private final String TELEFONES= "telefones.txt";
	private final String EMAILS= "emails.txt";

	@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contato);
				
				Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
				assert getSupportActionBar() != null;   //null check
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
				
				
			
        list= findViewById(R.id.idList);
        List<Contato> contatos= new ArrayList<>();
				
				try{
					ArrayList<String> nomes=  getArrayList(NOMES);
					ArrayList<String> telefones= getArrayList(TELEFONES);
					ArrayList<String> emails= getArrayList(EMAILS);
					
					for(int i=0; i<nomes.size(); i++){
						contatos.add(new Contato(nomes.get(i),telefones.get(i), emails.get(i)));
					}
			 
					ArrayAdapter adapter= new ArrayAdapter<Contato>(this,R.layout.activity_lista,R.id.simple_list_item,contatos);
					list.setAdapter(adapter);
					list.setOnItemClickListener(showItem);
					list.setLongClickable(true);
					list.setOnItemLongClickListener(menuOnPressingItem);
					
				}
				catch(Exception e){
				}
    }
		
		
		private void menuOnLongPress(){
			//Lista de itens
			ArrayList<String> itens = new ArrayList<String>();
			itens.add("	Remover");

			 
			//adapter utilizando um layout customizado (TextView)
			ArrayAdapter adapter= new ArrayAdapter(this,R.layout.activity_lista,R.id.simple_list_item,itens);

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			//define o diálogo como uma lista, passa o adapter.
			builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int menuItemPosition) {
						deleteItemFromArrayList(menuItemPosition, itemFromList);
						Intent intent = new Intent(ListaContatoActivity.this, ListaContatoActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(intent);
						finish();
					}
			});

			AlertDialog alerta = builder.create();
			alerta.show();
			alerta.getWindow().setLayout(450, 150);
		}
		
		public void onBackPressed(){
			finish();
		}
		
		private AdapterView.OnItemLongClickListener menuOnPressingItem= new AdapterView.OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int listItemPosition, long id){
				menuOnLongPress();
				itemFromList= listItemPosition;
				return true;
			}
		};
		
		private AdapterView.OnItemClickListener showItem= new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				Contato contato= (Contato) list.getItemAtPosition(position);
				AlertDialog.Builder alert= new AlertDialog.Builder(ListaContatoActivity.this);
				alert.setTitle("Nome: " + contato.getNome());
				alert.setMessage("Telefone: " + contato.getTelefone() + "\n" + "Email: " + contato.getEmail());
				alert.show();
			}
		};
		
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
		
		public void deleteItemFromArrayList(int itemFromMenuOnLongPress, int itemPosition){
			try{
				ArrayList<String> nomes= getArrayList(NOMES);
				ArrayList<String> telefones= getArrayList(TELEFONES);
				ArrayList<String> emails= getArrayList(EMAILS);
				
				nomes.remove(itemPosition);
				telefones.remove(itemPosition);
				emails.remove(itemPosition);
				
				saveArrayList(NOMES, nomes);
				saveArrayList(TELEFONES, telefones);
				saveArrayList(EMAILS, emails);
				
				Toast.makeText(ListaContatoActivity.this, "Item deletado com sucesso", Toast.LENGTH_SHORT).show();
			}
			catch(Exception e){
				Toast.makeText(ListaContatoActivity.this, "Erro ao deletar item" + itemPosition , Toast.LENGTH_SHORT).show();
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
						Intent intent = new Intent(ListaContatoActivity.this, MainActivity.class);
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
