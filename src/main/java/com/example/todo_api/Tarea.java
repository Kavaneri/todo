package com.example.todo_api;

import java.sql.Date;
import java.sql.Time;

import com.example.todo_api.User.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String titulo;
    private Date fecha;
    private Time hora;
    private String descripcion;
    private int prioridad;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User usuario;


    public Tarea() {
    }


    public Tarea( String titulo, Date fecha, Time hora, String descripcion, int prioridad, User usuario) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.usuario = usuario;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return this.hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrioridad() {
        return this.prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }


    public User getUsuario() {
        return this.usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
    
}
