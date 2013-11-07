package json.tushar.tutorial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import json.tushar.sample.R;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class JsonAsyncUiUpdater extends AsyncTask<String, Void, JSONObject> {
	private Activity mContext;
	private TextView display;
	private ListView lView;

	public JsonAsyncUiUpdater(Activity mContext) {
		// without getting Activity context we can not use
		// findViewById inside AsyncTask
		this.mContext = mContext;
		display = (TextView) mContext.findViewById(R.id.display);
		lView = (ListView) mContext.findViewById(R.id.listView);
	}

	@Override
	protected JSONObject doInBackground(String... urlStrs) {
		// doing stuffs on background after completing, automatically
		// onPostExecute method is called
		JSONObject jsonObjResult = null;
		try {
			jsonObjResult = JSONParser.getJSONObject(urlStrs[0]);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObjResult;
	}
	@Override
	protected void onPostExecute(JSONObject result) {
		// this method is called after doInBackground()
		// method completed successfully
		String jsonArrayName = "data";
		String jsonAttribName = "name";
		String jsonAttribId = "id";

		String nameStr = null;
		String idStr = null;

		JSONArray jsonArr = null;

		try {
			// getting json array out of JSONObject result
			jsonArr = result.getJSONArray(jsonArrayName);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// creating array list hash map for storing json parsed data/values
		ArrayList<HashMap<String, String>> arrList = new ArrayList<HashMap<String, String>>();

		for (int index = 0; index < jsonArr.length(); index++) {
			try {
				// getting json object from array index
				JSONObject tempJsonObj = jsonArr.getJSONObject(index);

				// getting value of attribute using key/attribute names
				nameStr = tempJsonObj.getString(jsonAttribName);
				idStr = tempJsonObj.getString(jsonAttribId);

				// creating a HashMap<Key,Value>
				HashMap<String, String> hashMap = new HashMap<String, String>();
				// storing data in key value pair into hash map
				// using hashmap.put(key,value)
				hashMap.put(jsonAttribName, nameStr);
				hashMap.put(jsonAttribId, idStr);

				// adding hash map to the array list
				arrList.add(hashMap);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// Updating task for Application UI starts here

		// Creating list adapter for list view using our hashmap array list
		ListAdapter entriesAdapter = new SimpleAdapter(mContext, arrList,
				android.R.layout.two_line_list_item, new String[] {
						jsonAttribName, jsonAttribId }, new int[] {
						android.R.id.text1, android.R.id.text2 });
		// SimpleAdapter parameters: Context, List<? extends Map<String,?>>,
		// int android.R resource type,String[] from hash map key names,
		// int[] to android.R resource type where to put

		lView.setAdapter(entriesAdapter);

		display.setText("Updated!");
	}

}
