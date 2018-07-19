package vn.hieuruoi.gallerytest;


import java.util.ArrayList;

public class ImageData {

    private String mNameFolder;
    private ArrayList<String> mListPathImage;

    public ImageData() {
    }

    public ImageData(String nameFolder, ArrayList<String> listPathImage) {
        this.mNameFolder = nameFolder;
        this.mListPathImage = listPathImage;
    }

    public String getNameFolder() {
        return mNameFolder;
    }

    public void setNameFolder(String mNameFolder) {
        this.mNameFolder = mNameFolder;
    }

    public ArrayList<String> getListPathImage() {
        return mListPathImage;
    }

    public void setListPathImage(ArrayList<String> mListPathImage) {
        this.mListPathImage = mListPathImage;
    }
}