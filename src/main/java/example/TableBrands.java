package example;

public class TableBrands {
    private int minPrice, maxPrice, year;
    private double averagePrice;
    private String brand;

    public TableBrands(){
        averagePrice=minPrice=maxPrice=year=0;
        brand="";
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public String toString(){
        return String.format("%-18d%-17d%-13.2f%-4d", maxPrice, minPrice, averagePrice, year);
    }
}
