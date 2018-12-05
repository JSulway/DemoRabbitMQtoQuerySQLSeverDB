package com.demo.reader.querysql;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demo.properties.PropertyHelper;
import com.demo.reader.querysql.handler.RetrieveRequestHandler;
import com.demo.reader.querysql.handler.RetrieveResponseHandler;
import com.demo.reader.querysql.validation.RetrieveRequestValidator;

/**
 * Message Reader Query SQL Application that listens for Messages on a RabbitMQ Request Queue. On reception
 * It will use Jackson to convert the String to a Java Object, performing to ensure the expected parameters are present
 * , pass the parameters to the stored procedure that exists in SQLServer, receive and convert the response
 * and finally place the JSON response on the reply-to-queue as defined in the headers on the original queue
 * request message. 
 * 
 * SpringBoot:
 * @SpringBootApplication - Convenience annotation that adds the following:
 *			@Configuration - Tags the class as a source of bean definitions for the application context.
 *			@EnableAutoConfiguration - Tells SpringBoot to start adding beans based on classpath settings, other beans, and various property settings.
 *			@ComponentScan - Tells Spring to look for other components, configurations, and services in the package, allowing it to find the controllers.
 * 
 * @author SULWAYJO
 *
 */

@SpringBootApplication(scanBasePackages={"com.demo"})
public class MessageReaderQuerySQLApplication {
	
	
    /**
     * The Application main method starting the Spring Application
     * @param args
     */
	public static void main(String[] args) {
		SpringApplication.run(MessageReaderQuerySQLApplication.class, args);
	}
	
	/**
	 * Return a SearchRequestHandler
	 * 
	 * @return
	 */
	@Bean
	public RetrieveRequestHandler seachRequestHandler(){
		return new RetrieveRequestHandler();
	}
	
	/**
	 * Return a SearchResponseHandler
	 * 
	 * @return
	 */
	@Bean
	public RetrieveResponseHandler seachResponseHandler(){
		return new RetrieveResponseHandler();
	}
	
	/**
	 * Return a populated error properties object
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Bean
	public PropertyHelper errorPropertyHelper() throws FileNotFoundException, IOException{
		PropertyHelper properties = new PropertyHelper();
		InputStream resourceAsInputStream = MessageReaderQuerySQLApplication.class.getClassLoader().getResourceAsStream("errors.properties");
		properties.load(resourceAsInputStream);
		return properties;
	}
	
	/**
	 * Return a RetrieveRequestValidator
	 * 
	 * @return
	 */
	@Bean
	public RetrieveRequestValidator retrieveRequestValidator(){
		return new RetrieveRequestValidator();
	}

}
