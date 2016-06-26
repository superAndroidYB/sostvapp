package activity.sostv.com.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import activity.sostv.com.adapter.SosShelfAdapter;
import activity.sostv.com.global.Constants;
import activity.sostv.com.global.WebServiceHelper;
import activity.sostv.com.model.SosBooks;
import activity.sostv.com.model.SostvRequest;
import activity.sostv.com.model.SostvResponse;
import activity.sostv.com.sostvapp.R;
import io.bxbxbai.common.T;
import io.bxbxbai.common.utils.GlobalExecutor;

public class WhiteBookActivity extends BaseActivity {

    @ViewInject(R.id.whiteBooksGridView)
    private GridView shelfGrid;
    private SosShelfAdapter shelfAdapter;
    private List<SosBooks> books ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_book);
        initToolBar();
        setTitle(R.string.whiteBook);
        ViewUtils.inject(this);

        books = new ArrayList<SosBooks>();
        shelfAdapter = new SosShelfAdapter(books, this);
        shelfGrid.setAdapter(shelfAdapter);

        shelfGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                SosBooks book = books.get(arg2);
                jumpBookActivity(book);
            }
        });

        loadData();
    }

    public void jumpBookActivity(SosBooks book){
        Intent intent = new Intent(this, BookInfoActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("book", book);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    private void loadData(){

        taskTool = new LoadBooksData();
        SostvRequest request = new SostvRequest();
        request.setProperty(0, Constants.WHITE_BOOKS);
        request.setProperty(1, "FragmentBooks_WhitBook");
        taskTool.execute(request);
    }

    private class LoadBooksData extends WebServiceHelper {

        @Override
        protected void onPostExecute(SostvResponse result) {
            if(result == null) {
                return;
            }
            if(!Constants.CODE.equals(result.getReturnCode())){
                T.showToast("服务器内部错误,请稍后重试");
                return;
            }
            String data = result.getReturnData();
            Gson gson = new Gson();
            List<SosBooks> list = gson.fromJson(data, new TypeToken<List<SosBooks>>(){}.getType());
            books.addAll(0,list);
            shelfAdapter.notifyDataSetChanged();
        }
    }

    public static void start(final Context context) {
        final Intent intent = new Intent(context, WhiteBookActivity.class);
        GlobalExecutor.MAIN_HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(intent);
            }
        }, 300);
    }
}
