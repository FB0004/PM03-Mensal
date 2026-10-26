
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Cliente;

public class ClienteDao {

    private ConexaoBanco conexao;

    public ClienteDao() {
        this.conexao = new ConexaoBanco();
    }

    public void inserir(Cliente cliente) {

        String sql = "INSERT INTO clientes "
                + "(nome, cpf, data_nascimento, telefone, genero_id, "
                + "cep, logradouro, bairro, cidade, uf, numero, complemento) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            if (this.conexao.conectar()) {

                Connection conn = this.conexao.getConnection();
                PreparedStatement sentenca = conn.prepareStatement(sql);

                sentenca.setString(1, cliente.getNome());
                sentenca.setString(2, cliente.getCpf());
                sentenca.setDate(3, Date.valueOf(cliente.getDataNascimento()));
                sentenca.setString(4, cliente.getTelefone());
                sentenca.setInt(5, cliente.getGeneroId());
                sentenca.setString(6, cliente.getCep());
                sentenca.setString(7, cliente.getLogradouro());
                sentenca.setString(8, cliente.getBairro());
                sentenca.setString(9, cliente.getCidade());
                sentenca.setString(10, cliente.getUf());
                sentenca.setString(11, cliente.getNumero());
                sentenca.setString(12, cliente.getComplemento());

                sentenca.executeUpdate();

                sentenca.close();
                conn.close();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void alterar(Cliente cliente) {

        String sql = "UPDATE clientes SET "
                + "nome = ?, cpf = ?, data_nascimento = ?, "
                + "telefone = ?, genero_id = ?, cep = ?, "
                + "logradouro = ?, bairro = ?, cidade = ?, "
                + "uf = ?, numero = ?, complemento = ? "
                + "WHERE id = ?";

        try {
            if (this.conexao.conectar()) {

                Connection conn = this.conexao.getConnection();
                PreparedStatement sentenca = conn.prepareStatement(sql);

                sentenca.setString(1, cliente.getNome());
                sentenca.setString(2, cliente.getCpf());
                sentenca.setDate(3, Date.valueOf(cliente.getDataNascimento()));
                sentenca.setString(4, cliente.getTelefone());
                sentenca.setInt(5, cliente.getGeneroId());
                sentenca.setString(6, cliente.getCep());
                sentenca.setString(7, cliente.getLogradouro());
                sentenca.setString(8, cliente.getBairro());
                sentenca.setString(9, cliente.getCidade());
                sentenca.setString(10, cliente.getUf());
                sentenca.setString(11, cliente.getNumero());
                sentenca.setString(12, cliente.getComplemento());
                sentenca.setInt(13, cliente.getId());

                sentenca.executeUpdate();

                sentenca.close();
                conn.close();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<Cliente> consultar() {

        ArrayList<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM clientes ORDER BY id";

        try {
            if (this.conexao.conectar()) {

                Connection conn = this.conexao.getConnection();
                PreparedStatement sentenca = conn.prepareStatement(sql);
                ResultSet resultado = sentenca.executeQuery();

                while (resultado.next()) {

                    Cliente cliente = new Cliente();

                    cliente.setId(resultado.getInt("id"));
                    cliente.setNome(resultado.getString("nome"));
                    cliente.setCpf(resultado.getString("cpf"));
                    cliente.setDataNascimento(
                            resultado.getDate("data_nascimento").toLocalDate());
                    cliente.setTelefone(resultado.getString("telefone"));
                    cliente.setGeneroId(resultado.getInt("genero_id"));
                    cliente.setCep(resultado.getString("cep"));
                    cliente.setLogradouro(resultado.getString("logradouro"));
                    cliente.setBairro(resultado.getString("bairro"));
                    cliente.setCidade(resultado.getString("cidade"));
                    cliente.setUf(resultado.getString("uf"));
                    cliente.setNumero(resultado.getString("numero"));
                    cliente.setComplemento(resultado.getString("complemento"));

                    lista.add(cliente);
                }

                resultado.close();
                sentenca.close();
                conn.close();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return lista;
    }
}
