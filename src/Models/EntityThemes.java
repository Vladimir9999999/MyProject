package Models;

import javax.persistence.*;
@Deprecated
@Entity
@Table(name = "themes", schema = "public", catalog = "ice")
public class EntityThemes {
    private int themeId;
    private String themeName;
    private Long shopId;
    private String parameters;

    @Id
    @Column(name = "theme_id")
    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    @Basic
    @Column(name = "theme_name")
    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    @Basic
    @Column(name = "shop_id")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "parameters")
    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityThemes that = (EntityThemes) o;

        if (themeId != that.themeId) return false;
        if (themeName != null ? !themeName.equals(that.themeName) : that.themeName != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;
        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = themeId;
        result = 31 * result + (themeName != null ? themeName.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }
}
