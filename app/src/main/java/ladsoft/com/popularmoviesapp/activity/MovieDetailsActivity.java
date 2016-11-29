package ladsoft.com.popularmoviesapp.activity;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.databinding.ActivityMovieDetailsBinding;
import ladsoft.com.popularmoviesapp.fragment.MovieDetailsFragment;
import ladsoft.com.popularmoviesapp.model.Movie;

public class MovieDetailsActivity extends AppCompatActivity {
    ActivityMovieDetailsBinding binding;

    public final static String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        if (savedInstanceState == null) {
            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            if (movie != null) {
                inflateFragment(movie);
            } else {
                finish();
            }
        }
    }

    private void inflateFragment(Movie movie) {
        MovieDetailsFragment fragment = MovieDetailsFragment.newInstance(movie);

        getSupportFragmentManager().beginTransaction()
                .replace(binding.content.getId(), fragment)
                .commit();
    }
}
