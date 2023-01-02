package com.rezztoran.rezztoranbe.response;

import com.rezztoran.rezztoranbe.enums.ResponseDataKey;
import com.rezztoran.rezztoranbe.enums.ReturnType;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    private final HttpStatus status;
    private final Map<String, Object> result;

    public ResponseBuilder(HttpStatus status, ReturnType returnType) {
        result = new HashMap<>();
        this.status = status;
        result.put("code", returnType.getCode());
        result.put("message", returnType.getMessage());
        result.put("isPaginated", false);
    }

    public ResponseBuilder withData(ResponseDataKey responseDataKey, Object object) {
        result.put(responseDataKey.getKey(), object);
        return this;
    }

    public ResponseBuilder withPaginatedData(Object objectList) {
        result.put("isPaginated", true);
        result.put(ResponseDataKey.CONTENT.getKey(), objectList);
        return this;
    }


    public ResponseBuilder withError(Exception e) {
        result.put(ResponseDataKey.ERROR.getKey(), e);
        return this;
    }

    public ResponseBuilder withError(String error) {
        result.put(ResponseDataKey.ERROR.getKey(), error);
        return this;
    }

    public ResponseBuilder withError(Object obj) {
        result.put(ResponseDataKey.ERROR.getKey(), obj);
        return this;
    }


    public ResponseEntity<Map<String, Object>> build() {
        return new ResponseEntity<>(result, status);
    }

}
