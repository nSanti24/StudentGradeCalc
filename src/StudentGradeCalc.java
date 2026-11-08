import java.util.Scanner;

public class StudentGradeCalc {

    private static final Scanner scanner = new Scanner(System.in);

    // SECTION INFORMATION
    static String section;
    static String subject;
    static int studentCount;

    static String[] studentNames;
    static double[] prelimGrades;
    static double[] midtermGrades;
    static double[] finalGrades;
    static double[] calculatedGrades;
    static char[] letterGrades;

    public static void main(String[] args) {

        // Get section details from user before running menu
        setupSectionInformation();

        boolean running = true;

        do {
            displayMenu();

            int choice = getValidInteger(
                "Enter your choice (1-3): ", 1, 3
            );

            switch (choice) {

                case 1:
                    insertGrades();
                    gradeOptionsMenu();
                    break;

                case 2:
                    displayInstructions();
                    break;

                case 3:
                    System.out.println("\nExiting program. Thank you!");
                    running = false;
                    break;
            }

        } while (running);

        scanner.close();
    }

    // ============= SETUP SECTION INFO =============

    public static void setupSectionInformation() {

        System.out.println("========================================");
        System.out.println("       SETUP SECTION INFORMATION");
        System.out.println("========================================");

        System.out.print("Enter Section Name: ");
        section = scanner.nextLine();

        System.out.print("Enter Subject Code: ");
        subject = scanner.nextLine();

        studentCount = getValidInteger(
            "Enter Number of Students (1-100): ", 1, 100
        );

        // Resize arrays to match user input
        studentNames = new String[studentCount];
        prelimGrades = new double[studentCount];
        midtermGrades = new double[studentCount];
        finalGrades = new double[studentCount];
        calculatedGrades = new double[studentCount];
        letterGrades = new char[studentCount];
    }

    // ============= MAIN MENU =============

    public static void displayMenu() {

        System.out.println("\n========================================");
        System.out.println("       STUDENT GRADE CALCULATOR");
        System.out.println("========================================");
        System.out.println("[1] Insert Grades / Calculate");
        System.out.println("[2] View Rubric & Weight Breakdown");
        System.out.println("[3] Exit");
        System.out.println("----------------------------------------");
    }

    // ============= INSERT GRADES =============

    public static void insertGrades() {

        System.out.println("\n========================================");
        System.out.println("          STUDENT INFORMATION");
        System.out.println("========================================");

        System.out.println("Section: " + section);
        System.out.println("Subject: " + subject);
        System.out.println("Number of Students: " + studentCount);

        // Input grades for all students
        for (int i = 0; i < studentCount; i++) {

            System.out.println("\n----------------------------------------");
            System.out.println("STUDENT " + (i + 1));
            System.out.println("----------------------------------------");

            System.out.print("Enter Student Name: ");
            studentNames[i] = scanner.nextLine();

            prelimGrades[i] = getValidScore(
                "Enter Prelim Grade (0-100): ", 0, 100
            );

            midtermGrades[i] = getValidScore(
                "Enter Midterm Grade (0-100): ", 0, 100
            );

            finalGrades[i] = getValidScore(
                "Enter Final Grade (0-100): ", 0, 100
            );

            // Calculate the student's average
            calculatedGrades[i] = calculateAverage(
                prelimGrades[i],
                midtermGrades[i],
                finalGrades[i]
            );

            // Determine letter grade
            letterGrades[i] = determineLetterGrade(
                calculatedGrades[i]
            );

            System.out.println("Grades successfully recorded!");
        }

        System.out.println("\nAll student grades have been calculated!");
    }

    // ============= GRADE OPTIONS MENU =============

    public static void gradeOptionsMenu() {

        boolean back = false;

        do {

            System.out.println("\n========================================");
            System.out.println("             GRADE OPTIONS");
            System.out.println("========================================");
            System.out.println("[1] Show Calculated Grades");
            System.out.println("[2] Insert Grades Again");
            System.out.println("[3] Back to Main Menu");
            System.out.println("----------------------------------------");

            int choice = getValidInteger(
                "Enter your choice (1-3): ", 1, 3
            );

            switch (choice) {

                case 1:
                    displayCalculatedGrades();
                    break;

                case 2:
                    insertGrades();
                    break;

                case 3:
                    back = true;
                    break;
            }

        } while (!back);
    }

    // ============= CALCULATE AVERAGE =============

    public static double calculateAverage(
        double prelim, double midterm, double finalGrade) {

        return (prelim + midterm + finalGrade) / 3.0;
    }

    // ============= DETERMINE LETTER GRADE =============

    public static char determineLetterGrade(double score) {

        if (score >= 90) {
            return 'A';
        } else if (score >= 80) {
            return 'B';
        } else if (score >= 70) {
            return 'C';
        } else if (score >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    // ============= DISPLAY CALCULATED GRADES =============

    public static void displayCalculatedGrades() {

        System.out.println("\n========================================");
        System.out.println("          CALCULATED GRADES");
        System.out.println("========================================");

        System.out.println("Section: " + section);
        System.out.println("Subject: " + subject);

        System.out.printf("%-15s %-10s %-10s %-10s %-10s %-8s\n",
            "NAME", "PRELIM", "MIDTERM", "FINAL", "AVERAGE", "GRADE");

        System.out.println(
            "----------------------------------------------------------------"
        );

        for (int i = 0; i < studentCount; i++) {

            System.out.printf("%-15s %-10.2f %-10.2f %-10.2f %-10.2f %-8c\n",
                studentNames[i],
                prelimGrades[i],
                midtermGrades[i],
                finalGrades[i],
                calculatedGrades[i],
                letterGrades[i]
            );
        }

        System.out.println(
            "----------------------------------------------------------------"
        );
    }

    // ============= RUBRIC / INSTRUCTIONS =============

    public static void displayInstructions() {

        System.out.println("\n--- GRADING BREAKDOWN ---");
        System.out.println("Prelim Grade  : 33.33%");
        System.out.println("Midterm Grade : 33.33%");
        System.out.println("Final Grade   : 33.33%");
        System.out.println("Total         : 100%");
    }

    // ============= INPUT VALIDATION =============

    public static double getValidScore(
        String prompt, double min, double max) {

        double value;

        while (true) {

            System.out.print(prompt);

            try {

                value = Double.parseDouble(scanner.nextLine());

                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println(
                        "Error: Grade must be between " +
                        min + " and " + max + "."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                    "Error: Please enter a valid numerical grade."
                );
            }
        }
    }

    // ============= INTEGER VALIDATION =============

    public static int getValidInteger(
        String prompt, int min, int max) {

        return (int) getValidScore(prompt, min, max);
    }
}