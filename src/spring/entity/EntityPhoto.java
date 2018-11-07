package spring.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "photo", schema = "public", catalog = "ice")

public class EntityPhoto {

    private long id;

    private long shopId;

    private String description;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "shop_id")
    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityPhoto photo = (EntityPhoto) o;
        return id == photo.id &&
                shopId == photo.shopId &&
                Objects.equals(description, photo.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, description);
    }
}
