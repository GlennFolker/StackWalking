package stack;

public class StackWalking {
    public static void main(String[] args) {
        new StackWalking();
    }

    private StackWalking() {
        new StackTest().exec();
    }

    private <T> void call(T thrown) {
        StringBuilder trace = new StringBuilder();
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for(int i = elements.length - 1; i >= 0; i--) {
            StackTraceElement stack = elements[i];

            trace.append(String.format(
                "\n\tat %s.%s(%s)",

                stack.getClassName(),
                stack.getMethodName(),
                stack.isNativeMethod()
                ?   "Native method"
                :   (stack.getFileName() + ":" + stack.getLineNumber())
            ));
        }

        System.out.println(String.format("Caller: %s", Utils.getCallerClass()));
        System.out.println(String.format("Hash  : %s", Utils.getCallerClass().hashCode()));
        System.out.println(String.format("Stack :\n%s%s", thrown.getClass().getName(), trace.toString()));
    }

    private class StackTest {
        private void exec() {
            call(this);
        }
    }

    public static class Utils {
        public static Class<?> getCallerClass() {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            try {
                return Class.forName(stack[stack.length - 3].getClassName(), true, Thread.currentThread().getContextClassLoader());
            } catch(ClassNotFoundException e) {
                return null;
            }
        }
    }
}
