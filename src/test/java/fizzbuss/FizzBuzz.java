package fizzbuss;

public class FizzBuzz {
    public String get(int i) {
        return (i % 15 == 0) ? "fizzbuzz" :
               (i % 3 == 0) ? "fizz" :
               (i % 5 == 0) ? "buzz" : "" + i;
    }
}
