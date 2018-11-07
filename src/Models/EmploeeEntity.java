package Models;

import javax.persistence.*;
@Deprecated
@Entity
@Table(name = "emploee", schema = "public", catalog = "order")
public class EmploeeEntity {

    private Long id;
    private String name;
    private String function= "";
    private int schedule;
    private Short privilege=1;
    private Long shopId;

    @Basic
    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
    @Column(name = "privilege")
    public Short getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Short privilege) {
        this.privilege = privilege;
    }

    @Basic
    @Column(name = "shop_id")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmploeeEntity that = (EmploeeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (function != null ? !function.equals(that.function) : that.function != null) return false;
        if (privilege != null ? !privilege.equals(that.privilege) : that.privilege != null) return false;
        if (shopId != null ? !shopId.equals(that.shopId) : that.shopId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (function != null ? function.hashCode() : 0);
        result = 31 * result + (privilege != null ? privilege.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        return result;
    }
    @Column(name = "schedule_id")
    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }


    public static class EmploeeBuilber{
        EmploeeEntity emploeeEntity = new EmploeeEntity();

        public EmploeeBuilber setId(long id){
            emploeeEntity.id = id;
            return this;
        }
        public EmploeeBuilber setName(String name){
            emploeeEntity.name = name;
            return this;
        }
        public EmploeeBuilber setFunction(String function){
            emploeeEntity.function = function;
            return this;
        }
        public EmploeeBuilber setPrivilege(Short privilege){
            emploeeEntity.privilege = privilege;
            return this;
        }
        public EmploeeBuilber setShopId(Long shopId){
            emploeeEntity.shopId = shopId;
            return this;
        }
        public EmploeeEntity build(){
            return emploeeEntity;
        }
    }

    public static EmploeeBuilber getBuilder(){
        return new EmploeeBuilber();
    }
}
