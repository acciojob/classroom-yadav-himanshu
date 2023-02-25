package com.driver;

import jdk.jshell.Diag;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class StudentRepository {
    private HashMap<String, Student> studentHashMap;
    private HashMap<String, Teacher> teacherHashMap;
    private HashMap<String, List<String>> teacherStudentMap;

    public StudentRepository(HashMap<String, Student> studentHashMap, HashMap<String, Teacher> teacherHashMap, HashMap<String, List<String>> teacherStudentMap) {
        this.studentHashMap = studentHashMap;
        this.teacherHashMap = teacherHashMap;
        this.teacherStudentMap = teacherStudentMap;
    }

    public void saveStudent(Student student){
        studentHashMap.put(student.getName(), student);
    }
    public void saveTeacher(Teacher teacher){
        teacherHashMap.put(teacher.getName(), teacher);
    }
    public void saveStudentTeacherPair(String student, String teacher){
        if(studentHashMap.containsKey(student) && teacherHashMap.containsKey(teacher)){
            List<String> currentStudent = new ArrayList<>();
            if(teacherStudentMap.containsKey(teacher)){
                currentStudent = teacherStudentMap.get(teacher);
            }
            currentStudent.add(student);
            teacherStudentMap.put(teacher, currentStudent);
        }
    }

    public Student findStudent(String student){ return studentHashMap.get(student);}

    public Teacher findTeacher(String teacher){ return teacherHashMap.get(teacher);}

    public List<String> findStudentfromTeacher(String teacher){
        List<String> studentList = new ArrayList<>();
        if(teacherStudentMap.containsKey(teacher)){
            studentList = teacherStudentMap.get(teacher);
        }
        return studentList;
    }

    public List<String> findAllStudents(){
        return new ArrayList<>(studentHashMap.keySet());
    }

    public void deleteTeacher(String teacher){
        List<String> students =new ArrayList<>();
        if(teacherStudentMap.containsKey(teacher)){
            students =teacherStudentMap.get(teacher);
            for(String student : students){
                if(studentHashMap.containsKey(student)){
                    studentHashMap.remove(student);
                }
            }
            teacherStudentMap.remove(teacher);
        }
        if(teacherHashMap.containsKey(teacher))
            teacherHashMap.remove(teacher);
    }

    public void deleteAllteachers(){
        HashSet<String> studentSet = new HashSet<>();

        for(String teacher : teacherStudentMap.keySet()){
            for(String student : teacherStudentMap.get(teacher)){
                studentSet.add(student);
            }
        }

        for(String student : studentSet){
            if(studentHashMap.containsKey(student)){
                studentHashMap.remove(student);
            }
        }
    }
}