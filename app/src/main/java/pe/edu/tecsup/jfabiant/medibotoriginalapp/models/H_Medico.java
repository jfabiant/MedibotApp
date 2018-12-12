package pe.edu.tecsup.jfabiant.medibotoriginalapp.models;

import java.util.Date;

public class H_Medico {
    private Integer id;
    private Date fecha;
    private String descripcion;
    private Integer c_usuario;
    private Integer c_enfermedad;
    private Integer c_hospital;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getC_usuario() {
        return c_usuario;
    }

    public void setC_usuario(Integer c_usuario) {
        this.c_usuario = c_usuario;
    }

    public Integer getC_enfermedad() {
        return c_enfermedad;
    }

    public void setC_enfermedad(Integer c_enfermedad) {
        this.c_enfermedad = c_enfermedad;
    }

    public Integer getC_hospital() {
        return c_hospital;
    }

    public void setC_hospital(Integer c_hospital) {
        this.c_hospital = c_hospital;
    }

    @Override
    public String toString() {
        return "H_Medico{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", c_usuario=" + c_usuario +
                ", c_enfermedad=" + c_enfermedad +
                ", c_hospital=" + c_hospital +
                '}';
    }
}
