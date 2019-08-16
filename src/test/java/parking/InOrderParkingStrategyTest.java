package parking;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InOrderParkingStrategyTest {

    final String PARKINGLOT_NAME = "ParkingLot_Name";
    final String CAR_NAME = "Car_Name";

    @Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
         * With using Mockito to mock the input parameter */
        Car car = mock(Car.class);
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(car.getName()).thenReturn(CAR_NAME);
        when(parkingLot.getName()).thenReturn(PARKINGLOT_NAME);

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        Receipt receipt = inOrderParkingStrategy.createReceipt(parkingLot, car);

        assertEquals(CAR_NAME, receipt.getCarName());
        assertEquals(PARKINGLOT_NAME, receipt.getParkingLotName());

    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(CAR_NAME);

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        Receipt receipt = inOrderParkingStrategy.createNoSpaceReceipt(car);

        assertEquals(CAR_NAME, receipt.getCarName());
        verify(car, times(1)).getName();

    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */

        Car car = mock(Car.class);
        when(car.getName()).thenReturn(CAR_NAME);

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        inOrderParkingStrategy.park(null, car);

        verify(inOrderParkingStrategy, times((1))).createNoSpaceReceipt(car);


    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(CAR_NAME);
        ParkingLot parkingLot = new ParkingLot(PARKINGLOT_NAME, 1);
        List<ParkingLot> parkingLots = Collections.singletonList(parkingLot);

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        inOrderParkingStrategy.park(parkingLots, car);

        verify(inOrderParkingStrategy, times(1)).createReceipt(parkingLot, car);
        assertEquals(car, parkingLot.getParkedCars().get(0));
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(CAR_NAME);
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.isFull()).thenReturn(true);
        List<ParkingLot> parkingLots = Collections.singletonList(parkingLot);

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        inOrderParkingStrategy.park(parkingLots, car);

        verify(inOrderParkingStrategy, times(1)).createNoSpaceReceipt(car);
        verify(inOrderParkingStrategy, times(0)).createReceipt(parkingLot,car);
    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot() {

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(CAR_NAME);
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.isFull()).thenReturn(true);
        ParkingLot parkingLot_other = new ParkingLot(PARKINGLOT_NAME, 2);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot,parkingLot_other);

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        inOrderParkingStrategy.park(parkingLots, car);

        verify(inOrderParkingStrategy, times(1)).createReceipt(parkingLot_other, car);
        assertEquals(car, parkingLot_other.getParkedCars().get(0));
    }


}
