package com.sam.daggerjavamvvm.ui;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sam.daggerjavamvvm.R;
import com.sam.daggerjavamvvm.repositories.CatRepository;
import com.sam.daggerjavamvvm.viewmodels.MainActivityViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sam.daggerjavamvvm.utils.NetworkUtils.createWebService;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainProgressBar)
    ProgressBar mainProgressBar;
    @BindView(R.id.catsRecyclerView)
    RecyclerView catsRecyclerView;

    MainActivityViewModel mainActivityViewModel = new MainActivityViewModel(new CatRepository(createWebService()));

    CatAdapter catAdapter = new CatAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // initializing recyclerview with required parameters
        initiateRecyclerView();
        // initiate ViewModel
        initiateViewModel();
        // initiate Observer
        initiateObservers();
        // initiate call of network to get the cats
        getMyCats();

    }


    private void initiateRecyclerView() {
        // initializing catAdapter with empty list
        catAdapter = new CatAdapter(new ArrayList<>());
        // apply allows you to alter variables inside the object and assign them
        catsRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        catsRecyclerView.setAdapter(catAdapter);
    }

    private void initiateViewModel() {
        // this is how we initialize viewModel
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
    }

    private void initiateObservers() {
        // a watcher to check when data is being retrieved
        mainActivityViewModel.catListMLD.observe(this, catModels -> {
            if (catModels != null && catModels.size() > 0) {
                catAdapter.data.addAll(catModels);
                catAdapter.notifyDataSetChanged();

            } else {
                Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
            }
        });
        // to show or hide progress bar
        mainActivityViewModel.progressBarVisibility.observe(this, progressBarVisibility -> {
            mainProgressBar.setVisibility(progressBarVisibility);
        });

        // to show error if something occured
        mainActivityViewModel.errorOccured.observe(this, errorOccured -> {
            Toast.makeText(this, errorOccured, Toast.LENGTH_SHORT).show();
        });
    }

    private void getMyCats() {
        mainActivityViewModel.getCats();
    }

}
