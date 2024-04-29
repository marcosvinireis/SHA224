package TabelaHash;

public class Nodo {
    private String id;
    private String hash;

    public Nodo(String id, String hash) {
        this.id = id;
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Elemento: " +
                "id='" + id + '\'' +
                ", hash='" + hash + '\'';
    }
}
