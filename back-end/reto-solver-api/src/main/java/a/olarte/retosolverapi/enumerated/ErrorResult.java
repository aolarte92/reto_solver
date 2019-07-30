package a.olarte.retosolverapi.enumerated;

public enum ErrorResult {

	EMPTRY_RESULT("%s vacío o null"),
    INVALID_ARGUMENT("%s no válido");
    private String message;
 
    private ErrorResult(String message) {
        this.message = message;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
}
