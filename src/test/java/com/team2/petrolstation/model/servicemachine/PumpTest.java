package com.team2.petrolstation.model.servicemachine;

import com.team2.petrolstation.model.customer.vehicle.Motorbike;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by canershefik on 07/06/2018.
 */
public class PumpTest {


    @Test
    public void testAddCustomer(){
        Pump pump = new Pump();

        assertEquals(0, pump.getCustomersInQueue().size());


        for(int i = 0; i < 2; i++){
            pump.addCustomer(new Motorbike());
        }

        assertEquals(2, pump.getCustomersInQueue().size());

        pump.addCustomer(new Motorbike());

        assertEquals(3, pump.getCustomersInQueue().size());

    }
}