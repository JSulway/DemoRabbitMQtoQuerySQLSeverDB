package com.demo.reader.querysql.msg;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.demo.error.CommonRuntimeException;
import com.demo.error.handler.ErrorTransformer;
import com.demo.message.MessageSender;
import com.demo.message.MessageValidator;
import com.demo.properties.PropertyHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.reader.querysql.exception.MsgReaderQuerySqlErrorCodes;
import com.demo.reader.querysql.handler.RetrieveRequestHandler;
import com.demo.reader.querysql.handler.RetrieveResponseHandler;
import com.demo.reader.querysql.json.dto.GetPlanetRequestDto;
import com.demo.reader.querysql.json.dto.GetPlanetResponseDto;


/**
 * A Receiver class containing queue listener bindings ensuring relevant
 * processing of retrieve operations as message
 * 
 * @author SULWAYJO
 *
 */
@Service
public class MessageReceiver {
	private final Logger logger = Logger.getLogger(MessageReceiver.class);

	@Value("${rabbitmq.host}")
	private String rabbitMQhost;
	
	@Value("${rabbitmq.vhost}")
	private String rabbitMQvirtualhost;
	
	@Value("${rabbitmq.exchange}")
	private String rabbitMQexchange;
	
	@Value("${rabbitmq.username}")
	private String username;
	
	@Value("${rabbitmq.password}")
	private String password;
	
	@Autowired
    private MessageSender messageSender;
	
	@Autowired
	RetrieveRequestHandler retrieveRequestHandler;
	
	@Autowired
	RetrieveResponseHandler retrieveResponseHandler;
	
	@Autowired
	PropertyHelper errorPropertyHelper;
	
	/**
	 * onMessage logic for a team retrieval request
	 * 
	 * @param jsonAsByteArray
	 * @param replyToQueue
	 * @param correlationID
	 * @param sourceIP
	 * @param usernameHeader
	 * @throws Exception
	 */
    @RabbitListener(bindings=@QueueBinding(
			value=@Queue(value="com.demo.get.data.request"),
			exchange=@Exchange(value="master", type=ExchangeTypes.TOPIC, durable="false"),
			key="com.demo.get.data.request"))	
    public void receiveMessageRetrieveTeam(@Payload byte[] jsonAsByteArray, 
		 @Header(name="reply-to-queue", required=false) String replyToQueue) throws Exception {
    	
    	 MessageValidator.validateHeaders(jsonAsByteArray, replyToQueue);
    	
    	 logger.info("Received JSON Message : \n\n" + new String(jsonAsByteArray) + "\n\n");
    	 
    	 // Initially set response string as nothing found
    	 String retrieveResponseJSONString = "{[]}";
    	 GetPlanetRequestDto getPlanetRequestDto = null;
    	 
    	 // Attempt to convert received message from JSON String to GetPlanetRequestDto Java Object
    	 try{
    		 ObjectMapper mapper = new ObjectMapper();
    		 getPlanetRequestDto = mapper.readValue(new String (jsonAsByteArray), GetPlanetRequestDto.class);
    		 logger.info("Values from request after JSON mapping : " + getPlanetRequestDto);
    	 }catch(Exception e){
    		 logger.error(errorPropertyHelper.getPropertyAndReplaceTemplates("ConversionExceptionMessage", e.getMessage()));
    		 CommonRuntimeException runex = new CommonRuntimeException(MsgReaderQuerySqlErrorCodes.CONVERSION_EXCEPTION, e, errorPropertyHelper.getPropertyAndReplaceTemplates("ConversionExceptionMessage", e.getMessage()) );
    		 ErrorTransformer transformer = new ErrorTransformer();
    		 retrieveResponseJSONString = transformer.transformErrorToJSON(runex);
    	 }
 
    	 
    	 try{
	    	 if(getPlanetRequestDto != null){
	    		 
	    		 GetPlanetResponseDto planetResponseDto = retrieveRequestHandler.handleRequest(getPlanetRequestDto);
				 
	    		 retrieveResponseJSONString = retrieveResponseHandler.handleResponse(planetResponseDto);
	    	 }
    	 }catch (Exception e){
    		 logger.error(errorPropertyHelper.getPropertyAndReplaceTemplates("ExceptionProcessingMessage"));
    		 ErrorTransformer transformer = new ErrorTransformer();
    		 retrieveResponseJSONString = transformer.transformErrorToJSON(e);
    	 }
    	 
		 logger.info("Returning retrieve Response : \n\n" + retrieveResponseJSONString);
    	 
    	 try{
    		 Map<String, Object> queueArgs = new HashMap<String, Object>();    		 
	    	 messageSender.setRabbitMQhost(rabbitMQhost);
			 messageSender.setQueueName(replyToQueue);
			 messageSender.setRabbitMQexchange(rabbitMQexchange);
			 messageSender.setRabbitMQvirtualhost(rabbitMQvirtualhost);
			 messageSender.setUsername(username);
			 messageSender.setPassword(password);
			 messageSender.publish(retrieveResponseJSONString,null,false,queueArgs);
			 logger.info("Retrieve response has been returned");
    	 }catch (Exception e){
    		 logger.error(errorPropertyHelper.getPropertyAndReplaceTemplates("ExceptionSendingMessage", e.getMessage()));
    		 throw new AmqpRejectAndDontRequeueException(errorPropertyHelper.getPropertyAndReplaceTemplates("ExceptionSendingMessage", e.getMessage()));
    	 }
	}
}
