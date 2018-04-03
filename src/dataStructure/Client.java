package dataStructure;

import static java.util.stream.Collectors.joining;

public class Client {

    private int id;
    private int x;
    private int y;
    private int quantity;

    public Client(int id, int x, int y, int quantity) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.quantity = quantity;
    }

    public Client(Client clientSource) {
        this.id = clientSource.getId();
        this.x = clientSource.getX();
        this.y = clientSource.getY();
        this.quantity = clientSource.getQuantity();
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.id)
                .append(" (")
                .append(this.quantity)
                .append(")");

        return stringBuilder.toString();

    }
}
