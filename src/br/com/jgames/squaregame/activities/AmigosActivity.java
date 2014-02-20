package br.com.jgames.squaregame.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import br.com.jgames.squaregame.R;
import br.com.jgames.squaregame.views.MainFragment;

public class AmigosActivity extends FragmentActivity {

    private MainFragment mainFragment;

    @Override
    public void onCreate(Bundle bundle) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(bundle);
        setContentView(R.layout.amigos);

        if (bundle == null) {
            // Add the fragment on initial activity setup
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.main, container, false);
//        return view;
//    }
}
