package com.sam.daggerjavamvvm.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sam.daggerjavamvvm.R;
import com.sam.daggerjavamvvm.di.factory.CatsViewModelFactory;
import com.sam.daggerjavamvvm.models.CatModel;
import com.sam.daggerjavamvvm.models.Response;
import com.sam.daggerjavamvvm.viewmodels.MainActivityViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainProgressBar)
    ProgressBar mainProgressBar;
    @BindView(R.id.catsRecyclerView)
    RecyclerView catsRecyclerView;


    @Inject
    CatsViewModelFactory viewModelFactory;

    MainActivityViewModel mainActivityViewModel;

    CatAdapter catAdapter = new CatAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidInjection.inject(this);

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
        mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);
    }

    private void initiateObservers() {

        mainActivityViewModel.response.observe(this, this::processResponse);
    }

    private void getMyCats() {
        mainActivityViewModel.getCats();
    }

    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case SUCCESS:
                renderDataState(response.data);
                break;

            case ERROR:
                renderErrorState(response.error);
                break;
        }
    }


    private void renderLoadingState() {
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    private void renderDataState(ArrayList<CatModel> catModels) {
        mainProgressBar.setVisibility(View.GONE);

        if (catModels != null && catModels.size() > 0) {
            catAdapter.data.addAll(catModels);
            catAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void renderErrorState(Throwable throwable) {
        mainProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
