package spring.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "article", schema = "public", catalog = "ice")
public class EntityArticle {

    private long id;
    private EntityPrice entityPrice;

    private int length;
    private String name;
    private double price;
    private int count;
    private boolean visible;
    private Set<EntityParameters> parameters;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    public EntityPrice getEntityPrice() {
        return entityPrice;
    }

    public void setEntityPrice(EntityPrice entityPrice) {
        this.entityPrice = entityPrice;
    }
    @Basic
    @Column(name = "length")
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Basic
    @Column(name = "count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    @Basic
    @Column(name = "visible")
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "article_parameters",
            joinColumns = {
                    @JoinColumn(name = "article")
            },

            inverseJoinColumns = {
                    @JoinColumn(name = "parameter")
            }
    )
    public Set<EntityParameters> getParameters() {
        return parameters;
    }

    public void setParameters(Set<EntityParameters> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityArticle that = (EntityArticle) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
