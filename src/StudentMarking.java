import java.util.Scanner;
// DO NOT import anything else

/**
 * This class forms Java Assignment 1, 2021
 */
public class StudentMarking {

    /**
     * The message that the main menu must display --- DO NOT CHANGE THIS
     */
    public final String MENU_TEMPLATE =
            "%nWelcome to the Student System. Please enter an option 0 to 3%n"
                    + "0. Exit%n"
                    + "1. Generate a student ID%n"
                    + "2. Capture marks for students%n"
                    + "3. List student IDs and average mark%n";
    /**
     * DO NOT CHANGE THIS
     */
    public final String NOT_FOUND_TEMPLATE =
            "No student id of %s exists";


   /* Do NOT change the two templates ABOVE this comment.
      DO CHANGE the templates BELOW.
   */

    // TODO (All questions) - Complete these templates which will be used throughout the program
    public final String ENTER_MARK_TEMPLATE = "Please enter mark %d for student %s%n";
    public final String STUDENT_ID_TEMPLATE = "Your studID is %s";
    public final String NAME_RESPONSE_TEMPLATE = "You have entered a given name of %s and a surname of %s%n";
    public final String LOW_HIGH_TEMPLATE = "Student %s has a lowest mark of %d%nA highest mark of %d%n";
    public final String AVG_MARKS_TEMPLATE = "Student ***%s*** has an average mark of %s%n";
    public final String COLUMN_1_TEMPLATE = "%4d";
    public final String COLUMN_2_TEMPLATE = "%12d%n";
    public final String CHART_KEY_TEMPLATE = "Highest     Lowest%n";
    public final String REPORT_PER_STUD_TEMPLATE = "| Student ID %3d is %6s | Average is %5s |%n";

    /**
     * Creates a student ID based on user input
     *
     * @param sc Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @return a studentID according to the pattern specified in {@link StudentMarking#STUDENT_ID_TEMPLATE}
     */
    public String generateStudId(Scanner sc) {
        // TODO (3.4) - Complete the generateStudId method which will allow a user to generate a student id
        String studId = ""; // TODO Don't have unnecessary initialisations


        System.out.printf(
                "Please enter your given name and surname (Enter 0 to return to main menu)%n");

        String givenName = sc.next();
        if (givenName.equals("0")) {
            return "0";
        } else {
            String surname = sc.next();
            String givenNameInitial = givenName.substring(0, 1).toUpperCase();
            String surnameInitial = surname.substring(0, 1).toUpperCase();
            int surnameLength = surname.length();
            String givenNameMiddleInitial = givenName.substring(givenName.length() / 2, givenName.length() / 2 + 1);
            String surnameMiddleInitial = surname.substring(surname.length() / 2, surname.length() / 2 + 1);
            studId = studId + givenNameInitial + surnameInitial + String.format("%02d", surnameLength) + givenNameMiddleInitial + surnameMiddleInitial;

            System.out.printf(NAME_RESPONSE_TEMPLATE, givenName, surname);
            System.out.printf(STUDENT_ID_TEMPLATE, studId);
            return studId;
        }
    }

    /**
     * Reads three marks (restricted to a floor and ceiling) for a student and returns their mean
     *
     * @param sc     Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @return the mean of the three marks entered for the student
     */
    public double captureMarks(Scanner sc, String studId) {
        // TODO (3.5) - Complete the captureMarks method which will allow a user to input three mark for a chosen student
        // DO NOT change MAX_MARK and MIN_MARK
        final int MAX_MARK = 100;
        final int MIN_MARK = 0;
        int[] inputtedMarks = new int[3];
        double avg; // TODO Don't have unnecessary initialisations

        for (int count = 0; count < 3; count++) {
            System.out.printf(ENTER_MARK_TEMPLATE, count + 1, studId);
            inputtedMarks[count] = sc.nextInt();
            if (MAX_MARK < inputtedMarks[count] || MIN_MARK > inputtedMarks[count]) {
                count--;
            }
        }

        int lowestMark;
        int highestMark;

        for (int count1 = 0; count1 < inputtedMarks.length - 1; count1++) {

            for (int count2 = 0; count2 < inputtedMarks.length - 1; count2++) {
                if (inputtedMarks[count2] > inputtedMarks[count2 + 1]) {
                    int hold = inputtedMarks[count2];
                    inputtedMarks[count2] = inputtedMarks[count2 + 1];
                    inputtedMarks[count2 + 1] = hold;
                }
            }
        }
        lowestMark = inputtedMarks[0];
        highestMark = inputtedMarks[2];
        System.out.printf(LOW_HIGH_TEMPLATE, studId, lowestMark, highestMark);

        avg = (inputtedMarks[0] + inputtedMarks[1] + inputtedMarks[2]) / 3.0;
        System.out.printf(AVG_MARKS_TEMPLATE, studId, String.format("%.2f", avg));


        System.out.printf("Would you like to print a bar chart? [y/n]%n");
        String barChartAnswer = sc.next().toUpperCase();
        if (barChartAnswer.equals("Y")) {
            printBarChart(studId, highestMark, lowestMark);
        }

        return avg;
    }

