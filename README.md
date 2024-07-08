# Loja_Online_Projeto_Zero
Loja online criada do zero utilizando-se prioritariamente as linguagens Java e Python, com banco de dados mongodb em arquitetura de microsserviços

### Requisitos Funcionais

1. **Cadastro e Autenticação de Usuários**
   - Registro de novos usuários.
   - Login de usuários existentes.
   - Recuperação de senha.
   - Perfil do usuário com informações pessoais e histórico de pedidos.

2. **Catálogo de Produtos**
   - Exibição de lista de produtos com categorias.
   - Página de detalhes do produto com informações detalhadas.
   - Busca por produtos.
   - Filtro e ordenação de produtos por preço, popularidade, etc.

3. **Carrinho de Compras**
   - Adicionar produtos ao carrinho.
   - Atualizar quantidade de produtos no carrinho.
   - Remover produtos do carrinho.
   - Visualização do resumo do carrinho.

4. **Processo de Checkout**
   - Endereços de entrega e cobrança.
   - Opções de pagamento (cartão de crédito, PayPal, etc.).
   - Resumo do pedido antes da confirmação.
   - Confirmação e geração de número de pedido.

5. **Gestão de Pedidos**
   - Visualização do histórico de pedidos do usuário.
   - Detalhes do pedido com status (processando, enviado, entregue).
   - Cancelamento de pedidos (dentro de um prazo específico).

6. **Administração da Loja**
   - Painel de administração para gerenciar produtos, categorias e estoques.
   - Gerenciamento de pedidos (visualização, atualização de status).
   - Gestão de usuários (visualização, banimento, etc.).
   - Relatórios de vendas e análise de desempenho.

### Requisitos Não Funcionais

1. **Performance**
   - Tempo de resposta rápido para todas as operações.
   - Suporte a um grande número de usuários simultâneos.

2. **Segurança**
   - Proteção contra ataques comuns (SQL Injection, XSS, CSRF).
   - Criptografia de dados sensíveis (senhas, informações de pagamento).

3. **Escalabilidade**
   - Estrutura de microsserviços para permitir escalabilidade horizontal.
   - Uso de balanceamento de carga.

4. **Usabilidade**
   - Interface intuitiva e fácil de usar.
   - Responsividade para dispositivos móveis.

5. **Manutenibilidade**
   - Código bem documentado.
   - Testes automatizados (unitários e de integração).

### Funcionalidades Detalhadas

1. **Página Inicial**
   - Destaques de produtos e promoções.
   - Navegação fácil para categorias principais.

2. **Página de Produto**
   - Imagens de alta qualidade do produto.
   - Descrição detalhada, especificações técnicas.
   - Avaliações e comentários de clientes.
   - Produtos relacionados.

3. **Sistema de Avaliação de Produtos**
   - Permitir que os usuários avaliem e comentem os produtos.
   - Exibir média de avaliações e comentários recentes.

4. **Carrinho de Compras**
   - Persistência do carrinho entre sessões.
   - Cálculo automático de impostos e frete.

5. **Checkout**
   - Validação em tempo real dos dados inseridos.
   - Opção para salvar endereços de entrega para futuros pedidos.

6. **Administração**
   - Interface amigável para adicionar/editar/remover produtos.
   - Controle de estoque em tempo real.
   - Ferramentas de análise de vendas (gráficos, relatórios).

### Tecnologias e Ferramentas

1. **Backend (Java)**
   - Spring Boot para desenvolvimento rápido e eficiente.
   - Spring Security para autenticação e autorização.
   - Spring Data para integração com MongoDB.

2. **Frontend (Django)**
   - Django REST framework para consumir APIs do backend.
   - Templates Django para renderização de páginas.

3. **Banco de Dados (MongoDB)**
   - Armazenamento de produtos, usuários, pedidos e avaliações.

4. **Microsserviços**
   - Comunicação via APIs RESTful.
   - Docker para containerização dos serviços.

5. **Ferramentas de Desenvolvimento**
   - Git para controle de versão.
   - Jenkins para CI/CD.
   - JIRA ou Trello para gestão de tarefas e sprints.


### Definição da Arquitetura de Microsserviços

A arquitetura de microsserviços proposta dividirá a aplicação em vários serviços independentes, cada um responsável por uma funcionalidade específica. Os serviços comunicar-se-ão entre si através de APIs RESTful, proporcionando escalabilidade e facilidade de manutenção. A seguir, a lista de microsserviços e suas responsabilidades:

