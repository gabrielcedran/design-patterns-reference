package br.com.cedran.creational.singleton;

/**
 * There might be cases where one does not want the singletons to be instantiated upfront, but only when they are needed.
 *
 * Solving this problem is quite straightforward, but it introduces a potential race condition problem, because it could
 * end up in a situation where two threads are trying to check the singleton existence at the same time for the first time
 * and they could enter the creation block, creating two instances of the singleton. Simply declaring the getInstance "synchronized"
 * could lead to performance issues.
 * Potential solutions:
 * 1. double-checked locking (a bit out dated)
 * 2. Inner Static Singleton
 *
 */
public class LazyInitializationAndThreadSafety {
    public static void main(String[] args) {
        System.out.println("Before getting the singleton instance");
        LazySingleton.getInstanceInnerStaticSingleton();
        System.out.println("After getting the singleton instance");
        System.out.println(LazySingleton.getInstanceInnerStaticSingleton() == LazySingleton.getInstanceInnerStaticSingleton());
    }
}

class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {
        System.out.println("Initializing lazy singleton.");
    }

    private static class Impl {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    // Method with potential race condition issue
    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    // Method with double-checked locking. It does not have performance issue since it only synchronized the
    // instance create if in fact needed.
    public static LazySingleton getInstanceDoubleCheckedLocking() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }

    // This solution is simpler than the previous one and does not have race condition problem, since it is not the thread
    // obtaining the singleton which is creating it and it is only created once the static method is accessed.
    public static LazySingleton getInstanceInnerStaticSingleton() {
        return Impl.INSTANCE;
    }
}
