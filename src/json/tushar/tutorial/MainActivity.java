package json.tushar.tutorial;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

public class MainActivity extends Activity {

	String jsonUrl = "https://raw.github.com/tushroy/TutorialFiles/master/test.json";
	String jsonDataAfterParsing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
//		jsonMainThreadNetworkOperation();
		
		JsonAsyncUiUpdater backGroundUITask = new JsonAsyncUiUpdater(MainActivity.this);
		backGroundUITask.execute(jsonUrl);
		

	}

	private void jsonMainThreadNetworkOperation() {
		// extra. code segment for StrictMode
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();

			StrictMode.setThreadPolicy(policy);
		}
		// extra code segment ends
		
		// Network Operation and JSON Parsing Starts
		try {
			// getting JSONObject using JSONParser Class
			JSONObject mainJsonObj = JSONParser.getJSONObject(jsonUrl);

			// getting the JSONArray out of the mainJsonObj
			JSONArray jsonArray = mainJsonObj.getJSONArray("data");
			// here our json array name is "data"
			// visit this url to get understand
			// https://raw.github.com/tushroy/TutorialFiles/master/test.json"

			int arrayindex = 4;
			// getting JSONObject out of jsonArray using array index
			JSONObject tempJsonObject = jsonArray.getJSONObject(arrayindex);
			// we have retrieve json object of index 4 from json array

			// "name" or "id"
			jsonDataAfterParsing = tempJsonObject.getString("name");
			// Retrieving json data using attribute name, you can try "id"

		} catch (Exception e) {
			e.printStackTrace();
		}

		TextView display = (TextView) findViewById(R.id.display);
		display.setText(jsonDataAfterParsing);

	}
}
