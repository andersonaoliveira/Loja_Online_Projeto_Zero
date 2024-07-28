## Documentação do Microsserviço `product-catalog-service`

### Descrição Geral
O `product-catalog-service` é responsável pelo gerenciamento do catálogo de produtos da loja online. Ele permite operações CRUD (Create, Read, Update, Delete) para produtos e categorias de produtos, além de fornecer funcionalidades de busca e filtro de produtos.

### Estrutura do Projeto
A estrutura do projeto é baseada em Spring Boot, utilizando MongoDB como banco de dados. O projeto é containerizado usando Docker e Docker Compose para facilitar o gerenciamento e a implantação.

#### Arquivos principais:
1. `pom.xml`
2. `Dockerfile`
3. `docker-compose.yml`
4. `application.properties`
5. Classes de controlador, serviço, repositório, modelo e exceção

### Dependências Principais
As principais dependências utilizadas no projeto são:
- `spring-boot-starter-actuator`: Para monitoramento e métricas.
- `spring-boot-starter-data-mongodb`: Integração com MongoDB.
- `spring-boot-starter-validation`: Validação de dados.
- `spring-boot-starter-web`: Para criar APIs RESTful.
- `lombok`: Para reduzir a verbosidade do código Java.

### Configuração do Banco de Dados
No arquivo `application.properties`, a URI do MongoDB é configurada para conectar-se ao banco de dados local:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/productcatalog
```

### Docker e Docker Compose
O `Dockerfile` define a imagem do Docker para o microsserviço:
```Dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/product-catalog-service-0.0.1-SNAPSHOT.jar /app/product-catalog-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/product-catalog-service.jar"]
```

O `docker-compose.yml` define os serviços necessários:
```yaml
version: '3.8'

services:
  mongodb:
    image: mongo:latest
    container_name: mongodb-product-catalog
    ports:
      - "27018:27017"
    volumes:
      - mongo-data:/data/db

  product-catalog-service:
    build: .
    container_name: product-catalog-service
    ports:
      - "8082:8082"
    environment:
      - SPRING_DATA_MONGODB_URI=mongodb://mongodb-product-catalog:27017/productcatalog
    depends_on:
      - mongodb

volumes:
  mongo-data:
```

### Endpoints da API
Os principais endpoints expostos pelo `product-catalog-service` são:

#### Categorias (`/api/categories`)
- `GET /api/categories`: Retorna todas as categorias.
- `GET /api/categories/{id}`: Retorna uma categoria específica pelo ID.
- `POST /api/categories`: Cria uma nova categoria.
- `DELETE /api/categories/{id}`: Exclui uma categoria pelo ID.

#### Produtos (`/api/products`)
- `GET /api/products`: Retorna todos os produtos.
- `GET /api/products/{id}`: Retorna um produto específico pelo ID.
- `POST /api/products`: Cria um novo produto.
- `DELETE /api/products/{id}`: Exclui um produto pelo ID.
- `GET /api/products/category/{category}`: Retorna produtos por categoria.
- `GET /api/products/search`: Busca produtos por nome.

### Exceções e Manipulação de Erros
O microsserviço inclui uma classe de manipulação global de exceções (`GlobalExceptionHandler`) para tratar exceções específicas como `CategoryNotFoundException` e `ConstraintViolationException`.

### Modelos de Dados
Os modelos principais utilizados são `Category` e `Product`, mapeados para as coleções do MongoDB:

#### Categoria (`Category`)
```java
@Data
@Document(collection = "categories")
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
}
```

#### Produto (`Product`)
```java
@Data
@Document(collection = "products")
public class Product {
    @Id
    private String id;

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private Integer stock;
}
```

### Repositórios
Os repositórios para acessar o MongoDB são `CategoryRepository` e `ProductRepository`.

#### `CategoryRepository`
```java
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByName(String name);
}
```

#### `ProductRepository`
```java
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategory(String category);
    List<Product> findByNameContainingIgnoreCase(String name);
}
```

### Serviços
Os serviços `CategoryService` e `ProductService` encapsulam a lógica de negócios e interagem com os repositórios.

#### `CategoryService`
```java
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}
```

#### `ProductService`
```java
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }
}
```

### Inicialização do Aplicativo
A classe `ProductCatalogServiceApplication` contém o método principal que inicializa o Spring Boot:
```java
@SpringBootApplication
public class ProductCatalogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogServiceApplication.class, args);
    }
}
```