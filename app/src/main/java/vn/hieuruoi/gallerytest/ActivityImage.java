package vn.hieuruoi.gallerytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActivityImage extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private List<String> mListItem = new ArrayList<>();
    private GridViewAdapter mAdapterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerview = findViewById(R.id.rv_image);
        mListItem = getIntent().getStringArrayListExtra(Constant.VALUE);
        mAdapterImage = new GridViewAdapter(mListItem, this);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setAdapter(mAdapterImage);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, Constant.SPAN_COUNT));
        mAdapterImage.notifyDataSetChanged();
    }
}