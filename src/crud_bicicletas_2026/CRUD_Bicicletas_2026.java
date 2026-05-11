/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package crud_bicicletas_2026;

import crud_bicicletas_2026.model.Bicicleta;
import crud_bicicletas_2026.model.BicicletaRepository;
import crud_bicicletas_2026.model.Preco;
import java.util.List;

/**
 *
 * @author cadul
 */
public class CRUD_Bicicletas_2026 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BicicletaRepository repo = new BicicletaRepository();
        
        System.out.println("=== Todas as bicicletas ===");
        List<Bicicleta> todas = repo.listarTodas();
        todas.forEach(b -> System.out.println(b.getId() + " - " + b + " - " + b.getPreco()));
        
        System.out.println("\n=== Salvando nova ===");
        Bicicleta nova = new Bicicleta();
        nova.setMarca("Sense");
        nova.setModelo("Impact Pro");
        nova.setTipo("MTB");
        nova.setNumeroMarchas(24);
        nova.setAro(29);
        nova.setPreco(Preco.de(11500.00));
        nova.setAnoFabricacao(2025);
        
        repo.salvar(nova); // sem se preocupar se é insert ou update
        System.out.println("Salvou com id: " + nova.getId());
        
        System.out.println("\n=== Top 3 mais caras ===");
        repo.listarMaisCaras(3)
            .forEach(b -> System.out.println(b + " - " + b.getPreco()));
        
        System.out.println("\n=== Buscando MTB ===");
        repo.buscarPorTipo("MTB")
            .forEach(b -> System.out.println(b));
        
        System.out.println("\nTotal de MTBs: " + repo.contarPorTipo("MTB"));
    }
    
}
