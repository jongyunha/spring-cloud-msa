package io.jongyun.catalogservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author jongyunha created on 22. 3. 22.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {

    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer stock;

    private Date createdAt;
}
