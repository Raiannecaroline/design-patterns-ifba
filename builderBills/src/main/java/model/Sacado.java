package model;

public class Sacado {

    private String nome;
    private String documento;
    private String endereco;

    public Sacado(String nome, String documento, String endereco) {
        this.nome = nome;
        this.documento = documento;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }
}
