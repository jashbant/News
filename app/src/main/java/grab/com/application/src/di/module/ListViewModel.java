package grab.com.application.src.di.module;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.HashMap;

import javax.inject.Inject;

import grab.com.application.src.data.model.News;
import grab.com.application.src.data.rest.RepoRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {

    private final RepoRepository repoRepository;
    private CompositeDisposable disposable;


    private final MutableLiveData<String> newsTag = new MutableLiveData<>();

    private final MutableLiveData<News> news = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public ListViewModel(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
        disposable = new CompositeDisposable();

    }




    public LiveData<News> getNews() {
        return news;
    }
   public LiveData<Boolean> getError() {
        return repoLoadError;
    }
  public  LiveData<Boolean> getLoading() {
        return loading;
    }

    public void setData(String str){

        newsTag.setValue(str);
    }

    public void fetchNews(HashMap<String,String> map) {
        loading.setValue(true);


        disposable.add(repoRepository.getNews(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<News>() {
                    @Override
                    public void onSuccess(News value) {
                        repoLoadError.setValue(false);
                        news.setValue(value);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
                        loading.setValue(false);
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
