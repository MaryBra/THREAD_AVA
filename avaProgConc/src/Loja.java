import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Loja extends Thread {
    private BlockingQueue<Veiculo> estoqueLoja = new LinkedBlockingQueue<>(20);
    private BlockingQueue<Veiculo> esteiraVenda;
    private int idLoja;
    public static final String GREEN = "\u001B[32m";


    public Loja(int idLoja) {
        this.idLoja = idLoja;
        this.esteiraVenda = new ArrayBlockingQueue<>(50);
    }

    public void comprarVeiculo() {
        try {
            Socket socket = new Socket("127.0.0.1", 12345);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject("comprar");
            out.flush();

            Veiculo veiculo = (Veiculo) in.readObject();
            esteiraVenda.put(veiculo);

            System.out.println("🚗[LOG RECEBIMENTO LOJA] Loja " + idLoja + " recebeu veículo: " + veiculo);
//            System.out.println("Loja " + idLoja + " recebeu veículo: " + veiculo);

            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Veiculo venderVeiculo() {
        synchronized (estoqueLoja) {
            if (estoqueLoja.isEmpty()) {
                return null;
            } else {
                return estoqueLoja.poll();
            }
        }
    }

    public BlockingQueue<Veiculo> getEsteiraVenda() {
        return esteiraVenda;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Veiculo veiculo = esteiraVenda.take();
                int posicaoNaEsteira = esteiraVenda.size();

                estoqueLoja.put(veiculo);

                System.out.println("\033[32m"+"\uD83D\uDCB5[LOG VENDA FÁBRICA -> LOJA] Veículo vendido -> " + veiculo
                        + ", Loja ID: " + this.idLoja + ", Posição na esteira da loja: " + posicaoNaEsteira + "\033[0m");

//                System.out.println("Loja " + idLoja + " colocou carro no estoque: " + veiculo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public long getId() {
        return idLoja;
    }
}
