package springclean.xml;

public class XomProcessingException extends RuntimeException {

    public XomProcessingException(String s) {
        super(s);
    }

    public XomProcessingException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public XomProcessingException(Throwable throwable) {
        super(throwable);
    }
}
