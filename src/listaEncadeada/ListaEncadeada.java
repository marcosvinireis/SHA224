package listaEncadeada;

public class ListaEncadeada<T> {
    private No<T> inicio;
    private No<T> ultimo;
    private int tamanho = 0;
    private final int NAO_ENCONTRADO = -1;

    public void adicionar(T elemento){
        No<T> celula = new No<T>(elemento);
        if (this.tamanho == 0){
            this.inicio = celula;
        } else {
            this.ultimo.setProximo(celula);
        }
        this.ultimo = celula;
        this.tamanho++;

    }

    public void adicionaNoInicio(T elemento) {
        if (this.tamanho == 0)
            this.adicionar(elemento);
        else {
            No<T> novoNo = new No<>(elemento, this.inicio);
            this.inicio = novoNo;
        }
        this.tamanho++;
    }
    public void adicionar(int posicao, T elemento){
        if (posicao < 0 || posicao > this.tamanho)
            throw new IllegalArgumentException("Posição inválida");

        if (posicao == 0){
            this.adicionaNoInicio(elemento);
        } else if (posicao == this.tamanho){
            this.adicionar(elemento);
        } else {
            No<T> noAnterior = this.buscaPorPosicao(posicao);
            No<T> noProximo = noAnterior.getProximo();
            No<T> novoNo = new No<>(elemento, noProximo);
            noAnterior.setProximo(novoNo);
            this.tamanho++;
        }
    }

    public T removerNoInicio(){
        if (this.tamanho == 0){
            throw new RuntimeException("A lista está vazia");
        }

        T removido = this.inicio.getElemento();
        this.inicio = this.inicio.getProximo();
        tamanho--;
        if (this.tamanho == 0){
            this.ultimo = null;
        }

        return removido;
    }

    public T removerNoFinal(){
        if (this.tamanho == 0)
            throw new RuntimeException("A lista está vazia");

        if (this.tamanho == 1)
            return this.removerNoInicio();


        T removido = this.ultimo.getElemento();
        this.ultimo = this.buscaPorPosicao(this.tamanho - 2);
        this.ultimo.setProximo(null);
        this.tamanho--;


        return removido;
    }

    public T remover(int posicao){
        if (this.tamanho == 0)
            throw new RuntimeException("A lista está vazia");
        if (posicao < 0 || posicao >= this.tamanho)
            throw new IllegalArgumentException("Posição inválida");

        if (this.tamanho == 1)
            return this.removerNoInicio();
        if (posicao == tamanho - 1)
            return this.removerNoFinal();

        No<T> anterior = this.buscaPorPosicao(posicao - 1);
        No<T> seguinte = this.buscaPorPosicao(posicao + 1);
        T removido = this.busca(posicao);
        anterior.getProximo().setProximo(null); //no removido aponta para null
        anterior.setProximo(seguinte);

        this.tamanho--;

        return removido;
    }

    public int getTamanho(){
        return this.tamanho;
    }

    public  void limpaLista(){
        for (No<T>atual = this.inicio; atual != null;){
            No<T> proximo = atual.getProximo();
            atual.setElemento(null);
            atual.setProximo(null);
            atual = proximo;
        }
        this.inicio = null;
        this.ultimo = null;
        tamanho = 0;
    }
    private No<T> buscaPorPosicao(int posicao){
        if (!(posicao>=0 && posicao < this.tamanho)){
            throw new IllegalArgumentException("A posição não existe");
        }
        No<T> atual = this.inicio;
        for (int i = 0; i < posicao; i++) {
            atual = atual.getProximo();
        }
        return atual;
    }
    public T busca(int posicao){
        return this.buscaPorPosicao(posicao).getElemento();
    }
    public int buscaPorElemento(T elemento){
        if (this.tamanho == 0)
            throw new RuntimeException("Lista vazia");

        No<T> atual = this.inicio;
        int pos = 0;
        while (atual != null){
            if (atual.getElemento().equals(elemento)) return pos;
            pos++;
            atual = atual.getProximo();
        }
        return NAO_ENCONTRADO;
    }

    public boolean contains(T elemento){
        if (this.tamanho == 0)
            throw new RuntimeException("Lista vazia");

        No<T> atual = this.inicio;
        int pos = 0;
        while (atual != null){
            if (atual.getElemento().equals(elemento)) return true;
            pos++;
            atual = atual.getProximo();
        }
        return false;
    }

    @Override
    public String toString() {
        if (this.tamanho == 0) return "{}";
        if (this.tamanho == 1) return "{" + this.inicio.getElemento() + "}";

        StringBuilder sb = new StringBuilder();
        No<T> atual = this.inicio;
        sb.append("{").append(atual.getElemento()).append(", ");

        while (atual.getProximo() != null){
            atual = atual.getProximo();
            if (atual.getProximo() != null)
                sb.append(atual.getElemento()).append(", ");
            else
                sb.append(atual.getElemento()).append("}");
        }


        return sb.toString();
    }
}
