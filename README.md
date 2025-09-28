# Projeto: Animação de Ordenações em JavaFX

Este projeto demonstra animações detalhadas dos algoritmos de ordenação HeapSort e TimSort utilizando JavaFX. Cada passo das ordenações é visualizado com botões que representam os valores do vetor, mostrando as trocas, comparações e transformações com animações suaves e código de cores específico para cada operação.

## Algoritmos Implementados

### 1. HeapSort (Ordenação por Heap)

O HeapSort é um algoritmo de ordenação que utiliza a estrutura de dados heap (monte). O processo ocorre em duas fases principais:

#### Fase 1: Construção do Max-Heap
- **Objetivo**: Transformar o array em um heap máximo, onde cada nó pai é maior que seus filhos
- **Processo**: Aplica a operação `heapificar` de baixo para cima, começando do último nó não-folha
- **Complexidade**: O(n log n)

#### Fase 2: Extração e Ordenação
- **Objetivo**: Extrair o maior elemento (raiz) repetidamente e reorganizar o heap
- **Processo**: 
  1. Troca a raiz (maior elemento) com o último elemento
  2. Reduz o tamanho do heap
  3. Reaplica `heapificar` na nova raiz
  4. Repete até o heap estar vazio

#### Código de Cores - HeapSort:
- **🟡 Dourado (`#FFD700`)**: Nó raiz sendo analisado no heapify
- **🔵 Azul Claro (`#BBDEFB`)**: Filho esquerdo do nó atual
- **🔴 Rosa (`#FFCDD2`)**: Filho direito do nó atual  
- **🟢 Verde Claro (`#C8E6C9`)**: Maior elemento encontrado entre pai e filhos
- **🟢 Verde Escuro (`#4CAF50`)**: Elementos já ordenados (posição final)
- **🟣 Rosa Escuro (`#E91E63`)**: Texto das operações de troca

### 2. TimSort (Ordenação Híbrida)

O TimSort é um algoritmo híbrido estável que combina Merge Sort e Insertion Sort, otimizado para dados do mundo real. Foi desenvolvido para Python e é usado como algoritmo padrão de ordenação.

#### Fase 1: Divisão em RUNs e Insertion Sort
- **Objetivo**: Dividir o array em pequenos blocos (RUNs) e ordená-los individualmente
- **Tamanho do RUN**: 4 elementos (configurável via `TAMANHO_RUN`)
- **Algoritmo interno**: Insertion Sort para cada RUN
- **Vantagem**: Insertion Sort é muito eficiente para arrays pequenos

#### Fase 2: Merge dos RUNs
- **Objetivo**: Mesclar os RUNs ordenados em blocos cada vez maiores
- **Processo**: 
  1. Combina RUNs adjacentes de tamanho 4
  2. Depois combina blocos de tamanho 8
  3. Continua dobrando o tamanho até o array estar completamente ordenado
- **Algoritmo**: Merge Sort tradicional para a mesclagem

#### Código de Cores - TimSort:

**Durante Insertion Sort:**
- **🟡 Dourado (`#FFD700`)**: Elemento atual sendo inserido na posição correta
- **🟠 Laranja (`#FFE082`)**: RUN atual sendo ordenado (destaque do bloco)
- **🟢 Verde Claro (`#C8E6C9`)**: Elementos já posicionados corretamente no RUN

**Durante Merge:**
- **🔵 Azul Claro (`#BBDEFB`)**: RUN da esquerda sendo mesclado
- **🟠 Laranja Claro (`#FFE0B2`)**: RUN da direita sendo mesclado
- **🟢 Verde Claro (`#C8E6C9`)**: Elementos já mesclados na posição final
- **🟢 Verde Muito Claro (`#E8F5E8`)**: Área completamente mesclada

**Finalizações:**
- **🟢 Verde Escuro (`#4CAF50`)**: Array completamente ordenado

## Estrutura do Projeto

### Classes Principais:

#### 1. `Principal.java`
- Interface principal da aplicação
- Controla a janela principal e os botões de seleção
- Inicializa os visualizadores específicos para cada algoritmo

