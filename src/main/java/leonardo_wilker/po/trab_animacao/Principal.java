package leonardo_wilker.po.trab_animacao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Principal extends Application {
    AnchorPane pane;
    Button botao_heapsort, botao_timsort;
    private Button vet[];
    private boolean animacaoEmAndamento = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        
        // Botões de ordenação
        botao_heapsort = new Button("HeapSort");
        botao_heapsort.setLayoutX(10);
        botao_heapsort.setLayoutY(50);
        botao_heapsort.setOnAction(e -> {
            if (!animacaoEmAndamento) iniciarHeapSort();
        });
        pane.getChildren().add(botao_heapsort);

        botao_timsort = new Button("TimSort");
        botao_timsort.setLayoutX(100);
        botao_timsort.setLayoutY(50);
        botao_timsort.setOnAction(e -> {
            if (!animacaoEmAndamento) iniciarTimSort();
        });
        pane.getChildren().add(botao_timsort);

        // Array de valores para ordenar
        int[] valores = {45, 23, 11, 89, 77, 98, 4, 28, 65, 43};
        vet = new Button[valores.length];
        
        // Criar botões para cada valor
        for (int i = 0; i < valores.length; i++) {
            vet[i] = new Button(String.valueOf(valores[i]));
            vet[i].setLayoutX(50 + i * 70);  // Espaçamento horizontal entre botões
            vet[i].setLayoutY(200);
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            vet[i].setFont(new Font(14));
            pane.getChildren().add(vet[i]);
        }

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void animarTroca(Button btn1, Button btn2, Runnable onComplete) {
        double x1 = btn1.getLayoutX();
        double x2 = btn2.getLayoutX();
        
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Move para cima/baixo
                for (int i = 0; i < 10; i++) {
                    final int step = i;
                    Platform.runLater(() -> {
                        btn1.setLayoutY(200 - step * 5);
                        btn2.setLayoutY(200 + step * 5);
                    });
                    Thread.sleep(30);
                }
                
                // Move lateralmente
                int steps = (int) Math.abs(x2 - x1) / 10;
                double stepX = (x2 - x1) / steps;
                for (int i = 0; i < steps; i++) {
                    final double moveX = stepX;
                    Platform.runLater(() -> {
                        btn1.setLayoutX(btn1.getLayoutX() + moveX);
                        btn2.setLayoutX(btn2.getLayoutX() - moveX);
                    });
                    Thread.sleep(30);
                }
                
                // Move de volta verticalmente
                for (int i = 10; i > 0; i--) {
                    final int step = i;
                    Platform.runLater(() -> {
                        btn1.setLayoutY(200 - (step - 1) * 5);
                        btn2.setLayoutY(200 + (step - 1) * 5);
                    });
                    Thread.sleep(30);
                }
                
                return null;
            }
        };
        
        task.setOnSucceeded(e -> {
            // Troca os textos dos botões
            String temp = btn1.getText();
            btn1.setText(btn2.getText());
            btn2.setText(temp);
            
            // Reset posições
            btn1.setLayoutX(x1);
            btn2.setLayoutX(x2);
            btn1.setLayoutY(200);
            btn2.setLayoutY(200);
            
            if (onComplete != null) {
                onComplete.run();
            }
        });
        
        new Thread(task).start();
    }

    private void animarTrocas(List<OrdenacaoController.Troca> trocas) {
        if (trocas.isEmpty()) {
            animacaoEmAndamento = false;
            return;
        }
        
        OrdenacaoController.Troca troca = trocas.get(0);
        List<OrdenacaoController.Troca> restantes = trocas.subList(1, trocas.size());
        
        animarTroca(vet[troca.i], vet[troca.j], () -> animarTrocas(restantes));
    }

    private void iniciarHeapSort() {
        if (animacaoEmAndamento) return;
        animacaoEmAndamento = true;
        
        OrdenacaoController controller = new OrdenacaoController();
        int[] valores = new int[vet.length];
        for (int i = 0; i < vet.length; i++) {
            valores[i] = Integer.parseInt(vet[i].getText());
        }
        
        List<OrdenacaoController.Troca> trocas = controller.heapSortAnimado(valores);
        animarTrocas(trocas);
    }

    private void iniciarTimSort() {
        if (animacaoEmAndamento) return;
        animacaoEmAndamento = true;
        
        OrdenacaoController controller = new OrdenacaoController();
        int[] valores = new int[vet.length];
        for (int i = 0; i < vet.length; i++) {
            valores[i] = Integer.parseInt(vet[i].getText());
        }
        
        List<OrdenacaoController.Troca> trocas = controller.timSortAnimado(valores);
        animarTrocas(trocas);
    }
}