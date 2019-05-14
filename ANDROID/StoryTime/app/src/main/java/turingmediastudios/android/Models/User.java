package turingmediastudios.android.Models;

public class User {

    private int user_id;
    private String user_name;
    private String user_lastname;
    private String user_email;
    private String user_phone;
    private String user_password;

    public User(int user_id, String user_name, String user_lastname, String user_email, String user_phone, String user_password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_lastname = user_lastname;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_password = user_password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
