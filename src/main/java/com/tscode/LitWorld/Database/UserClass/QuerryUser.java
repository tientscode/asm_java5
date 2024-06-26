package com.tscode.LitWorld.Database.UserClass;


import com.tscode.LitWorld.Database.RoleClass.RoleClass;
import com.tscode.LitWorld.Database.RoleClass.RoleRepository;
import com.tscode.LitWorld.Dto.SignUpDto;
import com.tscode.LitWorld.Dto.UserClassDto;
import com.tscode.LitWorld.exception.DuplicateUsernameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuerryUser implements khaibaohamUser {

    @Autowired
    private UserRepository storyHubsotry;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserClass adduser(SignUpDto signUpDto) {
        if (storyHubsotry.existsByaccount(signUpDto.getAccount())) {
            throw new DuplicateUsernameException("Tài khoản đã tồn tại");
        }
        UserClass userclass = new UserClass();
        userclass.setAccount(signUpDto.getAccount());
        userclass.setPassword(signUpDto.getPassword());
        userclass.setEmail(signUpDto.getEmail());
        userclass.setName(signUpDto.getName());
        userclass.setActive(true);
        RoleClass defaultRole = roleRepository.findByName("Role_User");
        userclass.setRoles(Collections.singleton(defaultRole));


        return storyHubsotry.save(userclass);
    }


    @Override
    public UserClass upClassUser(Integer Id, UserClass UserClass) {
        if (UserClass != null) {
            UserClass userClass1 = storyHubsotry.getById(Id);
            if (userClass1 != null) {
                userClass1.setName(UserClass.getName());
//                userClass1.setImage(UserClass.getImage());
                userClass1.setEmail(UserClass.getEmail());
                return storyHubsotry.save(userClass1);
            }
        }
        return null;
    }

    @Override
    public boolean deletteClassUser(Integer Id) {
        if (Id >= 0) {
            UserClass UserClass = storyHubsotry.getById(Id);
            if (UserClass != null) {
                storyHubsotry.delete(UserClass);
                return true;
            }

        }
        return false;
    }


    @Override
    public List<UserClass> getClassUsers() {
        return storyHubsotry.findAll();
    }

    @Override
    public UserClassDto getoneClassUser(Integer Id) {
        if (Id != null) {
            UserClass userClass1 = storyHubsotry.getById(Id);
            if (userClass1 != null) {
                return new UserClassDto(userClass1.getName(), userClass1.getAccount(), userClass1.getPassword());
            }
        }
        return null;
    }

    @Override
    public UserClass findByAccount(String account) {
        return storyHubsotry.findByAccount(account);
    }
    @Override
    public UserClass findByEmail(String email) {
        return storyHubsotry.findByAccount(email);
    }
}
