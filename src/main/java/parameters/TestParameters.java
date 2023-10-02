package parameters;

public class TestParameters {
    private String manufacturer;
    private double price;
    private int expectedItemCount;

    public TestParameters(String manufacturer, double price) {
        this.manufacturer = manufacturer;
        this.price = price;
        this.expectedItemCount = expectedItemCount;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getExpectedItemCount() {
        return expectedItemCount;
    }

    public void setExpectedItemCount(int expectedItemCount) {
        this.expectedItemCount = expectedItemCount;
    }
}