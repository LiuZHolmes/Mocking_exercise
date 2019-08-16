package parking;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class VipParkingStrategyTest {

	@Test
    public void testPark_givenAVipCarAndAFullParkingLot_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */
	    Car car = mock(Car.class);
	    ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.isFull()).thenReturn(true);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        doReturn(true).when(vipParkingStrategy).isAllowOverPark(car);
        vipParkingStrategy.park(parkingLots,car);

        verify(vipParkingStrategy).createReceipt(parkingLot,car);
    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLot_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        Car car = mock(Car.class);
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.isFull()).thenReturn(true);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        doReturn(false).when(vipParkingStrategy).isAllowOverPark(car);
        vipParkingStrategy.park(parkingLots,car);

        verify(vipParkingStrategy).createNoSpaceReceipt(car);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("CarA");
        CarDaoImpl carDao = mock(CarDaoImpl.class);
        when(carDao.isVip(anyString())).thenReturn(true);
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        doReturn(carDao).when(vipParkingStrategy).getCarDao();

        assertTrue(vipParkingStrategy.isAllowOverPark(car));
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("Car");
        CarDaoImpl carDao = mock(CarDaoImpl.class);
        when(carDao.isVip(anyString())).thenReturn(true);
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        doReturn(carDao).when(vipParkingStrategy).getCarDao();

        assertFalse(vipParkingStrategy.isAllowOverPark(car));
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("CarA");
        CarDaoImpl carDao = mock(CarDaoImpl.class);
        when(carDao.isVip(anyString())).thenReturn(false);
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        doReturn(carDao).when(vipParkingStrategy).getCarDao();

        assertFalse(vipParkingStrategy.isAllowOverPark(car));
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = mock(Car.class);
        when(car.getName()).thenReturn("Car");
        CarDaoImpl carDao = mock(CarDaoImpl.class);
        when(carDao.isVip(anyString())).thenReturn(false);
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        doReturn(carDao).when(vipParkingStrategy).getCarDao();

        assertFalse(vipParkingStrategy.isAllowOverPark(car));
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
