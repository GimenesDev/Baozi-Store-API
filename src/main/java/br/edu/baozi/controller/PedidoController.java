package br.edu.baozi.controller;

import br.edu.baozi.model.Pedido;
import br.edu.baozi.repository.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoRepository repositorio;
    private final ClienteRepository clientes;
    private final ProdutoRepository produtos;

    public PedidoController(PedidoRepository repositorio, ClienteRepository clientes, ProdutoRepository produtos) {
        this.repositorio = repositorio;
        this.clientes = clientes;
        this.produtos = produtos;
    }

    public record Entrada(@NotNull Long clienteId, @NotNull Long produtoId, @NotNull @Positive Integer quantidade) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido criar(@Valid @RequestBody Entrada dados) {
        return repositorio.save(new Pedido(
                clientes.findById(dados.clienteId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente inexistente")),
                produtos.findById(dados.produtoId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente")),
                dados.quantidade()));
    }

    @GetMapping
    public List<Pedido> listar() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Pedido consultar(@PathVariable Long id) {
        return repositorio.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido inexistente"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        repositorio.delete(consultar(id));
    }
}
