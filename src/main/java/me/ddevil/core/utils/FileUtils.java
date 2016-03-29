/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Selma
 */
public class FileUtils {

    public static void moveFileToDirectory(File file, File folder) throws SecurityException, IOException {
        Files.move(file.toPath(), folder.toPath());
    }
}
