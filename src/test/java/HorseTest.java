import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null,1 , 2));
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    public void blankNameException(String name) {
        IllegalArgumentException e  = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1 , 2));
        assertEquals("Name cannot be blank.", e.getMessage());

    }

    @Test
    public void nullMessage(){
        try {
            new Horse(null, 1, 2);
        }
        catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null." , e.getMessage());
        }
    }

    @Test
    public  void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("qwerty",1, 2);
        Field   name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals("qwerty", nameValue);
    }

    @Test
    public  void getSpeed() {
        Horse horse = new Horse("qwerty",1, 2);
        assertEquals(1, horse.getSpeed());
    }

    @Test
    public  void getDistance() {
        Horse horse = new Horse("qwerty",1, 2);
        assertEquals(2, horse.getDistance());
    }

    @Test
    public  void  zeroDistance(){
        Horse horse = new Horse("qwerty", 1);
        assertEquals(0,horse.getDistance());
    }

    @Test
    void  movesUsesGetRandom(){
        try(MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("qwerty", 1, 3).move();
            mockedStatic.verify(()-> Horse.getRandomDouble(0.2,0.9));
        }
    }

}
