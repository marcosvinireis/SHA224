package TabelaHash;

import dados.Pessoa;
import listaEncadeada.ListaEncadeada;
import sha224.Sha224;

import java.io.*;
import java.util.Objects;

public class TabelaHash {

    private ListaEncadeada<Nodo> [] tabela;
    private int tamanho;


    private final String ELEMENTO_NAO_EXISTE = "Esse elemento não existe";
    private final String ELEMENTO_CADASTRADO = "Esse elemento já foi cadastrado";
    private boolean posicaoVazia(int pos){return this.tabela[pos] == null;}


    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new ListaEncadeada[tamanho];
        for (int i = 0; i < tamanho; i++) this.tabela[i] = null;
    }

    protected int funcaoHash(String chave) {
        int hash = 0;
        int multiplicador = 31;
        for (int i = 0; i < chave.length(); i++) {
            hash = multiplicador * hash + chave.charAt(i);
        }
        hash = Math.abs(hash);
        return hash % this.tamanho;
    }

    private ListaEncadeada<Pessoa> lerDeArquivo(String caminho) {
        ListaEncadeada<Pessoa> dados = new ListaEncadeada<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(caminho));
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length == 2) {
                    Pessoa pessoa = new Pessoa(partes[0], partes[1]);
                    dados.adicionar(pessoa);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dados;
    }

    private void gravarEmArquivo(String caminho, String hash, String nome) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(caminho, true));

            bw.write(nome + " -> " + hash);
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inserirDeArquivo(String path){
        ListaEncadeada<Pessoa> dados = this.lerDeArquivo(path);
        for (int i = 0; i < dados.getTamanho(); i++) {
            Pessoa novoDado = dados.busca(i);
            this.inserir(novoDado.getId(), novoDado);
        }
    }

    public void inserir(String chave, Pessoa valor){
        int pos = funcaoHash(chave);
        String hash = Sha224.calcularSHA224(valor.getNome());
        gravarEmArquivo("src/dados/saidaDados", hash, valor.getNome());
        Nodo dado = new Nodo(valor.getId(), hash);
        if (posicaoVazia(pos)){
            ListaEncadeada<Nodo> bucket = new ListaEncadeada<>();
            bucket.adicionar(dado);
            this.tabela[pos] = bucket;
        } else {
            if (this.tabela[pos].contains(dado)){
                System.out.println(ELEMENTO_CADASTRADO);
                return;
            }
            this.tabela[pos].adicionar(dado);
        }
    }

    public void remover(String chave){
        int pos = funcaoHash(chave);

        if (posicaoVazia(pos)) {
            System.out.println(ELEMENTO_NAO_EXISTE);
            return;
        }else {
            Nodo elemento = new Nodo(null, null);
            for (int i = 0; i < this.tabela[pos].getTamanho(); i++) {
                if (Objects.equals(this.tabela[pos].busca(i).getId(), chave))
                     elemento = this.tabela[pos].busca(i);
            }

            if (this.tabela[pos].contains(elemento)){
                int posLista = this.tabela[pos].buscaPorElemento(elemento);
                this.tabela[pos].remover(posLista);
            }else System.out.println(ELEMENTO_NAO_EXISTE);
        }
    }

    public void buscar(String chave){
        int pos = funcaoHash(chave);
        if(posicaoVazia(pos)){
            System.out.println(ELEMENTO_NAO_EXISTE);
        }else{
            ListaEncadeada<Nodo> bucket = this.tabela[pos];
            boolean busca =false;
            for(int i=0;i< bucket.getTamanho();i++){
                Nodo valor = bucket.busca(i);
                if(valor !=null && valor.getId() == chave){
                    System.out.println("Elemento encontrado :"+ valor.getHash());
                    busca=true;
                    break;
                }
            }
            if(!busca){
                System.out.println(ELEMENTO_NAO_EXISTE);
            }
        }
    }

    public void imprimir(){
        System.out.println("Tabela Hash: ");
        for (int i=0;i<this.tamanho;i++){
            if(posicaoVazia(i)){
                System.out.println("Posição " + i + ": NULL");
            }else{
                System.out.print("Posicao " + i + ":\n");
                ListaEncadeada<Nodo> bucket = this.tabela[i];
                for (int j = 0; j < bucket.getTamanho(); j++) {
                    Nodo valor = bucket.busca(j);
                    System.out.print("    "+valor);
                    if (j < bucket.getTamanho() - 1) System.out.println();
                }
                System.out.println();

            }
        }
    }

}
