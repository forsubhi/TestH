package house.find.com.housefinder2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.gson.Gson;

import java.util.Arrays;

import restful.RestClientS;

/**
 * Created by MohammedSubhi on 1/9/2015.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "MainFragment";

    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(final Session session, final SessionState state, final Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    LoginButton authButton;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        authButton = (LoginButton) view.findViewById(R.id.loginFb);
        authButton.setFragment(this);
        authButton.setReadPermissions(Arrays.asList("public_profile"));

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

            @Override
            public void onCompleted(
                    final GraphUser user, Response response) {
                if (user != null) {
                    // Display the parsed user info
                    Toast.makeText(getActivity(),user.getFirstName(),Toast.LENGTH_LONG).show();


                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Person p = new Person();
                            p.setFirst_name(user.getFirstName());
                            p.setLast_name(user.getLastName());

                            Gson gson = new Gson();
                            String personStr = gson.toJson( p);

                            RestClientS restClientS = new RestClientS(ImonaCloudServices.registerService,personStr);
                            String resp = restClientS.CallService();
                            resp.toString();

                        }
                    });
                    HomeScreenFragment homeScreen = new HomeScreenFragment();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(android.R.id.content, homeScreen)
                            .commit();
                    thread.start();

                }
            }
        });
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }
}
