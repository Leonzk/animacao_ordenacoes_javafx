package leonardo_wilker.po.trab_animacao;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;

public class HeapSortVisualizerController {
    private AnchorPane painel;
    private Button[] botoesArrayPrincipal;
    private Stage palco;

    public void mostrarVisualizacaoHeapSort(int[] valores) {
        palco = new Stage();
        palco.setTitle("HeapSort Visualization");
        painel = new AnchorPane();
        
        botoesArrayPrincipal = new Button[valores.length];

        // Criar visualização do array principal
        for (int i = 0; i < valores.length; i++) {
            botoesArrayPrincipal[i] = criarBotaoArray(valores[i], i);
            painel.getChildren().add(botoesArrayPrincipal[i]);
        }

        // Label para explicações
        Label labelExplicacao = new Label("HeapSort Visualization");
        labelExplicacao.setLayoutX(50);
        labelExplicacao.setLayoutY(50);
        labelExplicacao.setFont(new Font("Consolas", 18));
        painel.getChildren().add(labelExplicacao);

        Scene cena = new Scene(painel, 1000, 600);
        palco.setScene(cena);
        palco.show();

        // Iniciar a animação
        iniciarAnimacaoHeapSort(valores);
    }

    private Button criarBotaoArray(int valor, int indice) {
        Button botao = new Button(String.valueOf(valor));
        botao.setLayoutX(50 + indice * 70);
        botao.setLayoutY(150);
        botao.setMinSize(50, 50);
        botao.setFont(new Font("Consolas", 18));
        botao.setStyle("-fx-background-radius: 25; -fx-background-color: #f0f0f0; -fx-border-color: #888; -fx-border-width: 2px;");
        return botao;
    }

