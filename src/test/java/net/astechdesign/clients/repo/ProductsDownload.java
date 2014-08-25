package net.astechdesign.clients.repo;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductsDownload {

    @Test
    public void download() throws Exception {


        String[] categories = {"gluten-free",
                "fish",
                "poultry-specialities",
                "meat-poultry",
                "fish-specialities",
                "shellfish",
                "meat-specialities",
                "desserts",
                "on-sale",
                "buffet-starters",
                "hors-doeuvres",
                "childrens",
                "vegetarian"};

        for (String cat : categories) {
        URL url = new URL("http://diningsolutionsdirect.co.uk/product-category/" + cat + "/");

        List<String> list = IOUtils.readLines((InputStream) url.openConnection().getContent());

        String name = "";
        String id = "";
        for (String line : list) {
            if (line.contains("below-thumb")) {
                Matcher matcher = Pattern.compile("thumb..(.*)..strong").matcher(line);
                matcher.find();
                name = matcher.group(1);
            }
            if (line.contains("data-product_id") ) {
                Matcher matcher = Pattern.compile("data-product_id=\"(\\d{4})\"").matcher(line);
                if (matcher.find()) {
                    id = matcher.group(1);
                    System.out.println(id + "|" + cat  + "|" + name);
                } else {
                    System.out.println(line);
                }
            }
        }
    }
    }
}
