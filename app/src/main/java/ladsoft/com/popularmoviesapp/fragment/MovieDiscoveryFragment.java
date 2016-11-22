package ladsoft.com.popularmoviesapp.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.adapter.MovieDiscoveryAdapter;
import ladsoft.com.popularmoviesapp.databinding.FragmentMainBinding;

public class MovieDiscoveryFragment extends Fragment {

    private FragmentMainBinding binding;
    private MovieDiscoveryAdapter<Object> adapter;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.forecastList.setLayoutManager(layoutManager);
        binding.forecastList.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //TODO: load stub data
        adapter.setDataSource(new ArrayList<>());
    }

}
