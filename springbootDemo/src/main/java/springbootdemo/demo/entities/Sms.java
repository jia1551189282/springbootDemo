package springbootdemo.demo.entities;

public class Sms {
    private String account;
    private String pwd;
    private String Content;
    private String mobile;
    private Integer singId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSingId() {
        return singId;
    }

    public void setSingId(Integer singId) {
        this.singId = singId;
    }
}
