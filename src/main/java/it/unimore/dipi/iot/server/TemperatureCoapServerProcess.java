package it.unimore.dipi.iot.server;

import it.unimore.dipi.iot.server.resource.JsonTemperatureResource;
import it.unimore.dipi.iot.server.resource.TemperatureObservableResource;
import it.unimore.dipi.iot.server.resource.TemperatureResource;
import org.eclipse.californium.core.CoapServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Demo Temperature CoAP Smart Object hosting 3 different resources:
 *
 * - basic temperature sensor resource with a random double value
 * - basic observable temperature sensor resource with a random double value (updated every 1 sec)
 * - basic temperature sensor resource with a random double value adapting Content-Type according to Request Accept Option
 *
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project coap-playground
 * @created 20/10/2020 - 21:54
 */
public class TemperatureCoapServerProcess extends CoapServer{

	private final static Logger logger = LoggerFactory.getLogger(TemperatureCoapServerProcess.class);

	public TemperatureCoapServerProcess(){

		super();

		//Basic Temperature Resource
		TemperatureResource temperatureResource = new TemperatureResource("temperature-sensor");

		//Basic Observable Temperature Resource
		TemperatureObservableResource temperatureObservableResource = new TemperatureObservableResource("temperature-sensor-obs");

		//Json Format Basic Temperature Resource
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
