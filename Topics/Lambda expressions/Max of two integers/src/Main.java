import java.util.function.IntBinaryOperator;

class Operator {

    public static IntBinaryOperator binaryOperator = (int x, int y) -> {
        if (x > y) {
            return x;
        } else {
            return y;
        }
    };
}