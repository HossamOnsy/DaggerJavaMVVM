package com.sam.daggerjavamvvm.models;

import androidx.annotation.NonNull;

import com.sam.daggerjavamvvm.models.enums.Status;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;

import static com.sam.daggerjavamvvm.models.enums.Status.ERROR;
import static com.sam.daggerjavamvvm.models.enums.Status.LOADING;
import static com.sam.daggerjavamvvm.models.enums.Status.SUCCESS;

public class Response {

    public final Status status;

    @Nullable
    public final ArrayList<CatModel> data;

    @Nullable
    public final Throwable error;

    private Response(Status status, @Nullable ArrayList<CatModel> data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static Response loading() {
        return new Response(LOADING, null, null);
    }

    public static Response success(@NonNull ArrayList<CatModel> data) {
        return new Response(SUCCESS, data, null);
    }

    public static Response error(@NonNull Throwable error) {
        return new Response(ERROR, null, error);
    }
}