package com.hacktonfiap.paciente_historico.mapper;

import com.hacktonfiap.paciente_historico.dto.PacienteResponse;
import com.hacktonfiap.paciente_historico.entity.Paciente;

public class PacienteMapper {

    public static PacienteResponse toResponse(Paciente paciente) {
        return new PacienteResponse(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf()
        );
    }
}