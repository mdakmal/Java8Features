import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.Optional;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Java8Features {

    // Interface with default and static methods
    interface MyInterface {
        default void defaultMethod() {
            System.out.println("Default method in interface");
        }

        static void staticMethod() {
            System.out.println("Static method in interface");
        }
    }

    // Functional Interface
    @FunctionalInterface
    interface MyFunctionalInterface {
        void execute();
    }

    public static void main(String[] args) {

        // Default method inside an interface
        MyInterface myInterface = new MyInterface() {}; // Anonymous class
        myInterface.defaultMethod();

        // Static method inside an interface
        MyInterface.staticMethod();

        // Lambda expression
        MyFunctionalInterface functionalInterface = () -> System.out.println("Lambda expression executed");
        functionalInterface.execute();

        // Predicates (Functional interface example)
        Predicate<Integer> isEven = (n) -> n % 2 == 0;
        System.out.println("Is 4 even? " + isEven.test(4));

        // Method reference (Double colon operator)
        List<String> names = Arrays.asList("John", "Jane", "Jack", "Doe");
        names.forEach(System.out::println);  // Method reference example

        // Constructor reference
        Supplier<ArrayList<String>> arrayListSupplier = ArrayList::new;
        ArrayList<String> newList = arrayListSupplier.get();
        newList.add("Hello");
        newList.forEach(System.out::println);

        // Stream API
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Stream filter, map, count
        long count = numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * 2)
                .count();
        System.out.println("Count of even numbers doubled: " + count);

        // Stream filter and lazy evaluation
        Stream<Integer> filteredStream = numbers.stream().filter(n -> {
            System.out.println("Filter: " + n);
            return n % 2 == 0;
        });
        System.out.println("Filtered stream is not executed until terminal operation");

        // Stream forEach
        numbers.stream().forEach(n -> System.out.println("Number: " + n));

        // Stream min, max, sorted, distinct
        Optional<Integer> min = numbers.stream().min(Comparator.naturalOrder());
        Optional<Integer> max = numbers.stream().max(Comparator.naturalOrder());
        System.out.println("Min: " + min.orElse(-1) + ", Max: " + max.orElse(-1));

        List<Integer> distinctSorted = numbers.stream().distinct().sorted().collect(Collectors.toList());
        System.out.println("Distinct and sorted numbers: " + distinctSorted);

        // Stream peek and skip
        numbers.stream().peek(n -> System.out.println("Peeked: " + n)).skip(5).forEach(System.out::println);

        // Stream range and rangeClosed
        IntStream.range(1, 5).forEach(System.out::println); // Prints 1 to 4
        IntStream.rangeClosed(1, 5).forEach(System.out::println); // Prints 1 to 5

        // Stream reduce
        int sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println("Sum of numbers: " + sum);

        // Stream Optional
        Optional<Integer> firstEven = numbers.stream().filter(isEven).findFirst();
        firstEven.ifPresent(e -> System.out.println("First even number: " + e));

        // Stream toSet and toMap
        Set<Integer> evenSet = numbers.stream().filter(isEven).collect(Collectors.toSet());
        System.out.println("Even numbers in set: " + evenSet);

        Map<String, Integer> nameLengthMap = names.stream().collect(Collectors.toMap(name -> name, String::length));
        System.out.println("Name to length map: " + nameLengthMap);

        // Stream averaging and summarizing
        Double average = numbers.stream().collect(Collectors.averagingInt(Integer::intValue));
        System.out.println("Average: " + average);

        IntSummaryStatistics stats = numbers.stream().collect(Collectors.summarizingInt(Integer::intValue));
        System.out.println("Summary statistics: " + stats);

        // Stream and File operations
        try (Stream<String> lines = Files.lines(Paths.get("example.txt"))) {
            lines.filter(line -> line.contains("Java")).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Stream map and flatMap
        List<List<Integer>> listOfLists = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );
        List<Integer> flatMapped = listOfLists.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println("FlatMapped list: " + flatMapped);

        // Parallel Stream
        numbers.parallelStream().forEach(n -> System.out.println("Parallel stream number: " + n));

        // Java 8 Date Time API
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println("Current date: " + date);
        System.out.println("Current time: " + time);
        System.out.println("Current date and time: " + dateTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Formatted DateTime: " + dateTime.format(formatter));
    }
}
