package ru.kpfu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class Main {

    public static void main(String[] args) {
        Map<String, Integer> result = task2("src/ru/kpfu/resources/attempts-1.txt");
        System.out.println(result);
    }

    public static Map<String, Integer> task2(String fileName) {
        Map<String, Integer> result = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            Map<String, Map<Integer, List<Attempt>>> attemptsByStudent = br.lines()
                    .map(line -> line.split(" "))
                    .map(Attempt::new)
                    .collect(groupingBy(Attempt::getLastName,
                            groupingBy(Attempt::getTaskNumber))
                    );

            return attemptsByStudent.entrySet().stream()
                    .collect(toMap(Map.Entry::getKey, studentAttemptsByTask -> studentAttemptsByTask.getValue().values().stream()
                            .map(Main::getAttemptScore)
                            .reduce(Integer::sum).get()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Integer getAttemptScore(List<Attempt> attempts) {
        if (attempts.size() >= 5 && attempts.size() <= 10) {
            return attempts.stream().max(Comparator.comparing(Attempt::getScore)).get().getScore() / 2;
        } else if (attempts.size() > 10) {
            return 1;
        } else {
            return attempts.stream()
                    .max(Comparator.comparing(Attempt::getAttemptNumber))
                    .get()
                    .getScore();
        }
    }
}