package spring.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "session", schema = "public", catalog = "ice")

public class EntitySession {

    private long id;
    private long userId;
    private String userAgent;
    private String tocken;
    private Timestamp lastVisit;
    private String googleTocken;
    private String appleTocken;

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
    @Column(name = "user_agent")
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Basic
    @Column(name = "tocken")
    public String getTocken() {
        return tocken;
    }

    public void setTocken(String tocken) {
        this.tocken = tocken;
    }

    @Basic
    @Column(name = "last_visit")
    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }
    @Basic
    @Column(name = "google_tocken")
    public String getGoogleTocken() {
        return googleTocken;
    }

    public void setGoogleTocken(String googleTocken) {
        this.googleTocken = googleTocken;
    }

    @Basic
    @Column(name = "apple_tocken")
    public String getAppleTocken() {
        return appleTocken;
    }

    public void setAppleTocken(String appleTocken) {
        this.appleTocken = appleTocken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntitySession session = (EntitySession) o;
        return id == session.id &&
                userId == session.userId &&
                Objects.equals(userAgent, session.userAgent) &&
                Objects.equals(tocken, session.tocken) &&
                Objects.equals(lastVisit, session.lastVisit) &&
                Objects.equals(googleTocken, session.googleTocken) &&
                Objects.equals(appleTocken, session.appleTocken);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, userAgent, tocken, lastVisit, googleTocken, appleTocken);
    }
}
