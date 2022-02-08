package dao.impl;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import entity.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Date;
import java.util.List;

@RunWith(JUnit4.class)
@DBUnit(url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "sa")
public class JdbcUserDaoTest {

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    private static JdbcUserDao jdbcUserDao;

    @BeforeClass
    public static void initDao() {
        jdbcUserDao = new JdbcUserDao();
    }

    @Test
    @DataSet(value = "datasets/users.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    @ExpectedDataSet(value = "expected/expectedCreateUsers.yml", ignoreCols = {"id"})
    public void createUserTest() {
        User user = new User("login", "password", "email", "firstname",
                "lastname", Date.valueOf("2001-01-03"), 2L);
        jdbcUserDao.create(user);
    }

    @Test
    @DataSet(value = "datasets/users.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    @ExpectedDataSet(value = "expected/expectedUpdateUsers.yml", ignoreCols = {"id"})
    public void updateUserTest() {
        User user = new User("Vitaliy", "1234", "Vitaliy@email", "Vitaliy",
                "Pavlov", Date.valueOf("2001-01-02"), 2L);
        user.setId(2L);
        user.setLogin("login");
        user.setPassword("password");
        user.setEmail("email");
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setBirthday(Date.valueOf("2001-01-03"));

        jdbcUserDao.update(user);
    }

    @Test
    @DataSet(value = "datasets/users.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    @ExpectedDataSet(value = "expected/expectedRemoveUsers.yml", ignoreCols = {"id"})
    public void removeUserTest() {
        User user = new User("Vitaliy", "1234", "Vitaliy@email", "Vitaliy",
                "Pavlov", Date.valueOf("2001-01-02"), 2L);
        user.setId(2L);
        jdbcUserDao.remove(user);
    }

    @Test
    @DataSet(value = "datasets/users.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    public void findAllUsersTest() {
        List<User> all = jdbcUserDao.findAll();
        Assert.assertEquals(2, all.size());
    }

    @Test
    @DataSet(value = "datasets/users.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    public void findByLoginUserTest() {
        User expectedUser = new User("Vitaliy", "1234", "Vitaliy@email",
                "Vitaliy", "Pavlov", Date.valueOf("2001-01-02"), 2L);
        expectedUser.setId(2L);
        User byLogin = jdbcUserDao.findByLogin("Vitaliy");

        Assert.assertEquals(expectedUser, byLogin);
    }

    @Test
    @DataSet(value = "datasets/users.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    public void findByEmailUserTest() {
        User expectedUser = new User("Vitaliy", "1234", "Vitaliy@email",
                "Vitaliy", "Pavlov", Date.valueOf("2001-01-02"), 2L);
        expectedUser.setId(2L);
        User byEmail = jdbcUserDao.findByEmail("Vitaliy@email");

        Assert.assertEquals(expectedUser, byEmail);
    }

    @Test
    @DataSet(value = "datasets/users.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    public void findByIdUserTest() {
        User expectedUser = new User("Vitaliy", "1234", "Vitaliy@email",
                "Vitaliy", "Pavlov", Date.valueOf("2001-01-02"), 2L);
        expectedUser.setId(2L);
        User byId = jdbcUserDao.findById(2L);

        Assert.assertEquals(expectedUser, byId);
    }
}
