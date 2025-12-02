package numbers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class NumbersImpl implements Numbers{

    private static NumbersImpl instance;

    private NumbersImpl(){}

    public static NumbersImpl getInstance() {
        if (instance == null) {
            instance = new NumbersImpl();
        }
        return instance;
    }

    public long sum(int[] numbers){
        if (numbers == null){
            throw new IllegalArgumentException("illegal argument: null");
        }

        long sum = 0;
        for (int i : numbers) {
            sum += i;
        }
        return sum;
    }

    public long sum_positive_even_numbers(int[] numbers){
        if (numbers == null){
            throw new IllegalArgumentException("illegal argument: null");
        }
        long sum = 0;
        for (int i : numbers) {
            if(i%2 == 0 && i > 0) sum += i;
        }
        return sum;
    }

    public long sum_recursive(int[] numbers, int i) {
        if (numbers == null){
            throw new IllegalArgumentException("illegal argument: null");
        }
        if (i == numbers.length) {
            return 0;
        }
        return numbers[i] + sum_recursive(numbers, i + 1);
    }


    public int findFirst(int[] numbers, int x){
        if (numbers == null){
            throw new IllegalArgumentException("illegal argument: null");
        }
        for (int i = 0; i < numbers.length; i++){
            if (numbers[i] == x){
                return i;
            }
        }
        return -1;
    }

    public int findLast(int[] numbers, int x){
        if (numbers == null){
            throw new IllegalArgumentException("illegal argument: null");
        }
        int y = -1;
        for (int i = 0; i < numbers.length; i++){
            if (numbers[i] == x){
                y = i;
            }
        }
        return y;
    }

    public List<Integer> findAll(int[] numbers, int x){
        if (numbers == null){
            throw new IllegalArgumentException("illegal argument: null");
        }
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++){
            if (numbers[i] == x){
                ints.add(i);
            }
        }
        return ints;
    }

    public Set<Pair> findSums(int[] numbers, int sum) {
        if (numbers == null){
            throw new IllegalArgumentException("illegal argument: null");
        }
        Set<Pair> sums = new HashSet<>();
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == sum) {
                    int a = Math.min(numbers[i], numbers[j]);
                    int b = Math.max(numbers[i], numbers[j]);
                    sums.add(new Pair(a, b));
                }
            }
        }
        return sums;
    }

    public Set<Set<Integer>> findAllSums(int[] numbers, int targetSum) {
        if (numbers == null){
            throw new IllegalArgumentException("illegal argument: null");
        }
        Set<Set<Integer>> result = new HashSet<>();
        backtrack(numbers, 0, targetSum, new HashSet<>(), result);
        return result;
    }

    private void backtrack(int[] numbers, int start, int remaining, Set<Integer> current, Set<Set<Integer>> result) {
        if (remaining == 0) {
            result.add(new HashSet<>(current));
            return;
        }
        if (remaining < 0) {
            return;
        }

        for (int i = start; i < numbers.length; i++) {
            int num = numbers[i];
            current.add(num);
            backtrack(numbers, i + 1, remaining - num, current, result);
            current.remove(num); // backtrack
        }
    }
}
