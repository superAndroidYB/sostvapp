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

import activity.sostv.com.global.CommonUtils;
import activity.sostv.com.model.SosBooks;
import activity.sostv.com.sostvapp.R;

public class SosShelfAdapter extends BaseAdapter{

	private List<SosBooks> mList;
	private Context mContext;
	private BitmapUtils bitmapUtils;
	protected Typeface pingfang;
	
	public SosShelfAdapter(List<SosBooks> mList, Context mContext) {
		this.mList = mList;
		this.mContext = mContext;
		bitmapUtils = new BitmapUtils(mContext);
		pingfang = Typeface.createFromAsset(mContext.getAssets(), "fonts/PingFangHeavy.ttf");
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
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
					R.layout.white_book_grid_item, null, false);
			holder = new ViewHolder();
			holder.itemImageView = (ImageView) convertView.findViewById(R.id.iv_book_itemImg);
			holder.itemTextView = (TextView) convertView.findViewById(R.id.iv_book_itemTitle);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(mList != null && !mList.isEmpty()){
			final SosBooks book = mList.get(position);
			if(holder != null && book != null){
				bitmapUtils.display(holder.itemImageView, book.getBookImage());
				holder.itemTextView.setText(CommonUtils.embellish(book.getBookName()));
				holder.itemTextView.setTypeface(pingfang);
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
