package leonardo_wilker.po.trab_animacao;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TimSortVisualizerController {
    private static final int RUN = 4;
    private AnchorPane pane;
    private Button[] mainArrayButtons;
    private Stage stage;

    public void showTimSortVisualization(int[] valores) {
        stage = new Stage();
        stage.setTitle("TimSort Visualization");
        pane = new AnchorPane();
        
        mainArrayButtons = new Button[valores.length];

        // Criar visualização do array principal
        for (int i = 0; i < valores.length; i++) {
            mainArrayButtons[i] = createArrayButton(valores[i], i);
            pane.getChildren().add(mainArrayButtons[i]);
        }

        // Label para explicações
        Label explanationLabel = new Label("TimSort Visualization");
        explanationLabel.setLayoutX(50);
        explanationLabel.setLayoutY(50);
        explanationLabel.setFont(new Font("Consolas", 18));
        pane.getChildren().add(explanationLabel);

        Scene scene = new Scene(pane, 1000, 600);
        stage.setScene(scene);
        stage.show();

        // Iniciar a animação
        startTimSortAnimation(valores);
    }

    private Button createArrayButton(int value, int index) {
        Button btn = new Button(String.valueOf(value));
        btn.setLayoutX(50 + index * 70);
        btn.setLayoutY(150);
        btn.setMinSize(50, 50);
        btn.setFont(new Font("Consolas", 18));
        btn.setStyle("-fx-background-radius: 25; -fx-background-color: #f0f0f0; -fx-border-color: #888; -fx-border-width: 2px;");
        return btn;
    }



    private void startTimSortAnimation(int[] arr) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int n = arr.length;
                
                Platform.runLater(() -> {
                    Label phase1Label = new Label("Fase 1: Dividindo em RUNs e ordenando com Insertion Sort");
                    phase1Label.setLayoutX(50);
                    phase1Label.setLayoutY(100);
                    phase1Label.setFont(new Font("Consolas", 16));
                    phase1Label.setStyle("-fx-text-fill: #2196F3; -fx-font-weight: bold;");
                    pane.getChildren().add(phase1Label);
                });
                Thread.sleep(2000);

                // Fase 1: Dividir em runs e ordenar cada um com insertion sort
                for (int i = 0; i < n && !isCancelled(); i += RUN) {
                    final int start = i;
                    final int end = Math.min(i + RUN - 1, n - 1);
                    
                    Platform.runLater(() -> {
                        // Destacar o run atual no array principal
                        for (int j = start; j <= end; j++) {
                            mainArrayButtons[j].setStyle("-fx-background-color: #FFE082; -fx-border-color: #FF9800; -fx-border-width: 3px;");
                        }
                    });
                    Thread.sleep(1000);
                    
                    // Realizar insertion sort animado no run atual
                    insertionSortAnimated(arr, start, end);
                    Thread.sleep(1500);
                    
                    Platform.runLater(() -> {
                        // Remover destaque após ordenação
                        for (int j = start; j <= end; j++) {
                            mainArrayButtons[j].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
                        }
                    });
                    Thread.sleep(1000);
                }

                Platform.runLater(() -> {
                    Label phase2Label = new Label("Fase 2: Mesclando os RUNs ordenados");
                    phase2Label.setLayoutX(50);
                    phase2Label.setLayoutY(120);
                    phase2Label.setFont(new Font("Consolas", 16));
                    phase2Label.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
                    pane.getChildren().add(phase2Label);
                });
                Thread.sleep(2000);

                // Fase 2: Mesclar os runs ordenados
                for (int size = RUN; size < n && !isCancelled(); size = 2 * size) {
                    for (int left = 0; left < n && !isCancelled(); left += 2 * size) {
                        int mid = left + size - 1;
                        int right = Math.min((left + 2 * size - 1), (n - 1));
                        if (mid < right) {
                            merge(arr, left, mid, right);
                            Thread.sleep(2000);
                        }
                    }
                }

                Platform.runLater(() -> {
                    Label completedLabel = new Label("TimSort Concluído! Array Ordenado");
                    completedLabel.setLayoutX(50);
                    completedLabel.setLayoutY(240);
                    completedLabel.setFont(new Font("Consolas", 16));
                    completedLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
                    pane.getChildren().add(completedLabel);
                    
                    // Destacar array final ordenado
                    for (int i = 0; i < n; i++) {
                        mainArrayButtons[i].setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-color: #2E7D32; -fx-border-width: 2px;");
                    }
                });

                return null;
            }
        };

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });

        new Thread(task).start();
    }

    private void insertionSortAnimated(int[] arr, int left, int right) throws InterruptedException {
        Platform.runLater(() -> {
            Label sortingLabel = new Label("Ordenando RUN de índice " + left + " até " + right + " com Insertion Sort");
            sortingLabel.setLayoutX(50);
            sortingLabel.setLayoutY(320);
            sortingLabel.setFont(new Font("Consolas", 14));
            sortingLabel.setStyle("-fx-text-fill: #FF9800;");
            pane.getChildren().add(sortingLabel);
        });
        
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // Destacar o elemento atual sendo inserido
            final int currentI = i;
            Platform.runLater(() -> {
                mainArrayButtons[currentI].setStyle("-fx-background-color: #FFD700; -fx-border-color: #FF9800; -fx-border-width: 3px;");
            });
            Thread.sleep(800);
            
            // Encontrar a posição correta e mover elementos
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                
                final int currentJ = j;
                Platform.runLater(() -> {
                    // Animar a movimentação do elemento
                    mainArrayButtons[currentJ + 1].setText(String.valueOf(arr[currentJ + 1]));
                    mainArrayButtons[currentJ + 1].setStyle("-fx-background-color: #FFCDD2; -fx-border-color: #F44336; -fx-border-width: 2px;");
                    mainArrayButtons[currentJ].setStyle("-fx-background-color: #E1F5FE; -fx-border-color: #2196F3; -fx-border-width: 2px;");
                });
                Thread.sleep(600);
                
                j--;
            }
            
            // Inserir o elemento na posição correta
            arr[j + 1] = key;
            final int finalPosition = j + 1;
            Platform.runLater(() -> {
                mainArrayButtons[finalPosition].setText(String.valueOf(key));
                mainArrayButtons[finalPosition].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
            });
            Thread.sleep(800);
        }
        
        // Limpar labels temporários
        Platform.runLater(() -> {
            pane.getChildren().removeIf(node -> 
                node instanceof Label && 
                ((Label) node).getText().contains("Ordenando RUN"));
        });
    }

    private void merge(int[] arr, int l, int m, int r) throws InterruptedException {
        Platform.runLater(() -> {
            Label mergeLabel = new Label("Mesclando RUNs: [" + l + ".." + m + "] com [" + (m+1) + ".." + r + "]");
            mergeLabel.setLayoutX(50);
            mergeLabel.setLayoutY(320);
            mergeLabel.setFont(new Font("Consolas", 14));
            mergeLabel.setStyle("-fx-text-fill: #4CAF50;");
            pane.getChildren().add(mergeLabel);
        });
        
        int[] leftArray = new int[m - l + 1];
        int[] rightArray = new int[r - m];
        
        System.arraycopy(arr, l, leftArray, 0, leftArray.length);
        System.arraycopy(arr, m + 1, rightArray, 0, rightArray.length);
        
        int i = 0, j = 0, k = l;
        
        // Destacar as duas partes que serão mescladas
        Platform.runLater(() -> {
            // Parte esquerda em azul
            for (int idx = l; idx <= m; idx++) {
                mainArrayButtons[idx].setStyle("-fx-background-color: #BBDEFB; -fx-border-color: #2196F3; -fx-border-width: 3px;");
            }
            // Parte direita em laranja
            for (int idx = m + 1; idx <= r; idx++) {
                mainArrayButtons[idx].setStyle("-fx-background-color: #FFE0B2; -fx-border-color: #FF9800; -fx-border-width: 3px;");
            }
        });
        Thread.sleep(1500);
        
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                final int index = k;
                final int value = leftArray[i];
                Platform.runLater(() -> {
                    mainArrayButtons[index].setText(String.valueOf(value));
                    mainArrayButtons[index].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
                });
                Thread.sleep(800);
                i++;
            } else {
                arr[k] = rightArray[j];
                final int index = k;
                final int value = rightArray[j];
                Platform.runLater(() -> {
                    mainArrayButtons[index].setText(String.valueOf(value));
                    mainArrayButtons[index].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
                });
                Thread.sleep(800);
                j++;
            }
            k++;
        }
        
        // Copiar elementos restantes da parte esquerda
        while (i < leftArray.length) {
            arr[k] = leftArray[i];
            final int index = k;
            final int value = leftArray[i];
            Platform.runLater(() -> {
                mainArrayButtons[index].setText(String.valueOf(value));
                mainArrayButtons[index].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
            });
            Thread.sleep(600);
            i++;
            k++;
        }
        
        // Copiar elementos restantes da parte direita
        while (j < rightArray.length) {
            arr[k] = rightArray[j];
            final int index = k;
            final int value = rightArray[j];
            Platform.runLater(() -> {
                mainArrayButtons[index].setText(String.valueOf(value));
                mainArrayButtons[index].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
            });
            Thread.sleep(600);
            j++;
            k++;
        }
        
        // Finalizar merge - destacar área mesclada
        Platform.runLater(() -> {
            for (int idx = l; idx <= r; idx++) {
                mainArrayButtons[idx].setStyle("-fx-background-color: #E8F5E8; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
            }
            
            // Remover label temporário
            pane.getChildren().removeIf(node -> 
                node instanceof Label && 
                ((Label) node).getText().contains("Mesclando RUNs"));
        });
        Thread.sleep(1000);
    }


}