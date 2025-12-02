package streams;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class StreamsImpl implements streams.Streams  {

    private static StreamsImpl instance;

    private StreamsImpl(){}

    public static StreamsImpl getInstance() {
        if (instance == null) {
            instance = new StreamsImpl();
        }
        return instance;
    }

    private final Random rand = new Random();

    @Override
    public java.util.stream.Stream<Integer> tenRandomNumbers() {
        return Stream.generate(() -> rand.nextInt(100)) //generator supplier (kein Input aber output) // consumer (msg) -> System.out.println(msg))
            .limit(10);
    }

    @Override
    public java.util.stream.Stream<Integer> tenEvenRandomNumbers() {
        return Stream.generate(() -> rand.nextInt(100)) //generator supplier (kein Input aber output) // consumer (msg) -> System.out.println(msg))
            .limit(10)
            .map(n -> (n / 2) * 2);  // zu nächster gerader Zahl machen
    }

    @Override
    public java.util.stream.Stream<Integer> tenSortedEvenRandomNumbers() {
        return Stream.generate(() -> rand.nextInt(100)) //generator supplier (kein Input aber output) // consumer (msg) -> System.out.println(msg))
            .limit(10)
            .map(n -> (n / 2) * 2)  // zu nächster gerader Zahl machen
            .sorted();             
    }

    @Override
    public java.util.List<Integer> filteredNumbers(String filter, int limit) {
        
        if (filter == null || filter.isEmpty() || !filterFunctions.containsKey(filter)) {
            throw new IllegalArgumentException(
                "filter null, empty or unknown: \"" + filter + "\""
            );
        }
        
        if (limit < 0) {
            throw new IllegalArgumentException(
                "negative limit: " + limit
            );
        }
        Function<Integer, Boolean> fn = filterFunctions.get(filter);

        // Generiere Random-Zahlen und filtere
        return new Random()
                .ints(0, 1000)         // Werte von 0 bis 999
                .filter(fn::apply)     // Filterfunktion anwenden
                .limit(limit)          // gewünschte Anzahl
                .boxed()
                .toList();
    }

    @Override
    public List<String> filteredNames(List<String> names, String regex) {

        if(names == null || regex == null) {
            throw new IllegalArgumentException("names or regex argument is null.");
        }

        Pattern pattern = Pattern.compile(regex);
        return names.stream()
            .filter(name -> pattern.matcher(name).matches())
            .toList();
    }

    @Override
    public List<String> sortedNames(List<String> names, int limit) {
        if(names == null) {
            throw new IllegalArgumentException("names argument is null.");
        }
        if(limit < 0) {
            throw new IllegalArgumentException("limit argument is negative: " + limit + ".");
        }
        
        return names.stream()
            .sorted()       // natural (alphabetical) order
            .limit(limit)   // take first 'limit' names
            .toList();
    }

    @Override
    public List<String> sortedNamesByLength(List<String> names) {
        if(names == null) {
            throw new IllegalArgumentException("names argument is null.");
        }
        return names.stream()
            .sorted(Comparator
                    .comparingInt(String::length)      // primary: length
                    .thenComparing(Comparator.naturalOrder()))  // secondary: alphabetical
            .toList();
    }

    @Override
    public long calculateOrderValue(List<Order> orders) {
        if (orders == null) {
            throw new IllegalArgumentException("orders argument is null.");
        }
        return orders.stream()
                .mapToLong(o -> o.units() * o.unitPrice())
                .sum();
    }

    @Override
    public List<Order> sortOrdersByValue(List<Order> orders) {
        return orders.stream()
                .sorted(Comparator
                        .comparingLong((Order o) -> o.units() * o.unitPrice())
                        .reversed())
                .toList();
    }
}
