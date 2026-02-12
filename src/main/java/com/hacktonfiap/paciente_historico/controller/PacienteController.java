package com.hacktonfiap.paciente_historico.controller;

import com.hacktonfiap.paciente_historico.dto.PacienteResponse;
import com.hacktonfiap.paciente_historico.entity.Paciente;
import com.hacktonfiap.paciente_historico.mapper.PacienteMapper;
import com.hacktonfiap.paciente_historico.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> criar(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.salvar(paciente));
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listar() {

        List<PacienteResponse> pacientes = pacienteService
                .listarTodos()
                .stream()
                .map(pacienteService::toResponseDTO)
                .toList();

        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf
    ) {

        if (nome != null) {
            return ResponseEntity.ok(
                    pacienteService.buscarPorNome(nome)
                            .stream()
                            .map(PacienteMapper::toResponse)
                            .toList()
            );
        }

        if (cpf != null) {
            return ResponseEntity.ok(
                    PacienteMapper.toResponse(
                            pacienteService.buscarPorCpf(cpf)
                    )
            );
        }

        return ResponseEntity.badRequest()
                .body("Informe nome ou cpf para busca");
    }


    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscar(@PathVariable Long id) {

        Paciente paciente = pacienteService.buscarPorId(id);

        return ResponseEntity.ok(
                pacienteService.toResponseDTO(paciente)
        );
    }
}
