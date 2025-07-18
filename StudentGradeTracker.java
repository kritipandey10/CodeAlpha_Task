import java.util.*;

class Student {
    String name;
    ArrayList<Integer> grades;

    Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    double getAverage() {
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return grades.isEmpty() ? 0 : (double) sum / grades.size();
    }

    int getHighest() {
        return grades.isEmpty() ? 0 : Collections.max(grades);
    }

    int getLowest() {
        return grades.isEmpty() ? 0 : Collections.min(grades);
    }

    void displayReport() {
        System.out.println("Student: " + name);
        System.out.println("Grades: " + grades);
        System.out.println("Average: " + getAverage());
        System.out.println("Highest: " + getHighest());
        System.out.println("Lowest: " + getLowest());
        System.out.println("-------------------------------");
    }
}

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.println("Welcome to Student Grade Tracker! 📊");

        System.out.print("How many students? ");
        int numberOfStudents = sc.nextInt();
        sc.nextLine(); // consume newline

        for (int i = 0; i < numberOfStudents; i++) {
            System.out.print("Enter student name: ");
            String name = sc.nextLine();
            Student student = new Student(name);

            System.out.print("Enter number of grades for " + name + ": ");
            int numGrades = sc.nextInt();

            for (int j = 0; j < numGrades; j++) {
                System.out.print("Grade " + (j + 1) + ": ");
                int grade = sc.nextInt();
                student.grades.add(grade);
            }
            sc.nextLine(); // consume newline
            students.add(student);
        }

        System.out.println("\n------ Summary Report ------");
        for (Student student : students) {
            student.displayReport();
        }
    }
}