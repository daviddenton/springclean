package springclean.core.domain;

public interface IdentifiedBean extends Bean, Identified {
    boolean isKnownAs(SpringId springId);
}