package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "delete_marker", schema = "public", catalog = "ice")
public class EntityDeleteMarker {
    private long id;
    private Timestamp date;

    private Set<EntityEmployee> entityEmployees;
    private Set<EntityCategoryShop> categoryShop;
    private List<EntityPrice> entityPrice;
    //private List<EntityOrders> orders;

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
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityDeleteMarker that = (EntityDeleteMarker) o;

        if (id != that.id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @OneToMany(fetch = FetchType.EAGER,  mappedBy = "deleteMarker", cascade = CascadeType.ALL)
    public Set<EntityEmployee> getEntityEmployees() {
        return entityEmployees;
    }

    public void setEntityEmployees(Set<EntityEmployee> entityEmployees) {
        this.entityEmployees = entityEmployees;
    }


    @OneToMany(fetch = FetchType.EAGER , mappedBy = "deleteMarker",cascade = CascadeType.ALL)
    public Set<EntityCategoryShop> getCategoryShop() {
        return categoryShop;
    }

    public void setCategoryShop(Set<EntityCategoryShop> categoryShop) {
        this.categoryShop = categoryShop;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "deleteMarker", cascade = CascadeType.ALL)
    public List<EntityPrice> getEntityPrice() {
        return entityPrice;
    }

    public void setEntityPrice(List<EntityPrice> entityPrice) {
        this.entityPrice = entityPrice;
    }
}
