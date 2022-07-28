package br.com.compass.Sprint05.service;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void enviaMensagem(String nomeDaFila, Object mensagem) {
        rabbitTemplate.convertAndSend(nomeDaFila, mensagem);
    }
}
