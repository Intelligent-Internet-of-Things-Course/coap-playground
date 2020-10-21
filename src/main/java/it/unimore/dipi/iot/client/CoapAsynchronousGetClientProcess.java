package it.unimore.dipi.iot.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;


/**
 * A simple CoAP Asynchronous Client implemented using Californium Java Library
 * The simple client send a GET request to a target CoAP Resource with some custom request parameters
 *
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project coap-playground
 * @created 20/10/2020 - 09:19
 */
public class CoapAsynchronousGetClientProcess {

	private final static Logger logger = LoggerFactory.getLogger(CoapAsynchronousGetClientProcess.class);

	private static final String COAP_ENDPOINT = "coap://127.0.0.1:5683/demo";

	public static void main(String[] args) {

		CoapClient coapClient = new CoapClient(COAP_ENDPOINT);
		
		Request request = new Request(Code.GET);
		request.setConfirmable(true);

		//Semaphore used in order to wait for the response of the server before ending 
		//the java process
		final Semaphore semaphore = new Semaphore(0);
		
		CoapHandler handler = new CoapHandler() {

			public void onLoad(CoapResponse coapResp) {

				logger.info("PrettyPrint Response: \n{}", Utils.prettyPrint(coapResp));
				semaphore.release();
			}

			public void onError() {
				logger.error("Failed");
			}
		};

		coapClient.advanced(handler, request);

		// wait until all requests finished
		try {
			semaphore.acquire(1);
		} catch (InterruptedException e) {}
		
	}

}
