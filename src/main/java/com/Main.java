package com;

import com.cardio_generator.*;
import com.data_management.*;
import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("DataStorage")) {
            DataStorage.main(new String[]{});
        } else {
            HealthDataSimulator.getInstance().main(args);
        }
    }
}
