package com.student.searchroom.repository;

import com.student.searchroom.entity.CodeForgotPass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeForgotPassRepository extends JpaRepository<CodeForgotPass, String> {

}
