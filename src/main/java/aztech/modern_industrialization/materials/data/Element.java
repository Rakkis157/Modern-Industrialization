package aztech.modern_industrialization.materials.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Element {

    public static final Logger LOGGER = LogManager.getLogger("Excessive_Realism");

    private int atomicNumber;
    private String symbol;
    private String name;
    private double meltPoint;
    private double boilPoint;

    public Element(int atomicNumber, String symbol, String name, double meltPoint, double boilPoint){
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
        this.name = name;
        this.meltPoint = meltPoint;
        this.boilPoint = boilPoint;
    }

    public void init(){

        //List of Metals. Temperatures in Kelvin.
        Element HYDROGEN = new Element(1, "H", "hydrogen", 14.01, 20.28);
        Element HELIUM = new Element(2, "He", "helium", 0, 4.22);
        Element IRON = new Element(26, "Fe", "iron", 1811, 3135);



        LOGGER.info("Elements initialized.");
    }
}
