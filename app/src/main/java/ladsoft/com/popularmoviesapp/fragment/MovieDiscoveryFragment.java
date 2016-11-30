package ladsoft.com.popularmoviesapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.List;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.activity.MovieDetailsActivity;
import ladsoft.com.popularmoviesapp.adapter.MovieDiscoveryAdapter;
import ladsoft.com.popularmoviesapp.api.parser.MovieSearchResult;
import ladsoft.com.popularmoviesapp.databinding.FragmentMainBinding;
import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.presenter.MovieDiscoveryPresenter;
import ladsoft.com.popularmoviesapp.presenter.MovieDiscoveryPresenterFactory;
import ladsoft.com.popularmoviesapp.util.UiUtils;

public class MovieDiscoveryFragment extends Fragment implements MovieDiscoveryPresenter.Callback<MovieSearchResult>,MovieDiscoveryAdapter.Callback<Movie> {

    private FragmentMainBinding binding;
    private MovieDiscoveryPresenter<Movie> presenter;
    private MovieDiscoveryAdapter<Movie> adapter;

    public static MovieDiscoveryFragment newInstance() {
        return new MovieDiscoveryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MovieDiscoveryAdapter<>(getLayoutInflater(savedInstanceState));
        adapter.setCallback(this);

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int rotation = windowManager.getDefaultDisplay().getRotation();
        int listSpan = rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_270
                ? 2 : 3;

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), listSpan);
        binding.movieDiscoveryList.setLayoutManager(layoutManager);
        binding.movieDiscoveryList.setAdapter(adapter);

        ArrayAdapter sortSelectorAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.movie_discovery_sort_selector_options, android.R.layout.simple_spinner_dropdown_item);
        binding.sortBySelector.setAdapter(sortSelectorAdapter);
        binding.sortBySelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.clearData();
                presenter.loadData(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter = MovieDiscoveryPresenterFactory.create(this);
//        presenter.loadData();
    }

    @Override
    public void onDataLoaded(MovieSearchResult movies) {
        adapter.setDataSource(movies.getResult());
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void onPresenterError(MovieDiscoveryPresenter.ErrorType errorType) {
        switch (errorType) {
            case DATA_LOAD_ERROR:
                UiUtils.showSnackbar(binding.getRoot(), getString(R.string.movie_discovery_data_load_error),
                        getString(R.string.movie_discovery_ok), Snackbar.LENGTH_SHORT, null);
            break;
        }
    }

}
