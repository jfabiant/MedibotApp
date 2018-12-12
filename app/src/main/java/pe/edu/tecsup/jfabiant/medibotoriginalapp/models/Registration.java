package pe.edu.tecsup.jfabiant.medibotoriginalapp.models;

public class Registration {

    private String username;
    private String email;
    private String password1;
    private String password2;
    private String first_name;
    private String last_name;
    private String key;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password1='" + password1 + '\'' +
                ", password2='" + password2 + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
