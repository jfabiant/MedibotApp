package pe.edu.tecsup.jfabiant.medibotoriginalapp.models;

public class Login {

    private Integer pk;
    private String key;

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Login{" +
                "pk=" + pk +
                ", key='" + key + '\'' +
                '}';
    }
}
