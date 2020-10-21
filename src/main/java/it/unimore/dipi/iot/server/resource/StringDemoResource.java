package it.unimore.dipi.iot.server.resource;

import it.unimore.dipi.iot.utils.StringGenerator;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple String resource randomly generated and editable by the client
 * through POST and/or PUT requests
 *
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project coap-playground
 * @created 20/10/2020 - 21:54
 */
public class StringDemoResource extends CoapResource {

	private final static Logger logger = LoggerFactory.getLogger(StringDemoResource.class);

	private String name;

	private String stringValue;
	
	public StringDemoResource(String name) {

		// set resource identifier
		super(name);
		this.name = name;
		this.stringValue = StringGenerator.generateRandomAlphanumericString();

		// set display name
		getAttributes().setTitle("String Demo Resource");
	}

	@Override
	public void handleGET(CoapExchange exchange) {

		logger.info("String Demo Resource: handleGET...");

		logger.info("Request Code: {}", exchange.getRequestCode());
		logger.info("Request Text: {}", exchange.getRequestText());
		logger.info("Request Pretty Print:\n{}", Utils.prettyPrint(exchange.advanced().getRequest()));

		exchange.respond(ResponseCode.CONTENT,this.stringValue);
	}

	@Override
	public void handlePOST(CoapExchange exchange) {

		try{

			logger.info("Request Pretty Print:\n{}", Utils.prettyPrint(exchange.advanced().getRequest()));
			logger.info("Received POST Request with body: {}", exchange.getRequestPayload());

			this.stringValue = StringGenerator.generateRandomAlphanumericString();

			logger.info("Resource Status Updated: {}", this.stringValue);

			//If a resource is created 2.01 should be returned
			exchange.respond(ResponseCode.CHANGED);

		}catch (Exception e){
			logger.error("Error Handling POST -> {}", e.getLocalizedMessage());
			exchange.respond(ResponseCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void handlePUT(CoapExchange exchange) {
		try{

			if(exchange.getRequestPayload() != null && exchange.getRequestPayload().length > 0){

				String receivedBody = new String(exchange.getRequestPayload());

				logger.info("Request Pretty Print:\n{}", Utils.prettyPrint(exchange.advanced().getRequest()));
				logger.info("Received PUT Request with body: {}", receivedBody);

				this.stringValue = receivedBody;

				exchange.respond(ResponseCode.CHANGED);

			}
			else {
				logger.warn("Received a PUT request with an empty body !");
				exchange.respond(ResponseCode.BAD_REQUEST);
			}

		}catch (Exception e){
			logger.error("Error handling PUT Request ! Error: {}", e.getLocalizedMessage());
			exchange.respond(ResponseCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void handleDELETE(CoapExchange exchange) {
		super.handleDELETE(exchange);
	}

}
