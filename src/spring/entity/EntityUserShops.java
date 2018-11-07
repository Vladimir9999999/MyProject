package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "user_shops", schema = "public", catalog = "ice")
public class EntityUserShops {

    private long id;
    private long userId;
    private Boolean favorite;
    private Long shop;
    private double summ;
    private int cashback;
    private Timestamp lastBuy;
    private long ref;
    private float cashbackActive;
    private float cashbackInactive;
    private float hole;
    private float myCb;
    private float frndCb;

    private List<EntityMessage> messageList;

    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "favorite")
    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @Basic
    @Column(name = "shop")
    public Long getShop() {
        return shop;
    }

    public void setShop(Long shop) {
        this.shop = shop;
    }

    @Basic
    @Column(name = "summ")
    public double getSumm() {
        return summ;
    }

    public void setSumm(double summ) {
        this.summ = summ;
    }

    @Basic
    @Column(name = "cashback")
    public int getCashback() {
        return cashback;
    }

    public void setCashback(int cashback) {
        this.cashback = cashback;
    }

    @Basic
    @Column(name = "last_buy")
    public Timestamp getLastBuy() {
        return lastBuy;
    }

    public void setLastBuy(Timestamp lastBuy) {
        this.lastBuy = lastBuy;
    }
    @Basic
    @Column(name = "cashback_active")
    public float getCashbackActive() {
        return cashbackActive;
    }

    public void setCashbackActive(float cashbackActive) {
        this.cashbackActive = cashbackActive;
    }

    @Basic
    @Column(name = "cashback_inactive")
    public float getCashbackInactive() {
        return cashbackInactive;
    }

    public void setCashbackInactive(float cashbackInactive) {
        this.cashbackInactive = cashbackInactive;
    }

    @Basic
    @Column(name = "hole")
    public float getHole() {
        return hole;
    }

    public void setHole(float hole) {
        this.hole = hole;
    }

    @Id
    @Basic
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name="ref")
    public long getRef() {
        return ref;
    }

    public void setRef(long ref) {
        this.ref = ref;
    }
    @Basic
    @Column(name = "my_cb")
    public float getMyCb() {
        return myCb;
    }

    public void setMyCb(float myCb) {
        this.myCb = myCb;
    }
    @Basic
    @Column(name = "frnd_cb")
    public float getFrndCb() {
        return frndCb;
    }

    public void setFrndCb(float frndCb) {
        this.frndCb = frndCb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityUserShops that = (EntityUserShops) o;

        if (userId != that.userId) return false;
        if (favorite != null ? !favorite.equals(that.favorite) : that.favorite != null) return false;
        if (shop != null ? !shop.equals(that.shop) : that.shop != null) return false;
        if (lastBuy != null ? !lastBuy.equals(that.lastBuy) : that.lastBuy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (favorite != null ? favorite.hashCode() : 0);
        result = 31 * result + (shop != null ? shop.hashCode() : 0);
        result = 31 * result + (lastBuy != null ? lastBuy.hashCode() : 0);
        return result;
    }
    @ManyToMany(mappedBy = "entityUserShopsList")
    public List<EntityMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<EntityMessage> messageList) {
        this.messageList = messageList;
    }
}
