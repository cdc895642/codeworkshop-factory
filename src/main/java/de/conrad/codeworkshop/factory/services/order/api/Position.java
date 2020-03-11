package de.conrad.codeworkshop.factory.services.order.api;

import de.conrad.codeworkshop.factory.services.validation.QuantityConstraint;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * @author Andreas Hartmann
 */
public class Position {
    @Min(100_000)
    @Max(999_999_999)
    private Integer productId;
    @QuantityConstraint
    private BigDecimal quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
