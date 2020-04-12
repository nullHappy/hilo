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

    private ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
    private static int current = 5; //number the user has to guess if the next number will be higher or lower than
    private static boolean needsSetup = true;

    @RequestMapping("/reset")
    @ResponseBody
    String reset(){
        numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        current = 5;
        needsSetup = true;
    }

    @RequestMapping("/")
    @ResponseBody
    String main() {
        if(needsSetup) {
            //just use 5 to start with out of simplicity
            current = 5;
            numbers.remove(4); //TODO bug here if I update the elements in the list
            needsSetup = false;
        }

        return constructHtml();
    }

    @RequestMapping("/hi")
    @ResponseBody
    String hi() {
        String response = ""; //TODO: this could be more elegant
        int answer = getAndRemoveNumberFromList();
        if (answer > current){
            current = answer;
            response = main();
        }
        else{
            //Fail
            response =  answer + " is not higher than: " + current + ". Unlucky!";
            reset();
        }
        return response;
    }

    @RequestMapping("/lo")
    @ResponseBody
    String lo() {
        String response = ""; //TODO: this could be more elegant, duplication
        int answer = getAndRemoveNumberFromList();
        if (answer < current){
            current = answer;
            response = main();
        }
        else{
            //Fail
            response =  answer + " is not lower than: " + current + ". Unlucky!";
            reset();
        }
        return response;
    }

    private String constructHtml(){
        String uglySource =
                "<html>\n" +
                        "<body>\n" +
                        "Remaining numbers: " + Arrays.toString(numbers.toArray()) +
                        "<p>\n Current number: " + String.valueOf(current) + "</p></br>" +
                        "<input type=\"button\"  onclick=\"location.href='/hi'\" value=\"hi\" >\n" +
                        "<input type=\"button\"  onclick=\"location.href='/lo'\" value=\"lo\" >\n" +
                        "<input type=\"button\"  onclick=\"location.href='/reset'\" value=\"reset\" >\n" +
                        "</p>\n" +
                        "</body>\n" +
                        "</html>";

        return uglySource;
    }

    private int getAndRemoveNumberFromList(){
        //TODO: game breaks if user gets to the win condition....

        Random random = new Random();
        int placeholderIndex = random.nextInt((numbers.size() - 1));

        int nextNum = numbers.get(placeholderIndex);
        numbers.remove(placeholderIndex);

        return nextNum;
    }

    public static void main(String[] args) {
        SpringApplication.run(HiLo.class, args);
    }
}