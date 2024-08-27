package com.example.todo_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    //Obtener todas la tareas
    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTareas(){
        return new ResponseEntity<List<Tarea>>(tareaService.todaTareas(), HttpStatus.OK) ;
    }

    //Crear tarea
    @PostMapping
    public ResponseEntity<Tarea> guardarTarea(@RequestBody Tarea tarea){
        return new ResponseEntity<Tarea>(tareaService.guardarTarea(tarea), HttpStatus.OK);
    }

    //Borrar tarea por id
    @DeleteMapping("/delete/{tareaId}")
    public ResponseEntity<Boolean> borrarTarea(@PathVariable("tareaId") long tareaId){
        return new ResponseEntity<Boolean>(tareaService.eliminarTarea(tareaId), HttpStatus.OK);
    } 

    //Borrar tarea por
}
