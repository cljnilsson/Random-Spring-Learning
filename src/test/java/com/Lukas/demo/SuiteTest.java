package com.Lukas.demo;

//import com.Lukas.demo.model.AllRoles;
//import com.Lukas.demo.repository.AllRolesRepository;

import com.Lukas.demo.model.AllRoles;
import com.Lukas.demo.repository.AllRolesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(
        classes = { AllRolesRepository.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class SuiteTest
{
    @Resource
    AllRolesRepository roles;

    @Test
    void testStuff() {
        AllRoles found = roles.getByName("whitelist");
        assertNotNull(found);
        //assertEquals("", "");
    }
}
