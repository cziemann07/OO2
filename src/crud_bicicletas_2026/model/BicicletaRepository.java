/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_bicicletas_2026.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author EPACS
 */
public class BicicletaRepository {
    private final BicicletaDAO dao;
    
    public BicicletaRepository() {
        this.dao = new BicicletaDAO();
    }
    
    // salva bicicleta. id = 0 -> insert, se não, é update
    public boolean salvar(Bicicleta b) {
        if (b.getId() == 0) {
            return dao.inserir(b);
        } else {
            return dao.alterar(b) > 0;
        }
    }
    
    // remove a bike do repo
    public boolean remover(int id) {
        return dao.excluir(id) > 0;
    }
    
    public Bicicleta buscarPorId(int id) {
        return dao.consultar(id);
    }
    
    public List<Bicicleta> listarTodas() {
        return dao.buscarTodos();
    }
    
    public List<Bicicleta> buscarPorMarca(String marca) {
        return dao.buscarTodosFiltro("marca", marca);
    }
    
    public List<Bicicleta> buscarPorModelo(String modelo) {
        return dao.buscarTodosFiltro("modelo", modelo);
    }
    
    public List<Bicicleta> buscarPorTipo(String tipo) {
        return dao.buscarTodosFiltro("tipo", tipo);
    }
    
    public List<Bicicleta> listarMaisCaras(int limite) {
        List<Bicicleta> todas = new ArrayList<>(listarTodas());
        todas.sort((a, b) -> b.getPreco().getValor().compareTo(a.getPreco().getValor()));
        return todas.subList(0, Math.min(limite, todas.size()));
    }
    
    public int contarPorTipo(String tipo) {
        return buscarPorTipo(tipo).size();
    }
}
