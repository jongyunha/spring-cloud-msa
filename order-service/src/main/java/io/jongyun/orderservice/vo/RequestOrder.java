package io.jongyun.orderservice.vo;

import lombok.Data;

/**
 * @author jongyunha created on 22. 4. 14.
 */
@Data
public class RequestOrder {

    private String productId;

    private Integer qty;

    private Integer unitPrice;

}
