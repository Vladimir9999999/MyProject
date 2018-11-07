package spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "schedule", schema = "public", catalog = "ice")
public class EntitySchedule {
    private String scheduleType;
    private String value;
    private int id;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @Basic
    @Column(name = "schedule_type")
    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntitySchedule that = (EntitySchedule) o;

        if (id != that.id) return false;
        if (scheduleType != null ? !scheduleType.equals(that.scheduleType) : that.scheduleType != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = scheduleType != null ? scheduleType.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