    private void iniciarAnimacaoHeapSort(int[] arranjo) {
        Task<Void> tarefa = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int tamanho = arranjo.length;
                
                Platform.runLater(() -> {
                    Label labelFase1 = new Label("Fase 1: Construindo o Max-Heap");
                    labelFase1.setLayoutX(50);
                    labelFase1.setLayoutY(100);
                    labelFase1.setFont(new Font("Consolas", 16));
                    labelFase1.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
                    painel.getChildren().add(labelFase1);
                });
                Thread.sleep(2000);

                // Fase 1: Construir o heap (heapify bottom-up)
                for (int i = tamanho / 2 - 1; i >= 0 && !isCancelled(); i--) {
                    heapificarAnimado(arranjo, tamanho, i, "Construindo heap - nó " + i);
                    Thread.sleep(1000);
                }

                Platform.runLater(() -> {
                    Label labelFase2 = new Label("Fase 2: Extraindo elementos do heap e ordenando");
                    labelFase2.setLayoutX(50);
                    labelFase2.setLayoutY(120);
                    labelFase2.setFont(new Font("Consolas", 16));
                    labelFase2.setStyle("-fx-text-fill: #2196F3; -fx-font-weight: bold;");
                    painel.getChildren().add(labelFase2);
                });
                Thread.sleep(2000);

                // Fase 2: Extrair elementos do heap um por um
                for (int i = tamanho - 1; i > 0 && !isCancelled(); i--) {
                    // Mover a raiz atual para o final
                    trocarAnimado(arranjo, 0, i, "Movendo maior elemento para posição " + i);
                    Thread.sleep(1500);
                    
                    // Destacar elemento ordenado
                    final int indiceOrdenado = i;
                    Platform.runLater(() -> {
                        botoesArrayPrincipal[indiceOrdenado].setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-color: #2E7D32; -fx-border-width: 2px;");
                    });
                    
                    // Chamar heapify na raiz reduzida
                    heapificarAnimado(arranjo, i, 0, "Reorganizando heap reduzido");
                    Thread.sleep(1000);
                }

                Platform.runLater(() -> {
                    Label labelConcluido = new Label("HeapSort Concluído! Array Ordenado");
                    labelConcluido.setLayoutX(50);
                    labelConcluido.setLayoutY(200);
                    labelConcluido.setFont(new Font("Consolas", 16));
                    labelConcluido.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
                    painel.getChildren().add(labelConcluido);
                    
                    // Destacar array final ordenado
                    for (int i = 0; i < tamanho; i++) {
                        botoesArrayPrincipal[i].setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-color: #2E7D32; -fx-border-width: 2px;");
                    }
                });

                return null;
            }
        };

        tarefa.setOnFailed(e -> {
            tarefa.getException().printStackTrace();
        });

        new Thread(tarefa).start();
    }

    private void heapificarAnimado(int[] arranjo, int tamanhoHeap, int indiceRaiz, String descricao) throws InterruptedException {
        Platform.runLater(() -> {
            // Remover apenas labels temporários de heapify (posição Y=300)
            painel.getChildren().removeIf(node -> 
                node instanceof Label && 
                node.getLayoutY() == 300.0);
            
            Label labelHeapificar = new Label(descricao);
            labelHeapificar.setLayoutX(50);
            labelHeapificar.setLayoutY(300);
            labelHeapificar.setFont(new Font("Consolas", 14));
            labelHeapificar.setStyle("-fx-text-fill: #FF9800;");
            painel.getChildren().add(labelHeapificar);
        });
        
        int maior = indiceRaiz;
        int filhoEsquerdo = 2 * indiceRaiz + 1;
        int filhoDireito = 2 * indiceRaiz + 2;
        
        // Destacar o nó raiz sendo analisado
        Platform.runLater(() -> {
            botoesArrayPrincipal[indiceRaiz].setStyle("-fx-background-color: #FFD700; -fx-border-color: #FF9800; -fx-border-width: 3px;");
        });
        Thread.sleep(800);
        
        // Destacar filhos se existirem
        if (filhoEsquerdo < tamanhoHeap) {
            Platform.runLater(() -> {
                botoesArrayPrincipal[filhoEsquerdo].setStyle("-fx-background-color: #BBDEFB; -fx-border-color: #2196F3; -fx-border-width: 2px;");
            });
        }
        if (filhoDireito < tamanhoHeap) {
            Platform.runLater(() -> {
                botoesArrayPrincipal[filhoDireito].setStyle("-fx-background-color: #FFCDD2; -fx-border-color: #F44336; -fx-border-width: 2px;");
            });
        }
        Thread.sleep(1000);
        
        // Encontrar o maior entre raiz, filho esquerdo e direito
        if (filhoEsquerdo < tamanhoHeap && arranjo[filhoEsquerdo] > arranjo[maior]) {
            maior = filhoEsquerdo;
        }
        if (filhoDireito < tamanhoHeap && arranjo[filhoDireito] > arranjo[maior]) {
            maior = filhoDireito;
        }
        
        // Se o maior não é a raiz, fazer a troca e continuar heapificando
        if (maior != indiceRaiz) {
            final int indiceMaior = maior;
            Platform.runLater(() -> {
                botoesArrayPrincipal[indiceMaior].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 3px;");
            });
            Thread.sleep(800);
            
            trocarAnimado(arranjo, indiceRaiz, maior, "Trocando " + arranjo[indiceRaiz] + " com " + arranjo[maior]);
            Thread.sleep(1000);
            
            // Recursivamente heapificar a subárvore afetada
            heapificarAnimado(arranjo, tamanhoHeap, maior, "Heapificando subárvore em " + maior);
        } else {
            // Remover destaques se não houve troca
            Platform.runLater(() -> {
                botoesArrayPrincipal[indiceRaiz].setStyle("-fx-background-radius: 25; -fx-background-color: #f0f0f0; -fx-border-color: #888; -fx-border-width: 2px;");
                if (filhoEsquerdo < tamanhoHeap) {
                    botoesArrayPrincipal[filhoEsquerdo].setStyle("-fx-background-radius: 25; -fx-background-color: #f0f0f0; -fx-border-color: #888; -fx-border-width: 2px;");
                }
                if (filhoDireito < tamanhoHeap) {
                    botoesArrayPrincipal[filhoDireito].setStyle("-fx-background-radius: 25; -fx-background-color: #f0f0f0; -fx-border-color: #888; -fx-border-width: 2px;");
                }
            });
        }
        

    }

    private void trocarAnimado(int[] arranjo, int i, int j, String descricao) throws InterruptedException {
        final Button botao1 = botoesArrayPrincipal[i];
        final Button botao2 = botoesArrayPrincipal[j];
        
        Platform.runLater(() -> {
            // Remover apenas labels temporários de swap (posição Y=325)
            painel.getChildren().removeIf(node -> 
                node instanceof Label && 
                node.getLayoutY() == 325.0);
            
            Label labelTroca = new Label(descricao);
            labelTroca.setLayoutX(50);
            labelTroca.setLayoutY(325);
            labelTroca.setFont(new Font("Consolas", 12));
            labelTroca.setStyle("-fx-text-fill: #E91E63;");
            painel.getChildren().add(labelTroca);
            
            // Destacar elementos sendo trocados
            botao1.setStyle("-fx-background-color: #FFD700; -fx-border-color: #FF9800; -fx-border-width: 3px;");
            botao2.setStyle("-fx-background-color: #FFD700; -fx-border-color: #FF9800; -fx-border-width: 3px;");
            
            // Criar animações de movimento
            TranslateTransition moverBotao1 = new TranslateTransition(Duration.millis(600), botao1);
            TranslateTransition moverBotao2 = new TranslateTransition(Duration.millis(600), botao2);
            
            // Movimento vertical para cima/baixo
            moverBotao1.setByY(-25);
            moverBotao2.setByY(25);
            
            moverBotao1.setOnFinished(e1 -> {
                // Movimento horizontal
                TranslateTransition horizontalBotao1 = new TranslateTransition(Duration.millis(500), botao1);
                TranslateTransition horizontalBotao2 = new TranslateTransition(Duration.millis(500), botao2);
                
                double distancia = (j - i) * 70; // 70 é o espaçamento entre botões
                horizontalBotao1.setByX(distancia);
                horizontalBotao2.setByX(-distancia);
                
                horizontalBotao1.setOnFinished(e2 -> {
                    // Movimento vertical de volta
                    TranslateTransition voltarBotao1 = new TranslateTransition(Duration.millis(500), botao1);
                    TranslateTransition voltarBotao2 = new TranslateTransition(Duration.millis(500), botao2);
                    
                    voltarBotao1.setByY(25); // Voltar para posição original
                    voltarBotao2.setByY(-25);
                    
                    voltarBotao1.setOnFinished(e3 -> {
                        // Realizar a troca no array
                        int temp = arranjo[i];
                        arranjo[i] = arranjo[j];
                        arranjo[j] = temp;
                        
                        // Trocar textos e resetar posições
                        String textoTemp = botao1.getText();
                        botao1.setText(botao2.getText());
                        botao2.setText(textoTemp);
                        
                        // Resetar transformações
                        botao1.setTranslateX(0);
                        botao1.setTranslateY(0);
                        botao2.setTranslateX(0);
                        botao2.setTranslateY(0);
                        
                        // Restaurar estilo
                        botao1.setStyle("-fx-background-radius: 25; -fx-background-color: #f0f0f0; -fx-border-color: #888; -fx-border-width: 2px;");
                        botao2.setStyle("-fx-background-radius: 25; -fx-background-color: #f0f0f0; -fx-border-color: #888; -fx-border-width: 2px;");
                        

                    });
                    
                    ParallelTransition voltarParalelo = new ParallelTransition(voltarBotao1, voltarBotao2);
                    voltarParalelo.play();
                });
                
                ParallelTransition horizontalParalelo = new ParallelTransition(horizontalBotao1, horizontalBotao2);
                horizontalParalelo.play();
            });
            
            ParallelTransition verticalParalelo = new ParallelTransition(moverBotao1, moverBotao2);
            verticalParalelo.play();
        });
        
        Thread.sleep(2200); // Aguardar animação completar
    }
}