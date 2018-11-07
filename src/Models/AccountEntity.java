package Models;

import spring.entity.EntityAccountUsers;

public class AccountEntity {
    private long id;
    private String password;
    private String login;
    private long shop_id;
    private int smsCode;
    private int refer;
    private boolean cahanged;
    private long employeeId;

    public  AccountEntity(){

    }

    public AccountEntity(EntityAccountUsers entityAccountUsers){
        id= entityAccountUsers.getId();
        password = entityAccountUsers.getPassword();
        login = entityAccountUsers.getLogin();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity that = (AccountEntity) o;

        if (id != that.id) return false;
       // if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        //result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
//        result = 31 * result + smsCode;

        return result;
    }

    public int getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(int smsCode) {
        this.smsCode = smsCode;
    }

    public boolean isCahanged() {
        return cahanged;
    }

    public void setCahanged(boolean cahanged) {
        this.cahanged = cahanged;
    }

    public int getRefer() {
        return refer;
    }

    public void setRefer(int refer) {
        this.refer = refer;
    }

    public long getShop_id() {
        return shop_id;
    }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
}
