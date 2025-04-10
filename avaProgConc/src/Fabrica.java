import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;


class Fabrica {
    private static final int ESTOQUE_PEÇAS = 500;
    private Semaphore estoqueSemaforo = new Semaphore(ESTOQUE_PEÇAS);
    private Semaphore esteiraSemaforo = new Semaphore(5);
    private BlockingQueue<Veiculo> esteiraProducao = new ArrayBlockingQueue<>(40);
    private Random random = new Random();
    public static final String BLUE = "\u001B[34m";

    private static final String[] cores = {"Vermelho", "Verde", "Azul"};
    private static final String[] tipos = {"SUV", "SEDAN"};

    private int contadorCores = 0;
    private int contadorTipos = 0;

    private final Object lock = new Object();

    public void iniciarServidor() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(12345)) { // Porta 12345
                System.out.println("Servidor da fábrica iniciado, aguardando conexões...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Loja conectada: " + socket.getInetAddress());

                    new Thread(() -> atenderLoja(socket)).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void iniciarProducao() {
        for (int i = 0; i < 4; i++) {
            int idEstacao = i + 1;
            for (int j = 0; j < 5; j++) {
                int idFuncionario = j + 1;
                new Thread(() -> {
                    try {
                        while (true) {
                            produzirVeiculo(idEstacao, idFuncionario);
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Produção interrompida: " + e.getMessage());
                    }
                }).start();
            }
        }
    }

    public void produzirVeiculo(int idEstacao, int idFuncionario) throws InterruptedException  {
        try {
            if (!estoqueSemaforo.tryAcquire()) {
                throw new InterruptedException("Sem peças disponíveis");
            }

            estoqueSemaforo.acquire();

            esteiraSemaforo.acquire();

            Thread.sleep(1000);

            String cor, tipo;
            synchronized (lock) {
                cor = cores[contadorCores % cores.length];
                contadorCores++;

                tipo = tipos[contadorTipos % tipos.length];
                contadorTipos++;
            }

            synchronized (this) {
                Veiculo veiculo = new Veiculo(cor, tipo, idEstacao, idFuncionario, esteiraProducao.size());

                esteiraProducao.put(veiculo);

                System.out.println("[LOG PRODUÇÃO] - Fábrica: " + veiculo);
            }

            esteiraSemaforo.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean temPecasDisponiveis() {
        return estoqueSemaforo.availablePermits() > 0;
    }

    public BlockingQueue<Veiculo> getEsteiraProducao() {
        return esteiraProducao;
    }

    private void atenderLoja(Socket socket) {
        try (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            String pedido = (String) in.readObject();
            System.out.println("Pedido recebido: " + pedido);

            if ("comprar".equals(pedido)) {
                Veiculo veiculo = esteiraProducao.take();
                out.writeObject(veiculo);
                out.flush();
                System.out.println("\u001B[45m"+ "\uD83C\uDFEC[LOG VENDA FÁBRICA] - Veículo enviado para loja: " + veiculo + "\u001B[0m");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}