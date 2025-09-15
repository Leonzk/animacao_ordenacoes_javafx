# Projeto: Animação de Ordenações em JavaFX

Este projeto demonstra animações dos algoritmos de ordenação HeapSort e TimSort utilizando JavaFX. Cada passo das ordenações é visualizado com botões que representam os valores do vetor, mostrando as trocas e atualizando o vetor em tempo real.

## Passo a Passo de Implementação

### 1. Estrutura do Projeto
- O projeto utiliza JavaFX para interface gráfica.
- As principais classes são:
  - `Principal.java`: Interface gráfica e animação.
  - `OrdenacaoController.java`: Algoritmos de ordenação e registro das trocas.

### 2. Criação das Classes

#### a) Classe Principal
- Responsável por criar a janela, botões de ordenação, botões para cada valor do vetor e um label para mostrar o vetor atual.
- Exemplo de inicialização dos botões:
  ```java
  int[] valores = {45, 23, 11, 89, 77, 98, 4, 28, 65, 43};
  vet = new Button[valores.length];
  for (int i = 0; i < valores.length; i++) {
      vet[i] = new Button(String.valueOf(valores[i]));
      // ...estilização e posicionamento...
  }
  ```

#### b) Classe OrdenacaoController
- Implementa HeapSort e TimSort.
- Cada método retorna uma lista de trocas (`List<Troca>`) que indica quais índices foram trocados em cada passo.
- Exemplo da classe Troca:
  ```java
  public static class Troca {
      public int i, j;
      public Troca(int i, int j) { this.i = i; this.j = j; }
  }
  ```

### 3. Registro das Trocas
- Durante a execução dos algoritmos, cada troca de elementos é registrada na lista de trocas.
- No HeapSort, as trocas são registradas no método `heapify` e na extração do maior elemento.
- No TimSort, as trocas são registradas tanto no `insertionSort` quanto no `merge`.

### 4. Animação das Trocas
- Para cada troca registrada, os botões correspondentes são animados:
  - Movem para cima/baixo.
  - Movem lateralmente para trocar de posição.
  - Voltam à posição original.
  - Trocam os valores exibidos.
  - São destacados com cor e borda durante a animação.
- O vetor é atualizado em um label a cada troca.
- Exemplo de animação:
  ```java
  private void animarTroca(Button btn1, Button btn2, Runnable onComplete) {
      // ...movimentação e destaque dos botões...
      // Troca os textos e atualiza o label do vetor
  }
  ```

### 5. Execução dos Algoritmos
- O usuário pode clicar nos botões "HeapSort" ou "TimSort" para iniciar a animação.
- Enquanto uma animação está em andamento, não é possível iniciar outra.

### 6. Estilização
- Botões de ordenação possuem cores distintas.
- Botões do vetor são arredondados, com borda e fonte monoespaçada.
- Botões em troca ficam destacados.
- O label do vetor mostra o estado atual do vetor.

## Como Executar
1. Compile e execute a classe `Principal.java`.
2. A janela mostrará os botões do vetor e os botões de ordenação.
3. Clique em "HeapSort" ou "TimSort" para ver a animação do algoritmo escolhido.
4. Observe as trocas e o vetor sendo atualizado passo a passo.

## Observações
- O projeto pode ser expandido para outros algoritmos de ordenação.
- A animação pode ser ajustada alterando os valores de `Thread.sleep()` para modificar a velocidade.
- O código está preparado para ser facilmente adaptado para outros tipos de visualização.

---

**Autor:** Leonardo Wilker
