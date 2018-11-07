package spring.entity;

import spring.interfaces.delete.Removable;
import javax.persistence.*;

@Entity
@Table(name = "employee", schema = "public", catalog = "ice")
public class EntityEmployee implements Removable {

    private String name;
    private String function;
    private Short privilege;
    private long shopId;
    private long id;
    private EntitySchedule scheduleByScheduleId;
    private EntityDeleteMarker deleteMarker;
    private boolean timeDependent = true;
    private String post;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "function")
    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }


    @Basic
    @Column(name  = "post")
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }




    @Basic
    @Column(name = "privilege")
    public Short getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Short privilege) {
        this.privilege = privilege;
    }

    @Basic
    @Column(name = "shop_id")
    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityEmployee that = (EntityEmployee) o;

        if (shopId != that.shopId) return false;
        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (function != null ? !function.equals(that.function) : that.function != null) return false;
        if (privilege != null ? !privilege.equals(that.privilege) : that.privilege != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (function != null ? function.hashCode() : 0);
        result = 31 * result + (privilege != null ? privilege.hashCode() : 0);
        result = 31 * result + (int) (shopId ^ (shopId >>> 32));
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    public EntitySchedule getScheduleByScheduleId() {
        return scheduleByScheduleId;
    }

    public void setScheduleByScheduleId(EntitySchedule scheduleByScheduleId) {

        this.scheduleByScheduleId = scheduleByScheduleId;

    }


    @ManyToOne
    @JoinColumn(name = "delete_mark", referencedColumnName = "id")
    public EntityDeleteMarker getDeleteMarker() {
        return deleteMarker;
    }

    public void setDeleteMarker(EntityDeleteMarker deleteMarker) {
        this.deleteMarker = deleteMarker;
    }

    @Basic
    @Column(name = "time_dependent")
    public boolean isTimeDependent() {
        return timeDependent;
    }

    public void setTimeDependent(boolean timeDependent) {
        this.timeDependent = timeDependent;
    }
}
