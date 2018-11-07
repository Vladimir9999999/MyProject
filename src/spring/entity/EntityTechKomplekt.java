package spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "tech_komplekt", schema = "public", catalog = "ice")
public class EntityTechKomplekt implements Comparable<EntityTechKomplekt> {
    private long id;
    private long product;
    private long nextProduct;
    private Long shopId;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "product")
    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    @Basic
    @Column(name = "next_product")
    public long getNextProduct() {
        return nextProduct;
    }

    public void setNextProduct(long nextProduct) {
        this.nextProduct = nextProduct;
    }

    @Basic
    @Column(name = "shop_id")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityTechKomplekt that = (EntityTechKomplekt) o;

        if (id != that.id) return false;
        if (product != that.product) return false;
        if (nextProduct != that.nextProduct) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (product ^ (product >>> 32));
        result = 31 * result + (int) (nextProduct ^ (nextProduct >>> 32));
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(EntityTechKomplekt o) {
        return 0;
    }
}
