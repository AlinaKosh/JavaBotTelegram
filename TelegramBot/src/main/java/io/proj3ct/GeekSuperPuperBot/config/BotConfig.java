package io.proj3ct.GeekSuperPuperBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("application.properties")//чтобы указать, где находится св-во
@Configuration
@Data //автоматически создаёт конструкторы, геттеры, сеттеры и toString для класса
public class BotConfig {

    @Value("${bot.name}") //берётся из application.properties
    String botName;

    @Value("${bot.token}") //берётся из application.properties
    String token;
}
