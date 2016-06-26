package activity.sostv.com.adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;
import java.util.Map;

import activity.sostv.com.global.CommonUtils;
import activity.sostv.com.model.SosBooks;
import activity.sostv.com.sostvapp.R;
import activity.sostv.com.widght.SostvExpandableListView;


public class SostvExpandableAdapter extends BaseExpandableListAdapter implements
		SostvExpandableListView.HeaderAdapter {

	private Map<String, List<SosBooks>> childrenData;
	private String[] groupData;
	private Context context;
	private SostvExpandableListView listView;
	private LayoutInflater inflater;
	private BitmapUtils bitmapUtils;
	protected Typeface pingfang;

	public SostvExpandableAdapter(Map<String, List<SosBooks>> childrenData, String[] groupData,
			Context context, SostvExpandableListView listView) {
		this.groupData = groupData;
		this.childrenData = childrenData;
		this.context = context;
		this.listView = listView;
		inflater = LayoutInflater.from(this.context);
		bitmapUtils = new BitmapUtils(context);
		pingfang = Typeface.createFromAsset(context.getAssets(), "fonts/PingFangHeavy.ttf");
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childrenData.get(groupData[groupPosition]);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		SosBooks book = childrenData.get(groupData[groupPosition]).get(childPosition);
		View view = null;
		if (convertView != null) {
			view = convertView;
		} else {
			view = createChildrenView();
		}
		if(book != null){
			ImageView image = (ImageView) view.findViewById(R.id.groupIcon);
			TextView text = (TextView) view.findViewById(R.id.childto);
			text.setTypeface(pingfang);
			bitmapUtils.display(image, book.getBookImage());
			text.setText(CommonUtils.embellish(book.getBookName()));
		}
		
		return view;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return childrenData.get(groupData[groupPosition]).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupData[groupPosition];
	}

	@Override
	public int getGroupCount() {
		return groupData.length;
	}

	@Override
	public long getGroupId(int arg0) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = null;
		if (convertView != null) {
			view = convertView;
		} else {
			view = createGroupView();
		}

		ImageView iv = (ImageView) view.findViewById(R.id.groupIcon);

		if (isExpanded) {
			iv.setImageResource(R.drawable.btn_browser2);
		} else {
			iv.setImageResource(R.drawable.btn_browser);
		}

		TextView text = (TextView) view.findViewById(R.id.groupto);
		text.setText(groupData[groupPosition]);
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	private View createChildrenView() {
		return inflater.inflate(R.layout.sostv_expandable_list_child, null);
	}

	private View createGroupView() {
		return inflater.inflate(R.layout.sostv_expandable_list_group, null);
	}

	@Override
	public int getHeaderState(int groupPosition, int childPosition) {
		final int childCount = getChildrenCount(groupPosition);
		if (childPosition == childCount - 1) {
			return PINNED_HEADER_PUSHED_UP;
		} else if (childPosition == -1
				&& !listView.isGroupExpanded(groupPosition)) {
			return PINNED_HEADER_GONE;
		} else {
			return PINNED_HEADER_VISIBLE;
		}
	}

	@Override
	public void configureHeader(View header, int groupPosition,
			int childPosition, int alpha) {
		String groupData = this.groupData[groupPosition];
		((TextView) header.findViewById(R.id.groupto)).setText(groupData);
	}

	private SparseIntArray groupStatusMap = new SparseIntArray();

	@Override
	public void setGroupClickStatus(int groupPosition, int status) {
		groupStatusMap.put(groupPosition, status);
	}

	@Override
	public int getGroupClickStatus(int groupPosition) {
		if (groupStatusMap.keyAt(groupPosition) >= 0) {
			return groupStatusMap.get(groupPosition);
		} else {
			return 0;
		}
	}

}
