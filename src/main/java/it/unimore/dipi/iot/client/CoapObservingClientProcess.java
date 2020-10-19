package it.unimore.dipi.iot.client;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.Request;

/**
 * A simple CoAP Synchronous Client implemented using Californium Java Library
 * The client Observe a target resource for 10 Seconds and then cancel the request and ends the execution
 */
public class CoapObservingClientProcess {

    public static void main(String[] args) {

        String targetCoapResourceURL = "coap://localhost:5683/hello-world-obs";

        CoapClient client = new CoapClient(targetCoapResourceURL);

        System.out.println("OBSERVING..");

        Request request = Request.newGet().setURI(targetCoapResourceURL).setObserve();
        request.setMID(8888);
        byte[] token = "a".getBytes();
        request.setToken(token);

        // NOTE:
        // The client.observe(Request, CoapHandler) method visibility has been changed from "private"
        // to "public" in order to get the ability to change the parameter of the observable GET
        //(e.g., to change token and MID).
        CoapObserveRelation relation = client.observe(request, new CoapHandler() {

            public void onLoad(CoapResponse response) {

                String content = response.getResponseText();
                byte[] token = response.advanced().getTokenBytes();
                String MID = Integer.toString(response.advanced().getMID());

                System.out.println("NOTIFICATION: " + content);
                System.out.println("Token: ");

                for (int i=0;i<token.length;i++)
                    System.out.print(token[i] +" ");

                System.out.println("\nMID: " + MID);
            }

            public void onError() {
                System.err.println("OBSERVING FAILED");
            }
        });

        // Observes the coap resource for 10 seconds then the observing relation is deleted
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("CANCELLATION.....");
        relation.proactiveCancel();

    }

}
