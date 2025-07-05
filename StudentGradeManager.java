import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeManager {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.println("=== Student Grade Management System ===");

        // Input loop
        while (true) {
            System.out.print("Enter student name (or 'done' to finish): ");
            String name = scanner.nextLine();

            if (name.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter grade for " + name + ": ");
            double grade = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            students.add(new Student(name, grade));
        }

        // Summary Report
        if (students.size() > 0) {
            double total = 0;
            double highest = students.get(0).grade;
            double lowest = students.get(0).grade;
            String topStudent = students.get(0).name;
            String bottomStudent = students.get(0).name;

            for (Student s : students) {
                total += s.grade;

                if (s.grade > highest) {
                    highest = s.grade;
                    topStudent = s.name;
                }
                if (s.grade < lowest) {
                    lowest = s.grade;
                    bottomStudent = s.name;
                }
            }

            double average = total / students.size();

            System.out.println("\n=== Summary Report ===");
            System.out.printf("Total Students: %d\n", students.size());
            System.out.printf("Average Grade: %.2f\n", average);
            System.out.printf("Highest Grade: %.2f (%s)\n", highest, topStudent);
            System.out.printf("Lowest Grade: %.2f (%s)\n", lowest, bottomStudent);

            System.out.println("\n--- Individual Records ---");
            for (Student s : students) {
                System.out.printf("%s: %.2f\n", s.name, s.grade);
            }
        } else {
            System.out.println("No students entered.");
        }

        scanner.close();
    }
}
