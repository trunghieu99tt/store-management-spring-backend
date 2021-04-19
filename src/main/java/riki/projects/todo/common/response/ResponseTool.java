package riki.projects.todo.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import riki.projects.todo.common.response.model.APIPagingResponse;
import riki.projects.todo.common.response.model.APIResponse;

import java.util.List;

public class ResponseTool {
    public static ResponseEntity<APIResponse> POST_OK(Object data) {
        return new ResponseEntity<APIResponse>(new APIResponse(201, "Created", data), HttpStatus.CREATED);
    }

    public static ResponseEntity<APIResponse> DELETE_OK() {
        return new ResponseEntity<APIResponse>(new APIResponse(200, "Deleted", null), HttpStatus.OK);
    }

    public static ResponseEntity<APIResponse> GET_OK(Object data) {
        return new ResponseEntity<APIResponse>(new APIResponse(200, "Get ok", data), HttpStatus.OK);
    }

    public static ResponseEntity<APIPagingResponse> GET_OK(List<Object> data, int total) {
        return new ResponseEntity<APIPagingResponse>(new APIPagingResponse(200, "Get OK", data, total), HttpStatus.OK);
    }

    public static ResponseEntity<APIResponse> PUT_OK(Object data) {
        return new ResponseEntity<APIResponse>(new APIResponse(200, "Put", data), HttpStatus.OK);
    }
}
