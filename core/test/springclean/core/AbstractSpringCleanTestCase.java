package springclean.core;

import springclean.util.ResourceLoader;

public abstract class AbstractSpringCleanTestCase {
    protected final ResourceLoader rl = new ResourceLoader(this);
}
