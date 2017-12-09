import org.h2.tools.RunScript
import org.hibernate.Session
import org.hibernate.jdbc.Work
import org.junit.AfterClass
import org.junit.BeforeClass

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import java.sql.Connection
import java.sql.SQLException

class JpaHibernateTest {

    static EntityManagerFactory emf
    static EntityManager em

    @BeforeClass
    static void init() {
        emf = Persistence.createEntityManagerFactory("groovyPU")
        em = emf.createEntityManager()
    }

    void initializerDatabase() {
        Session session = em.unwrap(Session.class)
        session.doWork(new Work() {
            @Override
            void execute(Connection connection) throws SQLException {
                try {
                    File script = new File(getClass().getResource("/data.sql").getFile())
                    RunScript.execute(connection, new FileReader(script))
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex)
                }
            }
        })
    }

    @AfterClass
    static void tearDown() {
        em.clear()
        em.close()
        emf.close()
    }
}
