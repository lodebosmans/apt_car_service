package com.example.carpropertiesservice;

import com.example.carpropertiesservice.model.Car;
import com.example.carpropertiesservice.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarRepository carRepository;

    private Car car1 = new Car("Audi A4",200,5);
    private Car car2 = new Car("Tesla", 150,4);

    private List<Car> allCars = Arrays.asList(car1, car2);

    private List<Car> AllCarsWith5Seats = Arrays.asList(car1);

    @Test
    public void givenCar_whenFindCarByNrOfSeats_thenReturnJsonListCar() throws Exception {

        given(carRepository.findCarsByNumberOfSeats(5)).willReturn(AllCarsWith5Seats);

        mockMvc.perform(get("/cars/seats/{nrofseats}",5))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].carBrand",is("Audi A4")))
                .andExpect(jsonPath("$[0].maxSpeed",is(200)))
                .andExpect(jsonPath("$[0].numberOfSeats",is(5)));
    }

    @Test
    public void givenCar_whenFindCarByCarBrand_thenReturnJsonCar() throws Exception {

        given(carRepository.findCarByCarBrand("Audi A4")).willReturn(car1);

        mockMvc.perform(get("/cars/{carBrand}","Audi A4"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carBrand",is("Audi A4")))
                .andExpect(jsonPath("$.maxSpeed",is(200)))
                .andExpect(jsonPath("$.numberOfSeats",is(5)));
    }

    @Test
    public void givenCars_whenGetAllCars_thenReturnJsonCars() throws Exception {

        given(carRepository.findAll()).willReturn(allCars);

        mockMvc.perform(get("/cars"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].carBrand",is("Audi A4")))
                .andExpect(jsonPath("$[0].maxSpeed",is(200)))
                .andExpect(jsonPath("$[0].numberOfSeats",is(5)))
                .andExpect(jsonPath("$[1].carBrand",is("Tesla")))
                .andExpect(jsonPath("$[1].maxSpeed",is(150)))
                .andExpect(jsonPath("$[1].numberOfSeats",is(4)));
    }

    @Test
    public void testSetCarBrand() {
        String carBrand = "Seat";
        Car car = new Car();
        car.setCarBrand(carBrand);
        assertEquals(car.getCarBrand(), carBrand);
    }

    @Test
    public void testSetMaxSpeed() {
        Integer maxSpeed = 100;
        Car car = new Car();
        car.setMaxSpeed(maxSpeed);
        assertEquals(car.getMaxSpeed(), maxSpeed);
    }


    @Test
    public void testSetNrOfSeats() {
        Integer nrOfSeats = 3;
        Car car = new Car();
        car.setNumberOfSeats(nrOfSeats);
        assertEquals(car.getNumberOfSeats(), nrOfSeats);
    }


}