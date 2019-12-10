package pawe322dev.makemoney.Model;

public class FullArticleItem {

    public String imageLink, title, content, articleId, subtitle, registerLink, buttonName;

    public FullArticleItem(String imageLink, String title, String content, String articleId, String subtitle, String registerLink, String buttonName) {
        this.imageLink = imageLink;
        this.title = title;
        this.content = content;
        this.articleId = articleId;
        this.subtitle = subtitle;
        this.registerLink = registerLink;
        this.buttonName = buttonName;
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
        return articleId;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getRegisterLink() {
        return registerLink;
    }

    public void setRegisterLink(String registerLink) {
        this.registerLink = registerLink;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
}
