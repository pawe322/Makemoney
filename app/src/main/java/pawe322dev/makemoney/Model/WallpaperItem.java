package pawe322dev.makemoney.Model;

public class WallpaperItem {

    public String categoryId, imageLink;

    public WallpaperItem(){}

    public WallpaperItem(String categoryId, String imageLink) {
        this.categoryId = categoryId;
        this.imageLink = imageLink;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
