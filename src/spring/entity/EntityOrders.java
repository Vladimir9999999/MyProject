package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "orders", schema = "public", catalog = "ice")
public class EntityOrders {

    public static final int STATUS_NEW  = 1;
    public static final int STATUS_CANCEL = 2;
    public static final int STATUS_COMPLETE = 3;
    public static final int STATUS_COMMENTED = 4;
    public static final int STATUS_REFUNDED = 5;
    public static final int STATUS_DELETED = 6;

    private long id;
    private long shopId;
    private Integer length;
    private Double summ;
    private Double prepay;
    private Boolean favorite;
    private Timestamp lastModification;
    private Long deleteMark;
    private Integer status;
    private String address;
    private List<EntityTaskList> taskList;
    private EntityAccountUsers clientToAccountUsers;

    @OneToMany( mappedBy = "orders", fetch = FetchType.EAGER)
    public List<EntityTaskList> getTaskListsById() {
        return taskList;
    }

    public void setTaskListsById(List<EntityTaskList> taskListsById) {
        this.taskList = taskListsById;
    }



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client", referencedColumnName = "id")

    public EntityAccountUsers getAccountUsers() {
        return clientToAccountUsers;
    }


    public void setAccountUsers(EntityAccountUsers accountUsers) {
        this.clientToAccountUsers = accountUsers;
    }


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

/*    @Basic
    @Column(name = "client")
    public long getClient() {
        return client;
    }

    public void setClient(long client) {
        this.client = client;
    }*/

    @Basic
    @Column(name = "length")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Basic
    @Column(name = "summ")
    public Double getSumm() {
        return summ;
    }

    public void setSumm(Double summ) {
        this.summ = summ;
    }

    @Basic
    @Column(name = "prepay")
    public Double getPrepay() {
        return prepay;
    }

    public void setPrepay(Double prepay) {
        this.prepay = prepay;
    }

    @Basic
    @Column(name = "favorite")
    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @Basic
    @Column(name = "last_modification")
    public Timestamp getLastModification() {
        return lastModification;
    }

    public void setLastModification(Timestamp lastModification) {
        this.lastModification = lastModification;
    }

    @Basic
    @Column(name = "delete_mark")
    public Long getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Long deleteMark) {
        this.deleteMark = deleteMark;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityOrders that = (EntityOrders) o;
        return id == that.id &&
                shopId == that.shopId &&
                Objects.equals(length, that.length) &&
                Objects.equals(summ, that.summ) &&
                Objects.equals(prepay, that.prepay) &&
                Objects.equals(favorite, that.favorite) &&
                Objects.equals(lastModification, that.lastModification) &&
                Objects.equals(deleteMark, that.deleteMark) &&
                Objects.equals(status, that.status) &&
                Objects.equals(address, that.address) &&
                Objects.equals(taskList, that.taskList) &&
                Objects.equals(clientToAccountUsers, that.clientToAccountUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shopId, length, summ, prepay, favorite, lastModification, deleteMark, status, address, taskList, clientToAccountUsers);
    }
}
