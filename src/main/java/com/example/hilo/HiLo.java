package com.example.hilo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

@Controller
@SpringBootApplication
public class HiLo {

    //TODO - an array probably isn't the best data structure for this (ordering and performance)
    private static int [] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static int answer = -1;

    @RequestMapping("/")
    @ResponseBody
    String main() {
        //BACKEND stuff
        //setup array with guesses
        //setup answer and remove it

        //Display stuff

        //String hiButton = "";
        //String loButton = "";

        String uglySource =
                "<html>\n" +
                "<body>\n" +
                "<p> 1, 2, 3, 4, 5, 6, 7, 8, 9, 10</p>\n" +
                "<p>\n" +
                "<input type=\"button\"  onclick=\"location.href='/hi'\" value=\"hi\" >\n" +
                "<input type=\"button\"  onclick=\"location.href='/lo'\" value=\"lo\" >\n" +
                "</p>\n" +
                "</body>\n" +
                "</html>";

        return uglySource;
    }

    @RequestMapping("/hi")
    @ResponseBody
    String hi() {
        return "Hi!";
    }

    @RequestMapping("/lo")
    @ResponseBody
    String lo() {
        return "Lo";
    }

    public static void main(String[] args) {
        SpringApplication.run(HiLo.class, args);
    }
}