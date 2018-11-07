package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "task_list", schema = "public", catalog = "ice")
public class EntityTaskList {
    private long id;
    private long orderId;
    private long employee;
    private String services;
    private Timestamp time;
    private  int length;
    private EntityOrders orders;



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
    @Column(name = "employee")
    public long getEmployee() {
        return employee;
    }

    public void setEmployee(long employee) {
        this.employee = employee;
    }

    @Basic
    @Column(name = "services")
    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "length")
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @ManyToOne
    @JoinColumn( name = "order_id", referencedColumnName = "id")
    public EntityOrders getOrders() {

        return orders;

    }

    public void setOrders(EntityOrders orders) {
        this.orders = orders;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityTaskList that = (EntityTaskList) o;

        if (id != that.id) return false;
        if (orderId != that.orderId) return false;
        if (employee != that.employee) return false;
        if (services != null ? !services.equals(that.services) : that.services != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + (int) (employee ^ (employee >>> 32));
        result = 31 * result + (services != null ? services.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
