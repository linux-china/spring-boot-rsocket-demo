package org.mvnsearch.account;

/**
 * Account
 *
 * @author linux_china
 */
public class Account {
    private Integer id;
    private String nick;
    private String phone;
    private String password;
    private Integer status;

    public Account() {
    }

    public Account(Integer id, String nick) {
        this.id = id;
        this.nick = nick;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
