/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ddevil.core.utils;

import java.io.File;

/**
 *
 * @author Selma
 */
public class FileUtils {

    public static boolean moveFileToDirectory(File file, File folder) throws SecurityException {
        return file.renameTo(
                new File(folder.getAbsolutePath() + file.getName())
        );
    }
}
