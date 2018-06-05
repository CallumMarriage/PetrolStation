package com.team2.petrolStation.model.facility;

import com.team2.petrolStation.model.customer.Customer;
import com.team2.petrolStation.model.customer.Driver;
import com.team2.petrolStation.model.customer.vehicle.Vehicle;
import com.team2.petrolStation.model.exceptions.PumpNotFoundException;
import com.team2.petrolStation.model.exceptions.ServiceMachineAssigningException;
import com.team2.petrolStation.model.serviceMachine.Pump;
import com.team2.petrolStation.model.serviceMachine.ServiceMachine;

import java.util.*;

import static com.team2.petrolStation.model.constants.ServiceMachineConstants.MAX_QUEUE_SIZE;

/**
 * Implements the process of assinging a customer to the best available service machine.
 *
 * @author callummarriage
 */
public class Facility {

    ServiceMachine[] customerServers;

    /**
     * Simulates a facility processing the customers at the front of the queues and adds those that have finished to a hashmap allong with the SM they have come from.
     *
     * @return a map of customers to their service machines.
     */
    public Map<Integer, Customer> manageTransactions() {
        Map<Integer, Customer> finishedCustomers = new HashMap<>();
        for(int i = 0; i < customerServers.length; i++) {
            Customer customer = customerServers[i].act();
            if(customer != null){
                finishedCustomers.put(i, customer);
            }
        }
        return finishedCustomers;
    }

    /**
     * Finds and adds each customer to the best free service machine.
     * If a vehicle does not find the best service machine it will leave the Petrol station and possible income will be added to lost money.
     * If a driver does not find the best service machine it will throw an exception.
     *
     * @param customers a list of drivers or vehicles to be added to service machines
     * @return the amount of lost vehicles.
     */
    public Collection<Customer> addCustomerToMachine(Collection<Customer> customers,Double priceOfFuel ) throws ServiceMachineAssigningException, PumpNotFoundException{

        //keep track of the vehicles lost
        Collection<Customer> lostCustomers = new ArrayList<>();

        for (Customer customer : customers) {
            //get the best machine for the customer
            int bestMachine = findBestMachine(customer);
            if (bestMachine < 0) {
                lostCustomers.add(customer);
            } else {
                //finally add the customer to the machine found.
                addCustomerToBestMachine(bestMachine, customer);
            }
        }
        return lostCustomers;
    }

    /**
     * Finds the best machine available for each customer.
     *
     * @param customer can be a driver or a vehicle
     * @return the position of the best service machine.
     */
     public int findBestMachine(Customer customer) throws PumpNotFoundException{
        double previous = -1.0;
        int positionOfPumpWithShortestTime = 0;

        for (int i = 0; i < customerServers.length; i++) {
            //if the custom server has not been set up correctly throw an exception as every time this method is called it will be invalid
            if (customerServers[i] != null) {
                //get the size of the vehicles at the current pump so that we can compare to other pumps
                double pumpQueueSize = customerServers[i].getSizeOfCustomersInQueue();

                //check if its valid (i.e if its a driver it should auto pass if its a vehicle it needs to be small enough).
                //Check if the previous is -1, if it is then set the smallest to the current as the previous' queue was full
                if (customerServers[i].checkIfCustomerFits(customer) && ( i == 0 || previous == -1.0 || pumpQueueSize < previous)) {
                    //if the pump has a smaller queue set the current pump to be the shortest and save the pump number
                    previous = pumpQueueSize;
                    positionOfPumpWithShortestTime = i;
                    //if the pump's size is zero it is impossible to find a smaller pump.
                    if (previous == 0.0) {
                        break;
                    }
                }
            } else {
                throw new PumpNotFoundException(i);
            }
        }
        //check if the previous is valid and return the pump it was at
        if(previous >= 0.0){
            return positionOfPumpWithShortestTime;
        } else {
            //if it is invalid return -1 so that we can check if the method was successful
            return -1;
        }
    }

    public void addCustomerToBestMachine(int positionOfPumpWithShortestTime, Customer customer){
        customerServers[positionOfPumpWithShortestTime].addCustomer(customer);
    }

    public ServiceMachine[] getServiceMachines(){
        return this.customerServers;
    }

    /**
     * Adds up the size of all of the queues in the facility
     *
     * @return number of vehicles at the facility
     */
    public Integer getLeftOverCustomers(){
        Integer numCustomers = 0;
        for(ServiceMachine pump : getServiceMachines()){
            numCustomers += pump.getCustomersInQueue().size();
        }

        return numCustomers;
    }
}
