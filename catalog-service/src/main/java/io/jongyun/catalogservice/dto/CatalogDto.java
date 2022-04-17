package io.jongyun.catalogservice.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jongyunha created on 22. 3. 22.
 */
@Data
public class CatalogDto implements Serializable {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
