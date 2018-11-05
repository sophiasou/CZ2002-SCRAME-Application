package com.ss4group8;

import sun.applet.Main;

import java.io.*;
import java.util.*;

public class FILEMgr {

    private static final String COMMA_DELIMITER=",";
    private static final String NEW_LINE_SEPARATOR="\n";
    private static final String LINE_DELIMITER="|";
    private static final String EQUAL_SIGN="=";

    private static final String studentFileName = "data/studentFile.csv";
    private static final String courseFileName = "data/courseFile.csv";
    private static final String professorFileName = "data/professorFile.csv";
    private static final String studentListFileName = "data/studentListFile.csv";
    private static final String markFileName = "data/markFile.csv";

    private static final String student_HEADER="studentID,studentName";
    private static final String course_HEADER="courseID,courseName,profInCharge,vacancies,totalSeats,lectureGroups,TutorialGroups,LabGroups,MainComponents";
    private static final String professor_HEADER="professorID,professorName";
    private static final String studentList_HEADER="courseID,";
    private static final String mark_HEADER="studentID,courseID,examMark,courseWorkMarks,totalMark";

    private static final int studentIdIndex = 0;
    private static final int studentNameIndex = 1;

    private static final int courseIdIndex = 0;
    private static final int courseNameIndex = 1;
    private static final int profInChargeIndex = 2;
    private static final int vacanciesIndex = 3;
    private static final int totalSeatsIndex = 4;
    private static final int lectureGroupsIndex = 5;
    private static final int tutorialGroupIndex = 6;
    private static final int labGroupIndex = 7;
    private static final int courseWorkIndex = 8;

    private static final int professorIdIndex = 0;
    private static final int professorNameIndex = 1;

    private static final int studentIdIndexInMarks = 0;
    private static final int courseIdIndexInMarks = 1;
    private static final int examMarkIndex = 2;
    private static final int courseWorkMarksIndex = 3;
    private static final int totalMarkIndex = 4;

    //when a new student is added, add him to the csv file
    public static void writeStudentsIntoFile(Student student){
        File file;
        FileWriter fileWriter = null;
        try{
            file = new File(studentFileName);
            //initialize file header if have not done so
            fileWriter = new FileWriter(studentFileName);
            if(file.length()== 0){
                fileWriter.append(student_HEADER);
            }
            fileWriter.append(student.getStudentID());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(student.getStudentName());
            fileWriter.append(NEW_LINE_SEPARATOR);
        }catch(Exception e){
            System.out.println("Error in adding a student to the file.");
            e.printStackTrace();
        }finally{
            try{
                fileWriter.flush();
                fileWriter.close();
            }catch(IOException e){
                System.out.println("Error in flushing or closing the file.");
                e.printStackTrace();
            }
        }
    }


    //load all the existing students from the csv file
    public static ArrayList<Student> loadStudents(){
        BufferedReader fileReader = null;
        ArrayList<Student> students = new ArrayList<Student>(0);
        try{
            String line;
            fileReader = new BufferedReader(new FileReader(studentFileName));
            fileReader.readLine();//read the header to skip it
            while((line = fileReader.readLine())!=null){
                String[] tokens = line.split(COMMA_DELIMITER);
                if(tokens.length>0){
                    Student student = new Student(tokens[studentIdIndex], tokens[studentNameIndex]);
                    students.add(student);
                }
            }
        }catch(Exception e){
            System.out.println("Error occurs when loading students.");
            e.printStackTrace();
        }finally{
            try{
                fileReader.close();
            }catch(IOException e){
                System.out.println("Error occurs when closing the fileReader.");
                e.printStackTrace();
            }
        }
        return students;
    }


