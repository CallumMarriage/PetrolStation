package com.team2.petrolstation.model.facility;

import com.team2.petrolstation.model.customer.Vehicle;
import org.junit.Test;

import java.util.Random;

import static com.team2.petrolstation.model.constant.VehicleConstants.*;
import static org.junit.Assert.assertEquals;


/**
 * Created by callummarriage on 25/05/2018.
 */
public class FillingStationTest {

    @Test
    public void testManageTransactionsForFillingStation(){

    }

    @Test
    public void testMaximumVehicles(){

        Random random = new Random(2);
        Vehicle truck = new Vehicle((random.nextInt(36 -24 +1  ) +24), (random.nextInt(20 - 15 + 1) + 15), (random.nextInt(40 - 30 + 1) + 30), CHANCE_OF_TRUCK_GOING_TO_SHOP, SIZE_OF_TRUCK, MAX_QUEUE_TIME_TRUCK);
        Vehicle truck2 = new Vehicle((random.nextInt(36 -24 +1  ) +24), (random.nextInt(20 - 15 + 1) + 15), (random.nextInt(40 - 30 + 1) + 30), CHANCE_OF_TRUCK_GOING_TO_SHOP, SIZE_OF_TRUCK, MAX_QUEUE_TIME_TRUCK);
        Vehicle truck3 = new Vehicle((random.nextInt(36 -24 +1  ) +24), (random.nextInt(20 - 15 + 1) + 15), (random.nextInt(40 - 30 + 1) + 30), CHANCE_OF_TRUCK_GOING_TO_SHOP, SIZE_OF_TRUCK, MAX_QUEUE_TIME_TRUCK);

        FillingStation facility = new FillingStation(2);

        try {
            assertEquals(0, facility.findBestMachine(truck));
            facility.addCustomerToBestMachine(0, truck);
            assertEquals(1, facility.findBestMachine(truck2));
            facility.addCustomerToBestMachine(1, truck2);
            assertEquals(-1, facility.findBestMachine(truck3));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
