package it.unimore.dipi.iot.server.resource;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringDemoResource extends CoapResource {

	private final static Logger logger = LoggerFactory.getLogger(StringDemoResource.class);

	private String name;
	
	public StringDemoResource(String name) {
		// set resource identifier
		super(name);
		this.name = name;
		// set display name
		getAttributes().setTitle("Hello-World Resource");
	}

	public StringDemoResource(String name, boolean visible) {
		super(name, visible);
		this.name = name;
		getAttributes().setTitle("Hello-World Resource");
	}
	
	@Override
	public void handleGET(CoapExchange exchange) {

		logger.info("String Demo Resource: handleGET...");

		logger.info("Request Code: {}", exchange.getRequestCode());
		logger.info("Request Text: {}", exchange.getRequestText());
		logger.info("Request Pretty Print:{}", Utils.prettyPrint(exchange.advanced().getRequest()));

		exchange.respond(ResponseCode.CONTENT,this.name);
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		super.handlePOST(exchange);
	}

	@Override
	public void handlePUT(CoapExchange exchange) {
		super.handlePUT(exchange);
	}

	@Override
	public void handleDELETE(CoapExchange exchange) {
		super.handleDELETE(exchange);
	}
	
}
