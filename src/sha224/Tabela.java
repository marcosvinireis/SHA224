package sha224;

import TabelaHash.TabelaHash;
import dados.Pessoa;

public class Tabela extends TabelaHash {
    public Tabela(int tamanho) {
        super(tamanho);
    }

    @Override
    public void inserirDeArquivo(String path) {
        super.inserirDeArquivo(path);
    }

    @Override
    public void inserir(String chave, Pessoa valor) {
        super.inserir(chave, valor);
    }
}
