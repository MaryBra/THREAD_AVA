import java.util.Random;

public class MainLoja {
    public static void main(String[] args) {
        Loja loja1 = new Loja(1);
        Loja loja2 = new Loja(2);
        Loja loja3 = new Loja(3);

        Loja[] lojas = {loja1, loja2, loja3};

        loja1.start();
        loja2.start();
        loja3.start();

        Cliente[] clientes = new Cliente[20];
        for (int i = 0; i < 20; i++) {
            clientes[i] = new Cliente(i + 1, lojas);
            clientes[i].start();
        }

        Random random = new Random();

        while (true) {
            int lojaEscolhida = random.nextInt(3) + 1;

            switch (lojaEscolhida) {
                case 1:
                    System.out.println("🛒 Loja 1 tentando comprar...");
                    loja1.comprarVeiculo();
                    break;
                case 2:
                    System.out.println("🛒 Loja 2 tentando comprar...");
                    loja2.comprarVeiculo();
                    break;
                case 3:
                    System.out.println("🛒 Loja 3 tentando comprar...");
                    loja3.comprarVeiculo();
                    break;
            }

            try {
                Thread.sleep(2000); // Espera 2 segundos pra próxima compra
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}