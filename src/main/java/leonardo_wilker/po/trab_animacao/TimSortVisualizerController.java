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

public class TimSortVisualizerController {
    private static final int TAMANHO_RUN = 4;
    private AnchorPane painel;
    private Button[] botoesArrayPrincipal;
    private Stage palco;

    public void mostrarVisualizacaoTimSort(int[] valores) {
        palco = new Stage();
        palco.setTitle("TimSort Visualization");
        painel = new AnchorPane();
        
        botoesArrayPrincipal = new Button[valores.length];

        // Criar visualização do array principal
        for (int i = 0; i < valores.length; i++) {
            botoesArrayPrincipal[i] = criarBotaoArray(valores[i], i);
            painel.getChildren().add(botoesArrayPrincipal[i]);
        }

        // Label para explicações
        Label labelExplicacao = new Label("TimSort Visualization");
        labelExplicacao.setLayoutX(50);
        labelExplicacao.setLayoutY(50);
        labelExplicacao.setFont(new Font("Consolas", 18));
        painel.getChildren().add(labelExplicacao);

        Scene cena = new Scene(painel, 1000, 600);
        palco.setScene(cena);
        palco.show();

        // Iniciar a animação
        iniciarAnimacaoTimSort(valores);
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



    private void iniciarAnimacaoTimSort(int[] arranjo) {
        Task<Void> tarefa = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int tamanho = arranjo.length;
                
                Platform.runLater(() -> {
                    Label labelFase1 = new Label("Fase 1: Dividindo em RUNs e ordenando com Insertion Sort");
                    labelFase1.setLayoutX(50);
                    labelFase1.setLayoutY(100);
                    labelFase1.setFont(new Font("Consolas", 16));
                    labelFase1.setStyle("-fx-text-fill: #2196F3; -fx-font-weight: bold;");
                    painel.getChildren().add(labelFase1);
                });
                Thread.sleep(2000);

                // Fase 1: Dividir em runs e ordenar cada um com insertion sort
                for (int i = 0; i < tamanho && !isCancelled(); i += TAMANHO_RUN) {
                    final int inicio = i;
                    final int fim = Math.min(i + TAMANHO_RUN - 1, tamanho - 1);
                    
                    Platform.runLater(() -> {
                        // Destacar o run atual no array principal
                        for (int j = inicio; j <= fim; j++) {
                            botoesArrayPrincipal[j].setStyle("-fx-background-color: #FFE082; -fx-border-color: #FF9800; -fx-border-width: 3px;");
                        }
                    });
                    Thread.sleep(1000);
                    
                    // Realizar insertion sort animado no run atual
                    insertionSortAnimado(arranjo, inicio, fim);
                    Thread.sleep(1500);
                    
                    Platform.runLater(() -> {
                        // Remover destaque após ordenação
                        for (int j = inicio; j <= fim; j++) {
                            botoesArrayPrincipal[j].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
                        }
                    });
                    Thread.sleep(1000);
                }

                Platform.runLater(() -> {
                    Label labelFase2 = new Label("Fase 2: Mesclando os RUNs ordenados");
                    labelFase2.setLayoutX(50);
                    labelFase2.setLayoutY(120);
                    labelFase2.setFont(new Font("Consolas", 16));
                    labelFase2.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
                    painel.getChildren().add(labelFase2);
                });
                Thread.sleep(2000);

                // Fase 2: Mesclar os runs ordenados
                for (int tamanhoBloco = TAMANHO_RUN; tamanhoBloco < tamanho && !isCancelled(); tamanhoBloco = 2 * tamanhoBloco) {
                    for (int esquerda = 0; esquerda < tamanho && !isCancelled(); esquerda += 2 * tamanhoBloco) {
                        int meio = esquerda + tamanhoBloco - 1;
                        int direita = Math.min((esquerda + 2 * tamanhoBloco - 1), (tamanho - 1));
                        if (meio < direita) {
                            mesclar(arranjo, esquerda, meio, direita);
                            Thread.sleep(2000);
                        }
                    }
                }

                Platform.runLater(() -> {
                    Label labelConcluido = new Label("TimSort Concluído! Array Ordenado");
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

    private void insertionSortAnimado(int[] arranjo, int esquerda, int direita) throws InterruptedException {
        Platform.runLater(() -> {
            // Remover apenas labels temporários de insertion sort (posição Y=280)
            painel.getChildren().removeIf(node -> 
                node instanceof Label && 
                node.getLayoutY() == 280.0);
            
            Label labelOrdenacao = new Label("Ordenando RUN de índice " + esquerda + " até " + direita + " com Insertion Sort");
            labelOrdenacao.setLayoutX(50);
            labelOrdenacao.setLayoutY(280);
            labelOrdenacao.setFont(new Font("Consolas", 14));
            labelOrdenacao.setStyle("-fx-text-fill: #FF9800;");
            painel.getChildren().add(labelOrdenacao);
        });
        
        for (int i = esquerda + 1; i <= direita; i++) {
            int chave = arranjo[i];
            int j = i - 1;
            
            // Destacar o elemento atual sendo inserido
            final int indiceAtual = i;
            Platform.runLater(() -> {
                botoesArrayPrincipal[indiceAtual].setStyle("-fx-background-color: #FFD700; -fx-border-color: #FF9800; -fx-border-width: 3px;");
            });
            Thread.sleep(800);
            
            // Encontrar a posição correta e mover elementos
            while (j >= esquerda && arranjo[j] > chave) {
                // Animar a troca dos elementos
                animarTroca(j, j + 1);
                
                arranjo[j + 1] = arranjo[j];
                j--;
            }
            
            // Inserir o elemento na posição correta
            arranjo[j + 1] = chave;
            final int posicaoFinal = j + 1;
            Platform.runLater(() -> {
                botoesArrayPrincipal[posicaoFinal].setText(String.valueOf(chave));
                botoesArrayPrincipal[posicaoFinal].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
            });
            Thread.sleep(800);
        }
        

    }

    private void animarTroca(int indice1, int indice2) throws InterruptedException {
        final Button botao1 = botoesArrayPrincipal[indice1];
        final Button botao2 = botoesArrayPrincipal[indice2];
        
        Platform.runLater(() -> {
            // Destacar botões sendo trocados
            botao1.setStyle("-fx-background-color: #FFD700; -fx-border-color: #FF9800; -fx-border-width: 3px;");
            botao2.setStyle("-fx-background-color: #FFD700; -fx-border-color: #FF9800; -fx-border-width: 3px;");
            
            // Criar animações de movimento
            TranslateTransition moverBotao1 = new TranslateTransition(Duration.millis(800), botao1);
            TranslateTransition moverBotao2 = new TranslateTransition(Duration.millis(800), botao2);
            
            // Movimento vertical para cima/baixo
            moverBotao1.setByY(-30);
            moverBotao2.setByY(30);
            
            moverBotao1.setOnFinished(e1 -> {
                // Movimento horizontal
                TranslateTransition horizontalBotao1 = new TranslateTransition(Duration.millis(600), botao1);
                TranslateTransition horizontalBotao2 = new TranslateTransition(Duration.millis(600), botao2);
                
                double distancia = (indice2 - indice1) * 70; // 70 é o espaçamento entre botões
                horizontalBotao1.setByX(distancia);
                horizontalBotao2.setByX(-distancia);
                
                horizontalBotao1.setOnFinished(e2 -> {
                    // Movimento vertical de volta
                    TranslateTransition voltarBotao1 = new TranslateTransition(Duration.millis(600), botao1);
                    TranslateTransition voltarBotao2 = new TranslateTransition(Duration.millis(600), botao2);
                    
                    voltarBotao1.setByY(30); // Voltar para posição original
                    voltarBotao2.setByY(-30);
                    
                    voltarBotao1.setOnFinished(e3 -> {
                        // Trocar textos e resetar posições
                        String temp = botao1.getText();
                        botao1.setText(botao2.getText());
                        botao2.setText(temp);
                        
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
        
        Thread.sleep(2500); // Aguardar animação completar
    }

    private void mesclar(int[] arranjo, int esquerda, int meio, int direita) throws InterruptedException {
        Platform.runLater(() -> {
            // Remover apenas labels temporários de merge (posição Y=305)
            painel.getChildren().removeIf(node -> 
                node instanceof Label && 
                node.getLayoutY() == 305.0);
            
            Label labelMesclar = new Label("Mesclando RUNs: [" + esquerda + ".." + meio + "] com [" + (meio+1) + ".." + direita + "]");
            labelMesclar.setLayoutX(50);
            labelMesclar.setLayoutY(305);
            labelMesclar.setFont(new Font("Consolas", 14));
            labelMesclar.setStyle("-fx-text-fill: #4CAF50;");
            painel.getChildren().add(labelMesclar);
        });
        
        int[] arrayEsquerdo = new int[meio - esquerda + 1];
        int[] arrayDireito = new int[direita - meio];
        
        System.arraycopy(arranjo, esquerda, arrayEsquerdo, 0, arrayEsquerdo.length);
        System.arraycopy(arranjo, meio + 1, arrayDireito, 0, arrayDireito.length);
        
        int i = 0, j = 0, k = esquerda;
        
        // Destacar as duas partes que serão mescladas
        Platform.runLater(() -> {
            // Parte esquerda em azul
            for (int idx = esquerda; idx <= meio; idx++) {
                botoesArrayPrincipal[idx].setStyle("-fx-background-color: #BBDEFB; -fx-border-color: #2196F3; -fx-border-width: 3px;");
            }
            // Parte direita em laranja
            for (int idx = meio + 1; idx <= direita; idx++) {
                botoesArrayPrincipal[idx].setStyle("-fx-background-color: #FFE0B2; -fx-border-color: #FF9800; -fx-border-width: 3px;");
            }
        });
        Thread.sleep(1500);
        
        while (i < arrayEsquerdo.length && j < arrayDireito.length) {
            if (arrayEsquerdo[i] <= arrayDireito[j]) {
                arranjo[k] = arrayEsquerdo[i];
                final int indice = k;
                final int valor = arrayEsquerdo[i];
                Platform.runLater(() -> {
                    botoesArrayPrincipal[indice].setText(String.valueOf(valor));
                    botoesArrayPrincipal[indice].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
                });
                Thread.sleep(800);
                i++;
            } else {
                arranjo[k] = arrayDireito[j];
                final int indice = k;
                final int valor = arrayDireito[j];
                Platform.runLater(() -> {
                    botoesArrayPrincipal[indice].setText(String.valueOf(valor));
                    botoesArrayPrincipal[indice].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
                });
                Thread.sleep(800);
                j++;
            }
            k++;
        }
        
        // Copiar elementos restantes da parte esquerda
        while (i < arrayEsquerdo.length) {
            arranjo[k] = arrayEsquerdo[i];
            final int indice = k;
            final int valor = arrayEsquerdo[i];
            Platform.runLater(() -> {
                botoesArrayPrincipal[indice].setText(String.valueOf(valor));
                botoesArrayPrincipal[indice].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
            });
            Thread.sleep(600);
            i++;
            k++;
        }
        
        // Copiar elementos restantes da parte direita
        while (j < arrayDireito.length) {
            arranjo[k] = arrayDireito[j];
            final int indice = k;
            final int valor = arrayDireito[j];
            Platform.runLater(() -> {
                botoesArrayPrincipal[indice].setText(String.valueOf(valor));
                botoesArrayPrincipal[indice].setStyle("-fx-background-color: #C8E6C9; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
            });
            Thread.sleep(600);
            j++;
            k++;
        }
        
        // Finalizar merge - destacar área mesclada
        Platform.runLater(() -> {
            for (int idx = esquerda; idx <= direita; idx++) {
                botoesArrayPrincipal[idx].setStyle("-fx-background-color: #E8F5E8; -fx-border-color: #4CAF50; -fx-border-width: 2px;");
            }
            
        });
        Thread.sleep(1000);
    }


}