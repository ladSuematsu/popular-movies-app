package ladsoft.com.popularmoviesapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.activity.MovieDetailsActivity;
import ladsoft.com.popularmoviesapp.adapter.FavoritesAdapter;
import ladsoft.com.popularmoviesapp.adapter.MovieDiscoveryAdapter;
import ladsoft.com.popularmoviesapp.data.MovieContract;
import ladsoft.com.popularmoviesapp.databinding.FragmentMainBinding;
import ladsoft.com.popularmoviesapp.model.Movie;
import ladsoft.com.popularmoviesapp.mvp.model.MovieDiscoveryModel;
import ladsoft.com.popularmoviesapp.mvp.MovieDiscoveryMvp;
import ladsoft.com.popularmoviesapp.mvp.MovieDiscoveryPresenterFactory;
import ladsoft.com.popularmoviesapp.util.UiUtils;
import ladsoft.com.popularmoviesapp.view.layoutmanager.decoration.SimplePaddingDecoration;

public class MovieDiscoveryFragment extends Fragment implements MovieDiscoveryMvp.View<Movie>, MovieDiscoveryAdapter.Callback<Movie>,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MovieDiscoveryFragment.class.getSimpleName();
    private String STATE_SELECTED_FILTER = "state_selected_filter";
    private String STATE_LIST_CONTENT = "state_list_content";
    private String STATE_LIST = "state_list";

    private FragmentMainBinding binding;
    private MovieDiscoveryMvp.Presenter<MovieDiscoveryMvp.View<Movie>> presenter;
    private MovieDiscoveryAdapter<Movie> adapter;
    private FavoritesAdapter favoritesAdapter;
    private static final int FAVORITES_LOADER = 0;
    private Loader<Cursor> loader;
    private int selectedFilterItemPosition;

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

        LayoutInflater inflater = getLayoutInflater(savedInstanceState);
        adapter = new MovieDiscoveryAdapter<>(inflater);
        adapter.setCallback(this);

        favoritesAdapter = new FavoritesAdapter(inflater, null);
        favoritesAdapter.setCallback(this);

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int rotation = windowManager.getDefaultDisplay().getRotation();
        int listSpan = rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180
                ? 2 : 3;

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), listSpan);
        binding.movieDiscoveryList.setLayoutManager(layoutManager);
        binding.movieDiscoveryList.addItemDecoration(
                new SimplePaddingDecoration(getResources().getDimensionPixelSize(R.dimen.list_item_content_margin)));
        binding.movieDiscoveryList.setAdapter(adapter);

        ArrayAdapter sortSelectorAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.movie_discovery_sort_selector_options, R.layout.spinner_item_inverse);
        sortSelectorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sortBySelector.setAdapter(sortSelectorAdapter);
        binding.sortBySelector.setOnTouchListener(selectorListener);
        binding.sortBySelector.setOnItemSelectedListener(selectorListener);
    }

    Parcelable listInstanceState;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter = MovieDiscoveryPresenterFactory.create(new MovieDiscoveryModel(getContext().getContentResolver()));
        presenter.attachView(this);
        loader = getLoaderManager().initLoader(FAVORITES_LOADER, null, this);

        if(savedInstanceState == null) {
            presenter.loadData(MovieDiscoveryMvp.SORT_TYPE_MOST_POPULAR);
        } else {
            selectedFilterItemPosition = savedInstanceState.getInt(STATE_SELECTED_FILTER);
            List<Movie> movies = savedInstanceState.getParcelableArrayList(STATE_LIST_CONTENT);
            adapter.setDatasource(movies);

            listInstanceState = savedInstanceState.getParcelable(STATE_LIST);
            if (selectedFilterItemPosition == MovieDiscoveryMvp.SORT_TYPE_USER_FAVORITES) {
                binding.movieDiscoveryList.setAdapter(favoritesAdapter);
            } else {
                binding.movieDiscoveryList.getLayoutManager().onRestoreInstanceState(listInstanceState);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (selectedFilterItemPosition == MovieDiscoveryMvp.SORT_TYPE_USER_FAVORITES) {
            loader.forceLoad();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_FILTER, binding.sortBySelector.getSelectedItemPosition());
        outState.putParcelableArrayList(STATE_LIST_CONTENT, (ArrayList<Movie>) adapter.getDatasource());
        outState.putParcelable(STATE_LIST, binding.movieDiscoveryList.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onItemClick(View view, Movie movie) {
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movie);

        Pair<View, String> sharedElements = Pair.create(view, getString(R.string.movie_details_transition_name_poster));

        UiUtils.startActivityWithSharedElementTrans((AppCompatActivity) getActivity(), intent, sharedElements);
    }

    @Override
    public void refreshMovies(List<Movie> videos) {
        binding.movieDiscoveryList.setAdapter(adapter);
        adapter.setDatasource(videos);
    }

    @Override
    public void showFavorites() {
        Log.i(TAG, "Swapping adapter list with favorites adapter");
        binding.movieDiscoveryList.setAdapter(favoritesAdapter);
        loader.forceLoad();
    }

    @Override
    public void showSnackbar(int messageResourceId) {
        UiUtils.showSnackbar(binding.getRoot(), getString(messageResourceId),
                getString(R.string.movie_discovery_ok), Snackbar.LENGTH_SHORT, null);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "Creating loader");
        return new CursorLoader(getActivity(),
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.MovieEntry.COLUMN_IS_FAVORITE + "=?",
                new String[] {"1"},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(TAG, "Loading cursor into favorites adapter");

        if(data == null) {
            showSnackbar(R.string.movie_discovery_favorites_load_error);
        }

        favoritesAdapter.swapCursor(data);
        binding.movieDiscoveryList.getLayoutManager().onRestoreInstanceState(listInstanceState);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i(TAG, "Resetting favorites adapter");
        favoritesAdapter.swapCursor(null);
    }

    SelectorListener selectorListener = new SelectorListener();
    private class SelectorListener implements AdapterView.OnItemSelectedListener,
        View.OnTouchListener {
        private boolean userInteraction;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            userInteraction = true;
            return false;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(userInteraction) {
                selectedFilterItemPosition = i;
                adapter.clearData();
                presenter.loadData(i);
            }

            userInteraction = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }
}
