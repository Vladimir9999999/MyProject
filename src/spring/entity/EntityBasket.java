package spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "basket", schema = "public", catalog = "ice")
public class EntityBasket {

    private long id;
    private long shopId;
    private long userId;
    private String services;

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
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityBasket that = (EntityBasket) o;

        if (id != that.id) return false;
        if (shopId != that.shopId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (shopId ^ (shopId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }

    @Basic
    @Column(name = "services")
    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}

