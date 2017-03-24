package me.smorenburg.jira.dummy;

import android.util.Log;

import me.smorenburg.jira.db.core.DatabaseManager;
import me.smorenburg.jira.db.models.base.Authority;
import me.smorenburg.jira.db.models.base.Skill;
import me.smorenburg.jira.db.models.base.Task;
import me.smorenburg.jira.db.models.base.User;
import me.smorenburg.jira.db.models.base.User_Authorities;
import me.smorenburg.jira.db.models.base.User_Skills;
import me.smorenburg.jira.db.models.base.WebServiceData;

/**
 * Created by MR x on 22/03/2017.
 */

public class Dummy {

    private DatabaseManager databaseManager;

    public Dummy(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void loadAuthorities() {

        Authority authorityAdmin = new Authority();
        authorityAdmin.setId(1L);
        authorityAdmin.setAuthority_name("ROLE_ADMIN");
        databaseManager.save(authorityAdmin);

        Authority authority = new Authority();
        authority.setId(2L);
        authority.setAuthority_name("ROLE_TEC");
        databaseManager.save(authority);

        User_Authorities authority_user_roles;

        authority_user_roles = new User_Authorities();
        authority_user_roles.setId(1L);
        authority_user_roles.setAuthority(authorityAdmin);
        authority_user_roles.setUser_id(1L);
        databaseManager.save(authority_user_roles);

        authority_user_roles = new User_Authorities();
        authority_user_roles.setId(2L);
        authority_user_roles.setAuthority(authority);
        authority_user_roles.setUser_id(2L);
        databaseManager.save(authority_user_roles);

        authority_user_roles = new User_Authorities();
        authority_user_roles.setId(3L);
        authority_user_roles.setAuthority(authority);
        authority_user_roles.setUser_id(3L);
        databaseManager.save(authority_user_roles);
    }

    public void loadDefaultUsers() {
        User user;

        user = new User();
        user.setId(1L);
        user.setName("Rubén");
        user.setLname("Garcia");
        user.setUsername("admin");
        user.setPassword("password");
        databaseManager.save(user);

        user = new User();
        user.setId(2L);
        user.setName("Rafael");
        user.setLname("Martin");
        user.setUsername("user");
        user.setPassword("password");
        databaseManager.save(user);

        user = new User();
        user.setId(3L);
        user.setName("Sarah");
        user.setLname("Lopez");
        user.setUsername("user1");
        user.setPassword("password");
        databaseManager.save(user);
    }

    public void loadDefaultTasks() {
        Task task;

        task = new Task();
        task.setTitle("Reponer Pasillo 2");
        task.setId(1L);
        task.setSkill_id(1L);
        task.setTaskDuration((11L) * 60 * 60 * 1000);
        task.setUser_id(2L);
        databaseManager.save(task);

        task = new Task();
        task.setTitle("Cobrar Clienta Caja 4");
        task.setId(2L);
        task.setSkill_id(2L);
        task.setTaskDuration((3L) * 60 * 60 * 1000);
        task.setUser_id(3L);
        databaseManager.save(task);


    }

    public void loadDefaultSkills() {
        Skill skill;

        skill = new Skill();
        skill.setId(1L);
        skill.setTittle("Reponedor de productos");
        databaseManager.save(skill);

        skill = new Skill();
        skill.setId(2L);
        skill.setTittle("Cobrar");
        databaseManager.save(skill);

        skill = new Skill();
        skill.setId(3L);
        skill.setTittle("Envolver");
        databaseManager.save(skill);

        skill = new Skill();
        skill.setId(4L);
        skill.setTittle("Etcétera");
        databaseManager.save(skill);

        User_Skills user_skills;

        user_skills = new User_Skills();
        user_skills.setId(1L);
        user_skills.setSkill_id(2L);
        user_skills.setUser_id(2L);
        databaseManager.save(user_skills);

        user_skills = new User_Skills();
        user_skills.setId(2L);
        user_skills.setSkill_id(1L);
        user_skills.setUser_id(3L);
        databaseManager.save(user_skills);

        user_skills = new User_Skills();
        user_skills.setId(3L);
        user_skills.setSkill_id(3L);
        user_skills.setUser_id(3L);
        databaseManager.save(user_skills);

        user_skills = new User_Skills();
        user_skills.setId(4L);
        user_skills.setSkill_id(4L);
        user_skills.setUser_id(2L);
        databaseManager.save(user_skills);

        user_skills = new User_Skills();
        user_skills.setId(5L);
        user_skills.setSkill_id(4L);
        user_skills.setUser_id(3L);
        databaseManager.save(user_skills);
    }

    public static String printTest() {
        StringBuilder text_view = new StringBuilder();
//        for (User u : DatabaseManager.getInstance().findAll(User.class))
//            text_view.append("\nUser: ")
//                    .append(" Username: ").append(u.getUsername())
//                    .append(" Pass: ").append(u.getPassword())
//                    .append(" Rol: ").append(u.getUser_authorities())
//                    .append(" Tasks: ").append(u.getTasks())
//                    .append(" Skill: ").append(u.getUser_skillses());
//        text_view.append("\n");
//
//        for (Skill u : DatabaseManager.getInstance().findAll(Skill.class))
//            text_view.append("\nSkill: ").append(u.getTittle());
//        text_view.append("\n");
//
//        for (Authority u : DatabaseManager.getInstance().findAll(Authority.class))
//            text_view.append("\nAuthority: ").append(u.getAuthority_name());
//        text_view.append("\n");
//
//        for (User_Authorities u : DatabaseManager.getInstance().findAll(User_Authorities.class))
//            text_view.append("\nUser_Authorities: " )
//                    .append( " Role: ").append(String.valueOf(u.getAuthority().getAuthority_name()))
//                    .append(" User: ").append(u.getUser().getUsername());
//        text_view.append("\n");
//
//        for (User_Skills u : DatabaseManager.getInstance().findAll(User_Skills.class))
//            text_view.append("\nUser_Skills: ")
//                    .append(" Skill: ").append(u.getSkill())
//                    .append(" User: ").append(u.getUser());
//        text_view.append("\n");
//
//        for (Task u : DatabaseManager.getInstance().findAll(Task.class))
//            text_view.append("\nTask: ")
//                    .append(" Skill: ").append(u.getSkill())
//                    .append(" Assign: ").append(u.getUser())
//                    .append(" Duration: ").append(u.getTaskDuration());

        for (WebServiceData u : DatabaseManager.getInstance().findAll(WebServiceData.class))
            text_view.append("\nWebServiceData: ")
                    .append(" Category: ").append(u.getCategory())
                    .append(" Id: ").append(u.getId())
                    .append(" Location_1_Id: ").append(u.getLocation_1Id())
                    .append(" Item: ").append(u.getItem());
        text_view.append("\n");

//        for (Location_1 u : DatabaseManager.getInstance().findAll(Location_1.class))
//            text_view.append("\nLocation_1: ")
//                    .append(" Id: ").append(u.getId())
//                    .append(" WebServiceDataId: ").append(u.getWebServiceDataId())
//                    .append(" HHAddress: ").append(u.getHuman_address());
//        text_view.append("\n");

        Log.d("DUMMY", text_view.toString());

        return text_view.toString();
    }
}
