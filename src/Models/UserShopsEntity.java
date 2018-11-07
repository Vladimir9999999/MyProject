package Models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Deprecated
@Entity
@Table(name = "user_shops", schema = "public", catalog = "order")
public class UserShopsEntity {

    private Long userId;
    private Boolean favorite = false;
    private Long shop;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserShopsEntity that = (UserShopsEntity) o;

        if (userId != that.userId) return false;
        if (favorite != null ? !favorite.equals(that.favorite) : that.favorite != null) return false;
        if (shop != null ? !shop.equals(that.shop) : that.shop != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (favorite != null ? favorite.hashCode() : 0);
        result = 31 * result + (shop != null ? shop.hashCode() : 0);
        return result;
    }
}
