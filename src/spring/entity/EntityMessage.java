package spring.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "public", catalog = "ice")

public class EntityMessage {

    private long id;
    private long shopId;
    private String message;
    private List<EntityUserShops> entityUserShopsList;
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
    @Column(name = "shop_id")
    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @ManyToMany
    @JoinTable(
            name = "message_user",
            joinColumns = {
                    @JoinColumn(name = "message_id")
            },

            inverseJoinColumns = {
                    @JoinColumn(name = "user_id")
            }
            )
    public List<EntityUserShops> getEntityUserShopsList() {
        return entityUserShopsList;
    }

    public void setEntityUserShopsList(List<EntityUserShops> entityUserShopsList) {
        this.entityUserShopsList = entityUserShopsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityMessage that = (EntityMessage) o;
        return id == that.id &&
                shopId == that.shopId &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, message);
    }


}
