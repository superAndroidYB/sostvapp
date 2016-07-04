package activity.sostv.com.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import activity.sostv.com.model.SosVideo;
import activity.sostv.com.sostvapp.R;

public class SosHomeZxtjGridViewAdapter extends BaseAdapter {

	private Context mContext;
	private List<SosVideo> datas;
	private BitmapUtils bitmapUtils;
	protected Typeface pingfang;
	
	public SosHomeZxtjGridViewAdapter(Context mContext,List<SosVideo> datas) {
		this.mContext = mContext;
		this.datas = datas;
		bitmapUtils = new BitmapUtils(mContext);
		bitmapUtils.configDefaultLoadingImage(R.mipmap.default_sos_img);
		bitmapUtils.configDefaultLoadFailedImage(R.mipmap.default_sos_img);
		pingfang = Typeface.createFromAsset(mContext.getAssets(), "fonts/PingFangLight.ttf");
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		if(datas == null){
			return null;
		}
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null){
			convertView = LayoutInflater.from(this.mContext).inflate(
					R.layout.home_grid_item, null, false);
			holder = new ViewHolder();
			holder.itemImageView = (ImageView) convertView.findViewById(R.id.iv_home_itemImg);
			holder.itemTextView = (TextView) convertView.findViewById(R.id.iv_home_itemTitle);
			holder.itemTextDesc = (TextView) convertView.findViewById(R.id.iv_home_itemDesc);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(datas != null){
			SosVideo video = datas.get(position);
			if(holder != null && video != null){
				bitmapUtils.display(holder.itemImageView, video.getVedioImage());
				holder.itemTextView.setText(video.getVideoName());
				holder.itemTextDesc.setText(video.getVideoName());
				
				holder.itemTextView.setTypeface(pingfang);
				holder.itemTextDesc.setTypeface(pingfang);
			}
		}
		
		return convertView;
	}

	static class ViewHolder {
		ImageView itemImageView;
		TextView itemTextView;
		TextView itemTextDesc;
	}
}
