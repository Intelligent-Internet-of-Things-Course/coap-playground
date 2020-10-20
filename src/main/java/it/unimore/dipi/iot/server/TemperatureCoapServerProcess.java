package it.unimore.dipi.iot.server;

import it.unimore.dipi.iot.server.resource.JsonTemperatureResource;
import it.unimore.dipi.iot.server.resource.TemperatureObservableResource;
import it.unimore.dipi.iot.server.resource.TemperatureResource;
import org.eclipse.californium.core.CoapServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemperatureCoapServerProcess extends CoapServer{

	private final static Logger logger = LoggerFactory.getLogger(TemperatureCoapServerProcess.class);

	public TemperatureCoapServerProcess(){

		super();

		TemperatureObservableResource temperatureObservableResource = new TemperatureObservableResource("temperature-sensor-obs");
		TemperatureResource temperatureResource = new TemperatureResource("temperature-sensor");
		JsonTemperatureResource jsonTemperatureResource = new JsonTemperatureResource("json-temperature-sensor");
		System.out.println("Defining and adding resurces...");

		//Add resources ....
		this.add(temperatureObservableResource);
		this.add(temperatureResource);
		this.add(jsonTemperatureResource);
	}

	public static void main(String[] args) {

		TemperatureCoapServerProcess demoCoapServerProcess = new TemperatureCoapServerProcess();

		logger.info("Starting Coap Server...");

		demoCoapServerProcess.start();

		logger.info("Coap Server Started ! Available resources: ");

		demoCoapServerProcess.getRoot().getChildren().stream().forEach(resource -> {
			logger.info("Resource {} -> URI: {} (Observable: {})", resource.getName(), resource.getURI(), resource.isObservable());
		});
	}
}
