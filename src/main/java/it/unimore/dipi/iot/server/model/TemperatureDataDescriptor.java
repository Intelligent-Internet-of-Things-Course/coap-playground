package it.unimore.dipi.iot.server.model;

/**
 *
 * Demo POJO used to structure a simple Temperature Data Structure
 *
 * @author Marco Picone, Ph.D. - picone.m@gmail.com
 * @project coap-playground
 * @created 20/10/2020 - 21:54
 */
public class TemperatureDataDescriptor {

    private long timestamp;

    private double value;

    public TemperatureDataDescriptor() {
    }

    public TemperatureDataDescriptor(long timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TemperatureData{");
        sb.append("timestamp=").append(timestamp);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
