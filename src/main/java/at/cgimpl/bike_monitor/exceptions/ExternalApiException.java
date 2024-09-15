package at.cgimpl.bike_monitor.exceptions;

public class ExternalApiException extends RuntimeException {

    public ExternalApiException() {
        super("An error occurred while fetching data from the external service");
    }

}
