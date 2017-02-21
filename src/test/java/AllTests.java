import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	com.bac.application.impl.AllTests.class, 
	com.bac.persistence.accessor.AllTests.class, 
	com.bac.persistence.entity.AllTests.class , 
	com.bac.application.predicates.AllTests.class 
})
public class AllTests {

}
