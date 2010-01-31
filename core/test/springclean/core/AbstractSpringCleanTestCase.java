package springclean.core;

import springclean.core.util.ResourceLoader;

public abstract class AbstractSpringCleanTestCase {
    protected final ResourceLoader rl = new ResourceLoader(this);
}
