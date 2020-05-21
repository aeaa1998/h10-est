import java.util.HashMap;

public class City {
    private String name;
//    private int weight;
    private HashMap<String, Route> routes = new HashMap<>();

    public City(String name) {
        this.name = name;
//        this.weight = weight;
    }

    public void addRoute(String cityName, Route route){
        this.routes.put(cityName, route);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Route> getRoutes() {
        return routes;
    }

    public void setRoutes(HashMap<String, Route> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return "Ciudad " + name;
    }
}
