package cinema;

import com.fasterxml.jackson.annotation.JsonView;

public class ErrorResponse {

    @JsonView(View.Public.class)
    private String error;

    public ErrorResponse() {

    }
    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}
