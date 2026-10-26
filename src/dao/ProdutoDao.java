
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Produto;

public class ProdutoDao {

    private ConexaoBanco conexao;

    public ProdutoDao() {
        this.conexao = new ConexaoBanco();
    }

    public void inserir(Produto produto) {

        String sql = "INSERT INTO produtos "
                + "(nome, descricao, preco, categoria) "
                + "VALUES (?, ?, ?, ?)";

        try {
            if (this.conexao.conectar()) {

                Connection conn = this.conexao.getConnection();
                PreparedStatement sentenca = conn.prepareStatement(sql);

                sentenca.setString(1, produto.getNome());
                sentenca.setString(2, produto.getDescricao());
                sentenca.setBigDecimal(3, produto.getPreco());
                sentenca.setString(4, produto.getCategoria());

                sentenca.executeUpdate();

                sentenca.close();
                conn.close();

            } else {
                throw new RuntimeException(
                        "Não foi possível conectar ao banco de dados.");
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void alterar(Produto produto) {

        String sql = "UPDATE produtos SET "
                + "nome = ?, descricao = ?, preco = ?, categoria = ? "
                + "WHERE id = ?";

        try {
            if (this.conexao.conectar()) {

                Connection conn = this.conexao.getConnection();
                PreparedStatement sentenca = conn.prepareStatement(sql);

                sentenca.setString(1, produto.getNome());
                sentenca.setString(2, produto.getDescricao());
                sentenca.setBigDecimal(3, produto.getPreco());
                sentenca.setString(4, produto.getCategoria());
                sentenca.setInt(5, produto.getId());

                int linhasAlteradas = sentenca.executeUpdate();

                sentenca.close();
                conn.close();

                if (linhasAlteradas == 0) {
                    throw new RuntimeException(
                            "Produto não encontrado ou nenhum dado foi alterado.");
                }

            } else {
                throw new RuntimeException(
                        "Não foi possível conectar ao banco de dados.");
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<Produto> consultar() {

        ArrayList<Produto> lista = new ArrayList<>();

        String sql = "SELECT * FROM produtos ORDER BY id";

        try {
            if (this.conexao.conectar()) {

                Connection conn = this.conexao.getConnection();
                PreparedStatement sentenca = conn.prepareStatement(sql);
                ResultSet resultado = sentenca.executeQuery();

                while (resultado.next()) {

                    Produto produto = new Produto();

                    produto.setId(resultado.getInt("id"));
                    produto.setNome(resultado.getString("nome"));
                    produto.setDescricao(resultado.getString("descricao"));
                    produto.setPreco(resultado.getBigDecimal("preco"));
                    produto.setCategoria(resultado.getString("categoria"));

                    lista.add(produto);
                }

                resultado.close();
                sentenca.close();
                conn.close();

            } else {
                throw new RuntimeException(
                        "Não foi possível conectar ao banco de dados.");
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return lista;
    }
}
