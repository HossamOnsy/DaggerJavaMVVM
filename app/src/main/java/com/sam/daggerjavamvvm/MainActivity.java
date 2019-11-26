package com.sam.daggerjavamvvm;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.sam.daggerjavamvvm.NetworkUtils.createWebService;
import static java.util.Collections.emptyList;

public class MainActivity extends AppCompatActivity {

    Disposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.mainProgressBar)
    ProgressBar mainProgressBar;
    @BindView(R.id.catsRecyclerView)
    RecyclerView catsRecyclerView;

    CatAdapter catAdapter = new CatAdapter(new ArrayList());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        // initializing recyclerview with required parameters
        initiateRecyclerView();


        CatApi catApi = createWebService();
        compositeDisposable = catApi.getCats(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::doSomething, this::showError);



    }

    private void showError(Throwable e) {
        e.printStackTrace();
        Log.e("ErrorMessageThrowable", "ErrorMessageThrowable ->> "  ) ;
    }

    private void doSomething(List<CatModel> carCategoryResponse) {
        if (carCategoryResponse != null && carCategoryResponse.size() > 0) {

            catAdapter.data.addAll(carCategoryResponse);
            catAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initiateRecyclerView() {
        // initializing catAdapter with empty list
         catAdapter = new CatAdapter(new ArrayList());
        // apply allows you to alter variables inside the object and assign them
        catsRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        catsRecyclerView.setAdapter( catAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
