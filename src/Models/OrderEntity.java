package Models;

import javax.persistence.*;
import java.sql.Timestamp;
@Deprecated
@Entity
@Table(name = "orders", schema = "public", catalog = "order")
public class OrderEntity {

    private long id;
    private int shopId;
    private long client;
    private String services;
    private int employee_id;
    private String employee;
    private Timestamp plannedtime;
    private Integer lendth;
    private Double summ;
    private Double prepay;
    private String status;
    private boolean favorite;
    private Timestamp lastModification;

    @Basic
    @Column(name = "last_modification")
    public Timestamp getLastModification() {
        return lastModification;
    }

    public void setLastModification(Timestamp lastModification) {
        this.lastModification = lastModification;
    }

    @Id
    @Column(name = "id")
    public long getId() {
        return id;

    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shop_id")
    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Basic
    @Column(name = "client")
    public long getClient() {
        return client;
    }

    public void setClient(long client) {
        this.client = client;
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
    @Column(name = "employee")
    public int getEmployeeId() {
        return employee_id;
    }

    public void setEmployeeId(int employee) {
        this.employee_id = employee;
    }

    @Basic
    @Column(name = "plannedtime")
    public Timestamp getPlannedtime() {
        return plannedtime;
    }

    public void setPlannedtime(Timestamp plannedtime) {
        this.plannedtime = plannedtime;
    }

    @Basic
    @Column(name = "lendth")
    public Integer getLendth() {
        return lendth;
    }

    public void setLendth(Integer lendth) {
        this.lendth = lendth;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (id != that.id) return false;
        if (shopId != that.shopId) return false;
        if (client != that.client) return false;
        if (employee_id != that.employee_id) return false;
        if (services != null ? !services.equals(that.services) : that.services != null) return false;
        if (plannedtime != null ? !plannedtime.equals(that.plannedtime) : that.plannedtime != null) return false;
        if (lendth != null ? !lendth.equals(that.lendth) : that.lendth != null) return false;
        if (summ != null ? !summ.equals(that.summ) : that.summ != null) return false;
        if (prepay != null ? !prepay.equals(that.prepay) : that.prepay != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + shopId;
        result = 31 * result + (int) (client ^ (client >>> 32));
        result = 31 * result + (services != null ? services.hashCode() : 0);
        result = 31 * result + employee_id;
        result = 31 * result + (plannedtime != null ? plannedtime.hashCode() : 0);
        result = 31 * result + (lendth != null ? lendth.hashCode() : 0);
        result = 31 * result + (summ != null ? summ.hashCode() : 0);
        result = 31 * result + (prepay != null ? prepay.hashCode() : 0);
        return result;
    }
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("{\"id\":"+id);
        builder.append(",\"shop\":"+shopId);
        builder.append(",\"client\":"+client);
        builder.append(",\"services\": "+services+"");
        builder.append(",\"employee\":"+employee_id);
        builder.append(",\"plannedTime\":\""+plannedtime+"\"");
        builder.append(",\"lasting\":"+lendth);
        builder.append(",\"summ\":"+summ);
        builder.append(",\"prepay\":"+prepay);
        builder.append("}");
        return new String(builder);
    }
    public static class OrderBuilder{
        OrderEntity orderEntity = new OrderEntity();
        public OrderBuilder setShopId(int shopId){
            orderEntity.setShopId(shopId);
            return this;
        }
        public OrderBuilder setClient(long client){
            orderEntity.setClient(client);
            return this;
        }
        public OrderBuilder setServices(String services){
            orderEntity.setServices(services);
            return this;
        }
        public OrderBuilder setEmploeeId(int employee){
            orderEntity.setEmployeeId(employee);
            return this;
        }

        public OrderBuilder setPlannedTime(Timestamp plannedTime){
            orderEntity.setPlannedtime(plannedTime);
            return this;
        }
        public OrderBuilder setLasting(int lasting){
            orderEntity.setLendth(lasting);
            return this;
        }
        public OrderBuilder setSumm(double summ){
            orderEntity.setSumm(summ);
            return this;
        }
        public OrderBuilder setPrepay(double prepay){
            orderEntity.setPrepay(prepay);
            return this;
        }
        public OrderBuilder setStatus(String status){
            orderEntity.status = status;
            return this;
        }

        public OrderBuilder setFavorite(boolean favorite){
            orderEntity.favorite = favorite;
            return this;
        }



        public OrderEntity build() {
            return orderEntity;
        }
    }
    public static OrderBuilder getBuilder(){

        return new OrderBuilder();
    }
}
