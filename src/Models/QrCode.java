package Models;

import java.io.Serializable;
import java.util.Date;

public class QrCode implements Serializable {

    public final static int SERVICE= 1;

    public final static int USER = 2;

    private long id;

    private String hash;
    private int    type;
    private Date date;
    private long host;
    private long reserved;

    @Deprecated
    public long getId() {
        return id;
    }

    @Deprecated
    public void setId(long id) {
        this.host = id;
        this.id = id;

    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object object){
        QrCode code = (QrCode) object;

        if (this.hash.equals(code.hash) ){

            return true;

        }
        return false;
    }


    public long getHost() {
        return host;
    }

    public void setHost(long host) {
        this.host = host;
    }

    public long getReserved() {
        return reserved;
    }

    public void setReserved(long reserved) {
        this.reserved = reserved;
    }
}
