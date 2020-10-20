package it.unimore.dipi.iot.server.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unimore.dipi.iot.server.model.TemperatureDataDescriptor;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Random;

public class JsonTemperatureResource extends CoapResource {

	private static final int TEMPERATURE_VALUE_BOUND = 30;

	private static final String OBJECT_TITLE = "TemperatureSensor";

	private ObjectMapper objectMapper;

	private double temperatureValue = 0.0;

	private Random random;

	public JsonTemperatureResource(String name) {
		super(name);
		init();
	}

	private void init(){
		getAttributes().setTitle(OBJECT_TITLE);
        this.objectMapper = new ObjectMapper();
		this.random = new Random();
	}

	private void updateTemperatureValue(){
		this.temperatureValue = this.random.nextInt(TEMPERATURE_VALUE_BOUND) + 1;
	}

	private String getTextResponse(){
		return Double.toString(this.temperatureValue);
	}

	private String getJsonResponse(){
		try{
			return this.objectMapper.writeValueAsString(new TemperatureDataDescriptor(System.currentTimeMillis(), this.temperatureValue));
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		updateTemperatureValue();
		if(exchange.getRequestOptions().getAccept() == MediaTypeRegistry.APPLICATION_JSON)
			exchange.respond(ResponseCode.CONTENT, getJsonResponse(), MediaTypeRegistry.APPLICATION_SENML_JSON);
		else
			exchange.respond(ResponseCode.CONTENT, getTextResponse(), MediaTypeRegistry.TEXT_PLAIN);
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
