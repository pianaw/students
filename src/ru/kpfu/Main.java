package ru.kpfu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class Main {

    public static void main(String[] args) {
//        Map<String, Integer> result1 = task2("src/ru/kpfu/resources/attempts-1.txt");
//        System.out.println(result1);

        Map<String, Map<Integer, List<Attempt>>> result2 = task4(
                "src/ru/kpfu/resources/attempts-1.txt",
                "src/ru/kpfu/resources/attempts-2.txt"
        );
        System.out.println(result2);
    }

    public static Map<String, Integer> task2(String fileName) {
        Map<String, Map<Integer, List<Attempt>>> attemptsByStudent = getAttemptsByStudentFromFile(fileName);

        return getScoreSumByStudent(attemptsByStudent);
    }

    public static Map<String, Map<Integer, List<Attempt>>> task4(String fileName1, String fileName2) {
        Map<String, Map<Integer, List<Attempt>>> attemptsByStudents1 = getAttemptsByStudentFromFile(fileName1);
        Map<String, Map<Integer, List<Attempt>>> attemptsByStudents2 = getAttemptsByStudentFromFile(fileName2);

        return mergeAttempts(attemptsByStudents1, attemptsByStudents2);
    }

    public static Map<String, Map<Integer, List<Attempt>>> getAttemptsByStudentFromFile(String fileName) {
        Map<String, Map<Integer, List<Attempt>>> result = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return br.lines()
                    .map(line -> line.split(" "))
                    .map(Attempt::new)
                    .collect(groupingBy(Attempt::getLastName,
                            groupingBy(Attempt::getTaskNumber))
                    );

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, Integer> getScoreSumByStudent(Map<String, Map<Integer, List<Attempt>>> attemptsByStudent) {
        return attemptsByStudent.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, studentAttemptsByTask -> studentAttemptsByTask.getValue().values().stream()
                        .map(Main::getAttemptScore)
                        .reduce(Integer::sum).get()));
    }

    public static Integer getAttemptScore(List<Attempt> attempts) {
        return attempts.stream().max(Comparator.comparing(Attempt::getScore)).get().getScore();
    }

    public static Map<String, Map<Integer, List<Attempt>>> mergeAttempts(Map<String, Map<Integer, List<Attempt>>> attempts1,
                                                                         Map<String, Map<Integer, List<Attempt>>> attempts2) {
        attempts1.forEach((student, task1) -> {
            if (attempts2.containsKey(student)) {
                Map<Integer, List<Attempt>> task2 = attempts2.get(student);
                task1.forEach((taskNumber, value) -> {
                    if (task2.containsKey(taskNumber)) {
                        task2.merge(taskNumber,
                                value,
                                (attempt1, attempt2) -> Stream.concat(
                                                attempt1.stream(),
                                                attempt2.stream())
                                        .collect(Collectors.toList())
                        );
                    } else {
                        task2.put(taskNumber, value);
                    }
                });
            } else {
                attempts2.put(student, task1);
            }
        });

        return attempts2;
    }
}