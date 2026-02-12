package com.hacktonfiap.paciente_historico.dto;

public record PacienteResponse(
        Long id,
        String nome,
        String cpf
) {}