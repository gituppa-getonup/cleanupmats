package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PersonHelper {
    public List<Person> generatePeople(Main m) {
        int numberThatShowUp = (int) Math.round(Math.random() * 15);

        List<Person> persons = IntStream.range(0, numberThatShowUp)
                .mapToObj(i ->
                {
                    Person person = new Person();
                    person.setM(m);
                    person.setName(this.generateRandomName());
                    person.setPersonalLimit(new Random().nextInt(1, 5));
                    person.setPersonalStack(new ArrayList<>());
                    return person;
                })
                .collect(Collectors.toList());

        return persons;
    }

    public String generateRandomName() {
        String it = "";
        while (it.length() < 6) {
            it = it.concat(Math.random() < 0.1 ? "ar" : "");
            it = it.concat(Math.random() < 0.1 ? "en" : "");
            it = it.concat(Math.random() < 0.1 ? "ol" : "");
            it = it.concat(Math.random() < 0.1 ? "be" : "");
            it = it.concat(Math.random() < 0.1 ? "al" : "");
        }

        it = it.substring(0, 1).toUpperCase() + it.substring(1);
        return it;
    }
}
