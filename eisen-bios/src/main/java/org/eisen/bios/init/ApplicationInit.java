package org.eisen.bios.init;

import java.util.TimeZone;

public class ApplicationInit {
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00"));
    }
}
