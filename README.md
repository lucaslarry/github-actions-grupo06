# **Java CI: Testes Automatizados e Relatórios no GitHub Pages**

Este repositório possui um fluxo de **Integração Contínua (CI)** utilizando **GitHub Actions** para validar a qualidade do código, executar testes automatizados e disponibilizar relatórios interativos via **GitHub Pages**.

## 🚀 **Pipeline de CI/CD**

O pipeline está definido no arquivo `.github/workflows/pipeline-github-actions.yml` e é composto por diversas etapas:

### **1️⃣ Compilação do Projeto**
- Baixa o código-fonte do repositório.
- Executa a construção do projeto (`mvn clean install -DskipTests`).
- Armazena os arquivos gerados para uso posterior.

### **2️⃣ Testes de Contrato**
- Recupera os arquivos da etapa anterior.
- Roda os testes de contrato (`mvn test -Dgroups=Contrato`).
- Armazena os resultados para a geração do relatório Allure.

### **3️⃣ Testes Funcionais**
- Baixa os arquivos processados anteriormente.
- Executa testes funcionais (`mvn test -Dgroups=Funcional`).
- Salva os resultados dos testes para geração do relatório.

### **4️⃣ Geração do Relatório Allure**
- Coleta os dados dos testes de Contrato e Funcional.
- Gera um relatório interativo com **Allure Report**.
- Publica o relatório no **GitHub Pages** para fácil acesso.

### **5️⃣ Análise de Segurança com CodeQL**
- Avalia vulnerabilidades no código utilizando **CodeQL**.
- Executa uma varredura automatizada para identificar possíveis riscos.

### **6️⃣ Notificação no Discord**
- Caso os testes sejam bem-sucedidos ✅, envia uma notificação com o link para o relatório Allure.
- Em caso de falha ❌, alerta sobre os erros para investigação.<br><br>
  ![img.png](img.png)

## 📌 **Como o Pipeline é Disparado?**
O workflow é acionado automaticamente quando:  
✔ Um **Pull Request** é aberto na branch `main`.

## 📊 **Acesso ao Relatório Allure**
Os relatórios gerados podem ser acessados diretamente via **GitHub Pages**:  
🔗 [Relatório Allure](https://lucaslarry.github.io/github-actions-grupo06)

## 🛠 **Tecnologias Utilizadas**
- **Java 17**
- **Maven**
- **TesteNG**
- **REST Assured**
- **Allure Report**
- **GitHub Actions**
- **GitHub Pages**
- **Discord Webhook**

## **Grupo**
- Lucas Larry
- Marcelo Dalcin
