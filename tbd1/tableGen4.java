import java.util.Scanner;
import java.io.*;
import java.util.Random;
import java.text.DecimalFormat;
import java.util.HashSet;


public class tableGen4 {


    private static int maxClassroom = 100;
    private static int maxStudent = 20001;
    private static int maxDepartment = 50;
    private static int maxRoom = 1000;
    private static int maxCourse = 1001;
    private static int maxSection = 2000;
    private static int maxAdvisor = 4000;
    private static int maxInstructor = 5001;
    private static int maxTeaches = 5000;
    private static int maxTakes= 100000;
    private static int maxPrereq =  1000;
    private static int maxBuilding = 100;
    private static int maxName = 4200;
    private static int maxDept = 100;
    private static int maxTitle = 1000;
    private static double maxSalary = 100000.0;
    private static double maxBudget = 999999.0;
    private static int maxID = 99999;
    private static Random rnd = new Random();
    private static String[] buildingArray = new String[maxBuilding];
    private static String[] nameArray = new String[maxName];
    private static String[] deptNameArray = new String[maxDept];
    private static String[] titleArray = new String[maxTitle];
    private static Boolean[] teachesArray = new Boolean[maxSection];
    private static int[] nextCourseSection = new int[1000]; // starts at 0
    private static DecimalFormat moneyFormat = new DecimalFormat("0.00");


    /**
     * primary keys
     */
    // classroom
    private static String[] classroomBuilding = new String[maxClassroom];
    private static int[] classroomRoom = new int[maxRoom];
    // department
    private static String[] departmentDeptName = new String[maxDepartment];
    // course
    private static int[] courseCourseID = new int[maxCourse];
    // store course dept for integrity check
    private static String[] courseDept = new String[maxCourse];
    // instructor
    private static int[] instructorID = new int[maxInstructor];
    // store instructor dept for integrity check
    private static String[] instructorDept = new String[maxInstructor];
    // section
    private static int[] sectionCourseID = new int[maxSection];
    private static int[] sectionSectionID = new int[maxSection];
    private static String[] sectionSemester = new String[maxSection];
    private static int[] sectionYear = new int[maxSection];
    // teaches
    private static int[] teachesID = new int[maxTeaches];
    private static int[] teachesCourseID = new int[maxTeaches];
    private static int[] teachesSectionID = new int[maxTeaches];
    private static String[] teachesSemester = new String[maxSection];
    private static int[] teachesYear = new int[maxSection];
    // student
    private static int[] studentID = new int[maxStudent];
    // takes
    private static int[] takesID = new int[maxTakes];
    private static int[] takesCourseID = new int[maxTakes];
    private static int[] takesSectionID = new int[maxTakes];
    private static String[] takesSemester = new String[maxTakes];
    private static int[] takesYear = new int[maxTakes];
    // advisor
    private static int[] advisorID = new int[maxAdvisor];


    private static String squote(int val) {
        return ("'"+val+"'");
    }


    private static String squote(String text) {
        return ("'"+text+"'");
    }


    private static Scanner openFile(String fileName) {
        Scanner in = null;
        try {
            in = new Scanner(new FileInputStream ("tbd1/ref/" + fileName));
        }
        catch(FileNotFoundException e) {
            System.out.println ("Could not open the file"+ e.getMessage());
            System.exit (0);
        }
        return in;
    }


