package com.sam.daggerjavamvvm.viewmodels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sam.daggerjavamvvm.di.usecases.LoadCatsUseCase;
import com.sam.daggerjavamvvm.models.Response;
import com.sam.daggerjavamvvm.repositories.CatRepository;
import com.sam.daggerjavamvvm.models.CatModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {

    CatRepository catRepository ;

    Disposable compositeDisposable = new CompositeDisposable();

    private final LoadCatsUseCase loadCatsUseCase;


    public MutableLiveData<ArrayList<CatModel>> catListMLD = new MutableLiveData<>() ;
    public MutableLiveData<Integer> progressBarVisibility = new MutableLiveData<>() ;
    public MutableLiveData<String> errorOccured = new MutableLiveData<>() ;
    public MutableLiveData<Response> response = new MutableLiveData<>() ;


    public MainActivityViewModel(LoadCatsUseCase loadCatsUseCase) {
        this.loadCatsUseCase = loadCatsUseCase;

    }


    public void getCats (){

        compositeDisposable = loadCatsUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::doOnComplete)
                .doOnSubscribe(__ -> response.setValue(Response.loading()))
                .subscribe(
                        catModelArrayList -> response.setValue(Response.success(catModelArrayList)),
                        throwable -> response.setValue(Response.error(throwable))
                );
    }

    private void startSubscribing(Disposable disposable) {
        progressBarVisibility.setValue(View.VISIBLE);
    }

    private void doOnComplete() {
        progressBarVisibility.setValue(View.GONE);
    }

    private void showError(Throwable throwable) {
        errorOccured.setValue(throwable.getMessage());
    }

    private void doSomething(ArrayList<CatModel> catModels) {
        catListMLD.setValue(catModels);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();

    }
}
