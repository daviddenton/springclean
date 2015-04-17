package microtypes;

import java.io.File;
import java.io.IOException;

public abstract class FileValue {

    protected final File value;
    private final String canonicalPath;

    protected FileValue(File value) {
        try {
            this.value = value;
            this.canonicalPath = value.getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalArgumentException("Querying filesystem about file " + value.getAbsolutePath(), e);
        }
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        FileValue fileValue = (FileValue) that;

        if (canonicalPath != null ? !canonicalPath.equals(fileValue.canonicalPath) : fileValue.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? canonicalPath.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.getClass() + ": value="+ value.getAbsolutePath();
    }
}
