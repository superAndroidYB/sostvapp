package activity.sostv.com.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.HashSet;
import java.util.Set;

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
    public SharedPreferences sp = null;

    public NewsContentListAdapter(Context context){
        super(context);
        this.context = context;
        bitmapUtils = new BitmapUtils(context);
        sp = context.getSharedPreferences("GRAY_SET", Context.MODE_PRIVATE);
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
            bitmapUtils.display(imageView, sosHome.getImageUrl());

            final TextView name = findView(R.id.tv_name);
            name.setText(sosHome.getTitle());
            Set<String> ids = getSetId();
            if(ids.contains(sosHome.getId())){
                name.setTextColor(context.getResources().getColor(R.color.DarkGray));
            }

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
                    saveSetId(sosHome.getId());
                    name.setTextColor(context.getResources().getColor(R.color.DarkGray));
                }
            });
        }

        private Set<String> getSetId(){
            Set<String> idSet = sp.getStringSet("GRAY_ID_SET", new HashSet<String>());
            return idSet;
        }

        private void saveSetId(String id){
            Set<String> idSet = sp.getStringSet("GRAY_ID_SET", new HashSet<String>());
            if(idSet.contains(id)){
                return;
            }
            idSet.add(id);
            sp.edit().putStringSet("GRAY_ID_SET",idSet).commit();
        }
    }
}
