package spring.entity;



import javax.persistence.*;

@Entity
@Table(name = "partners", schema = "public", catalog = "ice")
public class EntityPartners {
    private long id;
    private long shop1;
    private long shop2;

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
    @Column(name = "shop_1")
    public long getShop1() {
        return shop1;
    }

    public void setShop1(long shop1) {
        this.shop1 = shop1;
    }

    @Basic
    @Column(name = "shop_2")
    public long getShop2() {
        return shop2;
    }

    public void setShop2(long shop2) {
        this.shop2 = shop2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityPartners that = (EntityPartners) o;

        if (id != that.id) return false;
        if (shop1 != that.shop1) return false;
        if (shop2 != that.shop2) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (shop1 ^ (shop1 >>> 32));
        result = 31 * result + (int) (shop2 ^ (shop2 >>> 32));
        return result;
    }
}
