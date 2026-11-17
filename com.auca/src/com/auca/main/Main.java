package com.auca.main;

import com.auca.abstracts.Person;
import com.auca.administrator.Administrator;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.*;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.teacher.Teacher;
import com.auca.util.InputValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final List<Department>    departments    = new ArrayList<>();
    private static final List<Student>       students       = new ArrayList<>();
    private static final List<Teacher>       teachers       = new ArrayList<>();
    private static final List<Administrator> administrators = new ArrayList<>();
    private static final List<Course>        courses        = new ArrayList<>();
    private static final List<Classroom>     classrooms     = new ArrayList<>();
    private static final List<Enrollment>    enrollments    = new ArrayList<>();
    private static final List<Assessment>    assessments    = new ArrayList<>();
    private static final List<FeePayment>    feePayments    = new ArrayList<>();
    private static final List<LibraryBook>   libraryBooks   = new ArrayList<>();
    private static final List<BookLoan>      bookLoans      = new ArrayList<>();

    private static class FormCancelException extends RuntimeException {}
    private static class FormBackException   extends RuntimeException {}

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            System.out.print("Select an option: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("0")) {
                System.out.println("Exiting AUCA system. Goodbye!");
                break;
            }

            try {
                switch (choice) {
                    case "1"  -> registerDepartment();
                    case "2"  -> registerStudent();
                    case "3"  -> registerTeacher();
                    case "4"  -> registerAdministrator();
                    case "5"  -> registerCourse();
                    case "6"  -> registerClassroom();
                    case "7"  -> enrollStudent();
                    case "8"  -> recordAssessment();
                    case "9"  -> recordFeePayment();
                    case "10" -> registerLibraryBook();
                    case "11" -> borrowLibraryBook();
                    case "12" -> returnLibraryBook();
                    case "13" -> viewAllRecords();
                    case "14" -> searchRecordById();
                    case "15" -> updateExistingRecord();
                    case "16" -> showStudentAcademicSummary();
                    case "17" -> showStudentPaymentHistory();
                    case "18" -> viewAvailableBooks();
                    case "19" -> demonstratePolymorphism();
                    default   -> System.out.println("[!] Invalid choice. Enter 0-19.");
                }
            } catch (FormCancelException e) {
                System.out.println("\n-> Cancelled. No data saved.");
            } catch (FormBackException e) {
                System.out.println("\n-> Nothing to go back to.");
            } catch (Exception e) {
                System.out.println("\n[!] Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\nAUCA_ID SCHOOL MANAGEMENT");
        System.out.println("1. Register Department       11. Borrow Library Book");
        System.out.println("2. Register Student          12. Return Library Book");
        System.out.println("3. Register Teacher          13. View All Records");
        System.out.println("4. Register Administrator    14. Search Record by ID");
        System.out.println("5. Register Course           15. Update Existing Record");
        System.out.println("6. Register Classroom        16. Student Academic Summary");
        System.out.println("7. Enroll Student            17. Student Payment History");
        System.out.println("8. Record Assessment         18. View Available Books");
        System.out.println("9. Record Fee Payment        19. Demonstrate Polymorphism");
        System.out.println("10. Register Library Book    0. Exit");
    }


    private static void registerDepartment() throws Exception {
        String id = null, name = null, code = null, office = null, email = null;
        Teacher head = null;
        int step = 1;

        while (step <= 2) {
            try {
                if (step == 1) {
                    System.out.println("\n[STEP 1/2] DEPARTMENT DETAILS (Type BACK/CANCEL)");
                    id = askUniqueId("01 Department ID [String, unique]: ");
                    name = askString("02 Name [String, 2-60]: ", 2, 60);
                    code = askString("03 Code [String]: ", 1, 20);
                    office = askString("04 Office [String]: ", 1, 50);
                    email = askEmail("05 Contact Email [String, email]: ");
                    head = teachers.isEmpty() ? null : selectFromList("CHOOSE HEAD TEACHER", teachers, true);
                    step = 2;
                } else {
                    System.out.println("\n+--- REVIEW DEPARTMENT ---+");
                    System.out.printf("01 ID: %s\n02 Name: %s\n03 Code: %s\n04 Office: %s\n05 Email: %s\n06 Head: %s\n", 
                            id, name, code, office, email, head != null ? head.getDisplayName() : "None");
                    
                    int choice = askInt("[1] Save [2] Edit field [0] Cancel: ", 0, 2);
                    if (choice == 0) throw new FormCancelException();
                    if (choice == 1) {
                        Department d = new Department(id, name, code, office, email, head);
                        save(d, departments);
                        return;
                    }
                    if (choice == 2) {
                        int field = askInt("Field to edit (1-6): ", 1, 6);
                        if (field == 1) id = askUniqueId("New ID: ");
                        else if (field == 2) name = askString("New Name: ", 2, 60);
                        else if (field == 3) code = askString("New Code: ", 1, 20);
                        else if (field == 4) office = askString("New Office: ", 1, 50);
                        else if (field == 5) email = askEmail("New Email: ");
                        else if (field == 6) head = selectFromList("New Head", teachers, true);
                    }
                }
            } catch (FormBackException e) { if (step > 1) step--; }
        }
    }


    private static void registerStudent() throws Exception {
        String pId = null, fName = null, lName = null, email = null, phone = null;
        LocalDate dob = null, joinDate = null, admDate = null;
        String memNum = null, status = null, instEmail = null, advisor = null;
        Department dept = null;
        String stuNum = null, program = null, gPhone = null;
        int year = 1;
        boolean scholarship = false;
        int step = 1;

        while (step <= 4) {
            try {
                if (step == 1) {
                    System.out.println("\n[STEP 1/4] PERSONAL DETAILS");
                    pId = askUniqueId("01 Person ID [String, unique]: ");
                    fName = askString("02 First name [String, 2-60]: ", 2, 60);
                    lName = askString("03 Last name [String, 2-60]: ", 2, 60);
                    email = askEmail("04 Email [String, email]: ");
                    phone = askPhone("05 Phone [String, 10-15 digits]: ");
                    dob = askDate("06 Date of birth [YYYY-MM-DD]: ", true);
                    step = 2;
                } else if (step == 2) {
                    System.out.println("\n[STEP 2/4] ACADEMIC DETAILS");
                    memNum = askUniqueId("07 Member number [String, unique]: ");
                    dept = getOrRegisterDepartment();
                    joinDate = askDate("09 Join date [YYYY-MM-DD]: ", false);
                    status = askChoice("10 Status [1 ACTIVE / 2 INACTIVE]: ", "ACTIVE", "INACTIVE");
                    instEmail = askEmail("11 Institutional email: ");
                    advisor = askString("12 Advisor name [String]: ", 2, 120);
                    step = 3;
                } else if (step == 3) {
                    System.out.println("\n[STEP 3/4] STUDENT DETAILS");
                    stuNum = askUniqueStudentNumber("13 Student number [String, unique]: ");
                    program = askString("14 Program name [String]: ", 2, 120);
                    admDate = askDate("15 Admission date [YYYY-MM-DD]: ", false);
                    year = askInt("16 Study year [int, 1-8]: ", 1, 8);
                    gPhone = askPhone("17 Guardian phone [String]: ");
                    scholarship = askChoice("18 Scholarship [1 YES / 2 NO]: ", "YES", "NO").equals("YES");
                    step = 4;
                } else {
                    System.out.println("\n+--- REVIEW STUDENT ---+");
                    System.out.printf("01 ID: %s | 02 Name: %s %s | 03 Email: %s\n", pId, fName, lName, email);
                    System.out.printf("04 Phone: %s | 05 DOB: %s | 06 MemNo: %s\n", phone, dob, memNum);
                    System.out.printf("07 Dept: %s | 08 Joined: %s | 09 Status: %s\n", dept.getDepartmentName(), joinDate, status);
                    System.out.printf("10 InstEmail: %s | 11 Advisor: %s | 12 StuNo: %s\n", instEmail, advisor, stuNum);
                    System.out.printf("13 Program: %s | 14 AdmDate: %s | 15 Year: %d\n", program, admDate, year);
                    System.out.printf("16 GuardPhone: %s | 17 Schol: %b\n", gPhone, scholarship);

                    int choice = askInt("\n[1] Save [2] Edit field [0] Cancel: ", 0, 2);
                    if (choice == 0) throw new FormCancelException();
                    if (choice == 1) {
                        Student s = new Student(pId, fName, lName, email, phone, dob, memNum, dept, joinDate, status, instEmail, advisor, stuNum, program, admDate, year, gPhone, scholarship);
                        save(s, students);
                        return;
                    }
                    if (choice == 2) {
                        int f = askInt("Field to edit (1-18): ", 1, 18);
                        switch (f) {
                            case 1 -> pId = askUniqueId("New ID: ");
                            case 2 -> fName = askString("New First Name: ", 2, 60);
                            case 3 -> lName = askString("New Last Name: ", 2, 60);
                            case 4 -> email = askEmail("New Email: ");
                            case 5 -> phone = askPhone("New Phone: ");
                            case 6 -> dob = askDate("New DOB: ", true);
                            case 7 -> memNum = askUniqueId("New Member No: ");
                            case 8 -> dept = getOrRegisterDepartment();
                            case 9 -> joinDate = askDate("New Join Date: ", false);
                            case 10 -> status = askChoice("New Status: ", "ACTIVE", "INACTIVE");
                            case 11 -> instEmail = askEmail("New Inst Email: ");
                            case 12 -> advisor = askString("New Advisor: ", 2, 120);
                            case 13 -> stuNum = askUniqueStudentNumber("New Stu No: ");
                            case 14 -> program = askString("New Program: ", 2, 120);
                            case 15 -> admDate = askDate("New Adm Date: ", false);
                            case 16 -> year = askInt("New Year: ", 1, 8);
                            case 17 -> gPhone = askPhone("New Guard Phone: ");
                            case 18 -> scholarship = askChoice("[1 YES / 2 NO]: ", "YES", "NO").equals("YES");
                        }
                    }
                }
            } catch (FormBackException e) { if (step > 1) step--; }
        }
    }


    private static void registerTeacher() throws Exception {
        Department dept = getOrRegisterDepartment();
        String pId = null, fName = null, lName = null, email = null, phone = null;
        LocalDate dob = null, joinDate = null, hireDate = null;
        String memNum = null, status = null, instEmail = null, advisor = null;
        String empNum = null, spec = null, office = null, empType = null;
        BigDecimal salary = null;
        int step = 1;

        while (step <= 3) {
            try {
                if (step == 1) {
                    System.out.println("\n[STEP 1/3] PERSONAL DETAILS");
                    pId = askUniqueId("01 Person ID: ");
                    fName = askString("02 First Name: ", 2, 60);
                    lName = askString("03 Last Name: ", 2, 60);
                    email = askEmail("04 Email: ");
                    phone = askPhone("05 Phone: ");
                    dob = askDate("06 DOB: ", true);
                    step = 2;
                } else if (step == 2) {
                    System.out.println("\n[STEP 2/3] ACADEMIC DETAILS");
                    memNum = askUniqueId("07 Member No: ");
                    joinDate = askDate("08 Join Date: ", false);
                    status = askChoice("09 Status [1 ACTIVE / 2 INACTIVE]: ", "ACTIVE", "INACTIVE");
                    instEmail = askEmail("10 Inst Email: ");
                    advisor = askString("11 Advisor: ", 2, 120);
                    step = 3;
                } else {
                    System.out.println("\n[STEP 3/3] TEACHER DETAILS");
                    empNum = askUniqueEmployeeNumber("12 Emp No: ");
                    spec = askString("13 Spec: ", 1, 50);
                    hireDate = askDate("14 Hire Date: ", false);
                    salary = askMoney("15 Salary: ");
                    office = askString("16 Office: ", 1, 20);
                    empType = askChoice("17 Type [1 FULL_TIME / 2 PART_TIME]: ", "FULL_TIME", "PART_TIME");
                    
                    System.out.println("\n+--- REVIEW TEACHER ---+");
                    System.out.printf("01 ID: %s | 02 Name: %s %s | 03 Dept: %s\n", pId, fName, lName, dept.getDepartmentName());
                    System.out.printf("04 EmpNo: %s | 05 Salary: %s | 06 Type: %s\n", empNum, salary, empType);
                    
                    int choice = askInt("\n[1] Save [2] Edit field [0] Cancel: ", 0, 2);
                    if (choice == 0) throw new FormCancelException();
                    if (choice == 1) {
                        Teacher t = new Teacher(pId, fName, lName, email, phone, dob, memNum, dept, joinDate, status, instEmail, advisor, empNum, spec, hireDate, salary, office, empType);
                        save(t, teachers);
                        return;
                    }
                    if (choice == 2) {
                        int f = askInt("Field to edit (1-17): ", 1, 17);
                        switch (f) {
                            case 1 -> pId = askUniqueId("New ID: ");
                            case 2 -> fName = askString("New First Name: ", 2, 60);
                            case 3 -> lName = askString("New Last Name: ", 2, 60);
                            case 4 -> email = askEmail("New Email: ");
                            case 5 -> phone = askPhone("New Phone: ");
                            case 6 -> dob = askDate("New DOB: ", true);
                            case 7 -> memNum = askUniqueId("New Member No: ");
                            case 8 -> joinDate = askDate("New Join Date: ", false);
                            case 9 -> status = askChoice("New Status: ", "ACTIVE", "INACTIVE");
                            case 10 -> instEmail = askEmail("New Inst Email: ");
                            case 11 -> advisor = askString("New Advisor: ", 2, 120);
                            case 12 -> empNum = askUniqueEmployeeNumber("New Emp No: ");
                            case 13 -> spec = askString("New Spec: ", 1, 50);
                            case 14 -> hireDate = askDate("New Hire Date: ", false);
                            case 15 -> salary = askMoney("New Salary: ");
                            case 16 -> office = askString("New Office: ", 1, 20);
                            case 17 -> empType = askChoice("New Type: ", "FULL_TIME", "PART_TIME");
                        }
                    }
                }
            } catch (FormBackException e) { if (step > 1) step--; }
        }
    }


    private static void registerAdministrator() throws Exception {
        String pId = null, fName = null, lName = null, email = null, phone = null;
        LocalDate dob = null, empDate = null;
        String staffNum = null, title = null, office = null;
        int access = 1;
        boolean active = true;
        int step = 1;

        while (step <= 2) {
            try {
                if (step == 1) {
                    System.out.println("\n[STEP 1/2] PERSONAL DETAILS");
                    pId = askUniqueId("01 Person ID: ");
                    fName = askString("02 First Name: ", 2, 60);
                    lName = askString("03 Last Name: ", 2, 60);
                    email = askEmail("04 Email: ");
                    phone = askPhone("05 Phone: ");
                    dob = askDate("06 DOB: ", true);
                    step = 2;
                } else {
                    System.out.println("\n[STEP 2/2] ADMIN DETAILS");
                    staffNum = askUniqueId("07 Staff Number: ");
                    title = askString("08 Job Title: ", 2, 60);
                    office = askString("09 Assigned Office: ", 1, 30);
                    empDate = askDate("10 Employment Date: ", false);
                    access = askInt("11 Access Level (1-10): ", 1, 10);
                    active = askChoice("12 Active [1 YES / 2 NO]: ", "YES", "NO").equals("YES");

                    System.out.println("\n+--- REVIEW ADMIN ---+");
                    System.out.printf("01 ID: %s | 02 Name: %s %s\n", pId, fName, lName);
                    System.out.printf("03 StaffNo: %s | 04 Title: %s\n", staffNum, title);
                    
                    int choice = askInt("\n[1] Save [2] Edit field [0] Cancel: ", 0, 2);
                    if (choice == 0) throw new FormCancelException();
                    if (choice == 1) {
                        Administrator a = new Administrator(pId, fName, lName, email, phone, dob, staffNum, title, office, empDate, access, active);
                        save(a, administrators);
                        return;
                    }
                    if (choice == 2) {
                        int f = askInt("Field to edit (1-12): ", 1, 12);
                        switch (f) {
                            case 1 -> pId = askUniqueId("New ID: ");
                            case 2 -> fName = askString("New First Name: ", 2, 60);
                            case 3 -> lName = askString("New Last Name: ", 2, 60);
                            case 4 -> email = askEmail("New Email: ");
                            case 5 -> phone = askPhone("New Phone: ");
                            case 6 -> dob = askDate("New DOB: ", true);
                            case 7 -> staffNum = askUniqueId("New Staff No: ");
                            case 8 -> title = askString("New Title: ", 2, 60);
                            case 9 -> office = askString("New Office: ", 1, 30);
                            case 10 -> empDate = askDate("New Emp Date: ", false);
                            case 11 -> access = askInt("New Access: ", 1, 10);
                            case 12 -> active = askChoice("New Active: ", "YES", "NO").equals("YES");
                        }
                    }
                }
            } catch (FormBackException e) { if (step > 1) step--; }
        }
    }

    private static void registerCourse() throws Exception {
        Department dept = getOrRegisterDepartment();
        if (teachers.isEmpty()) {
            System.out.println("! No Teacher exists. Register one first.");
            if (askString("1=Register now, 0=Cancel: ", 1, 1).equals("1")) registerTeacher();
            else throw new FormCancelException();
        }
        Teacher teacher = selectFromList("CHOOSE TEACHER", teachers, false);
        
        String cId = askUniqueId("Course ID: ");
        String code = askString("Course Code: ", 2, 10);
        String title = askString("Course Title: ", 2, 100);
        int credits = askInt("Credit Hours (1-6): ", 1, 6);

        if (askInt("[1] Save [0] Cancel: ", 0, 1) == 1) {
            save(new Course(cId, code, title, credits, dept, teacher), courses);
        }
    }

    private static void registerClassroom() throws Exception {
        String id = askUniqueId("Classroom ID: ");
        String bldg = askString("Building: ", 1, 50);
        String room = askString("Room No: ", 1, 20);
        int cap = askInt("Capacity: ", 1, 500);
        int floor = askInt("Floor: ", 0, 50);
        boolean avail = askChoice("Available [1 YES / 2 NO]: ", "YES", "NO").equals("YES");

        if (askInt("[1] Save [0] Cancel: ", 0, 1) == 1) {
            save(new Classroom(id, bldg, room, cap, floor, avail), classrooms);
        }
    }

    private static void registerLibraryBook() throws Exception {
        String id = askUniqueId("Book ID: ");
        String isbn = askString("ISBN: ", 5, 20);
        String title = askString("Title: ", 2, 100);
        String author = askString("Author: ", 2, 60);
        int total = askInt("Total Copies: ", 0, 10000);
        int avail = askInt("Available (<= total): ", 0, total);

        if (askInt("[1] Save [0] Cancel: ", 0, 1) == 1) {
            save(new LibraryBook(id, isbn, title, author, total, avail), libraryBooks);
        }
    }

    private static void enrollStudent() throws Exception {
        if (students.isEmpty() || courses.isEmpty()) throw new PrerequisiteMissingException("Students or Courses missing.");
        Student s = selectFromList("CHOOSE STUDENT", students, false);
        Course c = selectFromList("CHOOSE COURSE", courses, false);
        
        String id = askUniqueId("Enrollment ID: ");
        LocalDate date = askDate("Date: ", false);
        String sem = askString("Semester: ", 4, 15);
        String status = askChoice("Status [1 ACTIVE / 2 DROPPED]: ", "ACTIVE", "DROPPED");

        for (Enrollment e : enrollments) {
            if (e.isActive() && e.getStudent().getId().equals(s.getId()) && e.getCourse().getId().equals(c.getId()) && e.getSemester().equalsIgnoreCase(sem)) {
                throw new DuplicateRecordException("Already enrolled in this semester.");
            }
        }

        if (askInt("[1] Save [0] Cancel: ", 0, 1) == 1) {
            save(new Enrollment(id, s, c, date, sem, status), enrollments);
        }
    }

    private static void recordAssessment() throws Exception {
        List<Enrollment> active = enrollments.stream().filter(Enrollment::isActive).toList();
        if (active.isEmpty()) throw new PrerequisiteMissingException("No active enrollments.");
        Enrollment en = selectFromList("CHOOSE ENROLLMENT", active, false);

        String id = askUniqueId("Assessment ID: ");
        String type = askChoice("Type [1 QUIZ / 2 MIDTERM / 3 FINAL]: ", "QUIZ", "MIDTERM", "FINAL");
        double max = askDouble("Max Score: ");
        double obt = askDouble("Obtained: ");
        LocalDate date = askDate("Date: ", false);

        if (askInt("[1] Save [0] Cancel: ", 0, 1) == 1) {
            save(new Assessment(id, en, type, max, obt, date), assessments);
        }
    }

    private static void recordFeePayment() throws Exception {
        if (students.isEmpty()) throw new PrerequisiteMissingException("No students.");
        Student s = selectFromList("CHOOSE STUDENT", students, false);

        String id = askUniqueId("Payment ID: ");
        BigDecimal amt = askMoney("Amount: ");
        LocalDate date = askDate("Date: ", false);
        String method = askChoice("Method [1 CASH / 2 BANK]: ", "CASH", "BANK_TRANSFER");
        String receipt = askString("Receipt: ", 1, 30);

        for (FeePayment fp : feePayments) if (fp.getReceiptNumber().equalsIgnoreCase(receipt)) throw new DuplicateRecordException("Duplicate receipt.");

        if (askInt("[1] Save [0] Cancel: ", 0, 1) == 1) {
            save(new FeePayment(id, s, amt, date, method, receipt), feePayments);
        }
    }

    private static void borrowLibraryBook() throws Exception {
        if (students.isEmpty() || libraryBooks.isEmpty()) throw new PrerequisiteMissingException("Prerequisites missing.");
        Student s = selectFromList("CHOOSE STUDENT", students, false);
        LibraryBook b = selectFromList("CHOOSE BOOK", libraryBooks, false);

        if (b.getAvailableCopies() <= 0) throw new BookUnavailableException("No copies available.");

        String id = askUniqueId("Loan ID: ");
        LocalDate borrowed = askDate("Borrow Date: ", false);
        LocalDate due = askDate("Due Date: ", false);

        if (askInt("[1] Save [0] Cancel: ", 0, 1) == 1) {
            save(new BookLoan(id, s, b, borrowed, due, null), bookLoans);
            b.setAvailableCopies(b.getAvailableCopies() - 1);
            System.out.println("+ Stock updated. Available: " + b.getAvailableCopies());
        }
    }

    private static void returnLibraryBook() throws Exception {
        List<BookLoan> active = bookLoans.stream().filter(BookLoan::isActive).toList();
        if (active.isEmpty()) throw new RecordNotFoundException("No active loans.");
        BookLoan loan = selectFromList("CHOOSE LOAN", active, false);
        loan.setReturnedDate(LocalDate.now());
        loan.validate();
        loan.getBook().setAvailableCopies(loan.getBook().getAvailableCopies() + 1);
        System.out.println("+ Book returned. Available: " + loan.getBook().getAvailableCopies());
    }

    private static void viewAllRecords() {
        List<Identifiable> all = getAllIdentifiables();
        System.out.println("\n--- ALL RECORDS (" + all.size() + ") ---");
        if (all.isEmpty()) System.out.println("No records found.");
        else all.forEach(item -> System.out.println(" -> " + item.getRegistrationSummary()));
    }

    private static void searchRecordById() throws Exception {
        String id = askString("\nEnter ID: ", 1, 30);
        for (Identifiable item : getAllIdentifiables()) {
            if (item.getId().equalsIgnoreCase(id)) {
                System.out.println("[+] Found: " + item);
                return;
            }
        }
        throw new RecordNotFoundException("Not found: " + id);
    }

    private static void updateExistingRecord() throws Exception {
        String id = askString("\nEnter Student or Dept ID: ", 1, 30);
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                s.setPhoneNumber(askPhone("New Phone: "));
                s.validate();
                System.out.println("+ Student updated!");
                return;
            }
        }
        for (Department d : departments) {
            if (d.getId().equalsIgnoreCase(id)) {
                d.setOfficeLocation(askString("New Office: ", 1, 50));
                d.validate();
                System.out.println("+ Department updated!");
                return;
            }
        }
        throw new RecordNotFoundException("Record not found.");
    }

    private static void showStudentAcademicSummary() throws Exception {
        Student s = findStudent();
        System.out.println("\n=== SUMMARY: " + s.getDisplayName() + " ===");
        enrollments.stream().filter(e -> e.getStudent().getId().equals(s.getId())).forEach(System.out::println);
        assessments.stream().filter(a -> a.getEnrollment().getStudent().getId().equals(s.getId())).forEach(System.out::println);
    }

    private static void showStudentPaymentHistory() throws Exception {
        Student s = findStudent();
        BigDecimal total = BigDecimal.ZERO;
        System.out.println("\n=== PAYMENTS: " + s.getDisplayName() + " ===");
        for (FeePayment fp : feePayments) {
            if (fp.getStudent().getId().equalsIgnoreCase(s.getId())) {
                System.out.println(fp);
                total = total.add(fp.getAmount());
            }
        }
        System.out.println("Total Paid: " + total);
    }

    private static void viewAvailableBooks() {
        System.out.println("\n--- AVAILABLE BOOKS ---");
        libraryBooks.stream().filter(b -> b.getAvailableCopies() > 0).forEach(System.out::println);
    }

    private static void demonstratePolymorphism() {
        System.out.println("\n=== RUNTIME POLYMORPHISM ===");
        List<Person> people = new ArrayList<>();
        people.addAll(students); people.addAll(teachers); people.addAll(administrators);
        
        System.out.println("1. List<Person>:");
        people.forEach(p -> System.out.println(" -> " + p.getDisplayName() + " | " + p.getRoleDescription()));

        System.out.println("2. List<Identifiable>:");
        getAllIdentifiables().forEach(i -> System.out.println(" -> " + i.getId() + " | " + i.getDisplayName() + " | Active: " + i.isActive()));
    }

    
    private static <T extends Identifiable> T selectFromList(String title, List<T> list, boolean allowNone) {
        System.out.println("\n--- " + title + " ---");
        if (allowNone) System.out.println("0. None");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". [" + list.get(i).getId() + "] " + list.get(i).getDisplayName());
        }
        int choice = askInt("Select: ", allowNone ? 0 : 1, list.size());
        return (choice == 0) ? null : list.get(choice - 1);
    }

    private static <T extends Identifiable & Registrable> void save(T record, List<T> list) throws ValidationException {
        record.validate();
        list.add(record);
        System.out.println("+ Saved successfully: " + record.getRegistrationSummary());
    }

    private static Student findStudent() throws Exception {
        String id = askString("\nEnter Student ID: ", 1, 30);
        return students.stream().filter(s -> s.getId().equalsIgnoreCase(id)).findFirst()
                .orElseThrow(() -> new RecordNotFoundException("Student not found."));
    }

    private static List<Identifiable> getAllIdentifiables() {
        List<Identifiable> all = new ArrayList<>();
        all.addAll(departments); all.addAll(students); all.addAll(teachers);
        all.addAll(administrators); all.addAll(courses); all.addAll(classrooms);
        all.addAll(enrollments); all.addAll(assessments); all.addAll(feePayments);
        all.addAll(libraryBooks); all.addAll(bookLoans);
        return all;
    }

    private static Department getOrRegisterDepartment() throws Exception {
        if (departments.isEmpty()) {
            System.out.println("\n! No Department exists. Register one first.");
            if (askString("1=Register now, 0=Cancel: ", 1, 1).equals("1")) registerDepartment();
            else throw new FormCancelException();
        }
        return selectFromList("CHOOSE DEPARTMENT", departments, false);
    }

    private static String askString(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL")) throw new FormCancelException();
            if (in.equalsIgnoreCase("BACK")) throw new FormBackException();
            if (in.length() >= min && in.length() <= max) return in;
            System.out.printf("! Must be %d-%d chars.\n", min, max);
        }
    }

    private static int askInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL")) throw new FormCancelException();
            if (in.equalsIgnoreCase("BACK")) throw new FormBackException();
            try {
                int v = Integer.parseInt(in);
                if (v >= min && v <= max) return v;
                System.out.printf("! Must be %d-%d.\n", min, max);
            } catch (NumberFormatException e) { System.out.println("! Invalid number."); }
        }
    }

    private static double askDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL")) throw new FormCancelException();
            if (in.equalsIgnoreCase("BACK")) throw new FormBackException();
            try {
                double v = Double.parseDouble(in);
                if (v >= 0) return v;
                System.out.println("! Cannot be negative.");
            } catch (NumberFormatException e) { System.out.println("! Invalid decimal."); }
        }
    }

    private static BigDecimal askMoney(String prompt) {
        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL")) throw new FormCancelException();
            if (in.equalsIgnoreCase("BACK")) throw new FormBackException();
            try {
                BigDecimal v = new BigDecimal(in);
                if (v.compareTo(BigDecimal.ZERO) > 0) return v;
                System.out.println("! Must be > 0.");
            } catch (NumberFormatException e) { System.out.println("! Invalid amount."); }
        }
    }

    private static LocalDate askDate(String prompt, boolean past) {
        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("CANCEL")) throw new FormCancelException();
            if (in.equalsIgnoreCase("BACK")) throw new FormBackException();
            try {
                LocalDate d = LocalDate.parse(in);
                if (past && !d.isBefore(LocalDate.now())) System.out.println("! Must be in the past.");
                else if (!past && d.isAfter(LocalDate.now())) System.out.println("! Cannot be in the future.");
                else return d;
            } catch (Exception e) { System.out.println("! Invalid date (YYYY-MM-DD)."); }
        }
    }

    private static String askChoice(String prompt, String... options) {
        while (true) {
            System.out.print(prompt);
            String in = scanner.nextLine().trim().toUpperCase();
            if (in.equalsIgnoreCase("CANCEL")) throw new FormCancelException();
            if (in.equalsIgnoreCase("BACK")) throw new FormBackException();
            try {
                int idx = Integer.parseInt(in);
                if (idx >= 1 && idx <= options.length) return options[idx - 1];
            } catch (NumberFormatException ignored) {}
            for (String opt : options) if (opt.equalsIgnoreCase(in)) return opt;
            System.out.println("! Invalid choice.");
        }
    }

    private static String askEmail(String prompt) {
        while (true) {
            String in = askString(prompt, 5, 100);
            if (in.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) return in;
            System.out.println("! Invalid email syntax.");
        }
    }

    private static String askPhone(String prompt) {
        while (true) {
            String in = askString(prompt, 10, 15);
            if (in.matches("^\\+?[0-9]{10,15}$")) return in;
            System.out.println("! Must be 10-15 digits.");
        }
    }

    private static String askUniqueId(String prompt) {
        while (true) {
            String id = askString(prompt, 4, 30);
            boolean exists = getAllIdentifiables().stream().anyMatch(i -> i.getId().equalsIgnoreCase(id));
            if (!exists) return id;
            System.out.println("! ID already exists.");
        }
    }

    private static String askUniqueStudentNumber(String prompt) {
        while (true) {
            String num = askString(prompt, 4, 30);
            if (students.stream().noneMatch(s -> s.getStudentNumber().equalsIgnoreCase(num))) return num;
            System.out.println("! Student number already exists.");
        }
    }

    private static String askUniqueEmployeeNumber(String prompt) {
        while (true) {
            String num = askString(prompt, 4, 30);
            if (teachers.stream().noneMatch(t -> t.getEmployeeNumber().equalsIgnoreCase(num))) return num;
            System.out.println("! Employee number already exists.");
        }
    }
}