package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "personal_data", schema = "public", catalog = "ice")
public class EntityPersonalData {

    private String name;
    private String patronymic;
    private Timestamp bday;
    private Boolean sex;
    private String surname;
    private Long employeeId;
    private Long id;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "patronymic")
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Basic
    @Column(name = "bday")
    public Timestamp getBday() {
        return bday;
    }

    public void setBday(Timestamp bday) {
        this.bday = bday;
    }

    @Basic
    @Column(name = "sex")
    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityPersonalData that = (EntityPersonalData) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (patronymic != null ? !patronymic.equals(that.patronymic) : that.patronymic != null) return false;
        if (bday != null ? !bday.equals(that.bday) : that.bday != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (bday != null ? bday.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = Math.toIntExact(31 * result + id);
        return result;
    }
    @Basic
    @Id
    @Column(name = "employee_id")
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
