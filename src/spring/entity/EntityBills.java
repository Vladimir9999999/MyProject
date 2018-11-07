package spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "bills", schema = "public", catalog = "ice")
public class EntityBills {

    public static final int PAY_METHOD_CASHBACK = 1;
    public static final int PAY_METHOD_CASH = 2 ;
    public static final int PAY_METHOD_TERMINAL = 3;


    private long id;
    private Long orderId;
    private Long tranzaction;
    private int type;
    private EntityOrders ordersByOrderId;

    private EntityTransactions transactionsByTranzaction;



    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

/*
    @Basic
    @Column(name = "order_id")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


    @Basic
    @Column(name = "tranzaction")
    public Long getTranzaction() {
        return tranzaction;
    }

    public void setTranzaction(Long tranzaction) {
        this.tranzaction = tranzaction;
    }
*/

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

        EntityBills that = (EntityBills) o;

        if (id != that.id) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (tranzaction != null ? !tranzaction.equals(that.tranzaction) : that.tranzaction != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (tranzaction != null ? tranzaction.hashCode() : 0);
        return result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public EntityOrders getOrdersByOrderId() {
        return ordersByOrderId;
    }


    public void setOrdersByOrderId(EntityOrders ordersByOrderId) {
        this.ordersByOrderId = ordersByOrderId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tranzaction", referencedColumnName = "id")
    public EntityTransactions getTransactionsByTranzaction() {
        return transactionsByTranzaction;
    }

    public void setTransactionsByTranzaction(EntityTransactions transactionsByTranzaction) {
        this.transactionsByTranzaction = transactionsByTranzaction;
    }

}
