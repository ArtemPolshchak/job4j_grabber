package ru.job4j.grabber;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author artem.polschak@gmail.com on 07.08.2022.
 * @project job4j_grabber
 */

public class Post {

    private int id;
    private String title;
    private String link;
    private String description;
    private LocalDateTime created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        Post post = (Post) o;
        return getId() == post.getId()
                && Objects.equals(getTitle(), post.getTitle())
                && Objects.equals(getLink(), post.getLink())
                && Objects.equals(getCreated(), post.getCreated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getLink(), getCreated());
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id
                + ", title='" + title + '\''
                + ", link='" + link + '\''
                + ", description='" + description
                + '\'' + ", created=" + created + '}';
    }
}
