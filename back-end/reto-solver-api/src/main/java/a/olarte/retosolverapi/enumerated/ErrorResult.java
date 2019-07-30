package a.olarte.retosolverapi.enumerated;

public enum ErrorResult {

	NULL_RESULT("%s con valor null"),
	EMPTY_RESULT("%s con valor vacío"),
    INVALID_ARGUMENT("%s no válido"),
    INVALID_LENGTH("%s con valor demasiado largo"),
    ;
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
