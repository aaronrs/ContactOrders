package net.astechdesign;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    @Ignore
    @Test
    public void processProducts() throws Exception {
        List<String> lines = FileUtils.readLines(new File("/home/aaron/dev/java/orders/src/main/resources/db/data/products"));
        ArrayList<String> ids = Lists.newArrayList();
        int count = 0;
        for (String line : lines) {
            String[] values = line.split("\\|");
            String description = values[2];
            System.out.println("" + count++ + "," + extract(description) + "," + values[2] + ",\"\",2014-11-11");
        }
    }

    private String extract(String description) {
        String result = "";
        for (String word : description.split(" ")) {
            result += String.valueOf(word.toCharArray()[0]);
        }
        return result;
    }
}
