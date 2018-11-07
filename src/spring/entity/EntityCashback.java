package spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "cashback", schema = "public", catalog = "ice")
public class EntityCashback {
    private long id;
    private long shopId;
    private Integer standard;
    private Integer silver;
    private Integer gold;
    private Integer partner;

    public static final int STANDART_CB = 1;
    public static final int SILVER_CB= 2;
    public static final int GOLD_CB = 3;

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
    @Column(name = "standard")
    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    @Basic
    @Column(name = "silver")
    public Integer getSilver() {
        return silver;
    }

    public void setSilver(Integer silver) {
        this.silver = silver;
    }

    @Basic
    @Column(name = "gold")
    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    @Basic
    @Column(name = "partner")
    public Integer getPartner() {
        return partner;
    }

    public void setPartner(Integer partner) {
        this.partner = partner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityCashback cashback = (EntityCashback) o;

        if (id != cashback.id) return false;
        if (shopId != cashback.shopId) return false;
        if (standard != null ? !standard.equals(cashback.standard) : cashback.standard != null) return false;
        if (silver != null ? !silver.equals(cashback.silver) : cashback.silver != null) return false;
        if (gold != null ? !gold.equals(cashback.gold) : cashback.gold != null) return false;
        if (partner != null ? !partner.equals(cashback.partner) : cashback.partner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (shopId ^ (shopId >>> 32));
        result = 31 * result + (standard != null ? standard.hashCode() : 0);
        result = 31 * result + (silver != null ? silver.hashCode() : 0);
        result = 31 * result + (gold != null ? gold.hashCode() : 0);
        result = 31 * result + (partner != null ? partner.hashCode() : 0);
        return result;
    }
}
