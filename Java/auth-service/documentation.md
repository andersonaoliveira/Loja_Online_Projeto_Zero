### Documentação do auth-service

#### Visão Geral
O `auth-service` é um microsserviço responsável por gerenciar a autenticação e o gerenciamento de usuários dentro do projeto da loja online. Ele utiliza Spring Boot, MongoDB, JWT para autenticação e integra-se com serviços de email para envio de notificações e links de redefinição de senha.

#### Estrutura do Projeto
O projeto é composto pelos seguintes componentes principais:
- **Arquivos de Configuração**: `pom.xml`, `Dockerfile`, `docker-compose.yml`, `application.properties`
- **Classes de Modelo**: Representam estruturas de dados (`User`, `JwtResponse`)
- **Interface de Repositório**: `UserRepository` para interações com o banco de dados
- **Classes de Serviço**: `EmailService`, `MyUserDetailsService` para lógica de negócios
- **Classes Utilitárias**: `DataLoader`, `JwtAuthenticationEntryPoint`, `JwtRequestFilter`, `JwtUtil` para funções auxiliares e configurações
- **Classe Principal da Aplicação**: `AuthServiceApplication`

#### Arquivos de Configuração

##### pom.xml
Define as dependências do projeto e as configurações de build:
- **Starters do Spring Boot**: `spring-boot-starter-web`, `spring-boot-starter-data-mongodb`, `spring-boot-starter-security`
- **Biblioteca JWT**: `jjwt`
- **Serviço de Email**: `spring-boot-starter-mail`
- **Outras Dependências**: `java-dotenv`, `lombok`, `jaxb-api`, `spring-boot-starter-test`

##### Dockerfile
Configura a imagem Docker para o serviço:
- Utiliza `eclipse-temurin:17-jre-alpine` como imagem base.
- Copia o arquivo JAR para dentro do contêiner.
- Expõe a porta 8081.
- Define o ponto de entrada para rodar o arquivo JAR.

##### docker-compose.yml
Define a configuração do Docker Compose para o serviço:
- Configura o serviço `mongodb`.
- Configura o `auth-service` para depender do `mongodb`.
- Define variáveis de ambiente para conexão com o MongoDB.

##### application.properties
Contém propriedades específicas da aplicação:
- URI de conexão com o MongoDB.
- Configuração do JWT.
- Configuração do serviço de email.
- Configuração da porta do servidor.

#### Classes de Modelo

##### JwtResponse.java
Representa a estrutura de resposta do JWT.
```java
public class JwtResponse {
    private final String jwt;

    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
```

##### User.java
Representa a entidade do usuário armazenada no MongoDB.
```java
@Document(collection = "users")
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
}
```

#### Interface de Repositório

##### UserRepository.java
Define a interface do repositório para a entidade `User`.
```java
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
```

#### Classes de Serviço

##### EmailService.java
Gerencia funcionalidades de envio de emails.
```java
@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendPasswordResetToken(String email, String token) {
        String subject = "Password Reset Request";
        String text = "Para redefinir sua senha, clique no link abaixo:\n" + "http://seudominio.com/reset-password?token=" + token;
        sendEmail(email, subject, text);
    }
}
```

##### MyUserDetailsService.java
Implementa `UserDetailsService` para carregar dados específicos do usuário.
```java
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
            new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
```

#### Classes Utilitárias

##### DataLoader.java
Inicializa o banco de dados com dados de teste.
```java
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2");
        user2.setEmail("user2@example.com");

        userRepository.save(user1);
        userRepository.save(user2);

        System.out.println("Test data inserted successfully!");
    }
}
```

##### JwtAuthenticationEntryPoint.java
Lida com tentativas de acesso não autorizadas.
```java
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if (request.getRequestURI().contains("/auth/login")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid username or password");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não autorizado");
        }
    }
}
```

##### JwtRequestFilter.java
Filtra solicitações recebidas para validar tokens JWT.
```java
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
                // Handle exceptions if needed
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
```

##### JwtUtil.java
Fornece funções utilitárias para geração e validação de tokens JWT.
```java
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.ms}")
    private Long expirationMs;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
```

#### Classe Principal da Aplicação

##### AuthServiceApplication.java
O ponto de entrada para a aplicação Spring Boot.
```java
@SpringBootApplication
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
```