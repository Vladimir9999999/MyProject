package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "transactions", schema = "public", catalog = "ice")
public class EntityTransactions {

    private long id;
    private Long sender;
    private Long payee;
    private Timestamp dete;
    private Long marketplace;
    private double summ;
    private Integer currency;
    private EntityBills bills;

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
    @Column(name = "sender")
    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    @Basic
    @Column(name = "payee")
    public Long getPayee() {
        return payee;
    }

    public void setPayee(Long payee) {
        this.payee = payee;
    }

    @Basic
    @Column(name = "dete")
    public Timestamp getDete() {
        return dete;
    }

    public void setDete(Timestamp dete) {
        this.dete = dete;
    }

    @Basic
    @Column(name = "marketplace")
    public Long getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Long marketplace) {
        this.marketplace = marketplace;
    }

    @Basic
    @Column(name = "summ")
    public double getSumm() {
        return summ;
    }

    public void setSumm(double summ) {
        this.summ = summ;
    }

    @Basic
    @Column(name = "currency")
    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityTransactions that = (EntityTransactions) o;

        if (id != that.id) return false;
        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (payee != null ? !payee.equals(that.payee) : that.payee != null) return false;
        if (dete != null ? !dete.equals(that.dete) : that.dete != null) return false;
        if (marketplace != null ? !marketplace.equals(that.marketplace) : that.marketplace != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (payee != null ? payee.hashCode() : 0);
        result = 31 * result + (dete != null ? dete.hashCode() : 0);
        result = 31 * result + (marketplace != null ? marketplace.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);

        return result;

    }
}
