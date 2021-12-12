package com.example.demo.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudent(){
        return studentRepository.findAll();
    }

    public void addStudent(Student s) {
        Optional<Student> sMail = studentRepository.findStudentByEmail(s.getEmail());
        if( sMail.isPresent() ){
            throw new IllegalStateException("mail already taken");
        }

        studentRepository.save(s);
    }

    public void deleteStudent(Long id) {
        if ( studentRepository.existsById(id) )
        {
            studentRepository.deleteById(id);
        }else{
            throw new IllegalStateException("student doesnt existe !");
        }
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
//        Optional<Student> std = studentRepository.findById(id);
//        System.out.println("Optional var = " + std);
        Student mystd = studentRepository.findStudentById(id);
//        System.out.println("mystd = " + mystd);
        if (studentRepository.existsById(id))
        {
            if (name != null && name.length() > 0){
                mystd.setName(name);
            }
            if (email != null && email.length() > 0){
                Optional<Student> sMail = studentRepository.findStudentByEmail(mystd.getEmail());
                if( sMail.isPresent() ){
                    throw new IllegalStateException("mail already taken");
                }
                mystd.setEmail(email);
            }
        }else
        {
            throw new IllegalStateException("Student Doesnt Existe !");
        }
    }
}
