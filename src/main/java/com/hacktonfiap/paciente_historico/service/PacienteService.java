package com.hacktonfiap.paciente_historico.service;

import com.hacktonfiap.paciente_historico.dto.PacienteResponse;
import com.hacktonfiap.paciente_historico.entity.Paciente;
import com.hacktonfiap.paciente_historico.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public Paciente salvar(Paciente paciente) {

        // Evita CPF duplicado
        pacienteRepository.findByCpf(paciente.getCpf())
                .ifPresent(p -> {
                    throw new RuntimeException("Paciente com esse CPF já existe.");
                });

        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    }

    public Paciente buscarPorCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    }

    public List<Paciente> buscarPorNome(String nome) {
        return pacienteRepository.findByNomeContainingIgnoreCase(nome);
    }


    private String mascararCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }

        return cpf.substring(0, 3) + ".***.***-" + cpf.substring(9);
    }

    public PacienteResponse toResponseDTO(Paciente paciente) {
        return new PacienteResponse(
                paciente.getId(),
                paciente.getNome(),
                mascararCpf(paciente.getCpf())
        );
    }
}
