package com.ex.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class HTMLWriter {

    private String filePath;

    public HTMLWriter(String filePath) {
        this.filePath = filePath;
    }

    public String getHtmlString() {
        try (FileInputStream iostream = new FileInputStream(this.filePath); Scanner scan = new Scanner(iostream)) {
            StringBuilder html = new StringBuilder();

            while (scan.hasNextLine()) {
                html.append(scan.nextLine());
            }

            return html.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
