package com.sam.daggerjavamvvm.viewmodels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sam.daggerjavamvvm.service.CatApi;
import com.sam.daggerjavamvvm.repositories.CatRepository;
import com.sam.daggerjavamvvm.models.CatModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.sam.daggerjavamvvm.utils.NetworkUtils.createWebService;

public class MainActivityViewModel extends ViewModel {

    CatRepository catRepository ;

    Disposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<ArrayList<CatModel>> catListMLD = new MutableLiveData<>() ;
    public MutableLiveData<Integer> progressBarVisibility = new MutableLiveData<>() ;
    public MutableLiveData<String> errorOccured = new MutableLiveData<>() ;

    public MainActivityViewModel(CatRepository catRepository) {
        this.catRepository = catRepository;
    }


    public void getCats (){

        compositeDisposable = catRepository.getCats(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::doOnComplete)
                .doOnSubscribe(this::startSubscribing)
                .subscribe(this::doSomething, this::showError);
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
