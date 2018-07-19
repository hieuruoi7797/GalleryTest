package vn.hieuruoi.gallerytest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {
    private List<String> mImageList;
    private Context mContext;

    public GridViewAdapter(List<String> mImageList, Context context) {
        this.mImageList = mImageList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GridViewAdapter.ViewHolder holder, int position) {
        Glide.with(mContext).load(Constant.PATH + mImageList.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (mImageList == null) {
            return 0;
        } else {
            return mImageList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
        }
    }
}
