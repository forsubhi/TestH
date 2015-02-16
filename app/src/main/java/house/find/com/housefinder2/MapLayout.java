package house.find.com.housefinder2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

/**
 * Created by MohammedSubhi on 1/28/2015.
 */
public class MapLayout extends Fragment implements AdapterView.OnItemClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getActivity(),R.layout.map_layout,null);
        AutoCompleteTextView autoCompView = (AutoCompleteTextView) v.findViewById(R.id.autoComplete);
        autoCompView.setAdapter(new AutoCompleteAdapter(getActivity(), R.layout.ac_tv));
        autoCompView.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String str = (String) parent.getItemAtPosition(position);
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

    }
}
