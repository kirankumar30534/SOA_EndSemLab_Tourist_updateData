package com.klu.tourist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TouristRecordsEndLabExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(TouristRecordsEndLabExamApplication.class, args);
        System.out.println("==========================================================================");
        System.out.println(" Tourist_Records_EndLabExam Application Started Successfully!             ");
        System.out.println(" In-Memory Store : Preloaded with 4 tourist records (IDs: 1, 2, 3, 4)     ");
        System.out.println(" Swagger UI URL  : http://localhost:8080/swagger-ui/index.html            ");
        System.out.println(" Alternative URL : http://localhost:8080/swagger-ui.html                  ");
        System.out.println(" Demo User/Pass  : admin / admin123                                       ");
        System.out.println("==========================================================================");
    }
}
