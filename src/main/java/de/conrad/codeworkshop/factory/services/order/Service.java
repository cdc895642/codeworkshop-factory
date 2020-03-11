package de.conrad.codeworkshop.factory.services.order;

import de.conrad.codeworkshop.factory.services.factory.Controller;
import de.conrad.codeworkshop.factory.services.order.api.Order;
import de.conrad.codeworkshop.factory.services.order.api.OrderConfirmation;
import de.conrad.codeworkshop.factory.services.order.api.OrderNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigInteger;
import java.util.Random;
import java.util.Set;

import static de.conrad.codeworkshop.factory.services.order.api.OrderConfirmation.BLANK_ORDER_CONFIRMATION;
import static de.conrad.codeworkshop.factory.services.order.api.OrderStatus.ACCEPTED;
import static de.conrad.codeworkshop.factory.services.order.api.OrderStatus.DECLINED;

/**
 * @author Andreas Hartmann
 */
@org.springframework.stereotype.Service("orderService")
public class Service {

    private final Controller factoryController;
    private final Validator validator;

    @Autowired
    public Service(de.conrad.codeworkshop.factory.services.factory.Controller factoryController,
                   Validator validator) {
        this.factoryController = factoryController;
        this.validator = validator;
    }

    /**
     * Validates the input order. If it is valid, it is enqueued in the factory via the factoryController. Either way an
     * appropriate order confirmation is returned.
     */
    @PostMapping("/create")
    public OrderConfirmation createOrder(final Order order) {
        order.validate();

        //use javax.validation
        Set<ConstraintViolation<Order>> orderValidationSet = validator.validate(order);
        if (orderValidationSet.size() == 0) {
            order.setStatus(ACCEPTED);
        } else {
            order.setStatus(DECLINED);
        }

        final OrderConfirmation orderConfirmation;

        if (order.getStatus() == ACCEPTED) {
            orderConfirmation = new OrderConfirmation(new OrderNumber(BigInteger.valueOf(new Random().nextInt())));

            order.setOrderConfirmation(orderConfirmation);

            factoryController.enqueue(order);
        } else {
            orderConfirmation = BLANK_ORDER_CONFIRMATION;
        }

        return orderConfirmation;
    }
}
