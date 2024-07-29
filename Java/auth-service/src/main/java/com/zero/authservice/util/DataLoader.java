package com.zero.authservice.util;
import com.zero.authservice.model.User;
import com.zero.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Limpar dados existentes (opcional)
        userRepository.deleteAll();

        // Inserir dados de teste
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

        // Exemplo de impressão para verificar
        System.out.println("Dados de teste inseridos com sucesso!");

        // Outras operações de inicialização se necessário
    }
}
