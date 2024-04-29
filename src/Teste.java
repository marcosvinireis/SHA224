import TabelaHash.TabelaHash;
import dados.Pessoa;
import listaEncadeada.ListaEncadeada;

public class Teste{

    public static void main(String[] args) {
        System.out.println("Iniciando o programa...");
        TabelaHash tabela = new TabelaHash(200);
        String caminho = "src/dados/entradaDados.txt";

        tabela.inserirDeArquivo(caminho);

        tabela.buscar("marcos");
        tabela.buscar("DEF456Y7");

        Pessoa p = new Pessoa("XYZ123L9", "Carlos");
        tabela.inserir("XYZ123L9", p);

        tabela.buscar("AAA999Z1");

        tabela.imprimir();
    }
//coisa

}
