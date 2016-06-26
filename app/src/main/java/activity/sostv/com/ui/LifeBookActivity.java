package activity.sostv.com.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activity.sostv.com.adapter.SostvExpandableAdapter;
import activity.sostv.com.model.SosBooks;
import activity.sostv.com.sostvapp.R;
import activity.sostv.com.widght.SostvExpandableListView;
import io.bxbxbai.common.utils.GlobalExecutor;

public class LifeBookActivity extends BaseActivity {

    @ViewInject(R.id.explistview)
    private SostvExpandableListView expListView;
    private SostvExpandableAdapter adapter;

    private Map<String, List<SosBooks>> childrenData;
    private String[] groupData;
    private int expandFlag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_book);
        ViewUtils.inject(this);
        initToolBar();
        setTitle(R.string.lifeBook);

        loadData();

    }
    private void loadData() {
        loadGroupData();
        loadChildData();


        // 设置悬浮头部VIEW
        expListView.setHeaderView(LayoutInflater.from(this).inflate(
                R.layout.sostv_expandable_list_group_head, null));
        adapter = new SostvExpandableAdapter(childrenData, groupData,
                LifeBookActivity.this, expListView);
        expListView.setAdapter(adapter);

    }

    private void loadChildData(){
        childrenData = new HashMap<String, List<SosBooks>>();
        List<SosBooks> dbBooks = new ArrayList<SosBooks>();
        try {
            dbBooks = db.findAll(SosBooks.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < groupData.length; i++) {
            childrenData.put(groupData[i], dbBooks);
        }
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, LifeBookActivity.class);
        GlobalExecutor.MAIN_HANDLER.postDelayed(new Runnable() {

            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 300);
    }

    public void loadGroupData(){
        groupData = new String[]{"现代真理系列丛书","就近真光指引","回归圣经指引","讲道集专辑","翻译重排专辑","名著翻译专辑"};
    }


    class GroupClickListener implements ExpandableListView.OnGroupClickListener {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {
            if (expandFlag == -1) {
                // 展开被选的group
                expListView.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                expListView.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            } else if (expandFlag == groupPosition) {
                expListView.collapseGroup(expandFlag);
                expandFlag = -1;
            } else {
                expListView.collapseGroup(expandFlag);
                // 展开被选的group
                expListView.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                expListView.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            }
            return true;
        }
    }
}
