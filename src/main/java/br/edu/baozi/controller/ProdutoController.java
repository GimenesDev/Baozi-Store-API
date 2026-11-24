package br.edu.baozi.controller;

import br.edu.baozi.model.Produto;
import br.edu.baozi.repository.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoRepository repositorio;

    public ProdutoController(ProdutoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public record Entrada(@NotBlank @Size(max = 255) String nome, @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal preco, @NotNull Boolean estoque) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criar(@Valid @RequestBody Entrada dados) {
        return repositorio.save(new Produto(
                dados.nome(),
                dados.preco(),
                dados.estoque()));
    }

    @GetMapping
    public List<Produto> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Produto consultar(@PathVariable Long id) {
        return repositorio.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        repositorio.delete(consultar(id));
    }
}
