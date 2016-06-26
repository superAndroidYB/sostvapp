package activity.sostv.com.fragmet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import activity.sostv.com.adapter.NewsContentListAdapter;
import activity.sostv.com.global.Constants;
import activity.sostv.com.global.WebServiceHelper;
import activity.sostv.com.model.SosHome;
import activity.sostv.com.model.SostvRequest;
import activity.sostv.com.model.SostvResponse;
import activity.sostv.com.sostvapp.R;
import activity.sostv.com.widght.RecycleViewDivider;
import io.bxbxbai.common.view.CircularLoadingView;


public class NewsContentListFragment extends Fragment {

    @ViewInject(R.id.recycler_view)
    private RecyclerView recyclerView;
    @ViewInject(R.id.v_loading)
    private CircularLoadingView mLoadingView;
    @ViewInject(R.id.swipe_refresh_widget)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.pullMessage)
    private RelativeLayout pullMsgView;
    @ViewInject(R.id.dataCount)
    private TextView dataCount;

    private List<SosHome> datas;
    private NewsContentListAdapter mAdapter;
    public NewsContentListFragment() {
    }

    public static NewsContentListFragment newInstance() {
        NewsContentListFragment fragment = new NewsContentListFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_content_list, container, false);
        ViewUtils.inject(this, v);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));

        swipeRefreshLayout.setColorSchemeResources(R.color.white);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.RoyalBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PullHomeData pullTool = new PullHomeData();
                SostvRequest request = new SostvRequest();
                request.setProperty(0, Constants.HOME);
                request.setProperty(1, "HomeTabActivity");
                if (datas.isEmpty()) {
                    request.setProperty(2, "");
                } else {
                    request.setProperty(2, datas.get(0).getDate());
                }
                pullTool.execute(request);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new NewsContentListAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
        loadHomeData();
    }

    private void slideView(int count){
        if(count > 0){
            dataCount.setText("更新了" +  count + "条动态");
        }else{
            dataCount.setText("没有最新的动态");
        }
        TranslateAnimation animation = new TranslateAnimation(0f, 0f, 0-pullMsgView.getHeight(),0f);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(1000);
        pullMsgView.setVisibility(View.VISIBLE);
        pullMsgView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                slideViewReturn();
            }
        });
    }

    private void slideViewReturn(){
        TranslateAnimation animation = new TranslateAnimation(0f, 0f, 0f,0-pullMsgView.getHeight());
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(1000);
        pullMsgView.setVisibility(View.VISIBLE);
        pullMsgView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                pullMsgView.setVisibility(View.GONE);
            }
        });
    }

    private void loadHomeData(){
        datas = new ArrayList<>();
        PullHomeData pullTool = new PullHomeData();
        SostvRequest request = new SostvRequest();
        request.setProperty(0, Constants.HOME);
        request.setProperty(1, "HomeTabActivity");
        if(datas.isEmpty()){
            request.setProperty(2, "");
        }else{
            request.setProperty(2, datas.get(0).getDate());
        }
        pullTool.execute(request);
    }

    private class PullHomeData extends WebServiceHelper {

        @Override
        protected void onPostExecute(SostvResponse result) {
            if(result == null){
                return;
            }
            if(!Constants.CODE.equals(result.getReturnCode())){
                return;
            }
            String data = result.getReturnData();
            Gson gson = new Gson();
            List<SosHome> list = gson.fromJson(data, new TypeToken<List<SosHome>>(){}.getType());
            if(!list.isEmpty()){
                recyclerView.setVisibility(View.VISIBLE);
                datas.clear();
                datas.addAll(0, list);
                mAdapter.clear();
                mAdapter.addItemList(list);
                recyclerView.scrollToPosition(0);
            }
            mLoadingView.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            slideView((datas.size() - list.size()));
        }
    }
}

