package grab.com.application.src.data.rest;

import java.util.Map;

import javax.inject.Inject;

import grab.com.application.src.data.model.News;
import io.reactivex.Single;


public class RepoRepository {

    private final RepoService repoService;

    @Inject
    public RepoRepository(RepoService repoService) {
        this.repoService = repoService;
    }


    public Single<News> getNews(Map<String,String> map) {
        return repoService.getNews(map);
    }


}