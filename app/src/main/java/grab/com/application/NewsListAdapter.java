package grab.com.application;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.grab.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import grab.com.application.src.data.model.Article;
import grab.com.application.src.data.model.News;
import grab.com.application.src.di.module.ListViewModel;
import grab.com.application.src.di.module.NewsSelectedListener;


public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.RepoViewHolder>{

    private NewsSelectedListener repoSelectedListener;
    private final List<Article> data = new ArrayList<>();
    LifecycleOwner lifecycleOwner;

    NewsListAdapter(ListViewModel viewModel, LifecycleOwner lifecycleOwner, NewsSelectedListener repoSelectedListener) {
        this.repoSelectedListener = repoSelectedListener;
        this.lifecycleOwner=lifecycleOwner;
        viewModel.getNews().observe(lifecycleOwner, new Observer<News>() {
            @Override
            public void onChanged(@Nullable News repos) {
                data.clear();
                if (repos != null) {
                    data.addAll(repos.getArticles());
                    notifyDataSetChanged();
                }
            }
        });

        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repo_list_item, parent, false);
        return new RepoViewHolder(view, repoSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).hashCode();
    }

    static final class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repo_name)
        TextView repoNameTextView;
        @BindView(R.id.news_img)
        ImageView newsImage;
        @BindView(R.id.tv_repo_description) TextView repoDescriptionTextView;
        @BindView(R.id.tv_forks) TextView forksTextView;
        @BindView(R.id.tv_stars) TextView starsTextView;

        private Article repo;
View itemView;
        RepoViewHolder(View itemView,final NewsSelectedListener repoSelectedListener) {
            super(itemView);
            this.itemView=itemView;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(repo != null) {
                        repoSelectedListener.onNewsSelected(repo);
                    }
                }
            });

        }

        void bind(Article repo) {
            this.repo = repo;
            repoDescriptionTextView.setText(repo.getDescription());
            repoNameTextView.setText(repo.getTitle());
            Glide
                    .with(itemView.getContext())
                    .load(repo.getUrlToImage())
                    .placeholder(R.drawable.item_dummy_noimg)
                    .into(newsImage);

        }
    }
}
