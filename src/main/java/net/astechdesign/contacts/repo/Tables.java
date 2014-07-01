package net.astechdesign.contacts.repo;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Tables {
    public final URL resource;

    public Tables(URL resource) throws IOException {
        this.resource = resource;
        buildTables();
    }

    public void buildTables() throws IOException {
        File[] tables = new File(resource.getFile(), "tables").listFiles();

        List<Table> tableList = Lists.newArrayList();
        for (File file : tables) {
            tableList.add(new Table(file.getName(), FileUtils.readLines(file)));
        }

        System.out.println(resource);
    }

    public List<Table> get() {
        List<Table> tables = Lists.newArrayList();
        return tables;
    }
}
