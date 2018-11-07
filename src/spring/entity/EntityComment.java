package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "comment", schema = "public", catalog = "ice")

public class EntityComment {

    private long id;
    private long shopId;
    private Long clientId;
    private Long orderId;
    private String text;
    private Byte diameter;
    private Byte shoeSize;
    private Timestamp time;

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
    @Column(name = "client_id")
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "order_id")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "diameter")
    public Byte getDiameter() {
        return diameter;
    }

    public void setDiameter(Byte diameter) {
        this.diameter = diameter;
    }

    @Basic
    @Column(name = "shoe_size")
    public Byte getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(Byte shoeSize) {
        this.shoeSize = shoeSize;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityComment that = (EntityComment) o;
        return id == that.id &&
                shopId == that.shopId &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(text, that.text) &&
                Objects.equals(diameter, that.diameter) &&
                Objects.equals(shoeSize, that.shoeSize) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shopId, clientId, orderId, text, diameter, shoeSize, time);
    }
}
