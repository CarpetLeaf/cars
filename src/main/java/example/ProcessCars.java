package example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ProcessCars {
    ArrayList<Car> car;

    public ProcessCars(){
        car = new ArrayList<>();
    }

    public ArrayList<Car> idCheck(ArrayList<Car> carList){
        List<Car> carl = carList.stream().sorted(Comparator.comparing(Car::getId)).collect(Collectors.toList());
        car.clear();
        if(carl.get(0).getId().equals(carl.get(1).getId()))
            car.add(carl.get(0));
        if(carl.get(carl.size()-1).getId().equals(carl.get(carl.size()-2).getId()))
            car.add(carl.get(carl.size()-1));
        for(int i = 1; i<carl.size()-1; i++) {
            if (carl.get(i).getId().equals(carl.get(i - 1).getId()) || carl.get(i).getId().equals(carl.get(i + 1).getId()))
                car.add(carl.get(i));
        }
        return car;
    }

    public ArrayList<Car> sameBatch(ArrayList<Car> carList){
        car.clear();
        List<Car> carl = carList.stream().sorted(Comparator.comparing(Car::getBatch)).collect(Collectors.toList());
        if(carl.get(0).getBatch()==carl.get(1).getBatch())
            car.add(carl.get(0));
        if(carl.get(carl.size()-1).getBatch()==carl.get(carl.size()-2).getBatch())
            car.add(carl.get(carl.size()-1));
        for(int i = 1; i<carl.size()-1; i++) {
            if (carl.get(i).getBatch()==carl.get(i - 1).getBatch() || carl.get(i).getBatch()==carl.get(i + 1).getBatch())
                car.add(carl.get(i));
        }
        return car;
    }

    public List<BrandPrice> averagePrice(ArrayList<Car> cur_cars){
        car.clear();
        HashMap<String, ArrayList<Integer>> hashBrand = new HashMap<>();
        ArrayList<BrandPrice> brands = new ArrayList<>();
        ArrayList<Integer> priceList;
        for(Car ob : cur_cars) {
            if (hashBrand.containsKey(ob.getBrand())) {
                hashBrand.get(ob.getBrand()).add(ob.getPrice());
            } else {
                priceList = new ArrayList<>();
                priceList.add(ob.getPrice());
                hashBrand.put(ob.getBrand(), priceList);
            }
        }
        for(String str : hashBrand.keySet())
        {
            double s = hashBrand.get(str).stream().reduce((s1, s2) -> s1+s2).orElse(0);
            brands.add(new BrandPrice(str, s/hashBrand.get(str).size()));
        }
        List<BrandPrice> result_brands=brands.stream().sorted(Comparator.comparing(BrandPrice::getAvaragePrice)).collect(Collectors.toList());
        return  result_brands;
    }

    public ArrayList<TableBrands> brandTable(ArrayList<Car> carList, String cur_brand){
        ArrayList<TableBrands> tableBrands = new ArrayList<>();
        TableBrands tb;
        Set<Integer> years = new HashSet();
        for(Car ob : carList)
            years.add(ob.getYear());
        for(Integer y : years){
            if(carList.stream().filter(o -> o.getBrand().equals(cur_brand)).filter(o -> o.getYear()==y).count()>0) {
                tb = new TableBrands();
                tb.setYear(y);
                tb.setBrand(cur_brand);
                tb.setMaxPrice(carList.stream().filter(o -> o.getYear()==y).filter(o -> o.getBrand().equals(cur_brand)).max(Comparator.comparing(Car::getPrice)).get().getPrice());
                tb.setMinPrice(carList.stream().filter(o -> o.getYear()==y).filter(o -> o.getBrand().equals(cur_brand)).min(Comparator.comparing(Car::getPrice)).get().getPrice());
                tb.setAveragePrice(carList.stream().filter(o -> o.getYear()==y).filter(o -> o.getBrand().equals(cur_brand)).mapToDouble(o -> o.getPrice()).average().getAsDouble());
                tableBrands.add(tb);
            }
        }
        return tableBrands.stream().sorted(Comparator.comparing(TableBrands::getYear)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Car> search_a_car(ArrayList<Car> carList, Set<Integer> set_of_years, String myColors[], String myBrand, String myCondition){
        ArrayList<Car> searchList = new ArrayList<>();
        ArrayList<Car> supportingList;
        for(Integer i : set_of_years)
            for(String cur_color : myColors) {
                supportingList = carList.stream().filter(o -> o.getBrand().equals(myBrand)).filter(o -> o.getCondition().equals(myCondition)).filter(o -> Calendar.getInstance().get(Calendar.YEAR) - o.getYear() == i).filter(o -> o.getColor().equals(cur_color)).collect(Collectors.toCollection(ArrayList::new));
                searchList.addAll(supportingList);
            }
            return searchList;
    }

    public Set<String> colorSet(ArrayList<Car> carList){
        Set<String> set_of_colors = new HashSet<>();
        for(Car ob : carList)
            set_of_colors.add(ob.getColor());
        return set_of_colors;
    }

    public Set<String> brandSet (ArrayList<Car> carList){
        Set<String> set_of_brands = new HashSet<>();
        for(Car ob : carList)
            set_of_brands.add(ob.getBrand());
        return set_of_brands;
    }

    @Override
    public String toString(){
        String s="";
        for(Car ob : this.car)
            s=s+ob+"\n";
        return s;
    }

}
