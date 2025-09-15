package leonardo_wilker.po.trab_animacao;

import java.util.ArrayList;
import java.util.List;

public class OrdenacaoController {
    public static class Troca {
        public int i, j;
        public Troca(int i, int j) { this.i = i; this.j = j; }
        @Override
        public String toString() {
            return "Troca{de=" + i + ", para=" + j + "}\n";
        }
    }

    public List<Troca> heapSortAnimado(int[] arr) {
        List<Troca> trocas = new ArrayList<>();
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i, trocas);
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            trocas.add(new Troca(0, i));
            heapify(arr, i, 0, trocas);
        }
        System.out.println(trocas);
        return trocas;
    }

    private void heapify(int[] arr, int n, int i, List<Troca> trocas) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && arr[l] > arr[largest])
            largest = l;
        if (r < n && arr[r] > arr[largest])
            largest = r;
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            trocas.add(new Troca(i, largest));
            heapify(arr, n, largest, trocas);
        }
    }

    // Tim Sort (simplified version)
    private static final int RUN = 4; // Reduzido para melhor visualização

    public List<Troca> timSortAnimado(int[] arr) {
        List<Troca> trocas = new ArrayList<>();
        int n = arr.length;
        // Sort individual subarrays of size RUN
        for (int i = 0; i < n; i += RUN)
            insertionSort(arr, i, Math.min((i + RUN - 1), (n - 1)), trocas);

        // Start merging from size RUN
        for (int size = RUN; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1));
                if (mid < right)
                    merge(arr, left, mid, right, trocas);
            }
        }
        return trocas;
    }

    private void insertionSort(int[] arr, int left, int right, List<Troca> trocas) {
        for (int i = left + 1; i <= right; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > temp) {
                arr[j + 1] = arr[j];
                trocas.add(new Troca(j + 1, j));
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    private void merge(int[] arr, int l, int m, int r, List<Troca> trocas) {
        int len1 = m - l + 1, len2 = r - m;
        int[] left = new int[len1];
        int[] right = new int[len2];
        int[] posicoes = new int[len1 + len2]; // Guarda as posições originais
        
        // Copia os elementos e suas posições originais
        for (int x = 0; x < len1; x++) {
            left[x] = arr[l + x];
            posicoes[x] = l + x;
        }
        for (int x = 0; x < len2; x++) {
            right[x] = arr[m + 1 + x];
            posicoes[len1 + x] = m + 1 + x;
        }

        int i = 0, j = 0, k = l;
        while (i < len1 && j < len2) {
            if (left[i] <= right[j]) {
                if (k != posicoes[i]) {
                    trocas.add(new Troca(posicoes[i], k));
                }
                arr[k++] = left[i++];
            } else {
                if (k != posicoes[len1 + j]) {
                    trocas.add(new Troca(posicoes[len1 + j], k));
                }
                arr[k++] = right[j++];
            }
        }
        
        while (i < len1) {
            if (k != posicoes[i]) {
                trocas.add(new Troca(posicoes[i], k));
            }
            arr[k++] = left[i++];
        }
        
        while (j < len2) {
            if (k != posicoes[len1 + j]) {
                trocas.add(new Troca(posicoes[len1 + j], k));
            }
            arr[k++] = right[j++];
        }
    }
}