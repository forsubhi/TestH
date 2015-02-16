package house.find.com.housefinder2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.widget.LoginButton;

import java.util.Arrays;

/**
 * Created by MohammedSubhi on 1/14/2015.
 */
public class HomeScreenFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_screen, container, false);
        View v = view.findViewById(R.id.i_need_button);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openINeed();
            }


        });
        return view;

    }

    private void openINeed() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new MapLayout())
                .commit();
    }
}
