package example;

public class Car {
    private int price, year, batch;
    private double run;
    private String brand, condition, color, id;

    public Car(){
        this.price=this.batch=this.year=0;
        this.run=0;
        this.brand=this.color=this.condition=this.id="";
    }

    public Car(int price, int year, int batch, double run, String brand, String condition, String color, String id){
        this.price=price;
        this.year=year;
        this.batch=batch;
        this.run=run;
        this.brand=brand;
        this.condition=condition;
        this.color=color;
        this.id=id;
    }

    public void setPrice(int price)
    {
        this.price=price;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRun(double run) {
        this.run = run;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public String getId() {
        return id;
    }

    public String getCondition() {
        return condition;
    }

    public String getColor() {
        return color;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public double getRun() {
        return run;
    }

    public int getBatch() {
        return batch;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Price: "+this.price+" Brand: "+this.brand+" Year of issue: "+this.year+" Vehicle condition: "+this.condition+" Run: "+this.run+" Color: "+this.color+" ID: "+this.id+" Batch: "+this.batch+";";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (!(obj instanceof Car))
            return false;
        Car b = (Car) obj;
        if(price==b.price && brand==b.brand && year==b.year && condition==b.condition && run==b.run && color==b.color && id==b.id && batch==b.batch)
            return true;
        return false;
    }

}