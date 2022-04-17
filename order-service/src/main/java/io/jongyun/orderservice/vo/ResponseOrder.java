package io.jongyun.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.Data;

/**
 * @author jongyunha created on 22. 3. 22.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private Date createdAt;
    private String orderId;
}
