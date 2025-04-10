import java.io.Serializable;

class Veiculo implements Serializable {
    static int contadorId = 0;
    int id;
    String cor;
    String tipo;
    int idEstacao;
    int idFuncionario;
    int posicaoEsteira;

    public Veiculo(String cor, String tipo, int idEstacao, int idFuncionario, int posicaoEsteira) {
        this.id = ++contadorId;
        this.cor = cor;
        this.tipo = tipo;
        this.idEstacao = idEstacao;
        this.idFuncionario = idFuncionario;
        this.posicaoEsteira = posicaoEsteira;
    }

    @Override
    public String toString() {
        return "Veiculo{id=" + id + ", cor='" + cor + "', tipo='" + tipo + "', idEstacao=" + idEstacao + ", idFuncionario=" + idFuncionario + ", posicaoEsteira=" + posicaoEsteira + "}";
    }

    public int getId() {
        return id;
    }

    public String getCor() {
        return cor;
    }

    public String getTipo() {
        return tipo;
    }

}