1. **Serviço de Autenticação e Autorização**
   - Registro de novos usuários
   - Login de usuários existentes
   - Recuperação de senha
   - Gerenciamento de tokens JWT

2. **Serviço de Usuários**
   - Perfil do usuário
   - Histórico de pedidos

3. **Serviço de Catálogo de Produtos**
   - Gerenciamento de produtos e categorias
   - Busca e filtro de produtos
   - Detalhes do produto

4. **Serviço de Carrinho de Compras**
   - Adicionar, atualizar e remover produtos do carrinho
   - Visualização do resumo do carrinho
   - Persistência do carrinho entre sessões

5. **Serviço de Checkout**
   - Processamento de endereços de entrega e cobrança
   - Opções de pagamento e integração com gateways de pagamento
   - Confirmação de pedidos e geração de número de pedido

6. **Serviço de Pedidos**
   - Gestão de pedidos (criação, atualização de status, cancelamento)
   - Histórico de pedidos do usuário
   - Detalhes do pedido

7. **Serviço de Administração**
   - Gerenciamento de produtos, categorias e estoques
   - Gestão de usuários (visualização, banimento)
   - Relatórios de vendas e análise de desempenho

8. **Serviço de Avaliações**
   - Gestão de avaliações e comentários de produtos
   - Exibição de média de avaliações e comentários recentes

### Diagrama de Fluxo de Dados

```mermaid
graph TD;
    Usuario-->Autenticacao;
    Autenticacao-->Usuarios;
    Usuarios-->Pedidos;
    Usuarios-->Carrinho;
    Carrinho-->Checkout;
    Checkout-->Pedidos;
    Pedidos-->Usuarios;
    Produtos-->Carrinho;
    Produtos-->Pedidos;
    Produtos-->Avaliacoes;
    Avaliacoes-->Produtos;
    Admin-->Produtos;
    Admin-->Usuarios;
    Admin-->Pedidos;
    Admin-->Relatorios;

    subgraph Admin
        Admin[Administração]
    end

    subgraph Catalogo
        Produtos[Catálogo de Produtos]
    end

    subgraph Pedidos
        Pedidos[Serviço de Pedidos]
    end

    subgraph Avaliacoes
        Avaliacoes[Sistema de Avaliações]
    end

    subgraph Checkout
        Checkout[Serviço de Checkout]
    end

    subgraph Carrinho
        Carrinho[Serviço de Carrinho de Compras]
    end

    subgraph Usuarios
        Usuarios[Serviço de Usuários]
    end

    subgraph Autenticacao
        Autenticacao[Serviço de Autenticação e Autorização]
    end
```

### Explicação do Fluxo de Dados

1. **Usuário e Autenticação**:
   - O usuário inicia o processo interagindo com o serviço de autenticação para login, registro ou recuperação de senha.
   - Uma vez autenticado, o usuário pode acessar seu perfil e histórico de pedidos através do serviço de usuários.

2. **Catálogo de Produtos e Avaliações**:
   - O usuário pode navegar pelo catálogo de produtos, visualizar detalhes, buscar produtos e aplicar filtros.
   - O serviço de catálogo de produtos se comunica com o serviço de avaliações para exibir comentários e classificações dos produtos.

3. **Carrinho de Compras**:
   - O usuário pode adicionar produtos ao carrinho, atualizar quantidades ou remover produtos.
   - O serviço de carrinho de compras persiste os itens do carrinho entre sessões e se comunica com o serviço de catálogo para obter informações detalhadas dos produtos.

4. **Checkout**:
   - No processo de checkout, o usuário insere endereços de entrega e cobrança, seleciona opções de pagamento e confirma o pedido.
   - O serviço de checkout interage com gateways de pagamento e, após a confirmação, gera um número de pedido.

5. **Gestão de Pedidos**:
   - O serviço de pedidos gerencia todo o ciclo de vida dos pedidos, desde a criação até a atualização de status e cancelamento.
   - O usuário pode visualizar o histórico e detalhes dos seus pedidos através do serviço de pedidos.

6. **Administração**:
   - O painel de administração permite aos administradores gerenciar produtos, categorias, estoques, usuários e pedidos.
   - Relatórios de vendas e análises de desempenho são gerados para auxiliar na tomada de decisões.

Esta arquitetura modular e desacoplada permite que cada serviço seja desenvolvido, implantado e escalado independentemente, proporcionando uma maior flexibilidade e resiliência para a aplicação.
