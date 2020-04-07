package br.com.cedran.solid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * In a nutshell, Single Responsibility Principle states that a class should have only one reason to change.
 * Following this principle helps to keep classes small, easier to understand and maintain and easy to cover with unit tests.
 * It is also a create help to separate concerns.
 * Keeping classes focused and specialized also avoid the so called God Classes.
 */
public class SingleResponsibilityPrinciple {
    {
        /**
         * In this example, this class is responsible for keeping records of a Journal entry, but not only it is performing this operation,
         * but it is also taking care of saving entries to a file. It is a clear violation of SPR because it has to reasons to change:
         * 1. In case business rules concerning journal entry chaanges
         * 2. How and where to save the entries
         */
        class Journal {
            private List<String> entry = new ArrayList<>();

            public void addNewItem(String item) {
                entry.add(item);
            }

            public void removeItem(Integer item) {
                entry.remove(item);
            }

            public void saveToFile(String fileName) {
                File file = new File(fileName);
                try (PrintStream out = new PrintStream(file)) {
                    out.println(toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    {
        /**
         * In this example, the responsibilities were segregated in two specialized classes, adhering to the SRP.
          */
        class Journal {
            private List<String> entry = new ArrayList<>();

            public void addNewItem(String item) {
                entry.add(item);
            }

            public void removeItem(Integer item) {
                entry.remove(item);
            }
        }

        class Persistence {
            public void saveToFile(String fileName, Journal journal) {
                File file = new File(fileName);
                try (PrintStream out = new PrintStream(file)) {
                    out.println(journal.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
