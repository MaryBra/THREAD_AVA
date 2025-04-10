//public class TesteFabrica {
//    public static void main(String[] args) {
//        Fabrica fabrica = new Fabrica();
//
//        // Vamos simular 10 funcionários
//        int numeroFuncionarios = 10;
//
//        for (int i = 0; i < numeroFuncionarios; i++) {
//            int idFuncionario = i + 1;
//            int idEstacao = (i % 3) + 1; // alterna entre 1, 2 e 3
//
//            new Thread(() -> {
//                while (true) {
//                    try {
//                        fabrica.produzirVeiculo(idEstacao, idFuncionario);
//                    } catch (InterruptedException e) {
//                        break; // Se der problema, sai da thread
//                    }
//                }
//            }).start();
//        }
//    }
//}
