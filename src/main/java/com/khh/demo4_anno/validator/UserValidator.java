package com.khh.demo4_anno.validator;

import com.khh.entity.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by 951087952@qq.com on 2017/8/25.
 */
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {

        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User)o;
        Integer id = user.getId();
        if(id < 0){
            errors.rejectValue("id","id < 0");
        }
    }
}
