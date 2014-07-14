package com.example.ideadtree;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {

	private ProgressDialog progress_bar;
	private static String url = "https://itunes.apple.com/search?term=Michael+jackson";
	
	//name of Json data
	private static final String TAG_SONGS = "results";

	private static final String TAG_NAME = "trackName";
	
	
	JSONArray songs = null;

	ArrayList<HashMap<String, String>> songList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		songList = new ArrayList<HashMap<String, String>>();

		ListView my_list = getListView();

		my_list.setOnItemClickListener(new OnItemClickListener() {


			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String name = ((TextView) view.findViewById(R.id.song_name))
						.getText().toString();
				

				// Starting single contact activity
				Intent in = new Intent(MainActivity.this, MySelectedSong.class);
				in.putExtra("key_name", name);
				startActivity(in);
			}
		});
		new GetSongs().execute();
	}
	
	private class GetSongs extends AsyncTask<Void, Void, Void> {

		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			progress_bar = new ProgressDialog(MainActivity.this);
			progress_bar.setMessage("Fetching Songs...");
			progress_bar.setCancelable(false);
			progress_bar.show();

		}
		
		protected Void doInBackground(Void... arg0) {
			JSONSongParser sh = new JSONSongParser();
			String jsonStr = sh.makeServiceCall(url, JSONSongParser.GET);
			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					songs = jsonObj.getJSONArray(TAG_SONGS);
					for (int i = 0; i < songs.length(); i++) {
						JSONObject c = songs.getJSONObject(i);
						String name = c.getString(TAG_NAME);
						HashMap<String, String> song = new HashMap<String, String>();
						song.put(TAG_NAME, name);
						songList.add(song);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e(" ParserError", "Error");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (progress_bar.isShowing())
				progress_bar.dismiss();
			ListAdapter adapter = new SimpleAdapter(MainActivity.this, songList, R.layout.songs_list, new String[] { TAG_NAME }, new int[] { R.id.song_name});
			setListAdapter(adapter);
		}

	}

}
