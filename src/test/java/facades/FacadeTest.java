package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dtos.user.UserDTO;
import entities.Bookings;
import entities.Car;
import entities.User;
import entities.WashingAssistants;
import errorhandling.API_Exception;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.StartDataSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade userFacade;
    private static Facade facade;

    public FacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = Facade.getFacade(emf);
        userFacade = UserFacade.getUserFacade(emf);
    }


    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp() {
        StartDataSet.setupInitialData(emf);
    }


    // TODO: Delete or change this method
    @Test
    public void testTrue() throws Exception {
        EntityManager em = emf.createEntityManager();
        User _both;
        try {
            _both = em.find(User.class, StartDataSet.both.getUserName());

        } finally{
            em.close();
        }


        assertEquals(_both.getUserName(), StartDataSet.both.getUserName());
    }

    @Test
    public void testGetWashingAssistants() {
        List<WashingAssistants> result = facade.getWashingAssistants();
        assertEquals(3,result.size());
    }

    @Test
    public void test_GetUserData() {
        User user = facade.getUserData(StartDataSet.user.getUserName());
        Assertions.assertNotNull(user);
    }

    @Test
    public void test_GetUserData_GetCar() {
        User user = facade.getUserData(StartDataSet.user.getUserName());
        assertEquals(1,user.getCarsList().size());
    }

    @Test
    public void test_GetUserData_GetCarBookings() {
        User user = facade.getUserData(StartDataSet.user.getUserName());
        Car car = user.getCarsList().get(0);
        assertEquals(1,car.getBookingsList().size());
    }

    @Test
    public void test_GetUserData_GetCarBookingAssistants() {
        User user = facade.getUserData(StartDataSet.user.getUserName());
        Bookings booking = user.getCarsList().get(0).getBookingsList().get(0);
        assertEquals(1,booking.getWashingAssistantsList().size());
    }

    @Test
    public void test_AddBooking() throws API_Exception {
        User user = facade.getUserData(StartDataSet.user.getUserName());
        String carReg = user.getCarsList().get(0).getRegistration();
        Bookings booking = new Bookings(new Date(), 10);
        WashingAssistants wa = facade.getWashingAssistants().get(0);
        booking.addWashingAssistant(wa);
        Car car = facade.addBooking(carReg, booking);
        Bookings bookingManaged = car.getBookingsList().get(car.getBookingsList().size()-1);
        Assertions.assertNotNull(bookingManaged.getId());
    }

    @Test
    public void test_AddWashingAssistant() throws API_Exception {
        WashingAssistants washingAssistant = new WashingAssistants("NewName","Danish", 2.0f,12.0f);
        washingAssistant = facade.addWashingAssistant(washingAssistant);
        Assertions.assertNotNull(washingAssistant.getId());
    }

    @Test
    public void test_GetAllCars() throws API_Exception {
        List<Car> result = facade.getAllCars();
        assertEquals(3, result.size());
    }

    @Test
    public void test_removeBooking() throws API_Exception {
        List<Car> before = facade.getAllCars();
        facade.removeBooking(before.get(2).getBookingsList().get(0).getId());
        List<Car> result = facade.getAllCars();
        assertEquals(0, result.get(2).getBookingsList().size());
    }

    //@Test
    public void test_editBooking() throws API_Exception {
        Car c = facade.getAllCars().get(0);
        Bookings booking = c.getBookingsList().get(0);
        WashingAssistants w = facade.getWashingAssistants().get(1);
        booking.addWashingAssistant(w);
        Car car = facade.editBooking(c.getRegistration(),booking);
        assertEquals(2, car.getBookingsList().get(0).getWashingAssistantsList().size());
    }

    @Test
    public void test_AddCar() throws API_Exception {
        Car car = new Car("Reg4", "skoda", "oktavia", 2000);
        car = facade.addCar(StartDataSet.user.getUserName(), car);
        Assertions.assertNotNull(car.getUser());
    }
    @Test
    public void test_editCar() throws API_Exception {
        Car car = facade.getAllCars().get(0);
        String username = car.getUser().getUserName();
        String oldMake = car.getMake();
        car.setMake("NewMake");
        Car carNew = facade.editCar(username, car);
        Assertions.assertNotEquals(oldMake, carNew.getMake());
    }

    @Test
    public void testCreateUser() throws Exception {
        String username = "TEST_NEW_USER";
        JsonObject inputJson = new JsonObject();
        inputJson.addProperty("username", username);
        inputJson.addProperty("password", "testUser");
        JsonArray jsonArray = new JsonArray();
        JsonObject roleObject = new JsonObject();
        roleObject.addProperty("rolename", "user");
        jsonArray.add(roleObject);
        inputJson.add("roles", jsonArray);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        UserDTO userDTO = gson.fromJson(inputJson, UserDTO.class);
        userFacade.createUser(userDTO);

        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, username);
        Assertions.assertNotNull(user);
    }


}
