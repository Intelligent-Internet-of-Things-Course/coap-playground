package it.unimore.dipi.iot.server;

import it.unimore.dipi.iot.server.resource.HelloWorldObservableResource;
import it.unimore.dipi.iot.server.resource.HelloWorldResource;
import it.unimore.dipi.iot.server.resource.TemperatureResource;
import org.eclipse.californium.core.CoapServer;

public class HelloworldCoapServer extends CoapServer{

	public HelloworldCoapServer(){

		super();

		HelloWorldResource hwRes = new HelloWorldResource("hello-world");
		HelloWorldObservableResource hwResObs = new HelloWorldObservableResource("hello-world-obs");
		TemperatureResource temperatureResource = new TemperatureResource("temperature-sensor");

		System.out.println("Defining and adding resurces...");

		//Add resources ....
		this.add(hwRes);
		this.add(hwResObs);
		this.add(temperatureResource);
	}
}
