package com.Lukas.demo.repository;

import com.Lukas.demo.model.AllRoles;
import com.Lukas.demo.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AllRolesRepository extends JpaRepository<AllRoles, Long>
{
    @Query("SELECT ur from AllRoles ur where ur.name=:name")
    public AllRoles getByName(@Param("name") String name);
}
