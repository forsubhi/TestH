package house.find.com.housefinder2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by MohammedSubhi on 1/15/2015.
 */
public class IneedFragment extends Fragment {
    City[] cities;
    private ArrayList<City> tops = new ArrayList<>();
    private Spinner citiesSpinner;
    private ArrayList<City> provsList = new ArrayList<>();
    private ArrayList<City> citiesList =new ArrayList<>();
    private Spinner provsSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.i_need_layout,null);
        loadCities();
        for(City c:cities)
        {
            if(c.city.equals(c.province))
                citiesList.add(c);
            else
                provsList.add(c);
        }
        citiesSpinner = (Spinner) v.findViewById(R.id.cities);
        citiesSpinner.setAdapter(new ArrayAdapter<City>(getActivity(),android.R.layout.simple_spinner_item,citiesList));
        provsSpinner = (Spinner) v.findViewById(R.id.provs);
        provsSpinner.setAdapter(new ArrayAdapter<City>(getActivity(),android.R.layout.simple_spinner_item,provsList));
        return v;
    }
    void loadCities()
    {InputStream raw = null;
        Reader is = null;
        try {
            raw = getActivity().getResources().openRawResource(R.raw.cities);
            is = new BufferedReader(new InputStreamReader(raw, "UTF8"));

            if(is!=null)
            {
                StringBuilder stringBuilder = new StringBuilder();
                int c ;
                while ((c = is.read()) != -1) {
                    stringBuilder.append((char) c);
                }
                Gson gson = new Gson();
                cities = gson.fromJson(stringBuilder.toString(),City[].class);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }}

}
