package pe.edu.tecsup.jfabiant.medibotoriginalapp.models;

public class Hospital {

    private Integer id;
    private String nombre;
    private String c_distrito;
    private String hosp_img;
    private Double longitud;
    private Double latitud;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getC_distrito() {
        return c_distrito;
    }

    public void setC_distrito(String c_distrito) {
        this.c_distrito = c_distrito;
    }

    public String getHosp_img() {
        return hosp_img;
    }

    public void setHosp_img(String hosp_img) {
        this.hosp_img = hosp_img;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", c_distrito='" + c_distrito + '\'' +
                ", hosp_img='" + hosp_img + '\'' +
                ", longitud=" + longitud +
                ", latitud=" + latitud +
                '}';
    }
}
