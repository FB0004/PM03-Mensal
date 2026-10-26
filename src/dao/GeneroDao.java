
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Genero;

public class GeneroDao {

    private ConexaoBanco conexao;

    public GeneroDao() {
        this.conexao = new ConexaoBanco();
    }

    public void inserir(Genero genero) {

        String sql = "INSERT INTO generos (descricao, sigla, stat) "
                   + "VALUES (?, ?, ?)";

        try {
            if (this.conexao.conectar()) {

                PreparedStatement sentenca =
                    this.conexao.getConnection().prepareStatement(sql);

                sentenca.setString(1, genero.getDescricao());
                sentenca.setString(2, genero.getSigla());
                sentenca.setString(3, genero.getStat());

                sentenca.executeUpdate();

                sentenca.close();
                this.conexao.getConnection().close();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void alterar(Genero genero) {

        String sql = "UPDATE generos SET descricao = ?, "
                   + "sigla = ?, stat = ? WHERE id = ?";

        try {
            if (this.conexao.conectar()) {

                PreparedStatement sentenca =
                    this.conexao.getConnection().prepareStatement(sql);

                sentenca.setString(1, genero.getDescricao());
                sentenca.setString(2, genero.getSigla());
                sentenca.setString(3, genero.getStat());
                sentenca.setInt(4, genero.getId());

                sentenca.executeUpdate();

                sentenca.close();
                this.conexao.getConnection().close();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<Genero> consultar() {

        ArrayList<Genero> lista = new ArrayList<>();

        String sql = "SELECT id, descricao, sigla, stat "
                   + "FROM generos ORDER BY descricao";

        try {
            if (this.conexao.conectar()) {

                PreparedStatement sentenca =
                    this.conexao.getConnection().prepareStatement(sql);

                ResultSet resultado = sentenca.executeQuery();

                while (resultado.next()) {

                    Genero genero = new Genero();

                    genero.setId(resultado.getInt("id"));
                    genero.setDescricao(resultado.getString("descricao"));
                    genero.setSigla(resultado.getString("sigla"));
                    genero.setStat(resultado.getString("stat"));

                    lista.add(genero);
                }

                resultado.close();
                sentenca.close();
                this.conexao.getConnection().close();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return lista;
    }
}
