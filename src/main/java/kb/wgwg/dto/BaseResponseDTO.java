package kb.wgwg.dto;

import lombok.Data;

import java.util.Map;

@Data
public class BaseResponseDTO {
    private int status;
    private boolean success;
    private String message;
    private Map<String, Object> data;
}
