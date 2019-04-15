package yeeet.com;

public class Recipe {
    private String mImageUrl;
    private String mTitle;
    private int mMinutes;

    public Recipe(String imageUrl, String title, int minutes){
        mImageUrl = imageUrl;
        mTitle = title;
        mMinutes = minutes;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getMinutes() {
        return mMinutes;
    }
}
