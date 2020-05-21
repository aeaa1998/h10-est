import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;


public class View {
    Scanner scanner;
    private static View view = null;
    public View(){
        scanner = new Scanner(System.in);
    }
    public void print(String text){
        System.out.println(text);
    }
    public String input(String text){
        this.print(text);
        return scanner.nextLine();
    }

    public static View getView(){
        if(view == null) view = new View();
        return view;
    }

    public <T extends  Iterable<String>, K extends Iterable<ArrayList<String>>> void printTable(T headers, K rows){
        StringBuilder dividerAppender = new StringBuilder();
        dividerAppender.append("-----");
        headers.forEach(header -> {
            dividerAppender.append("-".repeat(String.format("%25s", header.toString()).length()));
            System.out.printf("%25s", header.toString());
        });
        print("");
        print(dividerAppender.toString());
        print("");
        rows.forEach(row -> {
            row.forEach(value -> {
                System.out.printf("%25s", value);
            });
            print("");
        });
        print("\n\n");

    }
    public double doubleInput(String text, String text2, Double minimum){
        Boolean valid = true;
        double value = 0.0;
        while (valid)
        {
            System.out.println(text);
            String valueString = scanner.nextLine();
            try{
                value = Float.parseFloat(valueString);
                valid = value <= 0.0 || value < minimum;
                if (valid == true) {
                    System.out.println(text2);
                }

            }
            catch (Exception e) {
                System.out.println("Ingrese un valor double");
            }
        }
        return value;
    }

    public float floatInput(String text, String text2, Float minimum){
        Boolean valid = true;
        float value = 0f;
        while (valid)
        {
            System.out.println(text);
            String valueString = scanner.nextLine();
            try{
                value = Float.parseFloat(valueString);
                valid = value <= 0f || value < minimum;
                if (valid == true) {
                    System.out.println(text2);
                }
            }
            catch (Exception e) {
                System.out.println("Ingrese un valor float");
            }
        }
        return value;
    }

    public float floatInput(String text){
        Boolean valid = true;
        float value = 0f;
        while (valid)
        {
            System.out.println(text);
            String valueString = scanner.nextLine();
            try{
                value = Float.parseFloat(valueString);
                valid = !valid;
            }
            catch (Exception e) {
                System.out.println("Ingrese un valor float");
            }
        }
        return value;
    }

    public int intInput(String text, String text2, int minimum){
        Boolean valid = true;
        int value = 0;
        while (valid)
        {
            System.out.println(text);
            String valueString = scanner.nextLine();
            try{
                value = Integer.parseInt(valueString);
                valid = value <= 0 || value < minimum;
                if (valid == true) {
                    System.out.println(text2);
                }
            }
            catch (Exception e) {
                System.out.println("Ingrese un valor integer");
            }
        }
        return value;
    }

    public int intInput(String text, String text2, int minimum, int maximun){
        Boolean valid = true;
        int value = 0;
        while (valid)
        {
            System.out.println(text);
            String valueString = scanner.nextLine();
            try{
                value = Integer.parseInt(valueString);
                valid = value <= 0 || value < minimum || value > maximun;
                if (valid == true) {
                    System.out.println(text2);
                }
            }
            catch (Exception e) {
                System.out.println("Ingrese un valor integer");
            }
        }
        return value;
    }
    public int intPositiveInput(String text, String text2){
        Boolean valid = true;
        int value = 0;
        while (valid)
        {
            System.out.println(text);
            String valueString = scanner.nextLine();
            try{
                value = Integer.parseInt(valueString);
                valid = value <= 0;
                if (valid == true) {
                    System.out.println(text2);
                }
            }
            catch (Exception e) {
                System.out.println("Ingrese un valor integer");
            }
        }
        return value;
    }

