package com.student.searchroom.model.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.searchroom.entity.VerifyUser;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Log4j2
public class VerifyUserRequest {
    private List<String> imagesUrl;
    private String description;

    public VerifyUser toVerifyUser(String username) {
        ObjectMapper mapper = new ObjectMapper();
        VerifyUser result = new VerifyUser();
        result.setDescription(this.description);
        result.setUsername(username);
        try {
            result.setImagesUrl(mapper.writeValueAsString(this.imagesUrl));
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        result.setCreatedDate(new Date());
        result.setLastModifiedDate(new Date());
        result.setStatus(VerifyUser.Status.PENDING);
        return result;
    }

    public VerifyUser update(VerifyUser verifyUser) {
        if (this.description != null)
            verifyUser.setDescription(this.description);
        if (this.imagesUrl != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                verifyUser.setImagesUrl(mapper.writeValueAsString(this.imagesUrl));
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        }
        verifyUser.setLastModifiedDate(new Date());
        verifyUser.setStatus(VerifyUser.Status.PENDING);
        return verifyUser;
    }
}
