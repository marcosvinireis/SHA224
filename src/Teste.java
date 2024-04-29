import TabelaHash.TabelaHash;
import dados.Pessoa;
import listaEncadeada.ListaEncadeada;

import java.io.File;

public class Teste{

    public static void main(String[] args) {
        apagarArquivo("src/dados/saidaDados"); //apagar o arquivo de saida toda vez que rodar o teste

        System.out.println("Iniciando o programa...");

        TabelaHash tabela = new TabelaHash(100);
        String caminho = "src/dados/entradaDados.txt";
        tabela.inserirDeArquivo(caminho);


        Pessoa p = new Pessoa("AHG266D0", "Leonardo");
        tabela.inserir("AHG266D0", p);

        tabela.buscar("AHG266D0");



        tabela.imprimir();


    }

    public static void apagarArquivo(String caminho) {
        try {
            File arquivo = new File(caminho);

            if (arquivo.exists()) {
                if (!arquivo.delete()) {
                    System.out.println("Não foi possível excluir o arquivo: " + caminho);
                }
            } else {
                System.out.println("O arquivo não existe: " + caminho);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
