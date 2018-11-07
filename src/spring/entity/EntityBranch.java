package spring.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "branch", schema = "public", catalog = "ice")
public class EntityBranch {

    private long id;
    private String address;
    private String name;
    private long phone;
    private double longitude;
    private double latitude;

    private EntityShop shop;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    public EntityShop getShop() {
        return shop;
    }

    public void setShop(EntityShop shop) {
        this.shop = shop;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    @Column(name = "phone")
    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityBranch branch = (EntityBranch) o;
        return id == branch.id &&
                phone == branch.phone &&
                Double.compare(branch.longitude, longitude) == 0 &&
                Double.compare(branch.latitude, latitude) == 0 &&
                Objects.equals(address, branch.address) &&
                Objects.equals(name, branch.name) &&
                Objects.equals(shop, branch.shop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, name, phone, longitude, latitude, shop);
    }
}