#### 2. `HeapSortVisualizerController.java`
- Implementa a visualização completa do HeapSort
- **Métodos principais**:
  - `mostrarVisualizacaoHeapSort()`: Inicializa a interface de visualização
  - `iniciarAnimacaoHeapSort()`: Controla o fluxo das duas fases
  - `heapificarAnimado()`: Implementa o algoritmo heapify com animação
  - `trocarAnimado()`: Anima as trocas de elementos com movimento suave

#### 3. `TimSortVisualizerController.java`
- Implementa a visualização completa do TimSort
- **Métodos principais**:
  - `mostrarVisualizacaoTimSort()`: Inicializa a interface de visualização
  - `iniciarAnimacaoTimSort()`: Controla o fluxo das duas fases
  - `insertionSortAnimado()`: Insertion Sort animado para cada RUN
  - `animarTroca()`: Animação das trocas durante insertion sort
  - `mesclar()`: Merge animado dos RUNs ordenados

## Detalhes das Animações

### Animação de Troca (Ambos os Algoritmos):
1. **Destaque**: Elementos a serem trocados ficam dourados
2. **Movimento Vertical**: Elementos sobem/descem para criar separação visual
3. **Movimento Horizontal**: Elementos se movem lateralmente para trocar posições
4. **Retorno Vertical**: Elementos voltam à linha original
5. **Finalização**: Textos são trocados e estilos restaurados

### Sincronização:
- Todas as animações são executadas em threads separadas para não bloquear a interface
- `Platform.runLater()` é usado para atualizar a interface a partir de threads de background
- Tempos de sleep calibrados para permitir visualização clara de cada passo

## Características Educacionais

### Labels Informativos:
- **Fase atual**: Indica qual fase do algoritmo está executando
- **Operação específica**: Descreve a operação atual (ex: "Heapificando subárvore em 2")
- **Progresso**: Mostra qual RUN ou qual troca está sendo processada

### Código de Cores Pedagógico:
Cada cor tem um significado específico para ajudar na compreensão:
- **Elementos em análise**: Sempre em tons de dourado/amarelo
- **Elementos sendo comparados**: Azul e rosa para distinção
- **Elementos corretos**: Verde em diferentes intensidades
- **Áreas de trabalho**: Laranja para delimitar seções

## Como Executar

### Pré-requisitos:
- Java 11 ou superior
- JavaFX SDK configurado
- IDE com suporte a JavaFX (recomendado: IntelliJ IDEA ou Eclipse)

### Passos:
1. Clone o repositório
2. Configure o JavaFX no seu ambiente de desenvolvimento
3. Compile o projeto
4. Execute a classe `Principal.java`
5. Clique em "HeapSort" ou "TimSort" para iniciar a visualização

### Configurações Personalizáveis:
- **Velocidade da animação**: Modifique os valores `Thread.sleep()` nos métodos
- **Tamanho do RUN (TimSort)**: Altere a constante `TAMANHO_RUN`
- **Array inicial**: Modifique o array `valores` na classe `Principal`

## Valor Educacional

Este projeto foi desenvolvido com foco educacional para:

### Para Estudantes:
- **Visualização clara** dos passos de cada algoritmo
- **Compreensão das diferenças** entre algoritmos de ordenação
- **Observação da complexidade** na prática
- **Identificação de padrões** de movimento dos dados

### Para Educadores:
- **Ferramenta interativa** para demonstrações em sala
- **Código bem documentado** para explicar implementações
- **Possibilidade de modificação** para outros algoritmos
- **Base para projetos** de algoritmos e estruturas de dados

## Extensibilidade

O projeto está estruturado para fácil extensão:
- Adicione novos algoritmos criando novos `VisualizerController`
- Modifique cores e estilos nos métodos de estilização
- Implemente novos tipos de animação nos métodos de movimento
- Adicione controles de velocidade e pausa

## Tecnologias Utilizadas

- **JavaFX**: Interface gráfica e animações
- **Java Threads**: Execução assíncrona dos algoritmos
- **Platform.runLater()**: Sincronização com a thread da interface
- **TranslateTransition**: Animações suaves de movimento
- **ParallelTransition**: Execução simultânea de múltiplas animações

---

**Autor:** Leonardo Wilker  
**Objetivo:** Material educacional para ensino de algoritmos de ordenação  
**Licença:** Livre para uso educacional
