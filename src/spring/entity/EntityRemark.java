package spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "remark", schema = "public", catalog = "ice")
public class EntityRemark {
    private long id;
    private String msg;
    private int type;
    private long adresse;
    private long marketPlace;

    public static final int  REMARK_TYPE_ORDER_USER = 1;
    public static final int REMARK_TYPE_ORDER_SHOP = 2;
    public static final int REMARK_TYPE_USER_SHOP = 3;
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
    @Column(name = "msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "adresse")
    public long getAdresse() {
        return adresse;
    }

    public void setAdresse(long adresse) {
        this.adresse = adresse;
    }

    @Basic
    @Column(name = "market_place")
    public long getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(long marketPlace) {
        this.marketPlace = marketPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityRemark that = (EntityRemark) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (adresse != that.adresse) return false;
        if (marketPlace != that.marketPlace) return false;
        if (msg != null ? !msg.equals(that.msg) : that.msg != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (int) (adresse ^ (adresse >>> 32));
        result = 31 * result + (int) (marketPlace ^ (marketPlace >>> 32));
        return result;
    }
}
