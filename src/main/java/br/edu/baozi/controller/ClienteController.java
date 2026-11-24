package br.edu.baozi.controller;

import br.edu.baozi.model.Cliente;
import br.edu.baozi.repository.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteRepository repositorio;

    public ClienteController(ClienteRepository repositorio) {
        this.repositorio = repositorio;
    }

    public record Entrada(@NotBlank @Size(max = 255) String nome, @NotNull LocalDate clienteDesde) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente criar(@Valid @RequestBody Entrada dados) {
        return repositorio.save(new Cliente(
                dados.nome(),
                dados.clienteDesde()));
    }

    @GetMapping
    public List<Cliente> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Cliente consultar(@PathVariable Long id) {
        return repositorio.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente inexistente"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        repositorio.delete(consultar(id));
    }
}
