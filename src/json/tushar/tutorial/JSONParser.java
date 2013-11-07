package json.tushar.tutorial;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
	JSONObject jsonObj;

	public JSONParser(String url) throws ParseException,
			ClientProtocolException, JSONException, IOException {
		// My ultimate constructor 
		jsonObj = new JSONObject(EntityUtils.toString(new DefaultHttpClient()
				.execute(new HttpGet(url)).getEntity()));
	}

	public JSONObject getJSONObject() {
		return jsonObj;
	}

	public static JSONObject  getJSONObject(String url)
			throws ClientProtocolException, IOException, JSONException {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest req = new HttpGet(url);
		HttpResponse resp = httpClient.execute(req);
		HttpEntity entity = resp.getEntity();
		String temp = EntityUtils.toString(entity);
		JSONObject obj = new JSONObject(temp);
		return obj;

	}
}
