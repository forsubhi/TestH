package restful;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class RestClientS {

	String URL;

	String jsonStr;
	


	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public RestClientS(String url, String jsonStr) {
		this.jsonStr = jsonStr;

		URL = url;
	}

	public String CallService() {
		 boolean result = false;
		HttpPost p = new HttpPost(URL);
		HttpClient hc = new DefaultHttpClient();
		 String responseBody="";
		try {
			String message = jsonStr;

			p.setEntity(new StringEntity(message, "UTF8"));
			p.setHeader(HTTP.CONTENT_TYPE, "application/json");
			
			p.addHeader(BasicScheme.authenticate(
           		 new UsernamePasswordCredentials(RestClient.UserName, RestClient.Password),
           		 "UTF-8", false));
			
			HttpResponse resp = hc.execute(p);
			if (resp != null) {
				if (resp.getStatusLine().getStatusCode() == 204)
					result = true;
				
				HttpEntity entity = resp.getEntity();
			    if(entity != null) {
			         responseBody = EntityUtils.toString(entity);
			      
			    }
			}

			Log.d("Status line", "" + resp.getStatusLine().getStatusCode());

			
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return responseBody;
	}

}
