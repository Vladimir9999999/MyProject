package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "amount", schema = "public", catalog = "ice")
public class EntityAmount {

    private long id;
    private long shopId;
    private long employeeId;
    private String comment;
    private double amount;
    private Timestamp date;

    private int type;
    public static final int PENALTY = 1;
    public static final int PRIZE = 2;

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
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


    @Basic
    @Column(name = "amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityAmount amount1 = (EntityAmount) o;
        return id == amount1.id &&
                shopId == amount1.shopId &&
                employeeId == amount1.employeeId &&
                Double.compare(amount1.amount, amount) == 0 &&
                type == amount1.type &&
                Objects.equals(comment, amount1.comment) &&
                Objects.equals(date, amount1.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, employeeId, comment, amount, date, type);
    }
}
