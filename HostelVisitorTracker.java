import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/* ================= Visitor Class ================= */
class Visitor {

    private String visitorName;
    private String hostName;
    private String purpose;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private long duration; // in minutes

    public Visitor(String visitorName, String hostName, String purpose,
                   LocalDateTime entryTime, LocalDateTime exitTime, long duration) {
        this.visitorName = visitorName;
        this.hostName = hostName;
        this.purpose = purpose;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.duration = duration;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public String getHostName() {
        return hostName;
    }

    public String getPurpose() {
        return purpose;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public long getDuration() {
        return duration;
    }
}

/* ================= Stay Duration Calculator ================= */
class StayDurationCalculator {

    public static long calculateMinutes(LocalDateTime entryTime, LocalDateTime exitTime) {
        return Duration.between(entryTime, exitTime).toMinutes();
    }
}

/* ================= Visitor List Viewer (Module 4) ================= */
class VisitorListViewer {

    public static void displayVisitors(List<Visitor> visitors) {
        if (visitors.isEmpty()) {
            System.out.println("No visitor records available.");
            return;
        }

        System.out.println("\n----- Visitor List -----");
        for (Visitor v : visitors) {
            System.out.println("Visitor Name : " + v.getVisitorName());
            System.out.println("Host Name    : " + v.getHostName());
            System.out.println("Purpose      : " + v.getPurpose());
            System.out.println("Entry Time   : " + v.getEntryTime());
            System.out.println("Exit Time    : " + v.getExitTime());
            System.out.println("Duration     : " + v.getDuration() + " minutes");
            System.out.println("------------------------");
        }
    }
}

/* ================= Visit File Storage (Module 5) ================= */
class VisitFileStorage {

    private static final String FILE_NAME = "visitor_log.txt";

    public static void saveToFile(List<Visitor> visitors) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            for (Visitor v : visitors) {
                writer.write(
                        v.getVisitorName() + "," +
                        v.getHostName() + "," +
                        v.getPurpose() + "," +
                        v.getEntryTime() + "," +
                        v.getExitTime() + "," +
                        v.getDuration()
                );
                writer.newLine();
            }

            System.out.println("Visitor data saved to file successfully.");

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}

/* ================= Main Class ================= */
public class HostelVisitorTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Visitor> visitorList = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Hostel Visitor Time Tracker ---");
            System.out.println("1. Add Visitor Entry");
            System.out.println("2. View Visitor List");
            System.out.println("3. Save Visitor Data to File");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Visitor Name: ");
                    String visitorName = sc.nextLine();

                    System.out.print("Enter Host Name: ");
                    String hostName = sc.nextLine();

                    System.out.print("Enter Purpose of Visit: ");
                    String purpose = sc.nextLine();

                    LocalDateTime entryTime = LocalDateTime.now();
                    System.out.println("Entry Time Recorded: " + entryTime);

                    System.out.print("Press ENTER when visitor exits...");
                    sc.nextLine();

                    LocalDateTime exitTime = LocalDateTime.now();
                    System.out.println("Exit Time Recorded: " + exitTime);

                    long duration =
                            StayDurationCalculator.calculateMinutes(entryTime, exitTime);

                    Visitor visitor = new Visitor(
                            visitorName, hostName, purpose,
                            entryTime, exitTime, duration
                    );

                    visitorList.add(visitor);
                    System.out.println("Visitor entry added successfully.");
                    break;

                case 2:
                    VisitorListViewer.displayVisitors(visitorList);
                    break;

                case 3:
                    VisitFileStorage.saveToFile(visitorList);
                    break;

                case 4:
                    System.out.println("Exiting system. Thank you!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
