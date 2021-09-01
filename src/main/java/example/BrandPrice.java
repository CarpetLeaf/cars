package example;

public class BrandPrice {
    String brand;
    double avaragePrice;

    public BrandPrice(){
        this.brand="";
        this.avaragePrice=0;
    }

    public BrandPrice(String s, double x){
        this.brand=s;
        this.avaragePrice=x;
    }

    public String getBrand() {
        return brand;
    }

    public double getAvaragePrice() {
        return avaragePrice;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setAvaragePrice(double avaragePrice) {
        this.avaragePrice = avaragePrice;
    }

    @Override
    public String toString(){
        return "Марка: "+brand+" Средняя цена: "+avaragePrice;
    }
}
