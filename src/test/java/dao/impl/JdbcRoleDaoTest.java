package dao.impl;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import entity.Role;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
@DBUnit(url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "sa")
public class JdbcRoleDaoTest {

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    private static JdbcRoleDao jdbcRoleDao;

    @BeforeClass
    public static void initDao() {
        jdbcRoleDao = new JdbcRoleDao();
    }

    @Test
    @DataSet(value = "datasets/roles.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    @ExpectedDataSet(value = "expected/expectedCreateRoles.yml", ignoreCols = {"id"})
    public void createRoleTest() {
        Role role = new Role("newRole");
        jdbcRoleDao.create(role);
    }

    @Test
    @DataSet(value = "datasets/roles.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    @ExpectedDataSet(value = "expected/expectedUpdateRoles.yml", ignoreCols = {"id"})
    public void updateRoleTest() {
        Role role = new Role("newRole");
        role.setId(2L);
        jdbcRoleDao.update(role);
    }

    @Test
    @DataSet(value = "datasets/roles.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    @ExpectedDataSet(value = "expected/expectedRemoveRoles.yml", ignoreCols = {"id"})
    public void removeRoleTest() {
        Role role = new Role("user");
        role.setId(2L);
        jdbcRoleDao.remove(role);
    }

    @Test
    @DataSet(value = "datasets/roles.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    public void findAllRolesTest() {
        List<Role> all = jdbcRoleDao.findAll();
        Assert.assertEquals(2, all.size());
    }

    @Test
    @DataSet(value = "datasets/roles.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    public void findByNameRoleTest() {
        Role expectedRole = new Role("user");
        expectedRole.setId(2L);
        Role byName = jdbcRoleDao.findByName("user");

        Assert.assertEquals(expectedRole, byName);
    }

    @Test
    @DataSet(value = "datasets/roles.yml", executeScriptsBefore = "scripts/dataBaseInit.sql", executeScriptsAfter = "scripts/dataBaseDrop.sql")
    public void findByIdRoleTest() {
        Role expectedRole = new Role("user");
        expectedRole.setId(2L);
        Role byId = jdbcRoleDao.findById(2L);

        Assert.assertEquals(expectedRole, byId);
    }
}
