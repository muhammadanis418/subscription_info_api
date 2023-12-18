package com.subscription.api.subscription_info_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SubscriptionInfoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscriptionInfoApiApplication.class, args);
//    final List<Integer> list= new ArrayList<>();
//    list.add(10);
//    list.add(20);
//        System.out.println(list);
//      final List<Integer> list1= Arrays.asList(10,20);
//        list1.add(10);
//        list1.add(20);
//        System.out.println(list1);
//      final   List<String> names = new ArrayList<>();
//        names.add("Alice");
//        names.add("Bob");
//
//        names = new ArrayList<>(); // This will cause a compile-error, because names is final and cannot be reassigned
//        names.add("Eve");
//        System.out.println(names);
    }

}
