package it.unimore.dipi.iot.server;

import it.unimore.dipi.iot.server.resource.StringDemoResource;
import org.eclipse.californium.core.CoapServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo String CoAP Smart Object hosting 1 string as editable resource.
 * Supports GET, POST and PUT to read and update the memorized value.
 *
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project coap-playground
 * @created 20/10/2020 - 21:54
 */
public class StringDemoCoapServerProcess extends CoapServer{

	private final static Logger logger = LoggerFactory.getLogger(StringDemoResource.class);

	public StringDemoCoapServerProcess(){

		super();
		StringDemoResource stringDemoResource = new StringDemoResource("demo");
		//Add resources ....
		this.add(stringDemoResource);
	}

	public static void main(String[] args) {

		StringDemoCoapServerProcess demoCoapServerProcess = new StringDemoCoapServerProcess();

		logger.info("Starting Coap Server...");

		demoCoapServerProcess.start();

		logger.info("Coap Server Started ! Available resources: ");
		
		demoCoapServerProcess.getRoot().getChildren().stream().forEach(resource -> {
			logger.info("Resource {} -> URI: {}", resource.getName(), resource.getURI());
		});
	}
}
