import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cliente extends Thread {
    private int idCliente;
    private Loja[] lojas;
    private Random random = new Random();
    private List<Veiculo> garagem = new ArrayList<>();


    public Cliente(int idCliente, Loja[] lojas) {
        this.idCliente = idCliente;
        this.lojas = lojas;
    }

    @Override
    public void run() {
        while (true) {
            int indiceLoja = random.nextInt(lojas.length);
            Loja lojaEscolhida = lojas[indiceLoja];

            Veiculo veiculo = lojaEscolhida.venderVeiculo();

            if (veiculo == null) {
//                System.out.println("Cliente " + idCliente + " tentou comprar na Loja " + lojaEscolhida.getId() + " mas estava vazia, esperando...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("\uD83C\uDF89[LOG VENDA LOJA] Cliente " + idCliente + " comprou veículo: " + veiculo + ", Garagem: posição X");
//                System.out.println("Cliente " + idCliente + " comprou " + veiculo + " da Loja " + lojaEscolhida.getId());
                garagem.add(veiculo);
                System.out.println("\u001B[41m" + "[LOG GARAGEM CLIENTE] - Cliente " + idCliente + " colocou veículo ID=" + veiculo.getId() + " na garagem. Total na garagem: " + garagem.size() + "\u001B[0m");



                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
