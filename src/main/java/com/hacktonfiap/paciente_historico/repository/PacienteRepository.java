package com.hacktonfiap.paciente_historico.repository;

import com.hacktonfiap.paciente_historico.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByCpf(String cpf);

    List<Paciente> findByNomeContainingIgnoreCase(String nome);
}
