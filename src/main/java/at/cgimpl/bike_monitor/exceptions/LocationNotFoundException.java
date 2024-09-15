package at.cgimpl.bike_monitor.exceptions;

public class LocationNotFoundException extends RuntimeException {

    public LocationNotFoundException() {
        super("The specified location does not exist");
    }

}
