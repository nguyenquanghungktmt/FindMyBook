package hust.hungnq.findmybook;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String authors;
    private final int imageResource;
    private final String description;

    public Book(String title, String authors, int imageResource, String description) {
        this.title = title;
        this.authors = authors;
        this.imageResource = imageResource;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getDescription() {
        return description;
    }
}
