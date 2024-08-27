package com.example.todo_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class TareaService{

    @Autowired
    private TareaRepository tareaRepository;


    //Crear nueva tarea
    public Tarea guardarTarea(Tarea tarea){
        return tareaRepository.save(tarea);
    }
    

    //Obtener lista de tareas creadas
    public List<Tarea> todaTareas(){
        return tareaRepository.findAll();
    }

    //Actualizar tarea

    //Eliminar tarea por id
    public Boolean eliminarTarea(long tareaId){
        tareaRepository.deleteById(tareaId);
        return true;
    }
}   
