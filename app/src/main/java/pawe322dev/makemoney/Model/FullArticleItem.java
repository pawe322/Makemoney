package pawe322dev.makemoney.Model;

public class FullArticleItem {

    public String imageLink, title, content, articleId, subtitle;

    public FullArticleItem(String imageLink, String title, String content, String articleId, String subtitle) {
        this.imageLink = imageLink;
        this.title = title;
        this.content = content;
        this.articleId = articleId;
        this.subtitle = subtitle;
    }

    public FullArticleItem() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticleId() {
        return content;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

}
