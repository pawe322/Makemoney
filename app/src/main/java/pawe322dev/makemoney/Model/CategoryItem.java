package pawe322dev.makemoney.Model;

public class CategoryItem {

    public String imageLink, name;

    public CategoryItem(String imageLink, String name) {
        this.imageLink = imageLink;
        this.name = name;
    }

    public CategoryItem() {
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getName() {
        return name;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setName(String name) {
        this.name = name;
    }
}
