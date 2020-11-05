package tech.shmy.portal.application.domain;

public class PostEntity {
   public Integer id;
   public String title;
   public String author;

    public PostEntity(Integer id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
