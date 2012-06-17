		//	Toast.makeText(getApplicationContext(), "dziala", Toast.LENGTH_SHORT).show();

package org.mf.view;

import java.util.List;
import java.util.Random;

import org.mf.R;
import org.mf.model.Account;
import org.mf.model.Transaction;
import org.mf.model.sqlite.AccountsDataSource;
import org.mf.parser.Parser;
import org.mf.parser.ParserImpl;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class mfActivity extends ListActivity implements OnItemSelectedListener{
	private AccountsDataSource datasource;
	private Parser parser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		
		datasource = new AccountsDataSource(this);
		datasource.open();

		List<Account> values = datasource.getAllAccounts();
		parser = new ParserImpl(datasource);
		
		

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Account> adapter = new ArrayAdapter<Account>(this,
				android.R.layout.simple_list_item_1, datasource.getAllAccounts());
		setListAdapter(adapter);
		
		Spinner spinnerAccFrom = null;
		spinnerAccFrom = (Spinner)this.findViewById(R.id.lista_kont_z);
		
		Account fromAccount = new Account(0, "From");
		Account toAccount = new Account(0, "To");
		values.add(0, fromAccount);
	//	List<Account> accounts = new List<Account>(fromAccount, toAccount); 
		
		ArrayAdapter spinnerAdapterFrom = new ArrayAdapter(this, android.R.layout.simple_spinner_item, 
				//datasource.getAllAccounts().toArray()
				values.toArray()
				);
		spinnerAccFrom.setAdapter(spinnerAdapterFrom);
		
		
		// Tell the spinner what to do when an item is changed
        spinnerAccFrom.setOnItemSelectedListener(this);
        
        
        spinnerAccFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                
            	Object item = parent.getItemAtPosition(pos);
                EditText text = (EditText)findViewById(R.id.pole_tekstowe);
                String command = text.getText().toString();
                if (!item.toString().equals("From")){                	
                	 text.setText(command + " from " + item + "");
                     text.setSelection(text.getText().length());
                }
               
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        
        values.set(0, toAccount);
        Spinner spinnerAccTo = null;
		spinnerAccTo = (Spinner)this.findViewById(R.id.lista_kont_do);
		ArrayAdapter spinnerAdapterTo = new ArrayAdapter(this, android.R.layout.simple_spinner_item, values);
		spinnerAccTo.setAdapter(spinnerAdapterTo);
		
		// Tell the spinner what to do when an item is changed
        spinnerAccTo.setOnItemSelectedListener(this);
        
        
        spinnerAccTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                EditText text = (EditText)findViewById(R.id.pole_tekstowe);
                String command = text.getText().toString();
                if (!item.toString().equals("To")){
                	
               	 text.setText(command + " to " + item + "");
                    text.setSelection(text.getText().length());
               }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
		
	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		
		@SuppressWarnings("unchecked")
		ArrayAdapter<Account> adapter = (ArrayAdapter<Account>) getListAdapter();
		Account account = null;
		
		
		EditText text = (EditText)findViewById(R.id.pole_tekstowe);
		switch (view.getId()) {
		
		case R.id.send:
			String command=null;
			
			command = text.getText().toString();
			try{
			Transaction transaction = parser.parse(command);
			}catch(RuntimeException e){
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				//TODO print exception to user
			}
			text.setText(" ");

			
			break;
/*		case R.id.from:
			command = text.getText().toString() + " from ";
			text.setText(command);
			text.setSelection(command.length());
			
			break;
		case R.id.to:
			command = text.getText().toString() + " to ";
			text.setText(command);
			text.setSelection(command.length());
			break;*/
		case R.id.add:
			float[] accounts = {1.0f,2.0f,3.0f};
			int nextInt = new Random().nextInt(accounts.length);
			// Save the new comment to the database
			account = datasource.createAccount(accounts[nextInt],"Acc "+accounts[nextInt]);
			adapter.add(account);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				account = (Account) getListAdapter().getItem(0);
				datasource.deleteAccount(account);
				adapter.remove(account);
			}
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}