package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "polling", schema = "public", catalog = "ice")

public class EntityPolling  {

    private long id;

    private boolean vote;

    private long shopId;

    private long userId;

    private Timestamp date;

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
        return this.shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
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
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "vote")
    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityPolling polling = (EntityPolling) o;
        return id == polling.id &&
                vote == polling.vote &&
                shopId == polling.shopId &&
                userId == polling.userId &&
                Objects.equals(date, polling.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, vote, shopId, userId, date);
    }
}