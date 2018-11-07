package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "payment", schema = "public", catalog = "ice")
public class EntityPayment {

    private long id;
    private long userId;
    private long shopId;
    private Integer payment;
    private Integer year;
    private Integer month;
    private Integer day;
    private Timestamp date;

    private Boolean monthlyPayment;
    private Boolean paymentForTheDay;

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
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
    @Column(name = "payment")
    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    @Basic
    @Column(name = "year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @Column(name = "month")
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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
    @Column(name = "monthly_payment")
    public Boolean getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Boolean monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    @Basic
    @Column(name = "payment_for_the_day")
    public Boolean getPaymentForTheDay() {
        return paymentForTheDay;
    }

    public void setPaymentForTheDay(Boolean paymentForTheDay) {
        this.paymentForTheDay = paymentForTheDay;
    }

    @Basic
    @Column(name = "day")
    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityPayment that = (EntityPayment) o;
        return id == that.id &&
                userId == that.userId &&
                shopId == that.shopId &&
                Objects.equals(payment, that.payment) &&
                Objects.equals(year, that.year) &&
                Objects.equals(month, that.month) &&
                Objects.equals(day, that.day) &&
                Objects.equals(date, that.date) &&
                Objects.equals(monthlyPayment, that.monthlyPayment) &&
                Objects.equals(paymentForTheDay, that.paymentForTheDay);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, shopId, payment, year, month, day, date, monthlyPayment, paymentForTheDay);
    }
}
