
package tutorial;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.Literal;
import org.openrdf.model.ValueFactory;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import test.AGRepositoryConnectionTest;

import com.franz.agraph.repository.AGCatalog;
import com.franz.agraph.repository.AGRepository;
import com.franz.agraph.repository.AGServer;

public class LikeJUnits {
		
	public static void test0() {
	    System.out.println("Hello World");
	}

	/**
	 * Tests getting the repository up.  Is called by the other tests to do the startup.
	 */
	public static Repository test1() throws RepositoryException {
        AGServer server = new AGServer(AGRepositoryConnectionTest.SERVER_URL, AGRepositoryConnectionTest.USERNAME, AGRepositoryConnectionTest.PASSWORD);
	    System.out.println("Available catalogs " + server.listCatalogs());
	    AGCatalog catalog = server.getCatalog(AGRepositoryConnectionTest.CATALOG_ID);
//	    System.out.println("Available repositories in catalog '" + catalog.getName() + "': " +
//	    		catalog.listRepositories());    
	    AGRepository myRepository = catalog.createRepository("agraph_test4");// AllegroRepository.RENEW);
	    myRepository.initialize();
	    System.out.println( "Repository " + myRepository.getRepositoryID() + " is up!  It contains "
	    		+ myRepository.getConnection().size() + " statements.");
	    return myRepository;
	}

	public static void test2() throws RepositoryException {
	    AGRepository myRepository = (AGRepository)test1();
	    RepositoryConnection conn = myRepository.getConnection();
	    ValueFactory f = myRepository.getValueFactory();
	    String exns = "http://example.org/people/";
	    conn.setNamespace("ex", exns);
	    
	    Literal lit = f.createLiteral(" some \"special\" literal");
//	    URI uri = factory.createURI("http://www.fooco/ex#foo");
//	    conn.addTriple(uri, uri, lit, false, null);
//	    rows = conn.getStatements(None, None, None);
//	    for r in rows:
//	        print "LIT", r[2].getLabel()
	    
	}
	
	

	public static void main(String[] args) throws Exception {
		List<Integer> choices = new ArrayList<Integer>();
		int lastChoice = 6;
		for (int i = 1; i <= lastChoice; i++)
			choices.add(new Integer(i));
		if (true) {
			choices = new ArrayList<Integer>();
			choices.add(11);
		}
		for (Integer choice : choices) {
			System.out.println("Running test " + choice);
			switch(choice) {
			case 0: test0(); break;
			case 1: test1(); break;
//			case 2: test2(); break;			
//			case 3: test3(); break;			
//			case 4: test4(); break;						
//			case 5: test5(); break;									
//			case 6: test6(); break;	
//			case 7: test7(); break;
//			case 8: test8(); break;			
//			case 9: test9(); break;	
//			case 10: test10(); break;
//			case 11: test11(); break;			
//			case 12: test12(); break;						
//			case 12: test13(); break;									
			default: System.out.println("There is no choice for test " + choice);
			}
		}
	}
}