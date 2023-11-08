package com.backend.clinicaodontologica.controller;


import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.service.IPacienteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //POST
    @PostMapping("/registrar")
    public Paciente registrarPaciente(@RequestBody Paciente paciente){
        return pacienteService.registrarPaciente(paciente);
    }

    //PUT
    @PutMapping("/actualizar")
    public Paciente actualizarPaciente(@RequestBody Paciente paciente){
        return pacienteService.actualizarPaciente(paciente);
    }
}
