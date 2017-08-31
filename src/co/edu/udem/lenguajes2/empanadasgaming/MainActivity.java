package co.edu.udem.lenguajes2.empanadasgaming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnClickListener, OnItemSelectedListener{

	EditText txtSummonerID;
	Button btnSearch;
	String[] regions = new String[] {"NA", "BR", "LAN", "LAS"};
	Spinner spRegions;
	String Region = "na";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		txtSummonerID = (EditText)findViewById(R.id.txtSummonerID);
		btnSearch = (Button)findViewById(R.id.btnSearch);
		spRegions = (Spinner)findViewById(R.id.spRegions);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regions);
		
		btnSearch.setOnClickListener(this);
		spRegions.setOnItemSelectedListener(this);
		spRegions.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, SummonerDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Summoner", txtSummonerID.getText().toString());
        bundle.putString("Region", Region);
        intent.putExtras(bundle);
        startActivity(intent);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			Region = "na";
			break;
		case 1:
			Region = "br";
			break;
		case 2:
			Region = "lan";
			break;
		case 3:
			Region = "las";
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
