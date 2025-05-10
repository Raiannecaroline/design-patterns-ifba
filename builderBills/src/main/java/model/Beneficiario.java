package model;

public class Beneficiario {

    private String nome;
    private String documento;
    private String endereco;

    public Beneficiario(String nome, String documento, String endereco) {
        this.nome = nome;
        this.documento = documento;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getDocumento() {
        return documento;
    }
}
