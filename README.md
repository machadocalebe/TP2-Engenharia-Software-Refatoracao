# 🛠️ TP2 - Engenharia de Software: Refatoração (Build Pipeline Kata)

## 📌 Contexto do Projeto Original
Este projeto é baseado no clássico "Build Pipeline Refactoring Kata" de Emily Bache. O código original simulava a execução de um pipeline de integração contínua, porém apresentava diversos "Code Smells" (maus cheiros no código):
- Métodos excessivamente longos e com múltiplas responsabilidades (violação do SRP).
- Nomes de variáveis sem expressividade (`p`, `d`, `c`).
- Obsessão por primitivos (retorno de *Strings* em vez de *booleans* para indicar sucesso/falha).
- Listas de parâmetros extensas e acoplamento inadequado.

## 🚀 Melhorias Realizadas e Justificativas Técnicas

### 1. Reestruturação de Métodos e Expressividade (Exercícios 2 e 3)
- **Problema:** O método `Pipeline.run` possuía condicionais profundamente aninhadas (Arrow Anti-Pattern) e variáveis booleanas que dificultavam a leitura.
- **Solução:** Aplicamos o padrão **Guard Clauses (Retorno Antecipado)** para "achatar" a lógica. O método foi quebrado em métodos menores e focados, como `executeTests` e `executeDeploy`.
- **Justificativa:** A extração de métodos transformou o `run` em um "índice" de alto nível, melhorando drasticamente a legibilidade e facilitando a manutenção futura.

### 2. Melhoria de Assinaturas e Encapsulamento (Exercício 4)
- **Problema 1 (Primitive Obsession):** Os métodos `runTests()` e `deploy()` da classe `Project` retornavam Strings (`"success"` ou `"failure"`), o que é propenso a erros de digitação e não garante segurança de tipo (Type Safety).
- **Solução 1:** As assinaturas foram alteradas para retornar `boolean`.
- **Problema 2 (Long Parameter List):** O construtor de `Pipeline` recebia várias dependências soltas (`Config`, `Emailer`, `Logger`).
- **Solução 2:** Foi introduzido o padrão **Parameter Object**, criando a classe `BuildContext` para agrupar o ambiente de execução.

### 3. Reorganização de Classes e Alta Coesão (Exercício 5)
- **Problema:** A classe `Pipeline` orquestrava os testes/deploy e *também* decidia o conteúdo e envio de e-mails.
- **Solução:** Foi criada a classe especialista `BuildNotifier`.
- **Justificativa:** Aplicação estrita do **Princípio da Responsabilidade Única (SRP)**. Agora, se a regra de notificação mudar, a classe `Pipeline` não precisa ser alterada.

## ⚙️ Como Executar os Testes
O projeto utiliza **Maven** e **Java 21**. Para garantir que as refatorações não quebraram o comportamento original, basta executar:
```bash
mvn clean test
