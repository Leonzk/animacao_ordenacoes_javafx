package leonardo_wilker.po.trab_animacao;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Principal extends Application {
    AnchorPane pane;
    Button botao_heapsort, botao_timsort;
    private Button vet[];
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
        botao_heapsort.setOnAction(e -> iniciarHeapSort());
        pane.getChildren().add(botao_heapsort);

        botao_timsort = new Button("TimSort");
        botao_timsort.setLayoutX(120);
        botao_timsort.setLayoutY(50);
        botao_timsort.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        botao_timsort.setOnAction(e -> iniciarTimSort());
        pane.getChildren().add(botao_timsort);

        // Array de valores para visualizar
        //int[] valores = {45, 23, 11, 89, 77, 98, 4, 28, 65, 43};
        //int[] valores = {40,90,20,10,50,70,80};
        int[] valores = {8, 5, 6, 4, 7, 9};
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
        int[] valores = new int[vet.length];
        for (int i = 0; i < vet.length; i++) {
            valores[i] = Integer.parseInt(vet[i].getText());
        }
        
        HeapSortVisualizerController visualizador = new HeapSortVisualizerController();
        visualizador.mostrarVisualizacaoHeapSort(valores);
    }

    private void iniciarTimSort() {
        int[] valores = new int[vet.length];
        for (int i = 0; i < vet.length; i++) {
            valores[i] = Integer.parseInt(vet[i].getText());
        }
        
        TimSortVisualizerController visualizador = new TimSortVisualizerController();
        visualizador.mostrarVisualizacaoTimSort(valores);
    }
}