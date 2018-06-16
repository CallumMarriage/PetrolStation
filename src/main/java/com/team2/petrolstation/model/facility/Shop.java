package com.team2.petrolstation.model.facility;

import com.team2.petrolstation.model.customer.Customer;
import com.team2.petrolstation.model.customer.Driver;
import com.team2.petrolstation.model.customer.vehicle.Vehicle;
import com.team2.petrolstation.model.servicemachine.ServiceMachine;
import com.team2.petrolstation.model.servicemachine.Till;

import java.util.*;
import java.util.Map.Entry;

/**
 * The shop contains a map of all of the drivers that are browsing the shop, it also manages the movement of drivers in and out of the shop floor.
 * The shop manages the transactions at all of the tills and should return a map of drivers and their pump.
 *
 * @author callummarriage
 */
public class Shop extends Facility {

    private List<Driver> shopFloor;

    public Shop(Integer numOfServiceMachines){
        //generate the service machines based on the number provided.
        customerServers = new ServiceMachine[numOfServiceMachines];
        for(int i = 0; i < numOfServiceMachines; i++){
            customerServers[i] = new Till();
        }
        shopFloor = new ArrayList<>();
    }

    /**
     * This method splits the customers who have finished refueling into drivers that are going to spend time/money in the shop and those that are not.
     *
     * @param customers list of all vehicles who have finished with refueling
     * @param random random used to generate the decisions
     * @return a list containing a list of drivers not going to the shop and another with those that are,
     */
    public List<List<Driver>> decideToGoToShop(Map <Integer, Customer> customers, Random random, Double priceOfFuel){

        List<Driver> customersNotGoingToShop = new ArrayList<>();
        List<Driver> customersGoingToShop = new ArrayList<>();

        for(Entry<Integer, Customer> pump : customers.entrySet()){

            //get the vehicle from that pump
            Vehicle vehicle = (Vehicle) pump.getValue();

            //make a driver based on the fuel level and pump from the vehicle
            Driver driver = new Driver((Math.round((vehicle.getMaxFuel() * priceOfFuel)) * 100d /100d) , pump.getKey());

            if(vehicle.decide(random)){
                customersGoingToShop.add(driver);
                // add the amount that the customer is going to spend in the shop to the shop and add to the total.
                driver.addToCurrentSpend(vehicle.getShopPurchase());

                //add the time the driver will be spending in the shop.
                driver.setTimeInShop(vehicle.getShopTime());

                //add the customers that are going to the shop to the list.s
                customersGoingToShop.add(driver);

            } else {
                customersNotGoingToShop.add(driver);

            }
        }

        // add both lists into a list of lists so that they both can be returned.
        List<List<Driver>> allCustomers = new ArrayList<>();
        allCustomers.add(customersNotGoingToShop);
        allCustomers.add(customersGoingToShop);

        return allCustomers;
    }

    public void addToShopFloor(List<Driver> drivers){
        shopFloor.addAll(drivers);
    }

    public List<Driver> getShopFloor(){
        return shopFloor;
    }

    /**
     * simulates drivers spending time loitering in the store.
     *
     * @return a list of customers who have finished shopping.
     */
    public List<Customer> getDriversFinished(){
        List<Customer> finishedDrivers = new ArrayList<>();
        for(Driver driver :  shopFloor){
            if(driver.act(10)){
                finishedDrivers.add(driver);
            }
        }

        return finishedDrivers;
    }

    public void removeDrivers(List<Customer> customers){
        shopFloor.removeAll(customers);
    }
}