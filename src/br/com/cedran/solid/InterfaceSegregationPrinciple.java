package br.com.cedran.solid;

/**
 * ISP is a basic recommendation to split big interfaces into more specialized smaller interfaces, so that you avoid
 * subclasses which don't need some methods having to implement them with dumb implementation  (YAGNI - You ain't gonna need it)
 * and avoid client which are going to be provided those interfaces from accessing methods they shouldn't.
 *
 */
public class InterfaceSegregationPrinciple {

    class Document{

    }

    /**
     * When implementing this interface, you could be seeking to develop a simple printer which does not have any
     * other functionality than printing out documents. What would you do with the fax and scan methods in that case?
     *
     * When providing this interface to a client supposed to only be allowed to print out documents, rather than fax and scan
     * even if the implementation is of a Multi-Function printer. How could you prevent him from using undesired functionalities?
     */
    interface Machine {
        void print(Document document);
        void fax(Document document);
        void scan(Document document);
    }

    class SimplePrinter implements Machine {

        @Override
        public void print(Document document) {

        }

        @Override
        public void fax(Document document) {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public void scan(Document document) {
            throw new RuntimeException("Not implemented");
        }
    }

    /**
     * It would be better to have multiple interfaces and let the implementation decide on which ones it needs to implement.
     */

    interface Printer {
        void print(Document document);
    }

    interface Scanner {
        void scan(Document document);
    }

    class OldPrinter implements Printer {

        @Override
        public void print(Document document) {

        }
    }

    class MultiFunctionPrinter implements Printer, Scanner {

        @Override
        public void print(Document document) {

        }

        @Override
        public void scan(Document document) {

        }
    }
}
