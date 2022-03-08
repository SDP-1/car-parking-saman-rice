package module;

public class DeliveryVehicle {
    public String number;
    public String type;
    public int maximumWeight;
    public int numberOfPassengers;

    public DeliveryVehicle(String number, String type, int maximumWeight, int numberOfPassengers) {
        this.number = number;
        this.type = type;
        this.maximumWeight = maximumWeight;
        this.numberOfPassengers = numberOfPassengers;
    }

    public DeliveryVehicle() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaximumWeight() {
        return maximumWeight;
    }

    public void setMaximumWeight(int maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}
