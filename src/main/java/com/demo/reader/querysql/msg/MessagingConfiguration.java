package com.demo.reader.querysql.msg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Channel;

/**
 * Class configuration for the messages
 * 
 * @author SULWAYJO
 *
 */
@Configuration
@EnableRabbit
public class MessagingConfiguration {
	
	@Value("${rabbitmq.vhost}")
	private String rabbitMQvirtualhost;
	
	@Value("${rabbitmq.host}")
	private String rabbitMQhost;
	
	@Value("${rabbitmq.exchange}")
	private String rabbitExchange;
	
	@Value("${rabbitmq.username}")
	private String rabbitUsername;
	
	@Value("${rabbitmq.password}")
	private String rabbitPassword;
	
	@Value("${rabbitmq.demo.sql.get.data.request}")
	private String rabbitMQGetPlanetRequestQueue;
	
	@Value("${rabbitmq.demo.sql.get.data.response}")
	private String rabbitMQGetPlanetResponseQueue;

	/**
	 * Returns a rabbit template having set the exchange and json message converter
	 * 
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setExchange(rabbitExchange);
		return rabbitTemplate;
	}

	/**
	 * Returns SimpleRabbitListenerContainerFactory having set the connection factory
	 * 
	 * @param connectionFactory
	 * @return
	 * @throws IOException 
	 */
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory _connectionFactory) throws IOException {
		SimpleRabbitListenerContainerFactory containerfactory = new SimpleRabbitListenerContainerFactory();
		
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMQhost); 
		connectionFactory.setUsername(rabbitUsername);
		connectionFactory.setPassword(rabbitPassword);
		connectionFactory.setVirtualHost(rabbitMQvirtualhost);
        Connection connection = connectionFactory.createConnection();
	    Channel channel = connection.createChannel(true);
	    channel.exchangeDeclare(rabbitExchange, ExchangeTypes.TOPIC,true);
	    
	    // On declare, it will only create it if it doesn't already exist
	    // TODO Declared here until mule declares them to create them
	    Map<String, Object> queueArgs = new HashMap<String, Object>();
	    channel.queueDeclare(rabbitMQGetPlanetRequestQueue, false, false, false, queueArgs);
    	channel.queueBind(rabbitMQGetPlanetRequestQueue, rabbitExchange, rabbitMQGetPlanetRequestQueue);
    	channel.queueDeclare(rabbitMQGetPlanetResponseQueue, false, false, false, queueArgs);
    	channel.queueBind(rabbitMQGetPlanetResponseQueue, rabbitExchange, rabbitMQGetPlanetResponseQueue);
  		
    	containerfactory.setConnectionFactory(connectionFactory);
		return containerfactory; 
	}
}
