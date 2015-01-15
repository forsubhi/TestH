package house.find.com.housefinder2;

import android.os.AsyncTask;

import restful.RequestMethod;
import restful.RestClient;

/**
 * Created by MohammedSubhi on 1/14/2015.
 */
public class WebService extends AsyncTask<Void,Void,Void> {
    String url = "";
    String result;
    private AfterCallWebService afterCallWebService;
    private RequestMethod request = RequestMethod.GET;

    public WebService(String url) {
        super();
        this.url = url;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (afterCallWebService!=null)
            afterCallWebService.handler(result);
    }


    @Override
    protected Void doInBackground(Void... params) {
        RestClient client = new RestClient(url);
        try
        {
            client.Execute(request);
            result = client.getResponse();
        }
        catch (Exception exc){}
        return null;
    }
}
