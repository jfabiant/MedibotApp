package pe.edu.tecsup.jfabiant.medibotoriginalapp.models;

public class Enfermedad {

    private Integer id;
    private String nombre;
    private String descripcion;
    private String enf_img;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEnf_img() {
        return enf_img;
    }

    public void setEnf_img(String enf_img) {
        this.enf_img = enf_img;
    }

    @Override
    public String toString() {
        return "Enfermedad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", enf_img='" + enf_img + '\'' +
                '}';
    }
}
