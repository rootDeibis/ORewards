package me.rootdeibis.orewards.common.function;

public class Functions {

    @FunctionalInterface
    public static interface EFunction {
        public void apply();
    }
    @FunctionalInterface
    public static interface Function<In1> {
        public void apply(In1 in1);
    }

    @FunctionalInterface
    public static interface R1Function<In1, Out> {
        public Out apply(In1 in1);
    }

    @FunctionalInterface
    public static interface RFunction<Out> {
        public Out apply();
    }

    @FunctionalInterface
    public static interface R2Function<In1, In2, Out> {
        public Out apply(In1 in1, In2 in2);
    }
}
