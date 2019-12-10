package com.gas.test.http;

import com.gas.test.bean.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TestService {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

}
