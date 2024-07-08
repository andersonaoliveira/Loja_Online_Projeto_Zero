# Loja_Online_Projeto_Zero
Loja online criada do zero utilizando-se prioritariamente as linguagens Java e Python, com banco de dados mongodb em arquitetura de microsserviços


Requisitos Funcionais
Cadastro e Autenticação de Usuários

Registro de novos usuários.
Login de usuários existentes.
Recuperação de senha.
Perfil do usuário com informações pessoais e histórico de pedidos.
Catálogo de Produtos

Exibição de lista de produtos com categorias.
Página de detalhes do produto com informações detalhadas.
Busca por produtos.
Filtro e ordenação de produtos por preço, popularidade, etc.
Carrinho de Compras

Adicionar produtos ao carrinho.
Atualizar quantidade de produtos no carrinho.
Remover produtos do carrinho.
Visualização do resumo do carrinho.
Processo de Checkout

Endereços de entrega e cobrança.
Opções de pagamento (cartão de crédito, PayPal, etc.).
Resumo do pedido antes da confirmação.
Confirmação e geração de número de pedido.
Gestão de Pedidos

Visualização do histórico de pedidos do usuário.
Detalhes do pedido com status (processando, enviado, entregue).
Cancelamento de pedidos (dentro de um prazo específico).
Administração da Loja

Painel de administração para gerenciar produtos, categorias e estoques.
Gerenciamento de pedidos (visualização, atualização de status).
Gestão de usuários (visualização, banimento, etc.).
Relatórios de vendas e análise de desempenho.
Requisitos Não Funcionais
Performance

Tempo de resposta rápido para todas as operações.
Suporte a um grande número de usuários simultâneos.
Segurança

Proteção contra ataques comuns (SQL Injection, XSS, CSRF).
Criptografia de dados sensíveis (senhas, informações de pagamento).
Escalabilidade

Estrutura de microsserviços para permitir escalabilidade horizontal.
Uso de balanceamento de carga.
Usabilidade

Interface intuitiva e fácil de usar.
Responsividade para dispositivos móveis.
Manutenibilidade

Código bem documentado.
Testes automatizados (unitários e de integração).
Funcionalidades Detalhadas
Página Inicial

Destaques de produtos e promoções.
Navegação fácil para categorias principais.
Página de Produto

Imagens de alta qualidade do produto.
Descrição detalhada, especificações técnicas.
Avaliações e comentários de clientes.
Produtos relacionados.
Sistema de Avaliação de Produtos

Permitir que os usuários avaliem e comentem os produtos.
Exibir média de avaliações e comentários recentes.
Carrinho de Compras

Persistência do carrinho entre sessões.
Cálculo automático de impostos e frete.
Checkout

Validação em tempo real dos dados inseridos.
Opção para salvar endereços de entrega para futuros pedidos.
Administração

Interface amigável para adicionar/editar/remover produtos.
Controle de estoque em tempo real.
Ferramentas de análise de vendas (gráficos, relatórios).
Tecnologias e Ferramentas
Backend (Java)

Spring Boot para desenvolvimento rápido e eficiente.
Spring Security para autenticação e autorização.
Spring Data para integração com MongoDB.
Frontend (Django)

Django REST framework para consumir APIs do backend.
Templates Django para renderização de páginas.
Banco de Dados (MongoDB)

Armazenamento de produtos, usuários, pedidos e avaliações.
Microsserviços

Comunicação via APIs RESTful.
Docker para containerização dos serviços.
Ferramentas de Desenvolvimento

Git para controle de versão.
Jenkins para CI/CD.
JIRA ou Trello para gestão de tarefas e sprints.
