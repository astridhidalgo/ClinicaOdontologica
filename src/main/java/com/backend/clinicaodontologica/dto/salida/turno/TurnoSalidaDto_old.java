package com.backend.clinicaodontologica.dto.salida.turno;

import java.time.LocalDateTime;

public class TurnoSalidaDto_old {
    private int id;
    private LocalDateTime fechayHora;
    private String nombreOdontologo;
    private String nombrePaciente;


    public TurnoSalidaDto_old() {
    }

    public TurnoSalidaDto_old(int id, LocalDateTime fechayHora, String nombreOdontologo, String nombrePaciente) {
        this.id = id;
        this.fechayHora = fechayHora;
        this.nombreOdontologo = nombreOdontologo;
        this.nombrePaciente = nombrePaciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(LocalDateTime fechayHora) {
        this.fechayHora = fechayHora;
    }

    public String getNombreOdontologo() {
        return nombreOdontologo;
    }

    public void setNombreOdontologo(String nombreOdontologo) {
        this.nombreOdontologo = nombreOdontologo;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }
}
