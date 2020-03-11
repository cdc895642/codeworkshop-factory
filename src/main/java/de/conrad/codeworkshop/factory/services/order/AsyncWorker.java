package de.conrad.codeworkshop.factory.services.order;

import de.conrad.codeworkshop.factory.services.order.api.Order;
import de.conrad.codeworkshop.factory.services.order.api.OrderStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service("asyncService")
public class AsyncWorker {

    private final de.conrad.codeworkshop.factory.services.notification.Service
            notifitionService;

    public AsyncWorker(
            de.conrad.codeworkshop.factory.services.notification.Service notifitionService) {
        this.notifitionService = notifitionService;
    }

    @Async
    public void completeOrders(Queue<Order> orders) {
        Order order = null;
        while ((order = orders.poll()) != null) {
            try {
                order.setStatus(OrderStatus.COMPLETED);
                Thread.sleep(5000);
                notifitionService.notifyCustomer(order);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
