package ladsoft.com.popularmoviesapp.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.databinding.ActivityMainBinding;
import ladsoft.com.popularmoviesapp.fragment.MovieDiscoveryFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if(savedInstanceState == null) {
            inflateFragment();
        }
    }

    private void inflateFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(binding.content.getId(), MovieDiscoveryFragment.newInstance())
                .commit();
    }
}
