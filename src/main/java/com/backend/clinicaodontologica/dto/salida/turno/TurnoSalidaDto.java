package com.backend.clinicaodontologica.dto.salida.turno;

import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;

import java.time.LocalDate;

public class TurnoSalidaDto {
    private int id;
    private LocalDate fechayHora;
    private Odontologo odontologo;
    private Paciente paciente;

    public TurnoSalidaDto() {
    }

    public TurnoSalidaDto(int id, LocalDate fechayHora, Odontologo odontologo, Paciente paciente) {
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
