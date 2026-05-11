/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_bicicletas_2026.model;

import crud_bicicletas_2026.util.GerenciadorConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author cadul
 */
public class BicicletaDAO {
    private Connection conn;
    private PreparedStatement ps;

    private void conectar() {
        try {
            this.conn = GerenciadorConexao.getConexao();
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }

    
    // fecha o PreparedStatement e a Connection nessa ordem.
    private void desconectar() {
        try {
            if (this.ps != null) this.ps.close();
            if (this.conn != null) this.conn.close();
        } catch (Exception e) {
            System.out.println("Erro ao desconectar: " + e.getMessage());
        }
    }

    public boolean inserir(Bicicleta b) {
        boolean resultado = false;
        try {
            this.conectar();

            String comando = "INSERT INTO bicicleta "
                    + "(marca, "
                    + "modelo, "
                    + "tipo, "
                    + "numero_marchas, "
                    + "aro, "
                    + "preco, "
                    + "ano_fabricacao) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?);";

            // RETURN_GENERATED_KEYS: pede pro Postgres devolver o id gerado.
            this.ps = conn.prepareStatement(comando, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, b.getMarca());
            ps.setString(2, b.getModelo());
            ps.setString(3, b.getTipo());
            ps.setInt(4, b.getNumeroMarchas());
            ps.setDouble(5, b.getAro());
            // b.getPreco() retorna um Preco (Value Object).
            // o banco pede BigDecimal, por isso o .getValor().
            ps.setBigDecimal(6, b.getPreco().getValor());
            ps.setInt(7, b.getAnoFabricacao());

            ps.executeUpdate();

            // recupera o id gerado e seta no objeto
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                b.setId(rs.getInt(1));
            }

            resultado = true;

        } catch (Exception e) {
            System.out.println("Erro ao inserir registro: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return resultado;
    }

    public ArrayList<Bicicleta> buscarTodos() {
        ArrayList<Bicicleta> resultados = new ArrayList<>();
        try {
            this.conectar();
            String comando = "SELECT * FROM bicicleta ORDER BY modelo;";
            this.ps = conn.prepareStatement(comando);
            ResultSet rs = ps.executeQuery();

            // itera linha a linha do resultado
            while (rs.next()) {
                resultados.add(mapearBicicleta(rs));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar registros: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return resultados;
    }

    /**
     * Busca bicicletas filtrando por um campo de texto.
     *
     * Detalhe importante: o NOME DA COLUNA não pode ser parametrizado
     * com "?" no PreparedStatement (só valores podem). Para evitar SQL
     * injection no nome da coluna, fazemos whitelist: só aceitamos
     * campos pré-aprovados. O valor do filtro, esse sim, vai parametrizado.
     */
    public ArrayList<Bicicleta> buscarTodosFiltro(String campo, String filtro) {
        ArrayList<Bicicleta> resultados = new ArrayList<>();

        // Whitelist de campos permitidos para filtro
        if (!campo.equals("marca") && !campo.equals("modelo") && !campo.equals("tipo")) {
            return resultados;
        }

        try {
            this.conectar();
            // Concatenamos o nome da coluna (validado pela whitelist acima)
            // mas o VALOR do filtro vai como "?" parametrizado, seguro.
            String comando = "SELECT * FROM bicicleta WHERE "
                    + campo + " ILIKE ? ORDER BY modelo;";
            // ILIKE é case-insensitive no Postgres (LIKE é case-sensitive)

            this.ps = conn.prepareStatement(comando);
            ps.setString(1, "%" + filtro + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultados.add(mapearBicicleta(rs));
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar registros: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return resultados;
    }

    // ============================================================
    // DELETE
    // ============================================================

    public int excluir(int id) {
        int qtde = 0;
        try {
            this.conectar();
            String comando = "DELETE FROM bicicleta WHERE id = ?;";
            this.ps = conn.prepareStatement(comando);
            ps.setInt(1, id);

            ps.executeUpdate();
            qtde = ps.getUpdateCount(); // quantas linhas foram deletadas
        } catch (Exception e) {
            System.out.println("Erro ao excluir registro: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return qtde;
    }

    // ============================================================
    // SELECT por id
    // ============================================================

    public Bicicleta consultar(int id) {
        Bicicleta b = null;
        try {
            this.conectar();
            String comando = "SELECT * FROM bicicleta WHERE id = ?;";
            this.ps = conn.prepareStatement(comando);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                b = mapearBicicleta(rs);
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar registro: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return b;
    }

    // ============================================================
    // UPDATE
    // ============================================================

    public int alterar(Bicicleta b) {
        int qtde = 0;
        try {
            this.conectar();
            String comando = "UPDATE bicicleta SET "
                    + "marca = ?, "
                    + "modelo = ?, "
                    + "tipo = ?, "
                    + "numero_marchas = ?, "
                    + "aro = ?, "
                    + "preco = ?, "
                    + "ano_fabricacao = ? "
                    + "WHERE id = ?;";

            this.ps = conn.prepareStatement(comando);
            ps.setString(1, b.getMarca());
            ps.setString(2, b.getModelo());
            ps.setString(3, b.getTipo());
            ps.setInt(4, b.getNumeroMarchas());
            ps.setDouble(5, b.getAro());
            ps.setBigDecimal(6, b.getPreco().getValor()); // Preco VO -> BigDecimal
            ps.setInt(7, b.getAnoFabricacao());
            ps.setInt(8, b.getId()); // WHERE id = ?

            ps.executeUpdate();
            qtde = ps.getUpdateCount();
        } catch (Exception e) {
            System.out.println("Erro ao alterar registro: " + e.getMessage());
        } finally {
            this.desconectar();
        }
        return qtde;
    }

    // ============================================================
    // Mapeamento ResultSet -> Bicicleta
    // ============================================================

    /**
     * Método auxiliar privado que transforma uma linha do ResultSet num objeto Bicicleta.
     * Extraído para evitar duplicação entre buscarTodos(), buscarTodosFiltro() e consultar().
     */
    private Bicicleta mapearBicicleta(ResultSet rs) throws Exception {
        Bicicleta b = new Bicicleta();
        b.setId(rs.getInt("id"));
        b.setMarca(rs.getString("marca"));
        b.setModelo(rs.getString("modelo"));
        b.setTipo(rs.getString("tipo"));
        b.setNumeroMarchas(rs.getInt("numero_marchas"));
        b.setAro(rs.getDouble("aro"));
        // BigDecimal do banco vira Preco (VO) com validação no setor.
        // Se algum dia o banco devolver valor estranho, a exceção do
        // Preco.de() ajuda a flagrar o problema na borda.
        b.setPreco(Preco.de(rs.getBigDecimal("preco")));
        b.setAnoFabricacao(rs.getInt("ano_fabricacao"));
        return b;
    }
}
