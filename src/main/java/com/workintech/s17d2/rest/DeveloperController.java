package com.workintech.s17d2.rest;

import com.workintech.s17d2.model.*;
import com.workintech.s17d2.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DeveloperController {
    public Map<Integer, Developer> developers = new HashMap<>();
    private Taxable taxable;

    @PostConstruct
    public void init(){
        developers.put(1, new JuniorDeveloper(1, "Initial Developer", 5000.0, Experience.JUNIOR));
    }
    @Autowired
    public DeveloperController(@Qualifier("developerTax") Taxable taxable){
        this.taxable = taxable;
    }

    @GetMapping("/developers")
    public List<Developer> getAllDevelopers() {
        return developers.values().stream().toList();
    }

    @GetMapping("/developers/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable Integer id) {
        return ResponseEntity.ok(developers.get(id));
    }

    @PostMapping("/developers")
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer developer){
        Map<Integer, Developer> developers = new HashMap<>();
        double adjustedSalary = developer.getSalary();
        switch (developer.getExperience()){
            case JUNIOR :
                adjustedSalary -= adjustedSalary*taxable.getSimpleTaxRate();
                developer = new JuniorDeveloper(developer.getId(), developer.getName(), adjustedSalary, Experience.JUNIOR);
                break;
            case MID:
                adjustedSalary -= (adjustedSalary * taxable.getMiddleTaxRate() / 100);
                developer = new MidDeveloper(developer.getId(), developer.getName(), adjustedSalary, Experience.MID);
                break;
            case SENIOR:
                adjustedSalary -= (adjustedSalary * taxable.getUpperTaxRate() / 100);
                developer = new SeniorDeveloper(developer.getId(), developer.getName(), adjustedSalary, Experience.SENIOR);
                break;
            default:
                System.out.println("Invalid experience level");
        }
        developers.put(developer.getId(), developer);
        return new ResponseEntity<>(developer, HttpStatus.CREATED); // JSON formatında döner
    }

    @PutMapping("/developers/{id}")
    public Developer developers(@PathVariable Integer id, @RequestBody Developer developer){
        Map<Integer, Developer> developersMap = new HashMap<>();
        return developers.put(id, developer);
    }

    @DeleteMapping("/developers/{id}")
    public String deleteDeveloper(@PathVariable Integer id) {
        if (!developers.containsKey(id)) {
            System.out.println("Developer not found");
        }

        developers.remove(id);
        return "Developer with ID " + id + " deleted.";
    }

}
