package Models;

import java.util.Date;

public class Session {

    private long userId;
    private long shopId;
    private String token;
    private int privilege;
    private Date lastUseDate;
    private String userAgent;

    private String iTocken;
    private String gTocken;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session that = (Session) o;
        if(this.token.equals(that.token) && this.shopId==that.shopId && this.userId == that.userId && this.userAgent.equals(that.userAgent)){
            return true;

        }

        return false;
    }

    public Date getLastUseDate() {
        return lastUseDate;
    }

    public void setLastUseDate(Date lastUseDate) {
        this.lastUseDate = lastUseDate;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getiTocken() {
        return iTocken;
    }

    public void setiTocken(String iTocken) {
        this.iTocken = iTocken;
    }

    public String getgTocken() {
        return gTocken;
    }

    public void setgTocken(String gTocken) {
        this.gTocken = gTocken;
    }
}
