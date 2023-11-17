package com.backend.clinicaodontologica.dto.modificacion;

import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoModificacionEntradaDto {
    @NotNull(message = "Debe proveerse el id del odontologo que se desea modificar")
    private int id;
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha de ingreso del paciente")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechayHora;

    @NotNull(message = "El odontologo para el turno no puede ser nulo")
    @Valid
    private Odontologo odontologo;
    @NotNull(message = "El paciente para el turno no puede ser nulo")
    @Valid
    private Paciente paciente;

    public TurnoModificacionEntradaDto() {
    }

    public TurnoModificacionEntradaDto(int id, LocalDate fechayHora, Odontologo odontologo, Paciente paciente) {
        this.id = id;
        this.fechayHora = fechayHora;
        this.odontologo = odontologo;
        this.paciente = paciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(LocalDate fechayHora) {
        this.fechayHora = fechayHora;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
