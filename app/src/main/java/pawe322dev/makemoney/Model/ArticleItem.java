package pawe322dev.makemoney.Model;

public class ArticleItem {

    public String imageLink, title, subtitle, articleId;

    public ArticleItem(String imageLink, String title, String subtitle, String articleId) {
        this.imageLink = imageLink;
        this.title = title;
        this.subtitle = subtitle;
        this.articleId = articleId;
    }

    public ArticleItem() {
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getArticleId() {
        return articleId;
    }
}
