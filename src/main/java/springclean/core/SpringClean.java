package springclean.core;

import org.daisychain.source.util.IndentingStringWriter;
import springclean.core.domain.ApplicationContext;
import springclean.core.generate.ApplicationContextClassBuilder;
import springclean.core.xml.XmlApplicationContext;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class SpringClean {
    public static void main(String[] argv) throws Exception {
        List<File> springFiles = new ArrayList<File>();
        File outputDir = new File(".");
        String packageName = "tmp";

        Iterator<String> args = Arrays.asList(argv).iterator();
        while (args.hasNext()) {
            String arg = args.next();

            if (arg.equals("-d") || arg.equals("--output-directory")) {
                outputDir = new File(args.next());
            } else if (arg.equals("-p") || arg.equals("--package")) {
                packageName = args.next();
            } else if (arg.equals("--help")) {
                help();
                return;
            } else if (arg.startsWith("-")) {
                System.err.println("unknown option " + arg);
                help();
                System.exit(1);
            } else {
                springFiles.add(new File(arg));
            }
        }

        if (springFiles.isEmpty()) {
            System.err.println("no input files");
            help();
            System.exit(1);
        }

        for (File file : springFiles) {
            clean(file, packageName, outputDir);
        }
    }

    private static void help() {
        System.err.println("args: [<option> ...] <spring-file> ... ");
        System.err.println("options: ");
        System.err.println("--help");
        System.err.println("{-o |--output-directory} <output-directory>");
        System.err.println("{-p | --package} <package-name>");
    }


    private static void clean(File springFile, String packageName, File outputDir) throws Exception {
        ApplicationContext applicationContext = new XmlApplicationContext(springFile);
        File outputFile = new File(outputDir, applicationContext.name() + ".java");

        IndentingStringWriter writer = new IndentingStringWriter(new FileWriter(outputFile));
        try {
            new ApplicationContextClassBuilder(applicationContext).build().appendSource(writer);
        } finally {
            writer.close();
        }
    }
}
