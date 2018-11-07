package spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "shop_user", schema = "public", catalog = "ice")
public class EntityShopUser {
    private long userId;
    private EntityShopsCluster shopsClusterByShopCluster;

    @Basic
    @Id
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

        EntityShopUser that = (EntityShopUser) o;

        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (userId ^ (userId >>> 32));
    }

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_cluster", referencedColumnName = "id", nullable = false)
    public EntityShopsCluster getShopsClusterByShopCluster() {
        return shopsClusterByShopCluster;
    }

    public void setShopsClusterByShopCluster(EntityShopsCluster shopsClusterByShopCluster) {
        this.shopsClusterByShopCluster = shopsClusterByShopCluster;
    }
}
