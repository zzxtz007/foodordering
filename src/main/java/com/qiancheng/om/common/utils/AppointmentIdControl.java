package com.qiancheng.om.common.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author XLY
 */
public class AppointmentIdControl {

    private static String FILE_PATH = null;

    public synchronized static int getAppointmentId() {

        if (FILE_PATH == null) {
            FILE_PATH = AppContextUtils.getClasspath("/appointment-id.txt");
        }

        int appointmentId = 0;
        FileReader fileReader = null;
        File file = new File(FILE_PATH);
        try {
            if (!file.isFile()) {
                increaseAppointmentId(0);
            }

            fileReader = new FileReader(FILE_PATH);

            int c;
            StringBuilder stringBuilder = new StringBuilder();

            while ((c = fileReader.read()) != -1) {
                stringBuilder.append((char) c);
            }

            appointmentId = Integer.parseInt(stringBuilder.toString());
            increaseAppointmentId(Integer.valueOf(stringBuilder.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return appointmentId;
    }


    public synchronized static void increaseAppointmentId(int count) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(FILE_PATH, false);

            count += 1;
            fileWriter.write("" + count);
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