    public static void writeCourseIntoFile(Course course) {
        File file;
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(courseFileName);
            //initialize file header if have not done so
            file = new File(courseFileName);
            if(file.length() == 0){
                fileWriter.append(course_HEADER);
            }

            fileWriter.append(course.getCourseID());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(course.getCourseName());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(course.getProfInCharge().getProfID());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(course.getVacancies()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(course.getTotalSeats()));
            fileWriter.append(COMMA_DELIMITER);
            ArrayList<LectureGroup> lectureGroups = course.getLectureGroups();
            if(lectureGroups.size() != 0){
                int index = 0;
                for(LectureGroup lectureGroup:lectureGroups){
                    fileWriter.append(lectureGroup.getGroupName());
                    index++;
                    if(index != lectureGroups.size()){
                        fileWriter.append(LINE_DELIMITER);}
                }
            }
            else{
                fileWriter.append("NULL");
            }
            fileWriter.append(COMMA_DELIMITER);
            ArrayList<TutorialGroup> tutorialGroups = course.getTutorialGroups();
            if(tutorialGroups.size() != 0){
                int index = 0;
                for(TutorialGroup tutorialGroup:tutorialGroups){
                    fileWriter.append(tutorialGroup.getGroupName());
                    index ++;
                    if (index != tutorialGroups.size()){
                        fileWriter.append(LINE_DELIMITER);}
                }
            }
            else{
                fileWriter.append("NULL");
            }
            fileWriter.append(COMMA_DELIMITER);
            ArrayList<LabGroup> labGroups = course.getLabGroups();
            if(labGroups.size() != 0){
                int index = 0;
                for(LabGroup labGroup:labGroups){
                    fileWriter.append(labGroup.getGroupName());
                    index ++;
                    if(index != labGroups.size()){
                        fileWriter.append(LINE_DELIMITER);}
                }
            }
            else{
                fileWriter.append("NULL");
            }
            fileWriter.append(COMMA_DELIMITER);
            ArrayList<MainComponent> mainComponents = course.getMainComponents();
            if(mainComponents.size() != 0){
                int index = 0;
                for (MainComponent mainComponent:mainComponents)
                {
                    fileWriter.append(mainComponent.getComponentName());
                    index ++;
                    if(index != mainComponents.size()){
                        fileWriter.append(LINE_DELIMITER);
                    }
                }
            }else{
                fileWriter.append("NULL");
            }
            fileWriter.append(NEW_LINE_SEPARATOR);
        }catch(Exception e){
            System.out.println("Error in adding a course to the file.");
            e.printStackTrace();
        }finally{
            try{
                fileWriter.flush();
                fileWriter.close();
            }catch(IOException e){
                System.out.println("Error occurs occurs when flushing or closing the file.");
                e.printStackTrace();
            }
        }

    }

    public static ArrayList<Course> loadCourses(){
        ArrayList<Course> courses = new ArrayList<Course>(0);
        BufferedReader fileReader = null;
        try{
            String line;
            fileReader = new BufferedReader(new FileReader(courseFileName));
            fileReader.readLine();//read the header to skip it
            while((line = fileReader.readLine())!=null){
                String[] tokens = line.split(COMMA_DELIMITER);
                if(tokens.length>0){
//                    Student student = new Student(tokens[studentIdIndex], tokens[studentNameIndex]);
//                    students.add(student);
                    String courseID = tokens[courseIdIndex];
                    String courseName = tokens[courseNameIndex];
                    String profInCharge = tokens[profInChargeIndex];
                    ArrayList<Professor> professors = loadProfessors();
                    for(Professor professor:professors){
                        if(professor.getProfID().equals(profInCharge)){
                            Professor professorInCharge = professor;
                            break;
                        }
                    }
                    int vacancies = Integer.parseInt(tokens[vacanciesIndex]);
                    int totalSeats = Integer.parseInt(tokens[totalSeatsIndex]);
                    //getLectureGroups
                    //new course
                    //getTutorialGroups and set
                    //getLabGroups and set
                    //getMainComponent and set
                }
            }
        }catch(Exception e){
            System.out.println("Error happens when loading students.");
            e.printStackTrace();
        }finally{
            try{
                fileReader.close();
            }catch(IOException e){
                System.out.println("Error happens when closing the fileReader.");
                e.printStackTrace();
            }
        }
        return courses;
    }

    //profs
    public static void writeProfIntoFile(Professor professor){
        File file;
        FileWriter fileWriter = null;
        try{
            file = new File(professorFileName);
            //initialize file header if have not done so
            fileWriter = new FileWriter(professorFileName);
            if(file.length()== 0){
                fileWriter.append(professor_HEADER);
            }
            fileWriter.append(professor.getProfID());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(professor.getProfName());
            fileWriter.append(NEW_LINE_SEPARATOR);
        }catch(Exception e){
            System.out.println("Error in adding a professor to the file.");
            e.printStackTrace();
        }finally{
            try{
                fileWriter.flush();
                fileWriter.close();
            }catch(IOException e){
                System.out.println("Error occurs when flushing or closing the file.");
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Professor> loadProfessors(){
        BufferedReader fileReader = null;
        ArrayList<Professor> professors = new ArrayList<Professor>(0);
        try{
            String line;
            fileReader = new BufferedReader(new FileReader(professorFileName));
            fileReader.readLine();//read the header to skip it
            while((line = fileReader.readLine())!=null){
                String[] tokens = line.split(COMMA_DELIMITER);
                if(tokens.length>0){
                    Professor professor = new Professor(tokens[professorIdIndex], tokens[professorNameIndex]);
                    professors.add(professor);
                }
            }
        }catch(Exception e){
            System.out.println("Error occurs when loading professors.");
            e.printStackTrace();
        }finally{
            try{
                fileReader.close();
            }catch(IOException e){
                System.out.println("Error occurs when closing the fileReader.");
                e.printStackTrace();
            }
        }
        return professors;
    }

    //student list for lec/tut/lab/the whole course
    public static void updateStudentList(Course course, Student student){

    }

    public static ArrayList<Student> loadStudentList(Course course){
        ArrayList<Student> students = new ArrayList<Student>(0);

        return students;
    }

    //marks
    public static void updateStudentMarks(Mark mark){
        File file;
        FileWriter fileWriter = null;
        try{
            file = new File(markFileName);
            //initialize file header if have not done so
            fileWriter = new FileWriter(markFileName);
            if(file.length()== 0){
                fileWriter.append(mark_HEADER);
            }
            fileWriter.append(mark.getStudent().getStudentID());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(mark.getCourse().getCourseID());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(mark.getExamMark()));
            fileWriter.append(COMMA_DELIMITER);
            HashMap<CourseworkComponent, Double> courseworkMarks = mark.getCourseWorkMarks();
            if(!courseworkMarks.isEmpty()){
                int index = 0;
                for (HashMap.Entry<CourseworkComponent,Double> entry:courseworkMarks.entrySet())
                {
                    CourseworkComponent key = entry.getKey();
                    Double value = entry.getValue();
                    fileWriter.append(key.getComponentName());
                    fileWriter.append(EQUAL_SIGN);
                    fileWriter.append(key.getComponentWeight());
                    fileWriter.append(EQUAL_SIGN);
                    fileWriter.append(String.valueOf(value));
                    index ++;
                    if(index != courseworkMarks.size()){
                        fileWriter.append(LINE_DELIMITER);
                    }
                }
            }else{
                fileWriter.append("NULL");
            }
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(mark.getTotalMark()));
            fileWriter.append(NEW_LINE_SEPARATOR);
        }catch(Exception e){
            System.out.println("Error in adding a mark to the file.");
            e.printStackTrace();
        }finally{
            try{
                fileWriter.flush();
                fileWriter.close();
            }catch(IOException e){
                System.out.println("Error occurs in flushing or closing the file.");
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Mark> loadStudentMarks(){
        BufferedReader fileReader = null;
        ArrayList<Mark> marks = new ArrayList<Mark>(0);
        try{
            String line;
            int thisStudentIndex = 0;
            int thisCourseIndex = 0;
            HashMap<CourseworkComponent,Double> courseWorkMarks = new HashMap<CourseworkComponent, Double>();
            String[] thisCourseWorkMark;
            fileReader = new BufferedReader(new FileReader(markFileName));
            fileReader.readLine();//read the header to skip it
            while((line = fileReader.readLine())!=null){
                String[] tokens = line.split(COMMA_DELIMITER);
                if(tokens.length>0){
                    String studentID = tokens[studentIdIndexInMarks];
                    ArrayList<Student> students = loadStudents();
                    for(Student student:students){
                        int index = 0;
                        if(student.getStudentID().equals(studentID)){
                            thisStudentIndex = index;
                            break;
                        }else{
                            index ++;
                        }
                    }
                    String courseID = tokens[courseIdIndexInMarks];
                    ArrayList<Course> courses = loadCourses();
                    for(Course course:courses){
                        int index = 0;
                        if(course.getCourseID().equals(courseID)){
                            thisCourseIndex = index;
                            break;
                        }else{
                            index ++;
                        }
                    }
                    Double examMark = Double.parseDouble(tokens[examMarkIndex]);
                    String courseWorkMarksString = tokens[courseWorkMarksIndex];
                    String[] eachCourseWorkMark = courseWorkMarksString.split(LINE_DELIMITER);
                    for(int i  = 0; i < eachCourseWorkMark.length; i++){
                        thisCourseWorkMark = eachCourseWorkMark[i].split(EQUAL_SIGN);
                        courseWorkMarks.put(new SubComponent(thisCourseWorkMark[0],thisCourseWorkMark[1]),Double.parseDouble(thisCourseWorkMark[2]));
                    }
                    Double totalMark = Double.parseDouble(tokens[totalMarkIndex]);
                    Mark mark = new Mark(students.get(thisStudentIndex),courses.get(thisCourseIndex),examMark,courseWorkMarks,totalMark);
                    marks.add(mark);
                }
            }
        }catch(Exception e){
            System.out.println("Error occurs when loading student marks.");
            e.printStackTrace();
        }finally{
            try{
                fileReader.close();
            }catch(IOException e){
                System.out.println("Error occurs when closing the fileReader.");
                e.printStackTrace();
            }
        }
        return marks;
    }
}
