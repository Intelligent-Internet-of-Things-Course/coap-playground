package it.unimore.dipi.iot.client;

import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple CoAP Synchronous Client implemented using Californium Java Library
 * The client Observe a target resource for 10 Seconds and then cancel the request and ends the execution
 *
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project coap-playground
 * @created 20/10/2020 - 21:54
 */
public class CoapObservingClientProcess {

    private final static Logger logger = LoggerFactory.getLogger(CoapObservingClientProcess.class);

    public static void main(String[] args) {

        String targetCoapResourceURL = "coap://localhost:5683/temperature-sensor-obs";

        CoapClient client = new CoapClient(targetCoapResourceURL);

        logger.info("OBSERVING ... {}", targetCoapResourceURL);

        Request request = Request.newGet().setURI(targetCoapResourceURL).setObserve();
        request.setConfirmable(true);

        // NOTE:
        // The client.observe(Request, CoapHandler) method visibility has been changed from "private"
        // to "public" in order to get the ability to change the parameter of the observable GET
        //(e.g., to change token and MID).
        CoapObserveRelation relation = client.observe(request, new CoapHandler() {

            public void onLoad(CoapResponse response) {
                String content = response.getResponseText();
                logger.info("Notification Response Pretty Print: \n{}", Utils.prettyPrint(response));
                logger.info("NOTIFICATION Body: " + content);
            }

            public void onError() {
                logger.error("OBSERVING FAILED");
            }
        });

        // Observes the coap resource for 10 seconds then the observing relation is deleted
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.error("CANCELLATION.....");
        relation.proactiveCancel();

    }

}
