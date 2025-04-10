import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Main {
    public static void main(String[] args) {
            Fabrica fabrica = new Fabrica();
            fabrica.iniciarProducao();
            fabrica.iniciarServidor();

    }
}