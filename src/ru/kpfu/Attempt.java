package ru.kpfu;

public class Attempt {

    private String lastName;
    private int taskNumber;
    private int attemptNumber;
    private int score;

    public Attempt(String[] attemptInfo) {
        if (attemptInfo.length != 4) {
            throw new RuntimeException("Incorrect attempt info");
        }

        this.lastName = attemptInfo[0];
        this.taskNumber = Integer.parseInt(attemptInfo[1]);
        this.attemptNumber = Integer.parseInt(attemptInfo[2]);
        this.score = Integer.parseInt(attemptInfo[3]);
    }

    public String getLastName() {
        return lastName;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public int getScore() {
        return score;
    }
}
