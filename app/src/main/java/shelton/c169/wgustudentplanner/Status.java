package shelton.c169.wgustudentplanner;

import java.util.ArrayList;
import java.util.List;

public enum Status {

    PLAN_TO_TAKE("Plan to Take"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    DROPPED("Dropped");

    private String status;

    Status(String status) {

        this.status = status;
    }

    public String getStatus() {

        return status;
    }

    public static String[] getStatusArray() {

        List<String> text = new ArrayList<>();
        for (Status s : Status.values()) {
            text.add(s.getStatus());
        }

        String[] values = new String[text.size()];
        values = text.toArray(values);
        return values;
    }

}
