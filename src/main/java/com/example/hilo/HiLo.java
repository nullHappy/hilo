package com.example.hilo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@Controller
@SpringBootApplication
public class HiLo {
    
    //Variables & Constants ==============================================
    private static final int STARTING_NUMBER = 5; //arbitrary, but picked a number that gives user >50% chance of being correct on first go
    private static final String HI_ACTION = ">";
    private static final String LO_ACTiON = "<";
    private static int highScore = 0; //todo: could add a scheduler to reset this at midnight and change to daily high-score
    
    private static boolean resetGameData = true; //resets the 3 variables below, with the intention of starting a new game
    private ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
    private static int score = 0;
    private static int current = STARTING_NUMBER; //user has to guess if the next number will be higher or lower than this

    //Mappings ===========================================================
    
    @RequestMapping("/")
    @ResponseBody
    String main() {
        if(resetGameData) {
            current = STARTING_NUMBER;
            numbers.remove(STARTING_NUMBER - 1); 
            resetGameData = false;
        }

        return constructHtml();
    }
    
    @RequestMapping("/hi")
    @ResponseBody
    String hi() {
        return getResult(HI_ACTION);
    }

    @RequestMapping("/lo")
    @ResponseBody
    String lo() {
        return getResult(LO_ACTiON);
    }

    //Game logic =========================================================
    
    private String constructHtml(){
        String rawSource =
                "<html>\n" +
                  "<body>\n" +
                     "<p>\n Current number: " + String.valueOf(current) + "</br>" +
                        "Remaining numbers: " + Arrays.toString(numbers.toArray()) + "</br>" +
                        "<input type=\"button\"  onclick=\"location.href='/hi'\" value=\"higher\" >\n" +
                        "<input type=\"button\"  onclick=\"location.href='/lo'\" value=\"lower\" >\n" +
                        "</br>" +
                        "</br> Current score: " + String.valueOf(score)  +
                        "</br> HIGH SCORE: " + String.valueOf(highScore) +
                     "</p>\n" +
                  "</body>\n" +
                "</html>";
        return rawSource;
    }

    private String getResult(String action){
        String response = "";
        boolean isSuccess = false;
        int answer = getAndRemoveNumberFromList();

        if(HI_ACTION.equals(action)){
            if (answer > current){isSuccess = true;} 
        }
        else if(LO_ACTiON.equals(action)){
            if (answer < current){isSuccess = true;}
        }
        else{
            //action not recognised...
            return main();
        }

        if(isSuccess){
            current = answer;
            score ++;
            if (numbers.size() <= 1){
                response =  getWinConditionMessage();
            }
            else {
                response = main();
            }
        }
        else{
            //Fail
            response =  answer + " is not higher than: " + current + ". Unlucky!</br>" +
                    "<input type=\"button\"  onclick=\"location.href='/'\" value=\"try again\" >\n";
            setHighScore(score);
            reset();
        }

        return response;
    }

    void reset(){
        numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        current = STARTING_NUMBER;
        score = 0;
        resetGameData = true;
    }

    private int getAndRemoveNumberFromList(){
        Random random = new Random();
        int placeholderIndex = random.nextInt((numbers.size() - 1));
        int nextNum = numbers.get(placeholderIndex);
        numbers.remove(placeholderIndex);

        return nextNum;
    }

    private String getWinConditionMessage(){
       String successMessage ="<html><p>CONGRATULATIONS!!!</p></html>";
       return successMessage;
    }

    private void setHighScore(int score){
        if(score > highScore){
            highScore = score;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(HiLo.class, args);
    }
}