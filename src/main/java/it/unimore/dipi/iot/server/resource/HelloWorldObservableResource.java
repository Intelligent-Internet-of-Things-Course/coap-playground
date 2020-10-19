package it.unimore.dipi.iot.server.resource;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class HelloWorldObservableResource extends CoapResource {

	private String temperature;
	private String name;
	
	public HelloWorldObservableResource(String name) {

		super(name);
		this.name = name;
		Random rand = new Random();
		this.temperature = Integer.toString(rand.nextInt(30) + 1);

		setObservable(true); // enable observing
		setObserveType(Type.CON); // configure the notification type to CONs
		getAttributes().setTitle("Hello-World Resource Observable");
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
		exchange.setMaxAge(1); // the Max-Age value should match the update interval
		exchange.respond(this.temperature);
	}
	@Override
	public void handleDELETE(CoapExchange exchange) {
		delete(); // will also call clearAndNotifyObserveRelations(ResponseCode.NOT_FOUND)
		exchange.respond(ResponseCode.DELETED);
	}
	@Override
	public void handlePUT(CoapExchange exchange) {
		exchange.respond(ResponseCode.CHANGED);
		changed(); // notify all observers
	}

	
}
