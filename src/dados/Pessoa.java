package dados;

import java.util.Objects;

public class Pessoa {
    private String id;  // regra de formação: "[A-Z]{3}[0-9]{3}[A-Z]{1}[0-9]{1}"
    private String nome;

    public Pessoa(String id, String nome){
        //assumiremos por enquanto que todos os ids serão válidos
        this.id = id;
        this.nome = nome;
    }
    public Pessoa(){
        this.nome = null;
        this.id = null;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        long somaHash = 0;
        for (int i = 0; i < this.id.length(); i++) {
            char caractere = this.id.charAt(i);
            int valorAscii = (int) caractere;
            somaHash += valorAscii*Math.pow(3, this.id.length()-i);
        }
        return (int) somaHash;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
