// import static org.junit.jupiter.api.Assertions.*;

// class ControllerTest {

//     @org.junit.jupiter.api.Test
//     void fillCities() {
//         Controller c = new Controller();
// //        applyPattern();
//         try{
//             c.fillCities("guategrafo.txt");
//             assertTrue(true);
//         }catch (Exception e){
//             assertTrue(false);
//         }

//     }

//     @org.junit.jupiter.api.Test
//     void addNewDistance() {
//         Controller c = new Controller();
//         c.fillCities("guategrafo.txt");
//         c.applyPattern();
//         c.addNewDistance("julian", "julieto", 100);
//         assertTrue(c.getCities().containsKey("julian"));
//         assertTrue(c.getCities().get("julian").getRoutes().containsKey("julieto"));
//         assertEquals(100, c.getCities().get("julian").getRoutes().get("julieto").getWeight());
//     }

//     @org.junit.jupiter.api.Test
//     void addTrafficToCity() {
//         Controller c = new Controller();
//         c.fillCities("guategrafo.txt");
//         c.applyPattern();
//         c.addNewDistance("julian", "julieto", 100);
//         assertEquals(100, c.getCities().get("julian").getRoutes().get("julieto").getWeight());
//         c.addTrafficToCity("julian", "julieto", 10);
//         assertEquals(110, c.getCities().get("julian").getRoutes().get("julieto").getWeight());
//     }

//     @org.junit.jupiter.api.Test
//     void deleteArc() {
//         Controller c = new Controller();
//         c.fillCities("guategrafo.txt");
//         c.applyPattern();
//         c.addNewDistance("julian", "julieto", 100);
//         assertEquals(100, c.getCities().get("julian").getRoutes().get("julieto").getWeight());
//         c.deleteArc("julian", "julieto");
//         assertFalse(c.getCities().get("julian").getRoutes().containsKey("julieto"));
//     }

// }