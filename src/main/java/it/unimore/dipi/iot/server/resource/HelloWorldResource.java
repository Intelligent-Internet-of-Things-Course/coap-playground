package it.unimore.dipi.iot.server.resource;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class HelloWorldResource extends CoapResource {

	private String name;
	
	public HelloWorldResource(String name) {
		// set resource identifier
		super(name);
		this.name = name;
		// set display name
		getAttributes().setTitle("Hello-World Resource");
	}

	public HelloWorldResource(String name, boolean visible) {
		super(name, visible);
		this.name = name;
		getAttributes().setTitle("Hello-World Resource");
	}
	
	@Override
	public void handleGET(CoapExchange exchange) {
		//System.out.println("HelloWorld Resource: handleGET..");
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