    /**
     * outputs a simple character-based vertical bar chart with 2 columns
     *
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @param high   a student's highest mark
     * @param low    a student's lowest mark
     */
    public void printBarChart(String studId, int high, int low) {
        // TODO (3.6) - Complete the printBarChart method which will print a bar chart of the highest and lowest results of a student
        System.out.printf("Student id statistics: %s%n", studId);
        final char BAR_CHART_SYMBOL = '*';
        for (int count = high; count > 0; count--) {
            System.out.printf("%4c", BAR_CHART_SYMBOL);
            if (low >= count) {
                System.out.printf("%12c", BAR_CHART_SYMBOL);
            }
            System.out.printf("%n");
        }
        System.out.printf(CHART_KEY_TEMPLATE);
        System.out.printf(COLUMN_1_TEMPLATE, high);
        System.out.printf(COLUMN_2_TEMPLATE, low);

    }

    /**
     * Prints a specially formatted report, one line per student
     *
     * @param studList student IDs originally generated by {@link StudentMarking#generateStudId(Scanner)}
     * @param count    the total number of students in the system
     * @param avgArray mean (average) marks
     */
    public void reportPerStud(String[] studList,
                              int count,
                              double[] avgArray) {
        // TODO (3.7) - Complete the reportPerStud method which will print all student IDs and average marks
        for (int counter = 0; counter < count; counter++) {
            System.out.printf(REPORT_PER_STUD_TEMPLATE, counter + 1, studList[counter], String.format("%.2f", avgArray[counter]));
        }
    }

    /**
     * The main menu
     */
    public void displayMenu() {
        // TODO (3.2) - Complete the displayMenu method to use the appropriate template with printf
        System.out.printf(MENU_TEMPLATE);
    }

    /**
     * The controlling logic of the program. Creates and re-uses a {@link Scanner} that reads from {@link System#in}.
     *
     * @param args Command-line parameters (ignored)
     */
    public static void main(String[] args) {
        // TODO (3.3) - Complete the main method to give the program its core
        // done

        // DO NOT change sc, sm, EXIT_CODE, and MAX_STUDENTS
        Scanner sc = new Scanner(System.in);
        StudentMarking sm = new StudentMarking();
        final int EXIT_CODE = 0;
        final int MAX_STUDENTS = 5;

        // TODO Initialise these
        String[] keepStudId = new String[MAX_STUDENTS];
        double[] avgArray = new double[MAX_STUDENTS];

        int numStudents = 0;

        // TODO Build a loop around displaying the menu and reading then processing input
        int answer;

        do {
            sm.displayMenu();
            answer = sc.nextInt();

            switch (answer) {
                case EXIT_CODE:
                    break;
                case 1:
                    if (numStudents < MAX_STUDENTS) {
                        keepStudId[numStudents] = sm.generateStudId(sc);
                        if (!keepStudId[numStudents].equals("0")) {
                            numStudents++;
                        }
                    } else {
                        System.out.printf("You cannot add any more students to the array");
                    }
                    break;
                case 2:
                    System.out.printf("Please enter the studId to capture their marks (Enter 0 to return to main menu)%n");
                    String userId = sc.next();
                    if (!userId.equals("0")) {
                        boolean idFound = false;
                        for (int count = 0; count < keepStudId.length; count++) {
                            if (userId.equals(keepStudId[count])) {
                                avgArray[count] = sm.captureMarks(sc, keepStudId[count]);
                                idFound = true;
                            }
                        }
                        if (!idFound) {
                            System.out.printf(sm.NOT_FOUND_TEMPLATE, userId);
                        }
                    }
                    break;
                case 3:
                    sm.reportPerStud(keepStudId, numStudents, avgArray);
                    break;
                default: // Handle invalid main menu input
                    System.out.printf("You have entered an invalid option. Enter 0, 1, 2 or 3%n");// Skeleton: keep, unchanged
            }
        }
        while (answer != 0);


        // Handle invalid main menu input
        //System.out.printf(
        // "You have entered an invalid option. Enter 0, 1, 2 or 3%n");// Skeleton: keep, unchanged

        System.out.printf("Goodbye%n");
    }
}

/*
    TODO Before you submit:
         1. ensure your code compiles
         2. ensure your code does not print anything it is not supposed to
         3. ensure your code has not changed any of the class or method signatures from the skeleton code
         4. check the Problems tab for the specific types of problems listed in the assignment document
         5. reformat your code: Code > Reformat Code
         6. ensure your code still compiles (yes, again)
 */