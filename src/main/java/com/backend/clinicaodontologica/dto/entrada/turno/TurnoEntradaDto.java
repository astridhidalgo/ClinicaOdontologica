package com.backend.clinicaodontologica.dto.entrada.turno;

import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoEntradaDto {
    private LocalDate fechayHora;
    private Long odontologoId;
    private Long pacienteId;

    public TurnoEntradaDto() {
    }


    public TurnoEntradaDto(LocalDate fechayHora, Long odontologoId, Long pacienteId) {
        this.fechayHora = fechayHora;
        this.odontologoId = odontologoId;
        this.pacienteId = pacienteId;
    }

    public LocalDate getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(LocalDate fechayHora) {
        this.fechayHora = fechayHora;
    }

    public Long getOdontologoId() {
        return odontologoId;
    }

    public void setOdontologoId(Long odontologoId) {
        this.odontologoId = odontologoId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}
