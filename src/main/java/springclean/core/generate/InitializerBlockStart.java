package springclean.core.generate;

import org.daisychain.source.util.DynamicContent;
import org.daisychain.source.util.IndentingStringWriter;

import java.io.IOException;

public class InitializerBlockStart implements DynamicContent {
    public void append(IndentingStringWriter writer) throws IOException {
        writer.indent().append(" {{").newLine();
    }
}
