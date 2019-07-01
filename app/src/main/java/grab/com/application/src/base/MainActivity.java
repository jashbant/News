package grab.com.application.src.base;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.grab.app.R;

import java.util.HashMap;

import javax.inject.Inject;

import grab.com.application.DataConstant;
import grab.com.application.ListFragment;
import grab.com.application.src.di.module.ListViewModel;
import grab.com.application.src.di.module.ViewModelFactory;


public class MainActivity extends BaseActivity {
    @Inject
    ViewModelFactory viewModelFactory;
    private ListViewModel viewModel;


    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                System.out.println("jashbant");
                int id = item.getItemId();
                dl.closeDrawers();
                HashMap<String,String> map= new HashMap<>();
                switch(id)
                {
                    case R.id.bbc_news:
                        System.out.println("jashbant");
                        map.put("sources","bbc-news");
                        map.put("apiKey",DataConstant.API_KEY);
                           setData(map);
                        break;
                    case R.id.germany_news:
                        map.put("country","de");
                        map.put("category","business");
                        map.put("apiKey",DataConstant.API_KEY);
                        setData(map);
                        viewModel.setData("germany_news");
                        break;
                    case R.id.trump_news:
                        map.put("q","trump");
                        map.put("apiKey",DataConstant.API_KEY);
                        setData(map);
                        break;
                    case R.id.us_headline:
                        map.put("country","us");
                        map.put("apiKey",DataConstant.API_KEY);
                        break;
                    default:
                        return true;
                }


                return true;

            }
        });

        HashMap<String,String> map= new HashMap<>();
        map.put("country","us");
        map.put("apiKey",DataConstant.API_KEY);
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",map);
        listFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.screenContainer, listFragment).addToBackStack(null).commit();


    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()==1){
            finish();

        }
        super.onBackPressed();
    }

    private void setData(HashMap<String,String> map){

        ListFragment listFragment = new ListFragment();
            Bundle bundle = new Bundle();

            bundle.putSerializable("data",map);
            listFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.screenContainer, listFragment).addToBackStack(null).commit();


            }
}
