package co.edu.udem.lenguajes2.empanadasgaming;

import java.util.ArrayList;
import java.util.List;

import co.edu.udem.lenguajes2.empanadasgaming.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SummonerDataActivity extends Activity{
	
	ProgressDialog pDialog;
	TextView txtSummonerName, txtSummonerLvl;
	Button btnSearch;
	ImageView summonerIcon;
	String bundleName, bundleRegion;
	ListView lvTeams;
	
	private static String url = null;
	private static String urlteam = null;
	private static String TAG_USER = null;
	private static String TAG_ID = "id";
	private static String TAG_NAME = "name";
	private static String TAG_PROFILE_ICON = "profileIconId";
	private static String TAG_SUMMONER_LVL = "summonerLevel";
	private static String TAG_TEAM_NAME = "name";
	private static String TAG_TEAM_TAG = "tag";
	private static String TAG_TEAM_LASTGAMEDATE = "lastGameDate";
	
	JSONObject user;
	JSONArray userteams;
	String id;
	String name;
	String profileiconid;
	String summonerlevel;
	String teamname;
	String teamtag;
	Long teamlastgamedate;
	int resID=0;
	
	CustomListViewAdapter adapter;
	List<ListViewItem> items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.summoner_data);
		
		txtSummonerName = (TextView)findViewById(R.id.txtSummonerName);
		txtSummonerLvl = (TextView)findViewById(R.id.txtSummonerLvl);
		summonerIcon = (ImageView)findViewById(R.id.ivProfileIconId);
		lvTeams = (ListView)findViewById(R.id.lvTeams);
		items = new ArrayList<SummonerDataActivity.ListViewItem>();
		
		Bundle bundle = getIntent().getExtras();
		bundleName = bundle.getString("Summoner");
		bundleRegion = bundle.getString("Region");

		Typeface font = Typeface.createFromAsset(getAssets(), "HelveticaNeueLTStd-UltLt.otf");
		txtSummonerLvl.setTypeface(font);
		
		TAG_USER = bundleName;
		url = "http://lan.api.pvp.net/api/lol/"+bundleRegion+"/v1.4/summoner/by-name/"+bundleName+"?api_key=122bf351-77da-476e-92d5-85bfb513811d";
		new getData().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private String calcularDias(Long numero){
		String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm").format(new java.util.Date(numero));
		return date;
	} 
	
	public class getData extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SummonerDataActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					// Getting JSON Array node
					user = jsonObj.getJSONObject(TAG_USER);
					id = user.getString(TAG_ID);
					name = user.getString(TAG_NAME);
					profileiconid = user.getString(TAG_PROFILE_ICON);
					summonerlevel = user.getString(TAG_SUMMONER_LVL);
					String imagen = "a"+profileiconid;
					resID = getResources().getIdentifier(imagen, "drawable", getPackageName());
					urlteam = "http://lan.api.pvp.net/api/lol/"+bundleRegion+"/v2.4/team/by-summoner/"+id+"?api_key=122bf351-77da-476e-92d5-85bfb513811d";
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			if (pDialog.isShowing())
				pDialog.dismiss();
			
			txtSummonerName.setText(name);
			summonerIcon.setImageResource(resID);
			txtSummonerLvl.setText(summonerlevel);
			new getTeamData().execute();
		}
	}

	public class getTeamData extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SummonerDataActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall(urlteam, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					userteams = jsonObj.getJSONArray(id);
					for (int i = 0; i < userteams.length(); i++) {
						JSONObject c = userteams.getJSONObject(i);
						
						teamname = c.getString(TAG_TEAM_NAME);
						teamtag = c.getString(TAG_TEAM_TAG);
						teamlastgamedate = Long.valueOf(c.getString(TAG_TEAM_LASTGAMEDATE));
						
						items.add(new ListViewItem(){{
							TeamName = teamname;
							TeamTag = teamtag;
							TeamLastGameDate = calcularDias(teamlastgamedate);
						}});
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			if (pDialog.isShowing())
				pDialog.dismiss();
			
			adapter = new CustomListViewAdapter(SummonerDataActivity.this, items);
			lvTeams.setAdapter(adapter);
		}
	}

	public class ListViewItem{
		public String TeamName;
		public String TeamTag;
		public String TeamLastGameDate;
	}
}