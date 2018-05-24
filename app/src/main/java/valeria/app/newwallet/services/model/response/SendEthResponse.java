package valeria.app.newwallet.services.model.response;

public class SendEthResponse {

    private String hash;
    private String value;

    public SendEthResponse(String hash, String value) {
        this.hash = hash;
        this.value = value;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
