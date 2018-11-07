package Models;

import javax.persistence.*;
@Deprecated
@Entity
@Table(name = "shop", schema = "public", catalog = "order")
public class ShopEntity {
    private long id;
    private String nameShop;
    private String serverIp;
    private Integer themeId;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name_shop")
    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    @Basic
    @Column(name = "server_ip")
    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopEntity that = (ShopEntity) o;

        if (id != that.id) return false;
        if (nameShop != null ? !nameShop.equals(that.nameShop) : that.nameShop != null) return false;
        if (serverIp != null ? !serverIp.equals(that.serverIp) : that.serverIp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (nameShop != null ? nameShop.hashCode() : 0);
        result = 31 * result + (serverIp != null ? serverIp.hashCode() : 0);
        return result;
    }
    @Override
    public String toString(){
        return "{\"id\":"+id+",\"nameShop\":\""+nameShop+"\",\"server_ip\":\""+serverIp+"\"}";
    }

    @Column(name =  "theme_id")
    public Integer getThemeId() {
        return themeId;
    }

    public void setThemId(Integer themeId) {
        this.themeId = themeId;
    }
}
