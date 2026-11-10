# RP2 — Marco II: Simulação da Central SAMU

## 1. Sobre o projeto

Este projeto tem como objetivo analisar, corrigir e aprimorar um simulador da Central de Ambulâncias do SAMU, desenvolvido em Java, buscando garantir seu funcionamento conforme os requisitos definidos no enunciado da atividade.

O trabalho envolve a identificação e correção de erros, a refatoração do código, a implementação de testes unitários e a validação do sistema por meio de simulações.

## 2. Objetivo

Tornar o simulador confiável para representar o atendimento de chamados de emergência, avaliar os indicadores de desempenho e identificar a composição de frota de menor custo que atenda às metas estabelecidas.

## 3. Etapas do desenvolvimento

- **[Semana 1](Semana 1) — Identificação de erros:** identificar e corrigir erros de sintaxe e de semântica simples, registrando os problemas encontrados e as correções realizadas.
- **[Semana 2](Semana 2) — Refatoração:** melhorar a estrutura e a organização das classes sem alterar o comportamento externo do sistema.
- **[Semana 3](Semana 3) — Testes de unidade:** implementar testes unitários com JUnit 5 para verificar o comportamento esperado conforme o enunciado.
- **[Semana 4](Semana 4) — Operacionalização:** corrigir os defeitos identificados durante os testes, executar novamente os testes para verificar as correções e analisar os resultados da simulação para determinar a frota recomendada.

---

# 4. Plano de Teste do Projeto

**Identificação:** PT-SAMU-001  
**Versão:** 1.0  
**Status:** Em elaboração  
**Projeto:** RP2 — Marco II: Simulação da Central SAMU  
**Norma de referência:** ISO/IEC/IEEE 29119-3:2021 — Seção 7.2.

## 4.1 Contexto dos testes

Os testes têm como objetivo verificar se as classes e funcionalidades do simulador da Central SAMU estão funcionando conforme os requisitos definidos no enunciado da atividade.

Serão considerados os seguintes níveis de teste:

- **Testes unitários:** verificar individualmente o funcionamento de métodos e classes.
- **Testes de integração:** verificar a comunicação e o funcionamento conjunto entre diferentes classes.
- **Testes de sistema:** avaliar o funcionamento geral do simulador por meio da execução de simulações.

### Itens de teste

As classes que fazem parte do escopo de análise e teste são:

- `AmbulanciaBasica`
- `AnalisadorFrota`
- `CentralSamu`
- `Cidade`
- `CidadeGUI`
- `Estatisticas`
- `Randomizador`
- `Simulacao`
- `UTIMovel`
- `ItemDesenhavel`
- `Localizacao`
- `Paciente`
- `PacienteAusenteException`
- `Hospital`
- `FonteChamados`
- `Gravidade`

### Escopo dos testes

Serão verificadas funcionalidades relacionadas a:

- Cadastro e localização de cidades e pacientes;
- Funcionamento e disponibilidade das ambulâncias;
- Geração e atendimento de chamados;
- Priorização dos chamados conforme a gravidade e o tempo de espera;
- Descarte de chamados que ultrapassarem 30 etapas de espera;
- Seleção de veículos conforme a gravidade do paciente;
- Comunicação entre a central e os demais componentes;
- Tratamento de exceções e situações inesperadas;
- Escolha do hospital mais próximo com leito disponível;
- Controle do tempo de internação e liberação dos leitos;
- Cálculo dos indicadores e estatísticas;
- Execução das simulações e análise dos resultados;
- Funcionamento do analisador de frota;
- Reprodutibilidade dos resultados utilizando a mesma semente aleatória.

Não fazem parte do escopo a validação de sistemas externos ou de estruturas reais de atendimento hospitalar.

### Base para os testes

Os testes serão elaborados com base em:

- Enunciado da atividade;
- Requisitos e regras de funcionamento do simulador;
- Código-fonte e comportamento esperado das classes;
- Documentação do projeto;
- Norma ISO/IEC/IEEE 29119-3:2021.

## 4.2 Premissas e restrições

### Premissas

- O código-fonte do projeto estará disponível para análise e execução.
- O ambiente de desenvolvimento terá suporte à versão Java exigida pelo projeto.
- A biblioteca JUnit 5 será utilizada na implementação dos testes unitários.
- Os requisitos definidos no enunciado serão utilizados como referência para verificar os resultados esperados.
- Os integrantes terão acesso ao repositório GitLab.
- Os dados utilizados nos testes poderão ser controlados para facilitar a reprodução dos cenários.

### Restrições

- As atividades deverão respeitar o cronograma de quatro semanas.
- As correções não deverão comprometer os requisitos originais do simulador.
- Os testes deverão considerar as características e limitações do projeto.
- Os resultados esperados não deverão ser alterados apenas para fazer os testes passarem.
- O tempo disponível e o conhecimento da equipe sobre as ferramentas poderão limitar a quantidade de testes implementados.

## 4.3 Partes interessadas

