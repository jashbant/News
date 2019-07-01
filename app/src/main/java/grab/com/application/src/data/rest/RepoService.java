package grab.com.application.src.data.rest;

import java.util.Map;

import grab.com.application.src.data.model.News;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RepoService {




   // @GET("top-headlines")
   // Single<News>  getNews(@Query("country") String country,@Query("apiKey") String apikey);
    @GET("top-headlines")
    Single<News>  getNews(@QueryMap(encoded = false) Map<String, String> options);



}