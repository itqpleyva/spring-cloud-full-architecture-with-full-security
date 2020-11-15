package Main.Models;

public class ValidationRequest {

    private String token;
    private String method;
    private String path;

    public ValidationRequest(String token, String method, String path) {
        this.token = token;
        this.method = method;
        this.path = path;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public ValidationRequest() {
    }

}
    