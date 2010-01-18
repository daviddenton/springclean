package microtypes;

import java.io.File;

public class RootFile extends FileValue {

    private RootFile(File file) {
        super(file);
    }

    public static RootFile rootFile(File file) {
        return new RootFile(file);
    }
}