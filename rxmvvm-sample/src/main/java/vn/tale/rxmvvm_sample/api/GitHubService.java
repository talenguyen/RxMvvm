package vn.tale.rxmvvm_sample.api;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import vn.tale.rxmvvm_sample.model.User;

public interface GitHubService {
  class Factory {
    private static final Object LOCK = new Object();
    private static volatile GitHubService service;

    private Factory() {
      // NoOps
    }

    public static GitHubService create() {
      synchronized (LOCK) {
        if (service == null) {
          HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
          interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
          OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

          Retrofit retrofit = new Retrofit.Builder()
              .baseUrl("https://api.github.com/")
              .client(client)
              .addConverterFactory(GsonConverterFactory.create())
              .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
              .build();

          service = retrofit.create(GitHubService.class);
        }
        return service;
      }
    }
  }

  @GET("users")
  Observable<List<User>> getUsers();

}