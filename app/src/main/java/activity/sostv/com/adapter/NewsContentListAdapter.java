package activity.sostv.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import activity.sostv.com.model.SosHome;
import activity.sostv.com.sostvapp.R;
import activity.sostv.com.ui.NewContentActivity;
import activity.sostv.com.widght.BaseViewHolder;
import activity.sostv.com.widght.RoundImageView;

/**
 * Created by Administrator on 2016/6/4.
 */
public class NewsContentListAdapter extends BaseRecyclerAdapter<SosHome>{

    private BitmapUtils bitmapUtils;
    private Context context;

    public NewsContentListAdapter(Context context){
        super(context);
        this.context = context;
        bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public BaseViewHolder<SosHome> onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder vh = new SosHomeViewHolder(parent);
        return vh;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<SosHome> holder, int position) {
        SosHome entity = getItem(position);
        holder.bind(entity);
    }

    private class SosHomeViewHolder extends BaseViewHolder<SosHome> {

        public SosHomeViewHolder (ViewGroup parent){
            super(parent, R.layout.layout_news_content_info);
        }

        @Override
        public void bind(final SosHome sosHome) {
            final Context context = itemView.getContext();
            RoundImageView imageView = findView(R.id.avatar);
            bitmapUtils.display(imageView,sosHome.getImageUrl());

            TextView name = findView(R.id.tv_name);
            name.setText(sosHome.getTitle());

            TextView follower = findView(R.id.tv_follower);
            follower.setText(context.getString(R.string.like_count, sosHome.getLoveNum()));

            TextView postCount = findView(R.id.tv_post_count);
            postCount.setText(context.getString(R.string.post_count, sosHome.getCollectNum()));

            TextView description = findView(R.id.tv_description);
            description.setText(sosHome.getContent());

            itemView.setTag(R.id.key_slug, sosHome.getId());
            itemView.setTag(R.id.key_name, sosHome.getTitle());

            findView(R.id.ripple_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewContentActivity.start(context, sosHome);
                }
            });
        }
    }
}
