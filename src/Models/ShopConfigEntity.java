package Models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Deprecated
@Entity
@Table(name = "shop_config", schema = "public", catalog = "order")
public class ShopConfigEntity {
    private Long shopId;
    private Long superAdmin;
    private Integer catalog;
    private Integer style;
    private Boolean type;

    @Basic
    @Column(name = "shop_id")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "super_admin")
    public Long getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Long superAdmin) {
        this.superAdmin = superAdmin;
    }

    @Basic
    @Column(name = "catalog")
    public Integer getCatalog() {
        return catalog;
    }

    public void setCatalog(Integer catalog) {
        this.catalog = catalog;
    }

    @Basic
    @Column(name = "style")
    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    @Basic
    @Column(name = "type")
    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopConfigEntity that = (ShopConfigEntity) o;

        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (superAdmin != null ? !superAdmin.equals(that.superAdmin) : that.superAdmin != null) return false;
        if (catalog != null ? !catalog.equals(that.catalog) : that.catalog != null) return false;
        if (style != null ? !style.equals(that.style) : that.style != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shopId != null ? shopId.hashCode() : 0;
        result = 31 * result + (superAdmin != null ? superAdmin.hashCode() : 0);
        result = 31 * result + (catalog != null ? catalog.hashCode() : 0);
        result = 31 * result + (style != null ? style.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
