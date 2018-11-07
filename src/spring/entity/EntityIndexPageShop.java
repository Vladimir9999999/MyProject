package spring.entity;


import javax.persistence.*;

@Entity
@Table(name = "index_page_shop",schema = "public",catalog = "ice")
public class EntityIndexPageShop {

    private long id;
    private long shopId;
    private String value;

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
    @Column(name = "shop_id",nullable = false)
    public long getShopId() {
        return shopId;
    }
    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
