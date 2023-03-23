package entities;

public class Order {
    private long orderId;
    private long menuDishId;

    Order(long orderId, long menuDishId) {
        this.orderId = orderId;
        this.menuDishId = menuDishId;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getMenuDishId() {
        return menuDishId;
    }
}
