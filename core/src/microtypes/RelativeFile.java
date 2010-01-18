package microtypes;

import java.io.File;

public class RelativeFile extends FileValue {

    private final RootFile rootFile;

    private RelativeFile(RootFile rootFile, File target) {
        super(target);
        this.rootFile = rootFile;
    }

    public static RelativeFile relativeFile(RootFile rootFile, File target) {
        return new RelativeFile(rootFile, target);
    }

}