| Parte interessada | Responsabilidade ou interesse |
|---|---|
| Professora responsável | Orientar e avaliar o desenvolvimento do projeto. |
| Integrantes do grupo | Analisar, corrigir, refatorar e testar o sistema. |
| Usuários do simulador | Utilizar o sistema e obter resultados confiáveis. |
| Secretaria Municipal de Saúde | Representada no cenário do problema, possui interesse nos indicadores de desempenho e na definição de uma frota adequada. |

A equipe será responsável por identificar, registrar e analisar os problemas encontrados durante os testes. A professora será responsável pela avaliação do trabalho e de seus resultados.

## 4.4 Comunicação dos testes

A comunicação das atividades e dos resultados será realizada por meio de:

- **GitLab:** armazenamento do código-fonte, acompanhamento das alterações e registro das atividades.
- **Wiki:** documentação do plano de teste, das atividades e dos resultados.
- **Comunicação entre os integrantes:** divisão das tarefas, discussão dos problemas e compartilhamento dos resultados.
- **Registros de testes:** documentação dos casos executados, resultados obtidos e falhas identificadas.

Os defeitos encontrados deverão ser comunicados aos integrantes responsáveis pela implementação, permitindo que sejam analisados e corrigidos.

## 4.5 Registro de riscos

| Risco | Impacto | Medida preventiva |
|---|---|---|
| Existência de erros no código-fonte | Pode impedir a compilação ou execução dos testes. | Identificar e corrigir os erros antes da execução da suíte de testes. |
| Testes baseados no comportamento incorreto do código | Pode permitir que defeitos permaneçam no sistema. | Utilizar o enunciado como referência para definir os resultados esperados. |
| Dificuldade em reproduzir determinados cenários | Pode dificultar a identificação de erros. | Utilizar dados controlados e a semente fixa do Randomizador. |
| Falta de tempo para concluir os testes | Pode comprometer a cobertura das funcionalidades. | Organizar as atividades e priorizar as regras mais importantes da simulação. |
| Conflitos entre alterações dos integrantes | Pode causar inconsistências no código. | Sincronizar as alterações e utilizar o controle de versão do GitLab. |
| Problemas na configuração do JUnit 5 | Pode impedir a execução dos testes automatizados. | Verificar previamente as dependências e a configuração do ambiente. |
| Falhas não identificadas durante os testes | Podem comprometer a confiabilidade dos resultados da simulação. | Realizar revisões e executar novamente os testes após as correções. |

## 4.6 Estratégia de testes

### Abordagem geral

A estratégia adotada será baseada principalmente em testes de unidade automatizados, utilizando JUnit 5.

Cada classe deverá ser testada considerando sua responsabilidade dentro do sistema e as regras especificadas no enunciado.

Os testes deverão verificar situações normais de funcionamento, valores limite e comportamentos excepcionais.

### Níveis de teste

**Testes de unidade:**

Serão utilizados para verificar individualmente os métodos e as classes, garantindo que cada componente execute corretamente sua responsabilidade.

**Testes de integração:**

Serão utilizados para verificar o funcionamento conjunto dos componentes, especialmente a comunicação entre a central, os veículos, os pacientes e os hospitais.

**Testes de sistema:**

Serão utilizados para avaliar o funcionamento geral do simulador, considerando a execução das etapas e a apresentação dos indicadores de desempenho.

### Tipos de teste

- **Testes funcionais:** verificar se as funcionalidades implementadas respeitam as regras da simulação.
- **Testes de valores limite:** verificar comportamentos próximos aos limites definidos, como o tempo máximo de espera dos chamados e a capacidade dos hospitais.
- **Testes de exceção:** verificar se situações excepcionais são tratadas adequadamente.
- **Testes de regressão:** executar novamente os testes após alterações no código para verificar se as correções não introduziram novos defeitos.

### Técnicas de projeto de testes

Serão utilizadas as seguintes técnicas:

- **Particionamento de equivalência:** dividir os dados de entrada em grupos que apresentem comportamentos semelhantes.
- **Análise de valores limite:** verificar valores próximos aos limites estabelecidos nas regras da simulação.
- **Teste de transição de estados:** verificar as mudanças de estado dos veículos, pacientes e hospitais durante a simulação.

### Critérios de entrada

A execução dos testes deverá iniciar quando:

- O código-fonte estiver disponível no repositório.
- O ambiente Java estiver configurado.
- O JUnit 5 estiver disponível para execução.
- As classes necessárias para os testes estiverem compilando.
- Os comportamentos esperados estiverem identificados a partir do enunciado.

### Critérios de saída

Os testes serão considerados concluídos quando:

- As classes de teste previstas tiverem sido implementadas e executadas.
- Os testes apresentarem os resultados esperados conforme as regras do enunciado.
- Os defeitos identificados tiverem sido analisados e corrigidos ou devidamente registrados.
- A suíte de testes apresentar aprovação após as correções.
- A simulação respeitar as regras de funcionamento estabelecidas no enunciado.

