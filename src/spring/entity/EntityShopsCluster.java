package spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "shops_cluster", schema = "public", catalog = "ice")
public class EntityShopsCluster {
    private long id;
    private String shops;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shops")
    public String getShops() {
        return shops;
    }

    public void setShops(String shops) {
        this.shops = shops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityShopsCluster that = (EntityShopsCluster) o;

        if (id != that.id) return false;
        if (shops != null ? !shops.equals(that.shops) : that.shops != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (shops != null ? shops.hashCode() : 0);
        return result;
    }
}
