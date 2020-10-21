package it.unimore.dipi.iot.server.resource;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple temperature Observable resource represented as double value
 *
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project coap-playground
 * @created 20/10/2020 - 21:54
 */
public class TemperatureObservableResource extends CoapResource {

	private String temperature;

	private String name;
	
	public TemperatureObservableResource(String name) {

		super(name);
		this.name = name;
		Random rand = new Random();
		this.temperature = Integer.toString(rand.nextInt(30) + 1);

		setObservable(true); // enable observing
		setObserveType(Type.CON); // configure the notification type to CONs

		getAttributes().setTitle("Temperature Observable Resource");
		getAttributes().setObservable(); // mark observable in the Link-Format

		// schedule a periodic update task, otherwise let events call changed()
		Timer timer = new Timer();
		timer.schedule(new UpdateTask(), 0, 1000);
	}

	private class UpdateTask extends TimerTask {
		@Override
		public void run() {
			// .. periodic update of the resource
			Random rand = new Random();
			temperature = Integer.toString(rand.nextInt(30) + 1);
			changed(); // notify all observers 
		}
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		// the Max-Age value should match the update interval
		exchange.setMaxAge(1);
		exchange.respond(this.temperature);
	}
	@Override
	public void handleDELETE(CoapExchange exchange) {
		super.handleDELETE(exchange);
	}
	@Override
	public void handlePUT(CoapExchange exchange) {
		super.handlePUT(exchange);
	}

	
}
