package house.find.com.housefinder2;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RestClient {

	public static String UserName = "";

	public static String Password = "";

	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;

	private String url;

	private int responseCode;
	private String message;

	private String response;

	public String getResponse() {
		return response;
	}

	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public RestClient(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public void AddParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public void Execute(RequestMethod method) throws Exception {
		switch (method) {
		case GET: {
			// add parameters
			String combinedParams = "";
			if (!params.isEmpty()) {
				combinedParams += "?";
				for (NameValuePair p : params) {
					String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
					if (combinedParams.length() > 1) {
						combinedParams += "&" + paramString;
					} else {
						combinedParams += paramString;
					}
				}
			}

			// authen
			/*
			 * DefaultHttpClient httpclient = new DefaultHttpClient();
			 * 
			 * httpclient.getCredentialsProvider().setCredentials( new
			 * AuthScope(null, -1), new UsernamePasswordCredentials("sd",
			 * "ds"));
			 */

			HttpGet request = new HttpGet(url + combinedParams);

			request.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(UserName, Password), "UTF-8",
					false));

			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}

			executeRequest(request, url);
			break;
		}
		case POST: {
			HttpPost request = new HttpPost(url);

			request.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(UserName, Password), "UTF-8",
					false));

			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}

			if (!params.isEmpty()) {
				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}

			executeRequest(request, url);
			break;
		}
		}
	}

	private void executeRequest(HttpUriRequest request, String url) {
		HttpClient client = new DefaultHttpClient();

		HttpResponse httpResponse;

		try {
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {

				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);

				// Closing the input stream will trigger connection release
				instream.close();
			}

		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
		}
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	// I used synchronized because the server cannot take two calls in the same
	// time
	public synchronized void ExecuteSync(final RequestMethod method) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					
					Log.d("exp:ExecuteSync", "Execute start");
					Execute(method);

					

					Log.d("exp:ExecuteSync", "Execute end");
				} catch (Exception e) {

					e.printStackTrace();
					Log.d("exp:ExecuteSync", Log.getStackTraceString(e));
				}

			}
		});

		th.setPriority(9);
		th.start();

		try {
			th.join();
		} catch (InterruptedException e) {

			e.printStackTrace();

			Log.d("exp:ExecuteSync", Log.getStackTraceString(e));
		}

	}

	static RestClient singleton = null;

	public static RestClient getSingleton(String url) {
		if (singleton == null) {
			singleton = new RestClient(url);

		}

		return singleton;

	}

    public void ExecuteAndWaitForResult(final RequestMethod requestMethod) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Execute(requestMethod);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}