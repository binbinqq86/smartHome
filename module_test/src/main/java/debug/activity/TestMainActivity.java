package debug.activity;

import android.os.Bundle;
import android.view.View;

import com.example.test.R;
import com.tb.baselib.base.activity.BasicActivity;

public class TestMainActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.test_activity_main;
    }

    @Override
    protected void initViews(View contentView, View titlebarView, Bundle savedInstanceState) {

    }
}
