/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_bicicletas_2026.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * VO que representa valor monetário (Preço)
 *  IMUTÁVEL: uma vez criado, o valor nunca muda
 *  VALIDADO: nunca existe um Preco em estado inválido 
 * @author cadul
 */
public final class Preco {
    // só pode ser atribuído UMA vez. sem setter.
    private final BigDecimal valor;
    
    // construtor privado. unica forma de criar um Preco eh passando pelos metodos de validacao abaixo
    private Preco(BigDecimal valor) {
        this.valor = valor;
    }
    
    // recebe BigDecimal, valida, e retorna um Preco pronto
    // se invalido, exception eh lancada antes de criar o objeto 
    public static Preco de(BigDecimal valor) {
        // first validation = not nullable
        if (valor == null) {
            throw new IllegalArgumentException("Preço não pode ser nulo");
        }

        // second validation = not negative
        if (valor.signum() < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }

        // normalization
        BigDecimal valorNormalizado = valor.setScale(2, RoundingMode.HALF_UP);

        // so no final cria-se o objeto, válido garantido
        return new Preco(valorNormalizado);
    }

    // aceita double por conveniencia. mas converte pra BigDecimal e segue o barco
    public static Preco de(double valor) {
        return de(BigDecimal.valueOf(valor));
    }
    
    // getter valor cru
    public BigDecimal getValor() {
        return valor;
    }
    
    // inves d alterar o Preco atual, retorna um novo Preco com o valor descontado
    public Preco aplicarDesconto(double percentual) {
        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException("Percentual inválido: " + percentual);
        }

        BigDecimal fator = BigDecimal.valueOf(1 - percentual / 100.0);
        BigDecimal novoValor = valor.multiply(fator);
        return Preco.de(novoValor); // passa de novo pela validação
    }
    
    // formatacao BR
    public String formatado() {
        return "R$ " + valor.toString().replace('.', ',');
    }
   
    // vira string
    @Override
    public String toString() {
        return formatado();
    }
}
