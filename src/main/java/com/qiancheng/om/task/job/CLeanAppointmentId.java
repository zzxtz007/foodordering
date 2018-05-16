package com.qiancheng.om.task.job;

import com.qiancheng.om.common.utils.AppContextUtils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author XLY
 */
public class CLeanAppointmentId {

    private static String PATH = null;

    public void execute() {
        if (PATH == null) {
            PATH = AppContextUtils.getClasspath("/appointment-id.txt");
        }

        FileWriter fileWriter = null;
        try {

            fileWriter = new FileWriter(PATH, false);
            fileWriter.write("1");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
