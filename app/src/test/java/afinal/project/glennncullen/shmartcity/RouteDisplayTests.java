package afinal.project.glennncullen.shmartcity;


import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;


public class RouteDisplayTests {

    @Mock
    private MainActivity main = new MainActivity();

    @Test
    public void testMainActivityIsNotNull(){
        assertNotNull(main);
    }

    @Test
    public void testNextDirection(){
        assertTrue(main.getNextDirection('N', 'N').equals("straight"));
    }
}