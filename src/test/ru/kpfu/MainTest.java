package test.ru.kpfu;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.kpfu.Attempt;
import ru.kpfu.Main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * Main Tester.
 * @version 1.0
 */
public class MainTest {

    public List<Attempt> prepareAttempts(int number) {
        List<Attempt> attempts = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            attempts.add(new Attempt(new String[]{"Иванов", "1", String.valueOf(i), String.valueOf(10 + i)}));
        }
        return attempts;
    }

    @Test
    public void testGetAttemptScoreBetweenFiveAndTen() {
        final int NUMBER_ATTEMPT = 6;
        List<Attempt> attempts = prepareAttempts(NUMBER_ATTEMPT);

        int actual = Main.getAttemptScore(attempts);
        int expected = 7;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAttemptScoreWithNumberAttemptMoreThanTen() {
        final int NUMBER_ATTEMPT = 11;
        List<Attempt> attempts = prepareAttempts(NUMBER_ATTEMPT);

        int actual = Main.getAttemptScore(attempts);
        int expected = 1;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAttemptWithNumberAttemptLessThanFive() {
        final int NUMBER_ATTEMPT = 4;
        List<Attempt> attempts = prepareAttempts(NUMBER_ATTEMPT);

        int actual = Main.getAttemptScore(attempts);
        int expected = 13;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAttemptWithNumberAttemptEqualZero() {
        Assert.assertThrows(NoSuchElementException.class, () -> Main.getAttemptScore(new LinkedList<>()));
    }
} 