    public int intPositiveInput(String text, String text2, int _maximum){

        Boolean valid = true;
        int value = 0;
        while (valid)
        {
            System.out.println(text);
            String valueString = scanner.nextLine();
            try{
                value = Integer.parseInt(valueString);
                valid = value <= 0 || value > _maximum;
                if (valid == true) {
                    System.out.println(text2);
                }
            }
            catch (Exception e) {
                System.out.println("Ingrese un valor integer");
            }
        }
        return value;
    }

    public int intInput(Scanner scanner, String text){
        Boolean valid = true;
        int value = 0;
        while (valid)
        {
            System.out.println(text);
            String valueString = scanner.nextLine();
            try{
                value = Integer.parseInt(valueString);
                valid = !valid;
            }
            catch (Exception e) {
                System.out.println("Ingrese un valor integer");
            }
        }
        return value;
    }

    public String selectOptions(ArrayList<String> arrayList){
        String input = "";
        while (!arrayList.contains(input)){
            System.out.println("Ingrese una de las opciones\n");
            for (int i = 0; i < arrayList.size(); i++) {
                System.out.println((i+1) + ") " + arrayList.get(i) + "\n");
            }
            input = scanner.nextLine();
            if (!arrayList.contains(input)){
                System.out.println("Ingrese una opcion valida\n");
            }
        }
        return input;
    }
    public String selectOptions(ArrayList<String> arrayList, String text){
        String input = "";
        while (!arrayList.contains(input)){
            System.out.println(text);
            for (int i = 0; i < arrayList.size(); i++) {
                System.out.println((i+1) + ") " + arrayList.get(i) + "\n");
            }
            input = scanner.nextLine();
            if (!arrayList.contains(input)){
                System.out.println("Ingrese una opcion valida\n");
            }
        }
        return input;
    }
    public int selectOptions(ArrayList<?> arrayList, String text, String text2){
        int input = 0;
        while (input < 1 || input > arrayList.size()){
            for (int i = 0; i < arrayList.size(); i++) {
                System.out.println((i+1) + ") " + arrayList.get(i).toString() + "\n");
            }
            input = this.intInput(text, text2, 0);
            if (input < 1 || input > arrayList.size()){
                System.out.println("Ingrese una opcion valida\n");
            }
        }
        return input - 1;
    }

    public String selectOptions(String[] texts, String textsDisplay){
        Scanner scanner = new Scanner(System.in);
        Boolean valid = true;
        String value = "";
        while (valid)
        {
            System.out.println("Ingrese una opcion valida de texto\n" + textsDisplay +":\n");
            for (int i = 0; i < texts.length; i++) {
                String x = texts[i];
                System.out.print((i+1) + ". " + x + "\n");
            }
            value = scanner.nextLine();
            value = value.trim();
            valid = !Arrays.asList(texts).contains(value);
            if (valid) {
                System.out.println("Ingreso una opcion invalida.\n");
            }
        }
        return value;
    }

    public <K> String selectKey(Map<String, K> map){
        boolean invalid = true;

        ArrayList<String> ids = new ArrayList<String>();
        final int[] i = {0};
        View.getView().print("Ingrese uno de los siguientes valores");
        map.forEach((id, object) -> {
            System.out.print(id + " \n");
            ids.add(id);
            i[0]++;
        });
        String selected = null;
        while (invalid){
            selected = this.input("Ingrese la opcion que desee escojer");
            invalid = !ids.contains(selected);
        }

        return selected;
    }

    public <K> K selectObject(Map<String, K> map){
        boolean invalid = true;

        ArrayList<String> ids = new ArrayList<String>();
        final int[] i = {0};
        map.forEach((id, object) -> {
            System.out.print(id + ". " + object.toString() + "\n");
            ids.add(id);
            i[0]++;
        });
        String selected = null;
        while (invalid){
            selected = this.input("Ingrese la opcion que desee escojer");
            invalid = !ids.contains(selected);
        }

        return map.get(selected);
    }


//    public Class<? extends Class> filter(Map<Integer, Class<? extends Class>> map){
//
//    }

}