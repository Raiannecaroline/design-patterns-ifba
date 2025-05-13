# 🏗️ **Projeto de Gerador de Boletos com Padrão Builder**

## 📑 **Sumário**
1. [Introdução](#-1-introdução)
2. [Padrão Builder](#-2-padrão-builder)
3. [Implementação](#-3-implementação)
4. [Dependências](#-4-dependências)
5. [Como Executar](#-5-como-executar)
6. [Contribuição](#-6-contribuição)
7. [Licença](#-7-licença)

---

## 🎯 **1. Introdução**
Projeto Java que implementa o **padrão Builder** para gerar boletos bancários de forma flexível e validada, com suporte para:  
✅ Banco do Brasil  
✅ Itaú  
✅ Bradesco

**Objetivo**:
- Criar boletos com **código de barras** e **linha digitável** válidos seguindo padrões FEBRABAN.

---

## 🏗️ **2. Padrão Builder**

### 📖 **2.1 Definição**
Padrão de projeto **criacional** que permite construir objetos complexos **passo a passo**.

**Exemplo Conceitual**:
```java
public interface BoletoBuilder {
    BoletoBuilder comBeneficiario(String nome, String documento);
    BoletoBuilder comValor(BigDecimal valor);
    Boleto construir();
}
````

**Classe Abstrata:**
        
```java
public abstract class BaseBoletoBuilder implements BoletoBuilder {
    protected final Boleto boleto = new Boleto();

    protected abstract String gerarCampoLivre();

    protected void validarDados() {
        // Validações comuns
    }
}
```

## 🏆 **2.2 Vantagens**

✔ **Flexibilidade**: Permite construir objetos com diferentes configurações sem poluir o construtor.  
✔ **Clareza**: Métodos nomeados (ex: `.comValor()`) melhoram a legibilidade do código.  
✔ **Validação Gradual**: Verifica dados a cada etapa da construção.  
✔ **Imutabilidade**: O objeto final (`Boleto`) é thread-safe.  
✔ **Reusabilidade**: O mesmo processo de construção pode criar diferentes representações.

## 💻 **3. Implementação**

### 📂 **3.1 Estrutura do Projeto**

### **Pacote builder/**
- `BoletoBuilder.java`: Interface que define o contrato para construção de boletos
- `BaseBoletoBuilder.java`: Implementação parcial com lógica comum a todos os builders
- Builders concretos por banco (BB, Itaú, Bradesco) com regras específicas

### **Pacote model/**
- `Boleto.java`: Classe que representa o boleto bancário final
- `Beneficiario.java`: Contém informações do recebedor
- `Sacado.java`: Contém informações do pagador

### **Pacote service/**
- Serviços especializados em cálculos e geração de:
    - Códigos de barras (44 posições)
    - Linhas digitáveis (47 posições)

### **Pacote util/**
- Implementações dos algoritmos:
    - Módulo 10 (para dígitos verificadores)
    - Módulo 11 (para código de barras)

### 🛠️ **3.2 Código do Builder**

**Interface Principal**:
```java
public interface BoletoBuilder {
    BoletoBuilder comBeneficiario(String nome, String doc, String endereco);
    Boleto construirBoleto();
}
```

**Builder Concreto (Bradesco):**
```java
public class BradescoBuilder extends BaseBoletoBuilder {
    @Override
    protected String gerarCampoLivre() {
        return String.format("%04d%02d%011d%07d%d",
                agencia, carteira, nossoNumero, conta, digitoVerificador);
    }
}
```

### 🎯 **3.3 Exemplo de Uso**

```java
Boleto boleto = new BradescoBuilder()
        .comBeneficiario("Empresa", "00.000.000/0001-00", "Rua 1")
        .comValor(new BigDecimal("1500.00"))
        .construirBoleto();
```

### 🔄 **Dados para inserir (Via terminal - Após Escolher o Menu)**

**Dados Comuns de serem Inseridos:**
```shell
Beneficiário (Nome): Loja do Zé Ltda
Beneficiário (CNPJ/CPF): 12.345.678/0001-99
Beneficiário (Endereço): Rua das Flores, 123 - Centro - São Paulo/SP

Sacado (Nome): João da Silva
Sacado (CNPJ/CPF): 123.456.789-09
Sacado (Endereço): Av. Paulista, 1000 - Bela Vista - São Paulo/SP

Número do Documento: 20230001
Data Vencimento (dd/MM/yyyy): 30/12/2023
Valor: 350.50
```
**Dados Específicos de cada Banco:**

1. Banco do Brasil (001):
```shell
Agência: 1234
Conta Corrente: 123456-7
Carteira: 17
Nosso Número: 87654321
```

2. Itaú (341):
```shell
Agência: 1234
Conta Corrente: 12345
Carteira: 109
Nosso Número: 12345678
```

*Algumas regras do Layout do Itaú:*

- A conta corrente deve ter 5 dígitos
- A carteira normalmente é 109, 174 ou 175
- Nosso número deve ter 8 dígitos


3. Bradesco (237):
```shell
Agência: 1234
Conta Corrente: 1234567
Carteira: 09
Nosso Número: 12345678901
```

*Algumas regras do Layout do Bradesco:*

- A conta corrente deve ter 7 dígitos
- Carteira normalmente é 09, 19 ou 27
- Nosso número deve ter 11 dígitos

## 📦 **4. Dependências**

**pom.xml (Maven)**
```xml
<dependencies>
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.3</version>
    </dependency>
</dependencies>
```

**build.gradle (Gradle)**
```groovy
dependencies {
    implementation 'com.itextpdf:itextpdf:5.5.13.3'
}
```        

## ▶️ **5. Como Executar**

*Via Terminal:*

1. Clone o Projeto:

```githubexpressionlanguage
git clone https://github.com/Raiannecaroline/design-patterns-ifba.git
cd builderBills
```

2. Execute com o Maven

```githubexpressionlanguage
mvn clean install && java -jar target/builderBills.jar
```

*Via IDE:*

- Importe como Projeto Maven e execute Main.java


## 🤝 **6. Contribuição**

1. Faça um fork do projeto
2. Crie a sua Branch 
```githubexpressionlanguage
(git checkout -b feature/nova-feature)
```
3. Commit suas mudanças
```githubexpressionlanguage
git commit -m 'Add feature'
```
4. Push para a Branch
```githubexpressionlanguage
git push origin feature/nova-feature
```
5. Abra um Pull Request

## 📜 **7. Licença**

MIT License © 2023

```shell
Este projeto utiliza o código sob licença MIT do repositório [builder-patterns](https://github.com/Raiannecaroline/design-patterns-ifba).
```

Principais melhorias:
1. Organização mais clara das seções
2. Códigos melhor formatados com syntax highlighting
3. Adicionada seção de dependências completa
4. Fluxo de contribuição detalhado
5. Links e referências mais explícitos
6. Mantido todo o conteúdo original em markdown válido

## *Link do vídeo:* https://youtu.be/7Dw9lNdWWiM

