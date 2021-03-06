package com.team2.petrolstation.model.customer;

import static com.team2.petrolstation.model.constant.VehicleConstants.SIZE_OF_DRIVER;

/**
 * The driver is the model of a customer who enters the shop and purchases the fuel that they have just put in their vehicle.
 * The driver remembers how much they have spent and contains an act method that inputs a value and decides whether they have finished in the shop.
 *
 * @author callummarriage
 */
public class Driver implements Customer {

    private Double currentSpend;
    private Integer timeInShop;
    private Integer currentTimeInShop;
    private Integer maximumSpend;
    private Integer pumpNumber;

    public Driver(Double currentSpend, Integer pumpNumber ){
        this.currentSpend = currentSpend;
        this.timeInShop = 0;
        this.maximumSpend = 0;
        this.currentTimeInShop = 0;
        this.pumpNumber = pumpNumber;
    }

    @Override
    public Double getSize() {
        return SIZE_OF_DRIVER;
    }

    /**
     * Checks if driver has finished in shop.
     *
     * @param value the amount of time adding to the time spent in the shop.
     * @return boolean
     */
    @Override
    public Boolean act(Integer value) {
        if((this.currentTimeInShop + value) >= this.timeInShop){
            return true;
        } else {
            this.currentTimeInShop += value;
            return false;
        }
    }

    public void setTimeInShop(Integer timeInShop) {
        this.timeInShop = timeInShop;
    }

    public void addToCurrentSpend(Integer spendInShop){
        this.currentSpend += spendInShop;
    }

    public double getCurrentSpend(){
        return this.currentSpend;
    }

    public Integer getMaximumSpend() {
        return this.maximumSpend;
    }

    public Integer getPumpNumber() {
        return this.pumpNumber;
    }
}
