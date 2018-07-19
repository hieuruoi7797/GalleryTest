package vn.hieuruoi.gallerytest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<ImageData> mListItem = new ArrayList<>();
    private AdapterFolder mAdapterFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_image);
        checkPermission();
    }

    private void checkPermission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constant.REQUEST_PERMISSIONS);
            }
        } else {
            getImagePath();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constant.REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        getImagePath();
                    } else {
                        Toast.makeText(MainActivity.this, Constant.MESSAGE_ERRO, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    public List<ImageData> getImagePath() {
        mListItem.clear();
        int position = 0;
        boolean isFolder = false;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, projection,
                null, null, orderBy + Constant.SORT_ORDER);

        int indexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int indexFolder = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext()) {
            String imagePath = cursor.getString(indexData);
            for (int i = 0; i < mListItem.size(); i++) {
                if (mListItem.get(i).getNameFolder().equals(cursor.getString(indexFolder))) {
                    isFolder = true;
                    position = i;
                    break;
                } else {
                    isFolder = false;
                }
            }

            if (isFolder) {
                ArrayList<String> listPath = new ArrayList<>();
                listPath.addAll(mListItem.get(position).getListPathImage());
                listPath.add(imagePath);
                mListItem.get(position).setListPathImage(listPath);
            } else {
                ArrayList<String> listPath = new ArrayList<>();
                listPath.add(imagePath);
                ImageData itemImage = new ImageData();
                itemImage.setNameFolder(cursor.getString(indexFolder));
                itemImage.setListPathImage(listPath);
                mListItem.add(itemImage);
            }
        }

        mAdapterFolder = new AdapterFolder(mListItem, getApplicationContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, Constant.SPAN_COUNT));
        mRecyclerView.setAdapter(mAdapterFolder);
        mAdapterFolder.notifyDataSetChanged();
        return mListItem;
    }
}
