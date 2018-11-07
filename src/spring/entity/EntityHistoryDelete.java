package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "history_delete", schema = "public", catalog = "ice")
public class EntityHistoryDelete {

    private long id;

    private Timestamp data;

    private String nameClass;

    private String value;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "data")
    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;

    }

    @Basic
    @Column(name = "name_class")
    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}