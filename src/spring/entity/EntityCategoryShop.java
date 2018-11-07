package spring.entity;

import org.json.JSONObject;
import spring.interfaces.delete.Removable;
import javax.persistence.*;

@Entity
@Table(name = "category_shop", schema = "public", catalog = "ice")
public class EntityCategoryShop implements Removable{
    private long id;
    private long shopId;
    private EntityCategoryService categoryServiceByCategory;
    private  EntityDeleteMarker deleteMarker;
    private long parent;
    private int priority;
    private boolean visibility;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shop_id", nullable = false)
    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityCategoryShop that = (EntityCategoryShop) o;

        if (id != that.id) return false;
        if (shopId != that.shopId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (shopId ^ (shopId >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "category_id")
    public EntityCategoryService getCategoryServiceByCategory() {
        return categoryServiceByCategory;
    }

    public void setCategoryServiceByCategory(EntityCategoryService categoryServiceByCategory) {
        this.categoryServiceByCategory = categoryServiceByCategory;
    }


    @Basic
    @Column(name = "parent")
    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public JSONObject toJsonAndPrices(){
        JSONObject ret =  new JSONObject()
                .put("name",categoryServiceByCategory.getName())
                .put("id",id)
                .put("parent",parent)
                .put("priority",priority);
                return ret;
    }

    @Basic
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority",nullable = false)
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @ManyToOne
    @JoinColumn(name = "delete_mark", referencedColumnName = "id")
    public EntityDeleteMarker getDeleteMarker() {
        return deleteMarker;
    }

    public void setDeleteMarker(EntityDeleteMarker deleteMarker) {
        this.deleteMarker = deleteMarker;
    }

    @Basic
    @Column(name = "visibility")
    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
