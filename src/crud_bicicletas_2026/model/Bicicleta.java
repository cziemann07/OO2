/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_bicicletas_2026.model;

/**
 *
 * @author cadul
 */
public class Bicicleta {
    private int id;
    private String marca;
    private String modelo;
    private String tipo;
    private int numeroMarchas;
    private double aro;
    private Preco preco;
    private int anoFabricacao;
    
    public Bicicleta(){}
    
    public Bicicleta(int id, String marca, String modelo, String tipo,
                 int numeroMarchas, double aro, Preco preco, int anoFabricacao) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.numeroMarchas = numeroMarchas;
        this.aro = aro;
        this.preco = preco;
        this.anoFabricacao = anoFabricacao;
    }
    
    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }

    public String getMarca() { 
        return marca; 
    }
    public void setMarca(String marca) { 
        this.marca = marca; 
    }

    public String getModelo() { 
        return modelo; 
    }
    public void setModelo(String modelo) { 
        this.modelo = modelo; 
    }

    public String getTipo() { 
        return tipo; 
    }
    public void setTipo(String tipo) { 
        this.tipo = tipo; 
    }

    public int getNumeroMarchas() { 
        return numeroMarchas; 
    }
    public void setNumeroMarchas(int numeroMarchas) { 
        this.numeroMarchas = numeroMarchas; 
    }

    public double getAro() { 
        return aro; 
    }
    public void setAro(double aro) { 
        this.aro = aro; 
    }

    public Preco getPreco() {  
        return preco; 
    }
    public void setPreco(Preco preco) { 
        this.preco = preco; 
    }

    public int getAnoFabricacao() { 
        return anoFabricacao; 
    }
    public void setAnoFabricacao(int anoFabricacao) { 
        this.anoFabricacao = anoFabricacao; 
    }

    @Override
    public String toString() {
        return marca + " " + modelo + " (Aro " + aro + ")";
    }
}