    private static PrintWriter outputFile(String fileName) {
        PrintWriter out = null;
        try {
            out = new PrintWriter (new FileOutputStream ("tbd1/sql/" + fileName));
        }
        catch (FileNotFoundException e) {
            System.out.println ("Could not open the file");
            System.exit (0);
        }
        return out;
    }
    private static void fillArrays() {
        int i;
        // fill buildingArray
        Scanner in = openFile("buildingNames");
        i = 0;
        while (in.hasNext() && i < maxBuilding) {
            buildingArray[i++] = in.nextLine();
        }
        if (i < maxBuilding) maxBuilding = i;
        in.close();
        // fill nameArray
        in = openFile("personNames");
        i = 0;
        while (in.hasNext() && i < maxName) {
            nameArray[i++] = in.nextLine();
        }
        if (i < maxName) maxName = i;
        in.close();
        // fill deptNameArray
        in = openFile("deptNames");
        i = 0;
        while (in.hasNext() && i < maxDept) {
            deptNameArray[i++] = in.nextLine();
        }
        if (i < maxDept) maxDept = i;
        in.close();
        // fill titleArray
        in = openFile("courseTitles");
        i = 0;
        while (in.hasNext() && i < maxTitle) {
            titleArray[i++] = in.nextLine();
        }
        if (i < maxTitle) maxTitle= i;
        in.close();
    }
    private static String getBuilding() {
        // building char(15)
        return buildingArray[rnd.nextInt(maxBuilding)];
    }
    private static int getRoom() {
        // room_no char(7)
        return rnd.nextInt(maxRoom); // stick with 3 digit rooms
    }
    private static int getCapacity() {
        // capacity numeric(4,0)
        // we'll use 226 as max and bias it towards small rooms
        int i = rnd.nextInt(16);
        return 10 + rnd.nextInt(1+i*i);
    }
    private static String getBudget() {
        // budget numeric(12,2)
        return moneyFormat.format(maxBudget*rnd.nextFloat());
    }
    private static String getTitle() {
        // title char(50)
        return titleArray[rnd.nextInt(maxTitle)];
    }
    private static int getCredits() {
        // credits numeric(2,0)
        return rnd.nextBoolean() ? 3 : 4;
    }
    private static int getID() {
        // ID numeric(9,0)
        return rnd.nextInt(maxID+1);
    }
    private static String getName() {
        // name char(20)
        return nameArray[rnd.nextInt(maxName)];
    }
    private static String getDeptName() {
        // dept_name char(20)
        return deptNameArray[rnd.nextInt(maxDept)];
    }
    private static String getSalary() {
        // salary numeric(8,2)
        return moneyFormat.format(29000 + maxSalary*rnd.nextFloat());
    }
    private static int getCourseID() {
        // course_id char(8)
        // we'll have courses numbered 100 - 999
        return 101 + rnd.nextInt(899);
    }
    private static int getSecID(int courseID) {
        // sec_id char(8)
        // first section is section 1, then 2, by course number independent of year
        return ++nextCourseSection[courseID];
    }
    private static String getSemester() {
        // semester char(6) check semester in ('Fall', 'Winter', 'Spring', 'Summer')
        return rnd.nextBoolean() ? "Fall" : "Spring";
    }
    private static int getYear() {
        // year numeric(4,0) check (year > 1759 and year < 2100)
        // stick to 2001 through 2010
        return 2001 + rnd.nextInt(10);
    }
    private static int getTotCred() {
        // tot_cred numeric(3,0)
        return rnd.nextInt(130);
    }
    private static String getGrade() {
        // grade char(2)
        int pM = rnd.nextInt(3);
        int lG = rnd.nextInt(3); // just A, B, C
        String x;
        if (pM == 0) x = "-";
        else if (pM == 1) x = " ";
        else x = "+";
        char y = (char) ((int)'A' + lG);
        return y + x;
    }
    private static String getTimeSlot() {
        // time_slot_id char(4)
        // this table is fixed with slots A through P
        char x = (char) ((int)'A' + rnd.nextInt(16));
        return "" + x;
    }
    public static void main(String[] args) {
        int classroom = 10;
        int department = 10;
        int course = 700;
        int instructor = 3000;
        int teaches = 100;
        int advisor = 100;
        int student = 8000;
        int section = 200;
        int takes = 30000;
        int prereq = 100;
        int timeSlot = 10;
        int i = 0, j = 0, r = 0, c = 0, x = 0, y = 0;
        boolean tryValue = true;
        String b = "", d = "", s = "";
        fillArrays();


        // fill classroom
        PrintWriter out = outputFile("all4.sql");
        HashSet<String> classroomSet = new HashSet<>();
        for (i = 0; i < classroom; i++) {
            tryValue = true;
            while (tryValue) {
                b = getBuilding();
                r = getRoom();
                String key = b + "," + r;
                tryValue = classroomSet.contains(key);
                if (!tryValue) {
                    classroomSet.add(key);
                }
            }
            classroomBuilding[i] = b;
            classroomRoom[i] = r;
            s = squote(b) + ", " + r + ", " + getCapacity();
            out.println ("insert into classroom values(" + s +");");
        }


        // fill department
        HashSet<String> departmentSet = new HashSet<>();
        for (i = 0; i < department; i++) {
            tryValue = true;
            while (tryValue) {
                d = getDeptName();
                tryValue = departmentSet.contains(d);
                if (!tryValue) {
                    departmentSet.add(d);
                }
            }
            departmentDeptName[i] =  d;
            s = squote(d) + ", " + squote(getBuilding()) + ", " + getBudget();
            out.println ("insert into department values(" + s +");");
        }


        // fill course
        HashSet<Integer> courseSet = new HashSet<>();
        for (i = 0; i < course; i++) {
            tryValue = true;
            while (tryValue) {
                c = getCourseID();
                tryValue = courseSet.contains(c);
                if (!tryValue) {
                    courseSet.add(c);
                }
            }
            courseCourseID[i] = c;
            courseDept[c]=departmentDeptName[rnd.nextInt(department)];
            s = squote(c) + ", " + squote(getTitle()) + ", " + squote(courseDept[c]) + ", " + getCredits();
            out.println ("insert into course values(" + s +");");
        }


        // fill instructor
        HashSet<Integer> instructorSet = new HashSet<>();
        for (i = 0; i < instructor; i++) {
            tryValue = true;
            while (tryValue) {
                c = getID();
                tryValue = instructorSet.contains(c);
                if (!tryValue) {
                    instructorSet.add(c);
                }
            }
            instructorID[i] = c;
            instructorDept[i]=departmentDeptName[rnd.nextInt(department)];
            s = squote(c) + ", " + squote(getName()) + ", " + squote(instructorDept[i] ) + ", " + getSalary();
            out.println ("insert into instructor values(" + s +");");
        }


        // fill section
        for (i = 0; i < section; i++) {
            c = courseCourseID[rnd.nextInt(course)];
            r = getSecID(c);
            j = rnd.nextInt(classroom);
            sectionCourseID[i] = c;
            sectionSectionID[i] = r;
            b = getSemester();
            sectionSemester[i] = b;
            x = getYear();
            sectionYear[i] = x;
            s = squote(c)  + ", " + squote(r) + ", " + squote(b) + ", " + x +
                    ", " + squote(classroomBuilding[j]) + ", " + squote(classroomRoom[j]) + ", " + squote(getTimeSlot());
            out.println ("insert into section values(" + s +");");
        }


        // fill teaches
        for (i = 0; i < maxSection; i++) teachesArray[i] = false;
        for (i = 0; i < teaches; i++) {
            tryValue = true;
            while (tryValue) {
                j = rnd.nextInt(section);
                tryValue = teachesArray[j];
            }
            teachesArray[j] = true;
            c = sectionCourseID[j];
            r = sectionSectionID[j];
            b = sectionSemester[j];
            x = sectionYear[j];
            //  insist that instructor be in same department as the course being taught
            int chosenInstructor = -1;
            for (y = 0; y < instructor; y++) {
                if (instructorDept[y] == courseDept[c]) {
                    if (chosenInstructor == -1) chosenInstructor = y;
                    else if (rnd.nextInt(10) > 1) chosenInstructor = y;
                }
            }
            y = chosenInstructor == -1 ? rnd.nextInt(instructor) : chosenInstructor;
            s = squote(instructorID[y]) + ", " + squote(c) + ", " + squote(r) + ", " + squote(b) + ", " + x;
            out.println ("insert into teaches values(" + s +");");
        }


        // fill student
        HashSet<Integer> studentSet = new HashSet<>();
        for (i = 0; i < student; i++) {
            tryValue = true;
            while (tryValue) {
                c = getID();
                tryValue = studentSet.contains(c);
                if (!tryValue) {
                    studentSet.add(c);
                }
            }
            studentID[i] = c;
            s = squote(c) + ", " + squote(getName()) + ", " + squote(departmentDeptName[rnd.nextInt(department)])
                    + ", " + getTotCred();
            out.println ("insert into student values(" + s +");");


        }


        // fill takes
        HashSet<String> takesSet = new HashSet<>();
        for (i = 0; i < takes; i++) {
            tryValue = true;
            while (tryValue) {
                j = rnd.nextInt(student);
                x = studentID[j];
                j = rnd.nextInt(section);
                c = sectionCourseID[j];
                r = sectionSectionID[j];
                b = sectionSemester[j];
                y = sectionYear[j];
                
                String key = x + "," + c + "," + r;
                tryValue = takesSet.contains(key);
                if (!tryValue) {
                    takesSet.add(key);
                }
            }
            takesID[i] = x;
            takesCourseID[i] = c;
            takesSectionID[i] = r;
            takesSemester[i] = b;
            takesYear[i] = y;
            s = squote(x) + ", " + squote(c) + ", " + squote(r) + ", " + squote(b) + ", " + y + ", " + squote(getGrade());
            out.println ("insert into takes values(" + s +");");
        }


        // fill advisor
        for (i = 0; i < student; i++) {
            s = squote(studentID[i]) + ", " + squote(instructorID[rnd.nextInt(instructor)]) ;
            out.println ("insert into advisor values(" + s +");");
        }


        // fill prereq
        HashSet<String> prereqSet = new HashSet<>();
        for (i = 0; i < prereq; i++) {
            tryValue = true;
            int courseId1 = 0, courseId2 = 0;
            while (tryValue) {
                courseId1 = courseCourseID[rnd.nextInt(course)];
                courseId2 = courseCourseID[rnd.nextInt(course)];
                String key = courseId1 + "," + courseId2;
                tryValue = prereqSet.contains(key);
                if (!tryValue) {
                    prereqSet.add(key);
                }
            }
            s = squote(courseId1) + ", " + squote(courseId2);
            out.println ("insert into prereq values(" + s +");");
        }
        out.close();
    }
}
