import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    private HashMap<String, City> cities = new HashMap<>();
    private HashMap<String, City> originalCities = new HashMap<>();
    private ArrayList<String> options = new ArrayList<>(){{
        add("Distancia entre dos  ciudades");
        add("Ciudad que es centro del grafo");
        add("Agregar trafico");
        add("Agregar distancia entre ciudades");
        add("Borrar conexión");
        add("Salir");
    }};
    //From to
    private HashMap<HashMap<String, String>, Integer> citiesMap = new HashMap<>();
    public void init(){
        fillCities("guategrafo.txt");
        applyPattern();
        boolean running = true;
        printTable();
        while (running){
            switch (View.getView().selectOptions(options, "Seleccione una  de las  siguientes opciones",
                    "Ingrese un numero de opcion valido")){
                case 0:
                    ArrayList<String> origins = new ArrayList<String>(cities.keySet());
                    int origin  = View.getView().selectOptions(origins, "Seleccione el numero de ciudad de origen", "Seleccione un numero valido");
                    ArrayList<String> dests = new ArrayList<String>(cities.get(origins.get(origin)).getRoutes().keySet());
                    int destiny = View.getView().selectOptions(dests, "Seleccione la ciudad con que conecta que tiene trafico en numeros",
                            "Ingrese una opcion valida");
                    Route routeD = cities.get(origins.get(origin)).getRoutes().get(dests.get(destiny));
                    View.getView().print("Ciudad de la que partio: " + origins.get(origin));
                    if(routeD.getIntermediates().size() > 0){
                        View.getView().print("Ciudades por las que paso: ");
                        routeD.getIntermediates().forEach(intermediate -> View.getView().print(intermediate));
                    }
                    View.getView().print("Ciudad a la que llego: " + dests.get(destiny));
                    View.getView().print("Peso del viaje: " + String.valueOf(routeD.getWeight()));
                    break;
                case 1:
                    getCenter();
                    break;
                case 2:
                    addTrafficToCity();
                    View.getView().print("Nuevo mapa");
                    printTable();
                    break;
                case 3:
                    addNewDistance();
                    View.getView().print("Nuevo mapa");
                    printTable();
                    break;
                case 4:
                    deleteArc();
                    View.getView().print("Nuevo mapa");
                    printTable();
                    break;
                default:
                    running = false;
                    View.getView().print("Gracias por usar el programa");

            }
        }


    }

    public void getCenter(){
        HashMap<String, ArrayList<Integer>> distancesMapped = new HashMap<>();
        HashMap<String, Integer> distancesMin = new HashMap<>();
        City[] center = new City[1];
        int[] currentMin = {Integer.MAX_VALUE};
        cities.forEach((cityNameToEval, citytoEval)-> {
            distancesMapped.put(cityNameToEval, new ArrayList<>());
            distancesMin.put(cityNameToEval, Integer.MAX_VALUE);
            cities.forEach((cityName, city)->{
                if(city.getRoutes().containsKey(cityNameToEval)){
                    int w = city.getRoutes().get(cityNameToEval).getWeight();
                    distancesMapped.get(cityNameToEval).add(w);
                }
            });
        });
        distancesMapped.forEach((cityName, listOfNumbers)-> {
            listOfNumbers.forEach(n -> {
                if(distancesMin.get(cityName) > n){
                    distancesMin.put(cityName, n);
                }
            });
        });
        distancesMin.forEach((cityName, min)->{
            if(currentMin[0] > min && min != Integer.MAX_VALUE){
                currentMin[0] = min;
                center[0] = cities.get(cityName);
            }
        });
        View.getView().print("El actual centro del grafo es: " + center[0].getName());
        View.getView().print("Con excentrecidad: " + String.valueOf(currentMin[0]));
    }


    public void fillCities(String fileName){
        try {
            var mainPath = Controller.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            if (getOsName().startsWith("Windows")){
                if(String.valueOf(mainPath.charAt(0)).equals("/")) { mainPath = mainPath.substring(1, mainPath.length());}
            }
            System.out.println(mainPath + fileName);
            List<String> strings = Files.readAllLines(Path.of(mainPath + fileName));
            for (String line:
                    strings) {
                var holder = new ArrayList<String>(){{ addAll(List.of(line.split(" ")));}};
                var originCity = holder.get(0).toLowerCase();
                var destCity = holder.get(1).toLowerCase();
                var weight = holder.get(2);
                if(!cities.containsKey(originCity)){
                    cities.put(originCity, new City(originCity));
                    originalCities.put(originCity, new City(originCity));
                }
                if(!cities.containsKey(destCity)){
                    cities.put(destCity, new City(destCity));
                    originalCities.put(destCity, new City(destCity));
                }
                Route r = new Route(cities.get(destCity), Integer.parseInt(weight));
                cities.get(originCity).addRoute(destCity, r);
                originalCities.get(originCity).addRoute(destCity, r);
            }
        } catch(URISyntaxException | IOException e){
            System.out.println("Revise bien que su archivo txt exista");
        }
    }

    public void addNewDistance(){
        String origin  = View.getView().input("Ingrese la ciudad de origen").toLowerCase();
        String destiny = "";
        boolean invalid = true;
        while (invalid){
            destiny = View.getView().input("Ingrese la ciudad de destino").toLowerCase();
            invalid = destiny.equals(origin);
            if(invalid){
                View.getView().print("No se puede agregar una distancia hacia la misma ciudad");
            }
        }

        int distance = View.getView().intInput("Ingrese la distancia entre dichas ciudades",
                "Ingrese un numero valido", 1);
        if(!originalCities.containsKey(origin)){
            originalCities.put(origin, new City(origin));
        }
        if(!originalCities.containsKey(destiny)){
            originalCities.put(destiny, new City(destiny));
        }
        Route r = new Route(cities.get(destiny), distance);
        originalCities.get(origin).addRoute(destiny, r);
        cities.clear();
        citiesMap.clear();
        HashMap<String, City> holder = new HashMap<>();

        originalCities.forEach((s,c)-> {
            City holderCity = new City(s.toLowerCase());
            c.getRoutes().forEach((de, re) -> {
                holderCity.addRoute(de, re);
            });
            holder.put(s.toString(),holderCity);
        });
        cities.putAll(holder);
        applyPattern();
    }
    public void addNewDistance(String origin, String destiny, int distance){
        if(!originalCities.containsKey(origin)){
            originalCities.put(origin, new City(origin));
        }
        if(!originalCities.containsKey(destiny)){
            originalCities.put(destiny, new City(destiny));
        }
        Route r = new Route(cities.get(destiny), distance);
        originalCities.get(origin).addRoute(destiny, r);
        cities.clear();
        citiesMap.clear();
        HashMap<String, City> holder = new HashMap<>();

        originalCities.forEach((s,c)-> {
            City holderCity = new City(s.toLowerCase());
            c.getRoutes().forEach((de, re) -> {
                holderCity.addRoute(de, re);
            });
            holder.put(s.toString(),holderCity);
        });
        cities.putAll(holder);
        applyPattern();
    }

    public void addTrafficToCity(){
        ArrayList<String> origins = new ArrayList<>(originalCities.keySet());
        int origin  = View.getView().selectOptions(origins, "Seleccione el numero de ciudad de origen", "Seleccione un numero valido");
        ArrayList<String> dests = new ArrayList<String>(originalCities.get(origins.get(origin)).getRoutes().keySet());
        int destiny = View.getView().selectOptions(dests, "Seleccione la ciudad con que conecta que tiene trafico en numeros",
                "Ingrese una opcion valida");
        int traffic = View.getView().intInput("Ingrese cuanto peso extra tiene gracias al trafico",
                "Ingrese un valor valido", 1);
        int currentW = originalCities.get(origins.get(origin)).getRoutes().get(dests.get(destiny)).getWeight();
        originalCities.get(origins.get(origin)).getRoutes().get(dests.get(destiny)).setWeight(currentW + traffic);
        cities.clear();
        citiesMap.clear();
         HashMap<String, City> holder = new HashMap<>();

        originalCities.forEach((s,c)-> {
            City holderCity = new City(s.toLowerCase());
            c.getRoutes().forEach(holderCity::addRoute);
            holder.put(s.toString(),holderCity);
        });
        cities.putAll(holder);
        applyPattern();
    }


    public void addTrafficToCity(String origin, String destiny, int traffic){
        int currentW = originalCities.get((origin)).getRoutes().get((destiny)).getWeight();
        originalCities.get((origin)).getRoutes().get((destiny)).setWeight(currentW + traffic);
        cities.clear();
        citiesMap.clear();
        HashMap<String, City> holder = new HashMap<>();

        originalCities.forEach((s,c)-> {
            City holderCity = new City(s.toLowerCase());
            c.getRoutes().forEach(holderCity::addRoute);
            holder.put(s.toString(),holderCity);
        });
        cities.putAll(holder);
        applyPattern();
    }

    public void deleteArc(){
        ArrayList<String> origins = new ArrayList<>(originalCities.keySet());
        int origin  = View.getView().selectOptions(origins, "Seleccione el numero de ciudad de origen", "Seleccione un numero valido");
        ArrayList<String> dests = new ArrayList<String>(originalCities.get(origins.get(origin)).getRoutes().keySet());
        int destiny = View.getView().selectOptions(dests, "Seleccione la ciudad con que conecta que desea eliminar coneccion",
                "Ingrese una opcion valida");

        originalCities.get(origins.get(origin)).getRoutes().remove(dests.get(destiny));
        cities.clear();
        citiesMap.clear();
        HashMap<String, City> holder = new HashMap<>();

        originalCities.forEach((s,c)-> {
            City holderCity = new City(s.toLowerCase());
            c.getRoutes().forEach(holderCity::addRoute);
            holder.put(s.toString(),holderCity);
        });
        cities.putAll(holder);
        applyPattern();
    }
    public void deleteArc(String originI, String destinyI){
        ArrayList<String> origins = new ArrayList<>(originalCities.keySet());
        int origin  = origins.indexOf(originI);
        ArrayList<String> dests = new ArrayList<String>(originalCities.get(origins.get(origin)).getRoutes().keySet());
        int destiny = dests.indexOf(destinyI);
        originalCities.get(origins.get(origin)).getRoutes().remove(dests.get(destiny));
        cities.clear();
        citiesMap.clear();
        HashMap<String, City> holder = new HashMap<>();

        originalCities.forEach((s,c)-> {
            City holderCity = new City(s.toLowerCase());
            c.getRoutes().forEach(holderCity::addRoute);
            holder.put(s.toString(),holderCity);
        });
        cities.putAll(holder);
        applyPattern();
    }
    public void applyPattern(){
        int index1 = 0;
        for (int i = 0; i < cities.size(); i++) {
            String s = (String) cities.keySet().toArray()[i];
            City city = cities.get(s);
            HashMap<String, String> cityMap = new HashMap<>(){{put(city.getName(), city.getName());}};
            citiesMap.put(cityMap, 0);
        }
        for (int i = 0; i < cities.size(); i++) {
            String s = (String) cities.keySet().toArray()[i];
            City city = cities.get(s);
            for (int j = 0; j < city.getRoutes().size(); j++) {
                HashMap<String, Route> routes = city.getRoutes();
                routes.forEach((cityDest, route) -> {
                    HashMap<String, String> cityMap = new HashMap<>() {{
                        put(city.getName(), cityDest);
                    }};
                    citiesMap.put(cityMap, route.getWeight());
                });


            }
        }
        for (int i = 0; i < cities.size(); i++) {
            String s = (String) cities.keySet().toArray()[i];
            City city = cities.get(s);
            for (int j = 0; j < cities.size(); j++) {
                String s2 = (String) cities.keySet().toArray()[j];
                City city2 = cities.get(s2);
                for (int k = 0; k < cities.size(); k++) {
                    String s3 = (String) cities.keySet().toArray()[k];
                    City city3 = cities.get(s3);
                    HashMap<String, String> city1Key = new HashMap<>(){{put(s2, s3);}};
                    HashMap<String, String> city2Key = new HashMap<>(){{put(s2, s);}};
                    HashMap<String, String> city3Key = new HashMap<>(){{put(s, s3);}};
                    if(!citiesMap.containsKey(city1Key)){
                        citiesMap.put(city1Key, (int)Integer.MAX_VALUE/2);
                    }
                    if (citiesMap.get(city1Key) > citiesMap.get(city2Key) +citiesMap.get(city3Key)){
                        citiesMap.put(city1Key , citiesMap.get(city2Key) +citiesMap.get(city3Key));
                        if (cities.get(s2).getRoutes().get(s3) == null){
                            Route r = new Route(city3, (int)Integer.MAX_VALUE/2);
                            cities.get(s2).getRoutes().put(s3, r);
                        }
                        ArrayList<String> interm = cities.get(s2).getRoutes().get(s3).getIntermediates();
                        interm.add(s);
                        cities.get(s2).getRoutes().get(s3).setIntermediates(interm);
                        cities.get(s2).getRoutes().get(s3).setWeight(citiesMap.get(city2Key) +citiesMap.get(city3Key));
                    }
                }

            }
        }
    }

    private  String getOsName()
    {
        return System.getProperty("os.name");
    }


    private void printTable(){
        ArrayList<String> headers = new ArrayList<>(){{
            add("Paises");
            for (int i = 0; i < cities.keySet().toArray().length; i++) {
                add(cities.keySet().toArray()[i].toString());
            }
        }};
        ArrayList<ArrayList<String>> rows = new ArrayList<>(){{
            for (int i = 0; i < cities.keySet().toArray().length; i++) {
                ArrayList<String> holder =  new ArrayList<>();
                holder.add(cities.keySet().toArray()[i].toString());
                String cityOrigin = cities.keySet().toArray()[i].toString();
                for (int j = 0; j < cities.keySet().toArray().length; j++) {
                    String cityDest = cities.keySet().toArray()[j].toString();
                    HashMap<String, String> key = new HashMap<>();
                    key.put(cityOrigin, cityDest);
                    Integer distance = citiesMap.get(key);
                    String value;
                    if (distance != Integer.MAX_VALUE/2) {
                        value = String.valueOf(distance);
                    }else{
                        value = "infinite";
                    }
                    holder.add(value);
                }
                add(holder);
            }
        }};
        View.getView().printTable(headers, rows);
    }

    public HashMap<String, City> getCities() {
        return cities;
    }

    public void setCities(HashMap<String, City> cities) {
        this.cities = cities;
    }

    public HashMap<String, City> getOriginalCities() {
        return originalCities;
    }

    public void setOriginalCities(HashMap<String, City> originalCities) {
        this.originalCities = originalCities;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public HashMap<HashMap<String, String>, Integer> getCitiesMap() {
        return citiesMap;
    }

    public void setCitiesMap(HashMap<HashMap<String, String>, Integer> citiesMap) {
        this.citiesMap = citiesMap;
    }
}
