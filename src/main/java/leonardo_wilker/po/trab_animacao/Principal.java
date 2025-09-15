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
    private javafx.scene.control.Label vetorLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();

        // Label para mostrar o vetor
        vetorLabel = new javafx.scene.control.Label();
        vetorLabel.setLayoutX(50);
        vetorLabel.setLayoutY(150);
        vetorLabel.setFont(new Font("Consolas", 18));
        pane.getChildren().add(vetorLabel);
        // Botões de ordenação
        botao_heapsort = new Button("HeapSort");
        botao_heapsort.setLayoutX(10);
        botao_heapsort.setLayoutY(50);
        botao_heapsort.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        botao_heapsort.setOnAction(e -> {
            if (!animacaoEmAndamento) iniciarHeapSort();
        });
        pane.getChildren().add(botao_heapsort);

        botao_timsort = new Button("TimSort");
        botao_timsort.setLayoutX(120);
        botao_timsort.setLayoutY(50);
        botao_timsort.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        botao_timsort.setOnAction(e -> {
            if (!animacaoEmAndamento) iniciarTimSort();
        });
        pane.getChildren().add(botao_timsort);

        // Array de valores para ordenar
        //int[] valores = {45, 23, 11, 89, 77, 98, 4, 28, 65, 43};
        int[] valores = {40,90,20,10,50,70,80};
        vet = new Button[valores.length];

        // Criar botões para cada valor
        for (int i = 0; i < valores.length; i++) {
            vet[i] = new Button(String.valueOf(valores[i]));
            vet[i].setLayoutX(50 + i * 70);  // Espaçamento horizontal entre botões
            vet[i].setLayoutY(250);
            vet[i].setMinHeight(50);
            vet[i].setMinWidth(50);
            vet[i].setFont(new Font("Consolas", 18));
            vet[i].setStyle("-fx-background-radius: 25; -fx-background-color: #f0f0f0; -fx-border-color: #888; -fx-border-width: 2px;");
            pane.getChildren().add(vet[i]);
        }

        atualizarVetorLabel();

        Scene scene = new Scene(pane, 900, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void animarTroca(Button btn1, Button btn2, Runnable onComplete) {
        double x1 = btn1.getLayoutX();
        double x2 = btn2.getLayoutX();

        // Destacar botões
        Platform.runLater(() -> {
            btn1.setStyle(btn1.getStyle() + ";-fx-background-color: #FFD700; -fx-border-color: #FF9800; -fx-border-width: 3px;");
            btn2.setStyle(btn2.getStyle() + ";-fx-background-color: #FFD700; -fx-border-color: #FF9800; -fx-border-width: 3px;");
        });

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Move para cima/baixo
                for (int i = 0; i < 10; i++) {
                    final int step = i;
                    Platform.runLater(() -> {
                        btn1.setLayoutY(250 - step * 5);
                        btn2.setLayoutY(250 + step * 5);
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
                        btn1.setLayoutY(250 - (step - 1) * 5);
                        btn2.setLayoutY(250 + (step - 1) * 5);
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
            btn1.setLayoutY(250);
            btn2.setLayoutY(250);

            // Remover destaque
            btn1.setStyle("-fx-background-radius: 25; -fx-background-color: #f0f0f0; -fx-border-color: #888; -fx-border-width: 2px; -fx-font-size: 18px;");
            btn2.setStyle("-fx-background-radius: 25; -fx-background-color: #f0f0f0; -fx-border-color: #888; -fx-border-width: 2px; -fx-font-size: 18px;");

            atualizarVetorLabel();

            if (onComplete != null) {
                onComplete.run();
            }
        });

        new Thread(task).start();
    }

    private void animarTrocas(List<OrdenacaoController.Troca> trocas) {
        if (trocas.isEmpty()) {
            animacaoEmAndamento = false;
            atualizarVetorLabel();
            return;
        }

        OrdenacaoController.Troca troca = trocas.get(0);
        List<OrdenacaoController.Troca> restantes = trocas.subList(1, trocas.size());

        animarTroca(vet[troca.i], vet[troca.j], () -> animarTrocas(restantes));
    }
    private void atualizarVetorLabel() {
        StringBuilder sb = new StringBuilder("Vetor: [ ");
        for (int i = 0; i < vet.length; i++) {
            sb.append(vet[i].getText());
            if (i < vet.length - 1) sb.append(", ");
        }
        sb.append(" ]");
        vetorLabel.setText(sb.toString());
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
        //System.out.println(trocas);
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