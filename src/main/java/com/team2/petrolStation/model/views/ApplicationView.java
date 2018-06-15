package com.team2.petrolStation.model.views;

import com.team2.petrolStation.model.exceptions.InvalidInputException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.team2.petrolStation.model.constants.PetrolStationConstants.RESULTS_DESTINATION_FILE;
import static com.team2.petrolStation.model.constants.PetrolStationConstants.SECONDS_PER_TICK;

/**
 * @author callummarriage
 */
public class ApplicationView {

    private Simulator simulator;
    public ApplicationView(Simulator simulator){
        this.simulator = simulator;
    }

    /**
     * Start method intakes the values and kicks of the simulation using the values
     *
     */
    public void start(){
        //replace this with gui values
        Integer numOfTurns;
        Integer numPumps = 1;
        Integer numTills = 1;
        Double priceOfFuel = 1.2;
        Double p = 0.01;
        Double q = 0.02;

        Boolean truckIsActive = true;
        String time = "1";
        String identifiers = "h";

        try {
            numOfTurns = convertTimeIntoSeconds(time, identifiers);

        } catch(InvalidInputException e){
            e.printStackTrace();
            return;
        }

        updateScreen("Welcome to the simulation\nThe duration will be " + numOfTurns + " seconds or " + (numOfTurns / SECONDS_PER_TICK) + " ticks \n");

        //run the simulation using the inputed values
        simulator.simulate(numOfTurns, numPumps, numTills, priceOfFuel, p, q, truckIsActive);
    }

    public void updateScreen(String results){
        System.out.println(results);
    }

    public void printFinalResults(String results){
        updateScreen(results);
        generateFile(results);
    }


    /**
     * Converts the time into seconds based on its identifier and returns the new value.
     * I chose this design because it is clear what is calculating what, and is easy to add new time formats
     *
     * @param time the amount of time the simulation will execute for.
     * @return time in seconds
     * @throws InvalidInputException time could not be converted.
     */
    private static Integer convertTimeIntoSeconds(String time, String identifier) throws InvalidInputException{

        Integer number;
        try {
            Double doubleNumber = Double.parseDouble(time);
            //Do a check on the identifier each time, if its a day you will need to do all of the calculations,
            //This is clearer as you can see what each calculation is doing to the number even though the added if statements add additional run time.
            if(identifier.equals("t")){
                doubleNumber *= 10;
            } else {
                switch (identifier){
                    //year
                    case ("y"): doubleNumber *= 31536000;
                        break;
                    //week
                    case ("w"): doubleNumber *= 604800;
                        break;
                    //day
                    case ("d"): doubleNumber *= 86400;
                        break;
                    //hour
                    case ("h"): doubleNumber *= 3600;
                        break;
                    //minute
                    case ("m"): doubleNumber *= 60;
                        break;
                    //tick
                    case ("t"): doubleNumber *=10;
                    default: break;
                }
            }

            number = Integer.parseInt(Math.round(doubleNumber) + "");
        } catch (Exception e){
            throw new InvalidInputException(time);
        }

        return number;
    }

    /**
     * Writes results to a file
     * Prints result to screen as its writing.
     *
     * @param results list of all of the results.
     */
    private void generateFile(String results){
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;

        try{
            LocalDateTime currentDate = LocalDateTime.now();
            //create the file writer using the location store as a constant
            fileWriter = new FileWriter(RESULTS_DESTINATION_FILE+"-"+currentDate+".txt");
            //create a buffered write with the file writer as an argument
            bufferedWriter = new BufferedWriter(fileWriter);
            //loop through the results list
                //add the line to the file
                bufferedWriter.write(results);

        }catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                //close the writers
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
                if(fileWriter != null){
                    fileWriter.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
