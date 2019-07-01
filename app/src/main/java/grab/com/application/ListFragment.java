package grab.com.application;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grab.app.R;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import grab.com.application.src.base.BaseFragment;
import grab.com.application.src.data.model.Article;
import grab.com.application.src.data.model.News;
import grab.com.application.src.di.module.DetailsFragment;
import grab.com.application.src.di.module.ListViewModel;
import grab.com.application.src.di.module.NewsSelectedListener;
import grab.com.application.src.di.module.ViewModelFactory;


public class ListFragment extends BaseFragment implements NewsSelectedListener {

    @BindView(R.id.recyclerView)

    RecyclerView listView;
    @BindView(R.id.tv_error)
    TextView errorTextView;
    @BindView(R.id.loading_view) View loadingView;

    @Inject
    ViewModelFactory viewModelFactory;
    private ListViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.screen_list;
    }

    @Override
    public void onViewCreated(@NonNull View view,  Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);

        listView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(new NewsListAdapter(viewModel, this, this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        HashMap<String,String> map= new HashMap<>();
        observableViewModel();
        if(getArguments()!=null) {
        map = (HashMap<String,String>)getArguments().getSerializable("data");
        }
        System.out.println("jashbant data"+map);
        setData(map);
    }

    public void setData(HashMap<String,String> map){

        viewModel.fetchNews(map);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onNewsSelected(Article repo) {
        System.out.println("jashbant"+repo.getUrl());

        DetailsFragment detail = DetailsFragment.newInstance(repo.getUrl());
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.screenContainer, detail)
                .addToBackStack(null).commit();
    }

    private void observableViewModel() {



        viewModel.getNews().observe(this, new Observer<News>() {
            @Override
            public void onChanged(@Nullable News repos) {
                System.out.println("jashbant onChanged"+repos);
                if(repos != null) listView.setVisibility(View.VISIBLE);
                if(repos != null) listView.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isError) {
                if (isError != null) if(isError) {
                    System.out.println("jashbant error");
                    errorTextView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    errorTextView.setText("An Error Occurred While Loading Data!");
                }else {
                    errorTextView.setVisibility(View.GONE);
                    errorTextView.setText(null);
                }
            }
        });
        viewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if (isLoading != null) {
                    loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                    if (isLoading) {
                        errorTextView.setVisibility(View.GONE);
                        listView.setVisibility(View.GONE);
                    }
                }
            }
        });

    }
}