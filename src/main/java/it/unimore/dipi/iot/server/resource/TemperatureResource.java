package it.unimore.dipi.iot.server.resource;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Random;

/**
 * Simple temperature resource represented as double value
 *
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project coap-playground
 * @created 20/10/2020 - 21:54
 */
public class TemperatureResource extends CoapResource {

	private static final int TEMPERATURE_VALUE_BOUND = 30;

	private static final String OBJECT_TITLE = "TemperatureSensor";

	private double temperatureValue = 0.0;

	private Random random;

	public TemperatureResource(String name) {
		super(name);
		init();
	}

	private void init(){
		getAttributes().setTitle(OBJECT_TITLE);
		this.random = new Random();
	}

	private void updateTemperatureValue(){
		this.temperatureValue = this.random.nextInt(TEMPERATURE_VALUE_BOUND) + 1;
	}

	private String getTextResponse(){
		return Double.toString(this.temperatureValue);
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		updateTemperatureValue();
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
