package com.unir.grupo2.myzeancoach.data.UserData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class UserObject {

    @SerializedName("cambio_trabajo")
    @Expose
    private String cambioTrabajo;

    @SerializedName("sexo")
    @Expose
    private String sexo;

    @SerializedName("emoticono")
    @Expose
    private String emoticono;

    @SerializedName("fecha_nacimiento")
    @Expose
    private String fechaNacimiento;

    @SerializedName("zona_residencia")
    @Expose
    private String zonaResidencia;

    @SerializedName("nivel_de_estudios")
    @Expose
    private String nivelDeEstudios;

    @SerializedName("usuario")
    @Expose
    private String usuario;

    @SerializedName("contrasena")
    @Expose
    private String contrasena;

    @SerializedName("pais_nacimiento")
    @Expose
    private String paisNacimiento;

    @SerializedName("existe")
    @Expose
    private int existe;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("ciudad_nacimiento")
    @Expose
    private String ciudadNacimiento;

    public void setCambioTrabajo(String cambioTrabajo) {
        this.cambioTrabajo = cambioTrabajo;
    }

    public String getCambioTrabajo() {
        return cambioTrabajo;
    }

    public void setEmoticono(String emoticono) {
        this.emoticono = emoticono;
    }

    public String getEmoticono() {
        return emoticono;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setZonaResidencia(String zonaResidencia) {
        this.zonaResidencia = zonaResidencia;
    }

    public String getZonaResidencia() {
        return zonaResidencia;
    }

    public void setNivelDeEstudios(String nivelDeEstudios) {
        this.nivelDeEstudios = nivelDeEstudios;
    }

    public String getNivelDeEstudios() {
        return nivelDeEstudios;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public String getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setExiste(int existe) {
        this.existe = existe;
    }

    public int getExiste() {
        return existe;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCiudadNacimiento(String ciudadNacimiento) {
        this.ciudadNacimiento = ciudadNacimiento;
    }

    public String getCiudadNacimiento() {
        return ciudadNacimiento;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSexo() {

        return sexo;
    }

    @Override
    public String toString() {
        return "UserObject{" +
                "cambioTrabajo=" + cambioTrabajo +
                ", emoticono='" + emoticono + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", zonaResidencia='" + zonaResidencia + '\'' +
                ", sexo='" + sexo + '\'' +
                ", nivelDeEstudios='" + nivelDeEstudios + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", paisNacimiento='" + paisNacimiento + '\'' +
                ", existe=" + existe +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", ciudadNacimiento='" + ciudadNacimiento + '\'' +
                '}';
    }
}