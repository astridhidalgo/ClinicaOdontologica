package com.backend.clinicaodontologica.dto.entrada.turno;

import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoEntradaDto {
    private LocalDateTime fechayHora;
    @NotNull(message = "el odontólogoId no puede ser nulo")
    private Long odontologoId;
    @NotNull(message = "el pacienteId no puede ser nulo")
    private Long pacienteId;

    public TurnoEntradaDto() {
    }


    public TurnoEntradaDto(LocalDateTime fechayHora, Long odontologoId, Long pacienteId) {
        this.fechayHora = fechayHora;
        this.odontologoId = odontologoId;
        this.pacienteId = pacienteId;
    }

    public LocalDateTime getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(LocalDateTime fechayHora) {
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
