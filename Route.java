import java.util.ArrayList;

public class Route {
    private int weight;
    private ArrayList<String> intermediates = new ArrayList<>();


    public Route(City destination, int weight) {
        this.weight = weight;
//        this.intermediates = intermediates;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public ArrayList<String> getIntermediates() {
        return intermediates;
    }

    public void setIntermediates(ArrayList<String> intermediates) {
        this.intermediates = intermediates;
    }

}