### Critérios de suspensão e retomada

Os testes poderão ser temporariamente suspensos quando:

- O projeto apresentar erros que impeçam sua compilação.
- O ambiente de testes estiver configurado incorretamente.
- Uma falha impedir a execução dos demais testes.

A execução será retomada após a correção do problema que impediu a continuidade.

### Ambiente de testes

| Recurso | Utilização |
|---|---|
| Java 17 ou superior | Linguagem e ambiente de execução do projeto. |
| JUnit 5 | Implementação e execução dos testes automatizados. |
| IDE de desenvolvimento | Edição, compilação e execução do código. |
| GitLab | Controle de versão e armazenamento dos artefatos. |
| Randomizador | Controle da geração de eventos aleatórios e reprodutibilidade. |

### Dados de teste

Os dados utilizados deverão permitir verificar diferentes situações da simulação, incluindo:

- Chamados com gravidade LEVE, MODERADO e GRAVE.
- Veículos disponíveis e ocupados.
- Pacientes aguardando atendimento.
- Hospitais com leitos disponíveis e lotados.
- Diferentes posições e deslocamentos na cidade.
- Situações de espera e perda de chamados.

Sempre que possível, serão utilizados dados controlados para facilitar a reprodução dos cenários.

### Entregáveis dos testes

Os seguintes artefatos deverão ser produzidos ou atualizados:

- Plano de Teste do Projeto;
- Classes de teste automatizadas com JUnit 5;
- Registro dos resultados das execuções;
- Registro dos defeitos identificados;
- Código-fonte atualizado após as correções;
- Resultados finais da validação da simulação.

### Desvios da estratégia

Caso seja necessário modificar a estratégia inicialmente estabelecida, a alteração deverá ser discutida entre os integrantes do grupo e registrada na documentação do projeto, apresentando sua justificativa.

## 4.7 Atividades e estimativas

| Atividade | Descrição | Período |
|---|---|---|
| Planejamento dos testes | Definir o que será testado e os resultados esperados. | Semana 3 |
| Preparação do ambiente | Configurar o JUnit 5 e preparar as classes de teste. | Semana 3 |
| Implementação dos testes | Desenvolver os testes unitários das classes do projeto. | Semana 3 |
| Execução dos testes | Executar os testes e identificar falhas. | Semana 3 |
| Análise dos resultados | Investigar os defeitos encontrados. | Semana 3 e 4 |
| Correção dos defeitos | Ajustar os métodos que apresentam comportamento incorreto. | Semana 4 |
| Reexecução dos testes | Verificar se as correções resolveram os problemas. | Semana 4 |
| Validação final | Executar a simulação e o AnalisadorFrota após a validação dos testes. | Semana 4 |

As estimativas de duração de cada atividade deverão ser ajustadas pelo grupo conforme a divisão das tarefas e o andamento do desenvolvimento.

## 4.8 Recursos humanos e responsabilidades

As atividades de teste serão realizadas pelos integrantes do grupo, que poderão atuar nas seguintes responsabilidades:

| Responsabilidade | Atuação |
|---|---|
| Planejamento | Definição do escopo e dos objetivos dos testes. |
| Desenvolvimento dos testes | Implementação das classes de teste com JUnit 5. |
| Execução | Execução dos testes e registro dos resultados. |
| Análise | Investigação dos defeitos identificados. |
| Correção | Ajuste dos métodos e funcionalidades com problemas. |
| Revisão | Verificação das alterações e dos resultados obtidos. |

A divisão específica das responsabilidades será definida e acompanhada pelo grupo.

## 4.9 Cronograma

O planejamento seguirá as quatro semanas estabelecidas no enunciado do projeto.

| Semana | Etapa | Atividades |
|---|---|---|
| 1 | Identificação de erros | Identificação e correção de erros de sintaxe e semântica. |
| 2 | Refatoração | Melhorias na estrutura das classes, herança, polimorfismo e encapsulamento. |
| 3 | Testes de unidade | Implementação e execução dos testes utilizando JUnit 5. |
| 4 | Operacionalização | Correção dos defeitos, reexecução dos testes e validação final da simulação. |

O cronograma poderá ser atualizado conforme o andamento das atividades, mantendo o registro das alterações na Wiki.

## 4.10 Aprovação do plano

O plano de teste deverá ser revisado pelos integrantes do grupo antes de sua utilização, garantindo que as atividades previstas estejam de acordo com o escopo e o cronograma do projeto.

As alterações realizadas neste documento deverão ser registradas na Wiki do GitLab, mantendo o histórico das versões.

## 5. Referências

- Enunciado do problema: Simulação da Central de Ambulâncias do SAMU – Resolução de Problemas II, 2026/02.
- ISO/IEC/IEEE 29119-3:2021 – Software and systems engineering — Software testing — Part 3: Test documentation.
- Documentação oficial do JUnit 5: https://junit.org/junit5/