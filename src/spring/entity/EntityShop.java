package spring.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shop", schema = "public", catalog = "ice")
public class EntityShop {

    private long id;
    private String nameShop;
    private String serverIp;
    private Integer themeId;
    private String information;
    private Long phone;

    private List<EntityBranch> entityBranchList;

    @OneToMany(mappedBy = "shop",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    public List<EntityBranch> getEntityBranchList() {
        return entityBranchList;
    }

    public void setEntityBranchList(List<EntityBranch> entityBranchList) {
        this.entityBranchList = entityBranchList;
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
    @Column(name = "name_shop")
    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    @Basic
    @Column(name = "server_ip")
    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    @Basic
    @Column(name = "theme_id")
    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    @Basic
    @Column(name = "information")
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Basic
    @Column(name = "phone")
    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityShop that = (EntityShop) o;
        return id == that.id &&
                Objects.equals(nameShop, that.nameShop) &&
                Objects.equals(serverIp, that.serverIp) &&
                Objects.equals(themeId, that.themeId) &&
                Objects.equals(information, that.information) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(entityBranchList, that.entityBranchList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameShop, serverIp, themeId, information, phone, entityBranchList);
    }
}
