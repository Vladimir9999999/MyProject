package spring.entity;

import spring.interfaces.delete.Removable;
import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "price", schema = "public", catalog = "ice")
public class EntityPrice implements Removable{

    private long id;
    private EntityProduct productByProduct;
    private String description;

    private EntityCategoryShop categoryShop;

    private Integer reserve;

    private long shopId;
    private int priority;
    private EntityDeleteMarker deleteMarker;
    private boolean visible;
    private Set<EntityArticle> entityArticles;
    private Set<EntityEmployee> entityEmployees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityPrice that = (EntityPrice) o;
        return id == that.id &&
                shopId == that.shopId &&
                priority == that.priority &&
                visible == that.visible &&
                Objects.equals(productByProduct, that.productByProduct) &&
                Objects.equals(description, that.description) &&
                Objects.equals(categoryShop, that.categoryShop) &&
                Objects.equals(reserve, that.reserve) &&
                Objects.equals(deleteMarker, that.deleteMarker) &&
                Objects.equals(entityArticles, that.entityArticles) &&
                Objects.equals(entityEmployees, that.entityEmployees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productByProduct, description, categoryShop, reserve, shopId, priority, deleteMarker, visible, entityArticles, entityEmployees);
    }

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
    @Column(name = "reserve")
    public Integer getReserve() {

        return reserve;

    }

    public void setReserve(Integer reserve) {
        this.reserve = reserve;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product", referencedColumnName = "id")
    public EntityProduct getProductByProduct() {
        return productByProduct;
    }

    public void setProductByProduct(EntityProduct productByProduct) {
        this.productByProduct = productByProduct;
    }

    @ManyToOne
    @JoinColumn(name="category_shop", referencedColumnName = "id")
    public EntityCategoryShop getCategoryShop() {
        return categoryShop;
    }

    public void setCategoryShop(EntityCategoryShop categoryShop) {
        this.categoryShop = categoryShop;
    }

    @Basic
    @Column(name="shop_id",nullable = false)
    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shop_id) {
        this.shopId = shop_id;
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

    @Override
    public void setDeleteMarker(EntityDeleteMarker deleteMarker) {
        this.deleteMarker = deleteMarker;
    }

    @Basic
    @Column(name = "visible", nullable = false)
    public boolean isVisible() {

        return visible;

    }

    public void setVisible(boolean visible) {

        this.visible = visible;

    }

    @OneToMany(fetch = FetchType.EAGER,  mappedBy = "deleteMarker", cascade = CascadeType.ALL)
    public Set<EntityEmployee> getEntityEmployees() {
        return entityEmployees;
    }

    public void setEntityEmployees(Set<EntityEmployee> entityEmployees) {
        this.entityEmployees = entityEmployees;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "entityPrice",orphanRemoval = true)
    public Set<EntityArticle> getEntityArticles() {
        return entityArticles;
    }

    public void setEntityArticles(Set<EntityArticle> entityArticles) {
        this.entityArticles = entityArticles;
    }
}
