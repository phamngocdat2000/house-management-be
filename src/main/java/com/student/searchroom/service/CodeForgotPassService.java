package com.student.searchroom.service;

import com.student.searchroom.entity.CodeForgotPass;
import com.student.searchroom.exception.SearchRoomException;
import com.student.searchroom.model.error.ErrorCode;
import com.student.searchroom.repository.CodeForgotPassRepository;
import com.student.searchroom.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeForgotPassService {
    @Autowired
    private CodeForgotPassRepository codeForgotPassRepository;

    public CodeForgotPass generateCode(String username) {
        Long currentTime = System.currentTimeMillis();
        Long expireTime = currentTime + 300000L;
        String code = Utils.getAlphaNumericString(10);
        CodeForgotPass codeForgotPass = new CodeForgotPass();
        codeForgotPass.setCode(code);
        codeForgotPass.setExpireDate(expireTime);
        codeForgotPass.setUsername(username);
        return codeForgotPassRepository.save(codeForgotPass);
    }

    public void validateCode(String username, String code) {
        CodeForgotPass codeForgotPass = codeForgotPassRepository.findById(username).orElseThrow(() ->
                new SearchRoomException(ErrorCode.CODE_INVALID));
        if (codeForgotPass.getExpireDate() < System.currentTimeMillis())
            throw new SearchRoomException(ErrorCode.CODE_IS_EXPIRE);
        if (!code.equals(codeForgotPass.getCode()))
            throw new SearchRoomException(ErrorCode.CODE_INVALID);
        codeForgotPassRepository.delete(codeForgotPass);
    }
}
