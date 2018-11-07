package spring.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account_shop", schema = "public", catalog = "ice")
public class EntityAccountShop {

    private Integer id;
    private long shopId;
    private long employeeId;
    private String password;
    private String login;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shop_id")
    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "employee_id")
    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityAccountShop that = (EntityAccountShop) o;
        return shopId == that.shopId &&
                employeeId == that.employeeId &&
                Objects.equals(id, that.id) &&
                Objects.equals(password, that.password) &&
                Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shopId, employeeId, password, login);
    }
}
