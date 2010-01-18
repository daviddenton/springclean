package springclean.exception;

public class Defect extends RuntimeException {

    public Defect() {
        super();   
    }

    public Defect(String s) {
        super(s);   
    }

    public Defect(String s, Throwable throwable) {
        super(s, throwable);   
    }

    public Defect(Throwable throwable) {
        super(throwable);   
    }
}
