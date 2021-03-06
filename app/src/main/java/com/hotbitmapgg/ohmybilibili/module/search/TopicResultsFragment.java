package com.hotbitmapgg.ohmybilibili.module.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hotbitmapgg.ohmybilibili.R;
import com.hotbitmapgg.ohmybilibili.adapter.TopicResultsAdapter;
import com.hotbitmapgg.ohmybilibili.base.RxLazyFragment;
import com.hotbitmapgg.ohmybilibili.entity.search.SearchResult;
import com.hotbitmapgg.ohmybilibili.module.common.BrowserActivity;
import com.hotbitmapgg.ohmybilibili.utils.ConstantUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hcc on 16/9/4 12:26
 * 100332338@qq.com
 * <p/>
 * 话题搜索结果返回界面
 */
public class TopicResultsFragment extends RxLazyFragment
{

    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_view)
    ImageView mEmptyView;

    private SearchResult.ResultBean resultBean;

    private List<SearchResult.ResultBean.TopicBean> topics;

    public static TopicResultsFragment newInstance(SearchResult.ResultBean result)
    {

        TopicResultsFragment fragment = new TopicResultsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtils.EXTRA_DATA, result);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getLayoutResId()
    {

        return R.layout.fragment_search_result;
    }

    @Override
    public void finishCreateView(Bundle state)
    {

        resultBean = getArguments().getParcelable(ConstantUtils.EXTRA_DATA);
        initData();
    }

    private void initData()
    {

        topics = resultBean.getTopic();
        if (topics != null)
            if (topics.size() == 0)
                showEmptyView();
            else
                hideEmptyView();


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TopicResultsAdapter mAdapter = new TopicResultsAdapter(mRecyclerView, topics);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, holder) -> {

            SearchResult.ResultBean.TopicBean topicBean = topics.get(position);
            BrowserActivity.launch(getActivity(), topicBean.getArcurl(), topicBean.getTitle());
        });
    }

    public void showEmptyView()
    {

        mEmptyView.setVisibility(View.VISIBLE);
    }

    public void hideEmptyView()
    {

        mEmptyView.setVisibility(View.GONE);
    }
}
