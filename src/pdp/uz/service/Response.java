package pdp.uz.service;

public class Response {
    private String message;
    public Response(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return
                "message='" + message + '\'';
    }
}
