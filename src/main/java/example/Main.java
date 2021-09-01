package example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25));
        String s;
        Car car = new Car();
        ArrayList<Car> carList = new ArrayList<>();
        Scanner sc = new Scanner(new File("data_auto.txt"));
        FileWriter writer = new FileWriter("out.txt", true);
        while (sc.hasNext()) {
            s = sc.nextLine();
            car.setPrice(Integer.parseInt(sc.nextLine()));
            car.setBrand(sc.nextLine());
            s = sc.nextLine();
            car.setYear(Integer.parseInt(sc.nextLine()));
            car.setCondition(sc.nextLine());
            car.setRun(Double.parseDouble(sc.nextLine()));
            car.setColor(sc.nextLine());
            car.setId(sc.nextLine());
            car.setBatch(Integer.parseInt(sc.nextLine()));
            carList.add(car);
            car = new Car();
        }
        Font fontBig = Font.font("Tahoma", FontWeight.NORMAL, 26);
        Button btn1 = new Button("Show bar graph of colors");
        btn1.setFont(fontBig);
        btn1.setOnAction(event -> {
            Stage secondStage = new Stage();
            primaryStage.setTitle("Colors");
            ProcessCars result = new ProcessCars();
            TabPane root2 = new TabPane();
            Tab tabBarChart;
            Set<String> colorResult = result.colorSet(carList);
            for (String str : colorResult) {
                try {
                    tabBarChart = new Tab(str, createBarChart(str, carList));
                    root2.getTabs().add((tabBarChart));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            secondStage.setScene(new Scene(root2, 900, 600));
            secondStage.show();
        });
        Button btn2 = new Button("Go to search a car");
        btn2.setFont(fontBig);
        btn2.setOnAction(event -> {
            Stage thirdStage = new Stage();
            GridPane root3 = new GridPane();
            root3.setAlignment(Pos.CENTER);
            root3.setHgap(10);
            root3.setVgap(10);
            root3.setPadding(new Insets(25));
            Text scenetitle = new Text("Search a car");
            scenetitle.setFont(fontBig);
            TextArea textArea = new TextArea();
            textArea.setPrefColumnCount(15);
            textArea.setPrefRowCount(5);
            root3.add(textArea, 0, 7);
            Label brand = new Label("Enter a car brand: ");
            brand.setFont(fontBig);
            root3.add(brand, 0, 1);
            Label condition = new Label("Enter the technical condition of the vehicle (\"clean vehicle\" or \"salvage insurance\"): ");
            condition.setFont(fontBig);
            root3.add(condition, 0, 2);
            Label age = new Label("Enter age or range of years (after \"-\"): ");
            age.setFont(fontBig);
            root3.add(age, 0, 3);
            Label color = new Label("Enter a color or several color options separated by a space: ");
            color.setFont(fontBig);
            root3.add(color, 0, 4);
            TextField brandTextField = new TextField();
            brandTextField.setFont(fontBig);
            root3.add(brandTextField, 1, 1);
            ObservableList<String> options = FXCollections.observableArrayList("clean vehicle", "salvage insurance");
            ComboBox conditionField = new ComboBox(options);
            conditionField.setValue("clean vehicle");
            conditionField.setEditable(false);
            root3.add(conditionField, 1, 2);
            TextField ageTextField = new TextField();
            ageTextField.setFont(fontBig);
            root3.add(ageTextField, 1, 3);
            TextField colorTextField = new TextField();
            colorTextField.setFont(fontBig);
            root3.add(colorTextField, 1, 4);
            Label sortLabel = new Label("Sorted by:");
            sortLabel.setFont(fontBig);
            root3.add(sortLabel, 0, 5);
            Label resultLabel = new Label("Searching results");
            resultLabel.setFont(fontBig);
            root3.add(resultLabel, 0, 8);
            ObservableList<String> sort = FXCollections.observableArrayList("Ascending price", "Descending price", "Ascending run", "Descending run");
            ComboBox sortField = new ComboBox(sort);
            sortField.setValue("Ascending price");
            sortField.setEditable(false);
            root3.add(sortField, 1, 5);
            Button btn_search = new Button("Find a car");
            btn_search.setOnAction(event1 -> {
                ArrayList<Car> searchList = new ArrayList<>();
                String myBrand = brandTextField.getText();
                String myCondition = conditionField.getValue().toString();
                String myAge = ageTextField.getText();
                Set<Integer> set_of_years = new HashSet<>();
                if (myAge.contains("-") && myAge.matches("^[0-9]+\\W[0-9]+$")) {
                    String reg[] = myAge.split("-");
                    int max, min;
                    min = Integer.parseInt(reg[0]);
                    max = Integer.parseInt(reg[1]);
                    for (int i = min; i <= max; i++)
                        set_of_years.add(i);
                    } else if (myAge.matches("^[0-9]+$"))
                        set_of_years.add(Integer.parseInt(myAge));
                else {
                    System.out.println("Ошибка ввода возраста");
                    return;
                }
                String someColors = colorTextField.getText();
                String myColors[] = someColors.trim().split(" ");
                ProcessCars pr_cars = new ProcessCars();
                searchList = pr_cars.search_a_car(carList, set_of_years, myColors, myBrand, myCondition);
                ArrayList<Car> resultSearch;
                String sortString = sortField.getValue().toString();
                if (sortString.equals("Ascending price"))
                    resultSearch = searchList.stream().sorted(Comparator.comparing(Car::getPrice)).collect(Collectors.toCollection(ArrayList::new));
                else if (sortString.equals("Descending price"))
                    resultSearch = searchList.stream().sorted(Comparator.comparing(Car::getPrice).reversed()).collect(Collectors.toCollection(ArrayList::new));
                else if (sortString.equals("Ascending run"))
                    resultSearch = searchList.stream().sorted(Comparator.comparing(Car::getRun)).collect(Collectors.toCollection(ArrayList::new));
                else
                    resultSearch = searchList.stream().sorted(Comparator.comparing(Car::getRun).reversed()).collect(Collectors.toCollection(ArrayList::new));
                if (resultSearch.size() > 0) {
                    System.out.println("Подходящие машины:");
                    String result = "";
                    for (Car ob : resultSearch) {
                        System.out.println(ob);
                        result = result + ob + "\n";
                        textArea.setText(result);
                    }
                } else {
                    System.out.println("По вашим запросам автомобилей не нашллось");
//                    try {
//                        writer.write("По вашим запросам автомобилей не нашллось\n");
//                    }
//                    catch (IOException ex){
//                        ex.getMessage();
//                        return;
//                    }
                    textArea.setText("No cars were found for your requests\n");
                }
            });
            root3.add(btn_search, 0, 6);
            thirdStage.setScene(new Scene(root3, 1400, 600));
            thirdStage.show();
        });
        root.add(btn1, 0, 1);
        root.add(btn2, 0, 2);
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
}

    private Group createBarChart(String clr, ArrayList<Car> carList) throws FileNotFoundException {
        ProcessCars result = new ProcessCars();
        Set<String> brandResult = result.brandSet(carList);
        Group groupChart = new Group();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setMinHeight(600);
        bc.setMinWidth(800);
        bc.setTitle("Number of cars of color: "+clr);
        xAxis.setLabel("Cars");
        yAxis.setLabel("Count");

        XYChart.Series series1;
            series1 = new XYChart.Series();
            series1.setName(clr);
            for(String ob1 : brandResult){
                long k = carList.stream().filter(o -> o.getColor().equals(clr)).filter(o -> o.getBrand().equals(ob1)).count();
                if (k>0)
                    series1.getData().add(new XYChart.Data(ob1, k));
            }
            bc.getData().add(series1);

        groupChart.getChildren().add(bc);
        return groupChart;
    }

    public static void main(String[] args){
        ArrayList<Car> carList = new ArrayList<>();
        ProcessCars result = new ProcessCars();
        Car car = new Car();
        try {
            String s;
            Scanner sc = new Scanner(new File("data_auto.txt"));
            FileWriter writer = new FileWriter("out.txt", true);
            while (sc.hasNext()) {
                s = sc.nextLine();
                car.setPrice(Integer.parseInt(sc.nextLine()));
                car.setBrand(sc.nextLine());
                s = sc.nextLine();
                car.setYear(Integer.parseInt(sc.nextLine()));
                car.setCondition(sc.nextLine());
                car.setRun(Double.parseDouble(sc.nextLine()));
                car.setColor(sc.nextLine());
                car.setId(sc.nextLine());
                car.setBatch(Integer.parseInt(sc.nextLine()));
                carList.add(car);
                car = new Car();
            }
            sc.close();
            if(result.idCheck(carList).size()>0) {
                System.out.println("Машины c повторяющимися ID: \n" + result);
                writer.write("Машины c повторяющимися ID: \n" + result + "\n");
            }
            else{
                System.out.println("Все ID уникальны");
                writer.write("Все ID уникальны\n");
            }

            if(result.sameBatch(carList).size()>1) {
                System.out.println("Машины из одной партии:\n" + result);
                writer.write("Машины из одной партии:\n" + result + "\n");
            }
            else{
                System.out.println("Машин из одной партии нет");
                writer.write("Машин из одной партии нет\n");
            }

            System.out.println("Список марок, отсортированный по их средней цене:");
            writer.write("Список марок, отсортированный по их средней цене:\n");
            for(BrandPrice ob : result.averagePrice(carList)) {
                System.out.println(ob);
                writer.write(ob+"\n");
            }
            Set<String> brands = new HashSet<>();
            for(Car ob : carList)
                brands.add(ob.getBrand());
            System.out.format("%-18s%-17s%-13s%-4s\n", "Максимальная цена ", "Минимальная цена ", "Средняя цена ", "Год");
            writer.write("Максимальная цена | Минимальная цена | Средняя цена | Год\n");
            for(String cur_brand : brands){
                System.out.println(cur_brand);
                writer.write(cur_brand+"\n");
                for(TableBrands ob : result.brandTable(carList, cur_brand)) {
                    System.out.println(ob);
                    writer.write(ob+"\n");
                }
            }
            writer.close();
            launch();
        }catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
            return;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }
    }
}